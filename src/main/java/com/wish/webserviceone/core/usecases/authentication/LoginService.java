package com.wish.webserviceone.core.usecases.authentication;


import com.wish.webserviceone.core.usecases.account.ManageService;
import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authentication-login")
public class LoginService {

    private final ManageService manageService;

    @Autowired
    public LoginService(ManageService manageService) {
        this.manageService = manageService;
    }

    public Result<Account> loginByEmail(String email, String password) {
        Account content = null;
        String status = null;

        try {
            Result<Account> accountByEmail = manageService.readOneByEmail(email);
            if (accountByEmail.getStatus().equals("read")) {
                Result<Account> accountByEmailAndPasword = manageService.readOneByEmailAndPassword(email, password);
                if (accountByEmailAndPasword.getContent() == null) {
                    status = "invalid_credentials";
                } else {
                    content = accountByEmailAndPasword.getContent();
                    status = "logged_in";
                }
            } else {
                status = accountByEmail.getStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
