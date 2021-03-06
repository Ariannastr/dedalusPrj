# dedalusPrj

Simple project: <br/>
  -backend in Java EE<br/>
  -frontend Angular<br/>
  -DB postgreSQL <br/>

Folder description:<br />
  -angular_project/dedalus_prg: folder with the frontend code<br />
  -db-data: folder for the DB volume (it is autogenerated when you run docker-compose up)<br />
  -db: folder with Dockerfile and .sql for the DB<br />
  -demoTest: folder with the backend code<br />
  -deploy: folder with the .war file needed by Docker<br />
  
In order to run the project:<br />
  -clone the repo<br />
  -navigate inside the demoTest folder<br />
  -run: mvn clean package<br />
  -copy the .war file to the deploy folder<br />
  -navigate back and run docker-compose up<br />
<br/>
You can reach: <br/>
  -the frontend at http://localhost:80/<br/>
  -backend API at http://localhost:8080/app/api/patient/ <br/><br/>
Available API: <br/>
 -POST request in order to transfer a patient resource from the Fhir server to the DB: <br/>
 http://localhost:8080/app/api/patient/transferPatient with JSON body:<br/>
 { <br/>
	"fullUrl": "http://hapi.fhir.org/baseR4/Patient/{patient_id}" <br/>
 } <br/><br/>
 -GET request in order to retrieve a patient from the DB: <br/>
 http://localhost:8080/app/api/patient/getPatient/{id} <br/><br/>
 -GET request in order to retrieve all patients from the DB: <br/>
 http://localhost:8080/app/api/patient/getAllPatients <br/><br/>
 -GET request in order to create a patient on the Fhir server: <br/>
 http://localhost:8080/app/api/patient/createPatient <br/><br/>
