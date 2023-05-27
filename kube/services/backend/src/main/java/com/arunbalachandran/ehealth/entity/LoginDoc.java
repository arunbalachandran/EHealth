package com.arunbalachandran.ehealth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "login_doc")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class LoginDoc {

    @Id
    String uname;

    String email;

    String pwd;

    // Department - i.e. specialization
    String dep;
}
