package com.wish.webserviceone.core.usecases.comment;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Comment;
import com.wish.webserviceone.infrastructure.persistences.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("comment-manage")
public class ManageService {
    CommentRepository commentRepository;

    @Autowired
    public ManageService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Result<List<Comment>> readAll(Map<String, String> filter) {
        List<Comment> content = null;
        String status = null;
        try {
            content = commentRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Comment> readOneByID(UUID ID) {
        Comment content = null;
        String status = null;
        try {
            content = commentRepository.readOneByID(ID);
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

    public Result<Comment> createOne(Comment commentToCreate) {
        Comment content = null;
        String status = null;
        try {
            commentToCreate.setID(UUID.randomUUID());
            commentToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            commentToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Integer rowAffected = commentRepository.createOne(commentToCreate);
            content = commentToCreate;
            status = "created";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Comment> updateOneByID(UUID ID, Comment commentToUpdate) {
        Comment content = null;
        String status = null;
        try {
            content = commentRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                commentToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = commentRepository.updateOneByID(ID, commentToUpdate);
                content = commentToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Comment> deleteOneByID(UUID ID) {
        Comment content = null;
        String status = null;
        try {
            content = commentRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = commentRepository.deleteOneByID(ID);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
