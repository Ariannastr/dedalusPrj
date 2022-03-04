package com.patienttest.demotest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.patienttest.demotest.dto.PatientDto;
import com.patienttest.demotest.model.Patient;
import com.patienttest.demotest.repository.PatientRepository;

import javax.ejb.Stateless;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Stateless
public class PatientService {

    public String createPatient(){
        try {
            URL url = new URL ("http://hapi.fhir.org/baseR4/Patient/");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);
            String jsonInputString = "{'fhirUrl': 'http://hapi.fhir.org/baseR4/Patient/', 'resourceType': 'Patient'}";
            try(OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(con.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
                con.disconnect();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Error";
    }

    public String getPatient(String fullUrl) throws Exception {
        StringBuilder result = new StringBuilder();
        URL url = new URL (fullUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "application/json");
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null; ) {
                result.append(line);
            }
        }
        conn.disconnect();
        return result.toString();
    }

    public String insertOrUpdatePatient(PatientRepository patientRepository, String jsonResponse, String fullUrl) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map user = mapper.readValue(jsonResponse, Map.class);
        String patient_id = (String) user.get("id");
        Patient patientDB = getPatient(patient_id, patientRepository);
        if(patientDB != null){
            patientDB.setPatient_id(patient_id);
            patientDB.setFull_url(fullUrl);
            createPatientEntity(user, patientDB);
            patientRepository.update(patientDB);
        }else{
            Patient patient = new Patient();
            patient.setPatient_id(patient_id);
            patient.setFull_url(fullUrl);
            createPatientEntity(user, patient);
            patientRepository.create(patient);
        }
        return "Patient correctly transferred";
    }

    private void createPatientEntity(Map user, Patient patient){
        patient.setCreated_at(new Date());
        if(user.get("gender") != null)
            patient.setGender(String.valueOf(user.get("gender")));
        if(user.get("birthDate") != null)
            patient.setBirthday(String.valueOf(user.get("birthDate")));
        ArrayList name = (ArrayList) user.get("name");
        if(name != null && name.size()>0){
            Map patientName = (Map) name.get(0);
            if(patientName.get("family")!=null)
                patient.setFamily((String) patientName.get("family"));
            ArrayList given = (ArrayList) patientName.get("given");
            if(given != null && given.size()>0){
                String joined = String.join(",", given);
                patient.setGiven(joined);
            }
            ArrayList suffix = (ArrayList) patientName.get("suffix");
            if(suffix != null && suffix.size()>0){
                String joined = String.join(",", suffix);
                patient.setSuffix(joined);
            }
            ArrayList prefix = (ArrayList) patientName.get("prefix");
            if(prefix != null && prefix.size()>0){
                String joined = String.join(",", prefix);
                patient.setPrefix(joined);
            }
        }

    }

    public List getAllPatients(PatientRepository patientRepository){
        return patientRepository.getAllPatients();
    }

    public Patient getPatient(String patient_id, PatientRepository patientRepository){
        return patientRepository.findByPatientId(patient_id);
    }

    public PatientDto fromPatientToPatientDTO(Patient patient){
        PatientDto patientDto = new PatientDto();
        patientDto.setPatient_id(patient.getPatient_id());
        patientDto.setFamily(patient.getFamily());
        patientDto.setGender(patient.getGender());
        patientDto.setGiven(patient.getGiven());
        patientDto.setPrefix(patient.getPrefix());
        patientDto.setSuffix(patient.getSuffix());
        patientDto.setCreated_at(patient.getCreated_at());
        patientDto.setFull_url(patient.getFull_url());
        patientDto.setBirthDay(patient.getBirthday());
        patientDto.setId(patient.getId());
        return patientDto;
    }

}
