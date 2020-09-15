package com.arunbalachandran.ehealth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_pat")
public class Patient {

    @Id
    String uname;
    String age;
    String email;
    String pwd;
}
