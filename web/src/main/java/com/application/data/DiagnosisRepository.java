package com.application.data;

import com.application.data.internal.ReadOnlyRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource(path = "diagnosis")
@Repository
public interface DiagnosisRepository extends ReadOnlyRepository<Diagnosis, String> {
}
