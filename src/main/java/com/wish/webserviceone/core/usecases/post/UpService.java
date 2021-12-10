package com.wish.webserviceone.core.usecases.post;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.PostUp;
import com.wish.webserviceone.infrastructure.persistences.repositories.PostUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("post-up")
public class UpService {
    PostUpRepository postUpRepository;

    @Autowired
    public UpService(PostUpRepository postUpRepository) {
        this.postUpRepository = postUpRepository;
    }

    public Result<List<PostUp>> readAll(Map<String, String> filter) {
        List<PostUp> content = null;
        String status = null;
        try {
            content = postUpRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<PostUp> readOneByID(UUID ID) {
        PostUp content = null;
        String status = null;
        try {
            content = postUpRepository.readOneByID(ID);
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

    public Result<PostUp> createOne(PostUp postUpToCreate) {
        PostUp content = null;
        String status = null;
        try {
            postUpToCreate.setID(UUID.randomUUID());
            postUpToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            postUpToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Integer rowAffected = postUpRepository.createOne(postUpToCreate);
            content = postUpToCreate;
            status = "created";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<PostUp> updateOneByID(UUID ID, PostUp postUpToUpdate) {
        PostUp content = null;
        String status = null;
        try {
            content = postUpRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                postUpToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = postUpRepository.updateOneByID(ID, postUpToUpdate);
                content = postUpToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<PostUp> deleteOneByID(UUID ID) {
        PostUp content = null;
        String status = null;
        try {
            content = postUpRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = postUpRepository.deleteOneByID(ID);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
