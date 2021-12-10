package com.wish.webserviceone.core.usecases.authentication;


import com.wish.webserviceone.core.usecases.account.ManageService;
import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authentication-register")
public class RegisterService {

    private final ManageService manageService;

    @Autowired
    public RegisterService(ManageService manageService) {
        this.manageService = manageService;
    }

    public Result<Account> registerByEmail(Account accountToRegister) {
        Account content = null;
        String status = null;

        Result<Account> accountCreated = manageService.createOne(accountToRegister);
        try {
            if (accountCreated.getStatus().equals("created")) {
                content = accountCreated.getContent();
                status = "registered";
            } else {
                status = accountCreated.getStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
