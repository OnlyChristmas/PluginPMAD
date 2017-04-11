package cn.edu.pku.sei.tsr.dbinsight.persistence.jpa.repository;

import cn.edu.pku.sei.tsr.dbinsight.persistence.jpa.entity.AppleEntity;
import cn.edu.pku.sei.tsr.dbinsight.essentials.api.repository.AppleRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Created by Icing on 2017/2/26.
 */
@Repository
@ConditionalOnProperty(name = "dbinsight.persistence.type", havingValue = "jpa")
public interface AppleEntityRepository extends AppleRepository<AppleEntity>, JpaRepository<AppleEntity, String> {
    default AppleEntity newOne() {
        return new AppleEntity().setId(UUID.randomUUID().toString());
    }
}
