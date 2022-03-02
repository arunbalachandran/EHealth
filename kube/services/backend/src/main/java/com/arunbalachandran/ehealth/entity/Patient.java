package com.arunbalachandran.ehealth.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_pat")
@Data
public class Patient {

    @Id
    String uname;

    String age;
    
    String email;
    
    String pwd;
}
