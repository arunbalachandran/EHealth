create table if not exists doctor (
    id uuid primary key,
    email text not null,
    password text not null,
    name text not null,
    specialization text not null
);

create table if not exists patient (
    id uuid primary key,
    email text not null,
    password text not null,
    name text not null,
    age integer not null
);

create table if not exists appointments (
    id uuid primary key,
    doctor_id uuid not null,
    patient_id uuid not null,
    time_appt timestamp not null
);
