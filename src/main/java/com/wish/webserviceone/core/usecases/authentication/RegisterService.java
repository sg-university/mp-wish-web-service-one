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

@Service("authentication-register")
public class RegisterService {

    private final ManageService manageService;
    private final HuaweiService huaweiService;

    @Autowired
    public RegisterService(@Qualifier("account-manage") ManageService manageService, @Qualifier("account-huawei") HuaweiService huaweiService) {
        this.manageService = manageService;
        this.huaweiService = huaweiService;
    }

    public Result<Account> registerByEmail(CredentialsByEmail credentials) {
        Account content = null;
        String status = null;

        Account account = new Account();
        account.setEmail(credentials.getEmail());
        account.setPassword(credentials.getPassword());
        Result<Account> accountCreated = manageService.createOne(account);
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


    public Result<Account> registerByHuaweiOpenId(CredentialsByHuawei credentialsByHuawei) {
        Account content = null;
        String status = null;

        AccountHuawei accountHuawei = new AccountHuawei();
        accountHuawei.setOpenId(credentialsByHuawei.getOpenId());
        accountHuawei.setUnionId(credentialsByHuawei.getUnionId());
        accountHuawei.setAuthorizationCode(credentialsByHuawei.getAuthorizationCode());
        accountHuawei.setAccessToken(credentialsByHuawei.getAccessToken());
        Result<AccountHuawei> resultAccountHuaweiCreated = huaweiService.createOne(accountHuawei);
        try {
            if (resultAccountHuaweiCreated.getStatus().equals("created")) {
                Account account = new Account();
                Result<Account> resultAccountCreated = manageService.createOne(account);
                accountHuawei.setAccountId(resultAccountCreated.getContent().getId());
                Result<AccountHuawei> resultAccountHuaweiUpdated = huaweiService.updateOneById(resultAccountHuaweiCreated.getContent().getId(), accountHuawei);

                if (resultAccountHuaweiUpdated.getStatus().equals("updated")) {
                    content = resultAccountCreated.getContent();
                    status = "registered";
                } else {
                    status = resultAccountCreated.getStatus();
                }
            } else {
                status = resultAccountHuaweiCreated.getStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
