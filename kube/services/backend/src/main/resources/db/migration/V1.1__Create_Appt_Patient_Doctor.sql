-- TODO: change role to be an enum
create table if not exists users (
    id uuid primary key,
    name text not null,
    email text not null,
    password text not null,
    role varchar(255) not null,
    created_time timestamp not null,
    updated_time timestamp not null
);

create table if not exists doctor (
    id uuid primary key,
    user_id uuid not null,
    specialization text not null 
);

create table if not exists patient (
    id uuid primary key,
    user_id uuid not null,
    age integer not null
);

create table if not exists appointments (
    id uuid primary key,
    doctor_id uuid not null,
    patient_id uuid not null,
    time_appt timestamp not null
);
