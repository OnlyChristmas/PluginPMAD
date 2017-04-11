package cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.Resource;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.Instancer;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Icing on 2017/2/27.
 */
@NoRepositoryBean
public interface ResourceRepository<T extends Resource> extends PagingAndSortingRepository<T, String>, Instancer<T> {
    T newOne();
}
