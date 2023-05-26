package com.arunbalachandran.ehealth.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_doc")
@Data
public class LoginDoc {

    @Id
    // TODO: fixme -> remove the GenerationType
    @GeneratedValue(strategy = GenerationType.AUTO)
    String uname;

    String email;

    String pwd;

    // Department - i.e. specialization
    String dep;
}
