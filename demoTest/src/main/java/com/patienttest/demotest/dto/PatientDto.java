package com.patienttest.demotest.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PatientDto implements Serializable {

    private UUID id;
    private String patient_id;
    private  String given;
    private String family;
    private String suffix;
    private String prefix;
    private  String gender;
    private  String birthDay;
    private  String full_url;
    private  Date created_at;

    public PatientDto(){}


    public PatientDto(UUID id, String patient_id, String family, String given, String prefix, String suffix, String gender, String birthDay, String full_url, Date created_at) {
        this.id = id;
        this.patient_id = patient_id;
        this.family = family;
        this.given = given;
        this.prefix = prefix;
        this.suffix = suffix;
        this.gender = gender;
        this.birthDay = birthDay;
        this.full_url = full_url;
        this.created_at = created_at;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(String patient_id) {
        this.patient_id = patient_id;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String given) {
        this.given = given;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getFull_url() {
        return full_url;
    }

    public void setFull_url(String full_url) {
        this.full_url = full_url;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PatientDto entity = (PatientDto) o;
        return  Objects.equals(this.id, entity.id) &&
                Objects.equals(this.family, entity.family) &&
                Objects.equals(this.patient_id, entity.patient_id) &&
                Objects.equals(this.given, entity.given) &&
                Objects.equals(this.suffix, entity.suffix) &&
                Objects.equals(this.prefix, entity.prefix) &&
                Objects.equals(this.gender, entity.gender) &&
                Objects.equals(this.birthDay, entity.birthDay) &&
                Objects.equals(this.full_url, entity.full_url) &&
                Objects.equals(this.created_at, entity.created_at);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, family, patient_id, given, prefix, suffix, gender, birthDay, full_url, created_at);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", "+
                "family = " + family + ", "+
                "patient_id = " + patient_id + ", "+
                "given = " + given + ", "+
                "prefix = " + prefix + ", "+
                "suffix = " + suffix + ", "+
                "gender = " + gender + ", " +
                "birthday = " + birthDay + ", " +
                "full_url = " + full_url + ", " +
                "created_at = " + created_at + ")";
    }

}
