package com.application.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "diagnosis_copy")
public class Diagnosis {

    @Id
    @Column(name = "patient_sn")
    private String id;

    @Column
    private String diseaseName;

    public String getPatientSn() {
        return id;
    }

    public String getDiseaseName() {
        return diseaseName;
    }
}
