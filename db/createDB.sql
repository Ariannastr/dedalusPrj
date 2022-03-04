CREATE TABLE patient (
    id uuid not null,
    birthday varchar(255),
    created_at timestamp,
    family varchar(500),
    full_url varchar(255),
    gender varchar(10),
    given varchar(1000),
    patient_id varchar(50) not null,
    prefix varchar(500),
    suffix varchar(500),
    primary key (id)
);