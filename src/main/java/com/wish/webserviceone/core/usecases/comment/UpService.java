package com.wish.webserviceone.core.usecases.comment;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.CommentUp;
import com.wish.webserviceone.infrastructure.persistences.repositories.CommentUpRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("comment-up")
public class UpService {
    CommentUpRepository commentUpRepository;

    @Autowired
    public UpService(CommentUpRepository commentUpRepository) {
        this.commentUpRepository = commentUpRepository;
    }

    public Result<List<CommentUp>> readAll(Map<String, String> filter) {
        List<CommentUp> content = null;
        String status = null;
        try {
            content = commentUpRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<CommentUp> readOneByID(UUID ID) {
        CommentUp content = null;
        String status = null;
        try {
            content = commentUpRepository.readOneByID(ID);
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

    public Result<CommentUp> createOne(CommentUp commentUpToCreate) {
        CommentUp content = null;
        String status = null;
        try {
            commentUpToCreate.setID(UUID.randomUUID());
            commentUpToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            commentUpToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Integer rowAffected = commentUpRepository.createOne(commentUpToCreate);
            content = commentUpToCreate;
            status = "created";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<CommentUp> updateOneByID(UUID ID, CommentUp commentUpToUpdate) {
        CommentUp content = null;
        String status = null;
        try {
            content = commentUpRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                commentUpToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = commentUpRepository.updateOneByID(ID, commentUpToUpdate);
                content = commentUpToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<CommentUp> deleteOneByID(UUID ID) {
        CommentUp content = null;
        String status = null;
        try {
            content = commentUpRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = commentUpRepository.deleteOneByID(ID);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}