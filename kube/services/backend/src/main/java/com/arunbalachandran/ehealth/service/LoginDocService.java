package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.entity.LoginDoc;

import java.util.List;

public interface LoginDocService {
    public LoginDoc save(LoginDoc loginDoc);

    public List<LoginDoc> findAll();
}
