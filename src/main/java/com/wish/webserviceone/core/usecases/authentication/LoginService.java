package com.wish.webserviceone.core.usecases.authentication;


import com.wish.webserviceone.core.usecases.account.HuaweiService;
import com.wish.webserviceone.core.usecases.account.ManageService;
import com.wish.webserviceone.infrastructure.deliveries.contracts.CredentialsByEmail;
import com.wish.webserviceone.infrastructure.deliveries.contracts.CredentialsByHuawei;
import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Account;
import com.wish.webserviceone.infrastructure.persistences.entities.AccountHuawei;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("authentication-login")
public class LoginService {

    private final ManageService manageService;
    private final HuaweiService huaweiService;

    @Autowired
    public LoginService(@Qualifier("account-manage") ManageService manageService, @Qualifier("account-huawei") HuaweiService huaweiService) {
        this.manageService = manageService;
        this.huaweiService = huaweiService;
    }

    public Result<Account> loginByEmail(CredentialsByEmail credentialsByEmail) {
        Account content = null;
        String status = null;

        try {
            Result<Account> accountByEmail = manageService.readOneByEmail(credentialsByEmail.getEmail());
            if (accountByEmail.getStatus().equals("read")) {
                Result<Account> accountByEmailAndPasword = manageService.readOneByEmailAndPassword(credentialsByEmail.getEmail(), credentialsByEmail.getPassword());
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

    public Result<Account> loginByHuaweiOpenId(CredentialsByHuawei credentialsByHuawei) {
        Account content = null;
        String status = null;

        try {
            Result<AccountHuawei> resultByOpenId = huaweiService.readOneByOpenId(credentialsByHuawei.getOpenId());
            if (resultByOpenId.getStatus().equals("read")) {
                Result<Account> resultByAccountId = manageService.readOneById(resultByOpenId.getContent().getAccountId());
                if (resultByAccountId.getStatus().equals("read")) {
                    // recommend to change open_id to others and revalidate it to huawei api to prevent cross-site request forgery
                    content = resultByAccountId.getContent();
                    status = "logged_in";
                } else {
                    status = resultByAccountId.getStatus();
                }
            } else {
                status = resultByOpenId.getStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
