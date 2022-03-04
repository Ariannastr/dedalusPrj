FROM postgres:9.6.1
ENV POSTGRES_USER postgres
ENV POSTGRES_PASSWORD root
ENV POSTGRES_DB patient
ADD createDB.sql /docker-entrypoint-initdb.d/
