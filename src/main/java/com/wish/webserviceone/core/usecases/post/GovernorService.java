package com.wish.webserviceone.core.usecases.post;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.PostGovernor;
import com.wish.webserviceone.infrastructure.persistences.repositories.PostGovernorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("post-governor")
public class GovernorService {
    PostGovernorRepository postGovernorRepository;

    @Autowired
    public GovernorService(PostGovernorRepository postGovernorRepository) {
        this.postGovernorRepository = postGovernorRepository;
    }

    public Result<List<PostGovernor>> readAll(Map<String, String> filter) {
        List<PostGovernor> content = null;
        String status = null;
        try {
            content = postGovernorRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<PostGovernor> readOneById(UUID Id) {
        PostGovernor content = null;
        String status = null;
        try {
            content = postGovernorRepository.readOneById(Id);
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

    public Result<PostGovernor> createOne(PostGovernor postGovernorToCreate) {
        PostGovernor content = null;
        String status = null;
        try {
            postGovernorToCreate.setId(UUID.randomUUID());
            postGovernorToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            postGovernorToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Integer rowAffected = postGovernorRepository.createOne(postGovernorToCreate);
            content = postGovernorToCreate;
            status = "created";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<PostGovernor> updateOneById(UUID Id, PostGovernor postGovernorToUpdate) {
        PostGovernor content = null;
        String status = null;
        try {
            content = postGovernorRepository.readOneById(Id);
            if (content == null) {
                status = "not_found";
            } else {
                postGovernorToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = postGovernorRepository.updateOneById(Id, postGovernorToUpdate);
                content = postGovernorToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<PostGovernor> deleteOneById(UUID Id) {
        PostGovernor content = null;
        String status = null;
        try {
            content = postGovernorRepository.readOneById(Id);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = postGovernorRepository.deleteOneById(Id);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
