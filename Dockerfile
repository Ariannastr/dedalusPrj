FROM tonda100/wildfly-postgresql

ADD /deploy/app.war ${WILDFLY_HOME}/standalone/deployments/
