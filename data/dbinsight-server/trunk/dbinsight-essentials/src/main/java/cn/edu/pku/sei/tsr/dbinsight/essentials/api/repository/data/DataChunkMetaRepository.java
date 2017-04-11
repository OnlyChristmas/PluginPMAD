package cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.data;

import cn.edu.pku.sei.tsr.dbinsight.commons.resource.data.DataChunkMeta;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.ResourceRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by Icing on 2017/2/27.
 */
@NoRepositoryBean
public interface DataChunkMetaRepository<T extends DataChunkMeta> extends ResourceRepository<T> {
    @Override
    T newOne();

    @Override
    Iterable<T> findAll(Sort sort);

    @Override
    Page<T> findAll(Pageable pageable);

    @Override
    <S extends T> S save(S s);

    @Override
    <S extends T> Iterable<S> save(Iterable<S> iterable);

    @Override
    T findOne(String s);

    @Override
    boolean exists(String s);

    @Override
    Iterable<T> findAll();

    @Override
    Iterable<T> findAll(Iterable<String> iterable);

    @Override
    long count();

    @Override
    void delete(String s);

    @Override
    void delete(T t);

    @Override
    void delete(Iterable<? extends T> iterable);

    @Override
    void deleteAll();
}
