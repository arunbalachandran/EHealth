package com.arunbalachandran.ehealth.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "login_doc")
@Data
public class LoginDoc {

    @Id
    String uname;

    String email;

    String pwd;

    // Department - i.e. specialization
    String dep;
}
