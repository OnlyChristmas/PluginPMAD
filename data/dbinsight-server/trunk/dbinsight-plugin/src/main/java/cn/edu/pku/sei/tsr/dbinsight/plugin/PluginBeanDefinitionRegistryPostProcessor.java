package cn.edu.pku.sei.tsr.dbinsight.plugin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import ro.fortsoft.pf4j.DefaultPluginManager;
import ro.fortsoft.pf4j.PluginManager;
import ro.fortsoft.pf4j.PluginWrapper;
import ro.fortsoft.pf4j.spring.ExtensionsInjector;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Shawn on 2017/2/25.
 */
@Component
public class PluginBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {
    private static final Logger log = LoggerFactory.getLogger(PluginBeanDefinitionRegistryPostProcessor.class);
    private static PluginManager pluginManager = null;

    private PluginManager pluginManager() {
        if (pluginManager == null) {
            synchronized (PluginManager.class) {
                if (pluginManager == null) {
                    pluginManager = new DefaultPluginManager();
                    log.info("Plugin path: " + System.getProperty("pf4j.pluginsDir", "plugins"));
                    pluginManager.loadPlugins();
                    pluginManager.startPlugins();
                }
            }
        }
        return pluginManager;
    }

    public static ExtensionsInjector extensionsInjector() {
        return new ExtensionsInjector();
    }

    private ScopeMetadataResolver scopeMetadataResolver = new AnnotationScopeMetadataResolver();
    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();
    private ConfigurationClassPostProcessor postProcessor = new ConfigurationClassPostProcessor();
    private Environment environment;

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        PluginManager pluginManager = pluginManager();
        List plugins = pluginManager.getStartedPlugins();
        Iterator pluginItr = plugins.iterator();

        List<BeanDefinitionRegistry> pluginBeanDefinitionRegistries = new ArrayList<>();

        postProcessor.setEnvironment(environment);

        while(pluginItr.hasNext()) {
            PluginWrapper plugin = (PluginWrapper) pluginItr.next();
            Set extensionClassNames = pluginManager.getExtensionClassNames(plugin.getPluginId());
            Iterator extensionClassNameItr = extensionClassNames.iterator();

            BeanDefinitionRegistry pluginRegistry = new SimpleBeanDefinitionRegistry();
            ClassLoader pluginClassLoader = plugin.getPluginClassLoader();

            while(extensionClassNameItr.hasNext()) {
                String extensionClassName = (String) extensionClassNameItr.next();
                try {
                    Class clazz = pluginClassLoader.loadClass(extensionClassName);
                    registerBean(pluginRegistry, clazz.getSimpleName(), clazz);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            postProcessor.setBeanClassLoader(pluginClassLoader);
            postProcessor.postProcessBeanDefinitionRegistry(pluginRegistry);
            pluginBeanDefinitionRegistries.add(pluginRegistry);
        }

        for (int i = 0; i < pluginBeanDefinitionRegistries.size(); i++) {
            BeanDefinitionRegistry pluginBeanDefinitionRegistry = pluginBeanDefinitionRegistries.get(i);
            PluginWrapper plugin = (PluginWrapper) plugins.get(i);
            String[] beanDefinitionNames = pluginBeanDefinitionRegistry.getBeanDefinitionNames();
            for (String beanDefinitionName : beanDefinitionNames) {
                if(!beanDefinitionRegistry.containsBeanDefinition(beanDefinitionName)) {
                    BeanDefinition beanDefinition = pluginBeanDefinitionRegistry.getBeanDefinition(beanDefinitionName);

                    if (beanDefinition.getBeanClassName() == null) {
                        beanDefinitionRegistry.registerBeanDefinition(beanDefinitionName, beanDefinition);
                        continue;
                    }

                    ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
                    ClassLoader pluginClassLoader = plugin.getPluginClassLoader();
                    try {
                        defaultClassLoader.loadClass(beanDefinition.getBeanClassName());
                        beanDefinitionRegistry.registerBeanDefinition(beanDefinitionName, beanDefinition);
                    } catch (ClassNotFoundException e) {
                        try {
                            Class clazz = pluginClassLoader.loadClass(beanDefinition.getBeanClassName());
                            registerBean(beanDefinitionRegistry, beanDefinitionName, clazz);
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                            throw new RuntimeException(e1);
                        }
                    }
                }
            }
        }
    }

    private void registerBean(BeanDefinitionRegistry registry, String name, Class<?> beanClass){
        AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);

        ScopeMetadata scopeMetadata = this.scopeMetadataResolver.resolveScopeMetadata(abd);
        abd.setScope(scopeMetadata.getScopeName());

        String beanName = (name != null ? name : this.beanNameGenerator.generateBeanName(abd, registry));

        AnnotationConfigUtils.processCommonDefinitionAnnotations(abd);

        BeanDefinitionHolder definitionHolder = new BeanDefinitionHolder(abd, beanName);
        BeanDefinitionReaderUtils.registerBeanDefinition(definitionHolder, registry);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.registerSingleton(PluginManager.class.getName(), pluginManager());
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
