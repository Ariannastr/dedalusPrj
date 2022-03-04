package com.patienttest.demotest.repository;

import com.patienttest.demotest.model.Patient;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Stateless
public class PatientRepository {

    @PersistenceContext(unitName = "patient_pu")
    EntityManager em;

    public Patient findById(Long id) {
        return em.find(Patient.class, id);
    }

    public List getAllPatients(){
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Patient> criteria = builder.createQuery(Patient.class);
        Root<Patient> from = criteria.from(Patient.class);
        criteria.select(from);

        TypedQuery<Patient> typed = em.createQuery(criteria);
        try {
            return typed.getResultList();
        } catch (final NoResultException nre) {
            return null;
        }
    }

    public Patient findByPatientId(String patient_id){

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Patient> criteria = builder.createQuery(Patient.class);
        Root<Patient> from = criteria.from(Patient.class);
        criteria.select(from);

        criteria.where(builder.equal(from.get("patient_id"), patient_id));
        TypedQuery<Patient> typed = em.createQuery(criteria);
        try {
            return typed.getSingleResult();
        } catch (final NoResultException nre) {
            return null;
        }
    }

    public void create(Patient patient) {
        em.persist(patient);
        em.flush();
    }

    public void update(Patient patient) {
        em.merge(patient);
    }

    public void delete(Patient patient) {
        if (!em.contains(patient)) {
            patient = em.merge(patient);
        }

        em.remove(patient);
        em.flush();
    }
}
