package com.wish.webserviceone.core.usecases.account;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.AccountHuawei;
import com.wish.webserviceone.infrastructure.persistences.repositories.AccountHuaweiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("account-huawei")
public class HuaweiService {
    AccountHuaweiRepository accountHuaweiRepository;

    @Autowired
    public HuaweiService(AccountHuaweiRepository accountHuaweiRepository) {
        this.accountHuaweiRepository = accountHuaweiRepository;
    }

    public Result<List<AccountHuawei>> readAll(Map<String, String> filter) {
        List<AccountHuawei> content = null;
        String status = null;
        try {
            content = accountHuaweiRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<AccountHuawei> readOneById(UUID id) {
        AccountHuawei content = null;
        String status = null;
        try {
            content = accountHuaweiRepository.readOneById(id);
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

    public Result<AccountHuawei> readOneByOpenId(String openId) {
        AccountHuawei content = null;
        String status = null;
        try {
            Map<String, String> filter = new HashMap<String, String>(Map.of("open_id", openId));
            Result<List<AccountHuawei>> result = this.readAll(filter);
            if (result.getStatus().equals("read")) {
                content = result.getContent().parallelStream().findAny().orElse(null);
                status = "read";
                if (content == null) {
                    status = "not_found";
                }
            } else {
                status = result.getStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<AccountHuawei> createOne(AccountHuawei accountHuaweiToCreate) {
        AccountHuawei content = null;
        String status = null;
        try {
            accountHuaweiToCreate.setId(UUID.randomUUID());
            accountHuaweiToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            accountHuaweiToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Result<AccountHuawei> resultByOpenId = this.readOneByOpenId(accountHuaweiToCreate.getOpenId());
            if (resultByOpenId.getStatus().equals("read")) {
                status = "exists";
            } else {
                Integer rowAffected = accountHuaweiRepository.createOne(accountHuaweiToCreate);
                content = accountHuaweiToCreate;
                status = "created";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<AccountHuawei> updateOneById(UUID id, AccountHuawei accountToUpdate) {
        AccountHuawei content = null;
        String status = null;
        try {
            content = accountHuaweiRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                Result<AccountHuawei> resultByOpenId = this.readOneByOpenId(accountToUpdate.getOpenId());
                if (resultByOpenId.getStatus().equals("read") && !resultByOpenId.getContent().getOpenId().equals(accountToUpdate.getOpenId())) {
                    status = "exists";
                } else {
                    accountToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                    Integer rowAffected = accountHuaweiRepository.updateOneById(id, accountToUpdate);
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

    public Result<AccountHuawei> deleteOneById(UUID id) {
        AccountHuawei content = null;
        String status = null;
        try {
            content = accountHuaweiRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = accountHuaweiRepository.deleteOneById(id);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
