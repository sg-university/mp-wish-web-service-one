package com.wish.webserviceone.core.usecases.fund;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Fund;
import com.wish.webserviceone.infrastructure.persistences.repositories.FundRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("fund-manage")
public class ManageService {
    FundRepository fundRepository;

    @Autowired
    public ManageService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    public Result<List<Fund>> readAll(Map<String, String> filter) {
        List<Fund> content = null;
        String status = null;
        try {
            content = fundRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Fund> readOneById(UUID Id) {
        Fund content = null;
        String status = null;
        try {
            content = fundRepository.readOneById(Id);
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

    public Result<Fund> createOne(Fund fundToCreate) {
        Fund content = null;
        String status = null;
        try {
            fundToCreate.setId(UUID.randomUUID());
            fundToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            fundToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Integer rowAffected = fundRepository.createOne(fundToCreate);
            content = fundToCreate;
            status = "created";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Fund> updateOneById(UUID Id, Fund fundToUpdate) {
        Fund content = null;
        String status = null;
        try {
            content = fundRepository.readOneById(Id);
            if (content == null) {
                status = "not_found";
            } else {
                fundToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = fundRepository.updateOneById(Id, fundToUpdate);
                content = fundToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Fund> deleteOneById(UUID Id) {
        Fund content = null;
        String status = null;
        try {
            content = fundRepository.readOneById(Id);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = fundRepository.deleteOneById(Id);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
