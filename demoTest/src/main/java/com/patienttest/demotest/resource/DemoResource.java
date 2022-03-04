package com.patienttest.demotest.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patienttest.demotest.dto.PatientDto;
import com.patienttest.demotest.dto.TransferRequest;
import com.patienttest.demotest.model.Patient;
import com.patienttest.demotest.repository.PatientRepository;
import com.patienttest.demotest.service.PatientService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Map;

@Path("/patient")
public class DemoResource {

    @Inject
    private PatientRepository patientRepository;

    private PatientService patientService = new PatientService();


    @GET
    @Path("/createPatient")
    public Response createPatient(){
        String result = patientService.createPatient();
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map createdPatient = mapper.readValue(result, Map.class);
            String fullUrl = "http://hapi.fhir.org/baseR4/Patient/"+ createdPatient.get("id");
            String patientR = patientService.getPatient(fullUrl);
            return Response.ok(patientR, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity("Error in creating patient").build();
        }
    }

    @GET
    @Path("/getAllPatients")
    public Response getAllPatients(){
        try {
            List patients = patientService.getAllPatients( patientRepository);
            if(patients == null){
                return Response.status(Response.Status.NOT_FOUND).entity("No patients present").build();
            }
            return Response.ok(patients, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity("Error in retrieving patients").build();
        }
    }

    @GET
    @Path("/getPatient/{id}")
    public Response getPatient(@PathParam("id") String id){
        try {
            Patient patient = patientService.getPatient(id, patientRepository);
            if(patient == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Patient not present in DB").build();
            }
            PatientDto result = patientService.fromPatientToPatientDTO(patient);
            return Response.ok(result, MediaType.APPLICATION_JSON).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity("Error in retrieving patient").build();
        }
    }

    @POST
    @Path("/transferPatient")
    @Consumes("application/json")
    public Response transferPatient(TransferRequest request) {
        //process message json
        try {
            String result = patientService.getPatient(request.getFullUrl());
            patientService.insertOrUpdatePatient(patientRepository, result, request.getFullUrl());
            return Response.status(Response.Status.OK).entity("Patient correctly transferred").build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.NOT_FOUND).entity("Error in transferring patient").build();
        }
    }
}