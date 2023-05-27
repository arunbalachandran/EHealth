package com.arunbalachandran.ehealth.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Table(name = "appointments")
@Builder(toBuilder = true)
public class Appointment {

    @Id
    String id;

    String unameDoc;
    String unamePat;
    String dt;
    String time;
}
