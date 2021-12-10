package com.wish.webserviceone.core.usecases.account;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Account;
import com.wish.webserviceone.infrastructure.persistences.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("account-manage")
public class ManageService {
    AccountRepository accountRepository;

    @Autowired
    public ManageService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Result<List<Account>> readAll(Map<String, String> filter) {
        List<Account> content = null;
        String status = null;
        try {
            content = accountRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Account> readOneByID(UUID ID) {
        Account content = null;
        String status = null;
        try {
            content = accountRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                status = "read";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Account> readOneByEmailAndPassword(String email, String password) {
        Account content = null;
        String status = null;
        try {
            content = accountRepository.readOneByEmailAndPassword(email, password);
            if (content == null) {
                status = "not_found";
            } else {
                status = "read";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Account> readOneByEmail(String email) {
        Account content = null;
        String status = null;
        try {
            content = accountRepository.readOneByEmail(email);
            if (content == null) {
                status = "not_found";
            } else {
                status = "read";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Account> readOneByUsername(String username) {
        Account content = null;
        String status = null;
        try {
            content = accountRepository.readOneByUsername(username);
            if (content == null) {
                status = "not_found";
            } else {
                status = "read";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Account> createOne(Account accountToCreate) {
        Account content = null;
        String status = null;
        try {
            accountToCreate.setID(UUID.randomUUID());
            accountToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            accountToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Account accountByUsername = accountRepository.readOneByUsername(accountToCreate.getUsername());
            Account accountByEmail = accountRepository.readOneByUsername(accountToCreate.getEmail());
            if (accountByUsername != null || accountByEmail != null) {
                status = "exists";
            } else {
                Integer rowAffected = accountRepository.createOne(accountToCreate);
                content = accountToCreate;
                status = "created";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Account> updateOneByID(UUID ID, Account accountToUpdate) {
        Account content = null;
        String status = null;
        try {
            content = accountRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                Account accountByUsername = accountRepository.readOneByUsername(accountToUpdate.getUsername());
                Account accountByEmail = accountRepository.readOneByEmail(accountToUpdate.getEmail());
                if (accountByUsername != null && !accountByUsername.getUsername().equals(accountToUpdate.getUsername())) {
                    status = "exists";
                } else if (accountByEmail != null && !accountByEmail.getEmail().equals(accountToUpdate.getEmail())) {
                    status = "exists";
                } else {
                    accountToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    Integer rowAffected = accountRepository.updateOneByID(ID, accountToUpdate);
                    content = accountToUpdate;
                    status = "updated";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Account> deleteOneByID(UUID ID) {
        Account content = null;
        String status = null;
        try {
            content = accountRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = accountRepository.deleteOneByID(ID);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
