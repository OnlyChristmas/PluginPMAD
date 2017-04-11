package cn.edu.pku.sei.tsr.dbinsight.web.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Created by Icing on 2017/2/28.
 */
@Configuration
public class JacksonConfig {

    public static class CustomModule extends SimpleModule {
        @Override
        public CustomModule setNamingStrategy(PropertyNamingStrategy naming) {
            super.setNamingStrategy(naming);
            return this;
        }
    }

//    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

//    @PostConstruct
    public void injectCustomObjectMapper() {

        List<HttpMessageConverter<?>> converters = requestMappingHandlerAdapter.getMessageConverters();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter messageConverter = (MappingJackson2HttpMessageConverter) converter;
                if (objectMapper == null)
                    objectMapper = messageConverter.getObjectMapper();
                else
                    messageConverter.setObjectMapper(objectMapper);
            }
        }

    }
//    @Bean
//    public Module module() {
//        return new CustomModule().setNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
//    }

}
