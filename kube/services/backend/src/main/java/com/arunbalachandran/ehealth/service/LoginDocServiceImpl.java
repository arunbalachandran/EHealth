package com.arunbalachandran.ehealth.service;

import com.arunbalachandran.ehealth.entity.LoginDoc;
import com.arunbalachandran.ehealth.repository.LoginDocRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class LoginDocServiceImpl implements LoginDocService{

    @Autowired
    LoginDocRepository loginDocRepository;

    @Transactional
    public LoginDoc save(LoginDoc loginDoc) {
        return loginDocRepository.save(loginDoc);
    }

    public List<LoginDoc> findAll() {
        return loginDocRepository.findAll();
    }
}
