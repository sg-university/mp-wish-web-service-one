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

    public Result<Comment> readOneById(UUID id) {
        Comment content = null;
        String status = null;
        try {
            content = commentRepository.readOneById(id);
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
            commentToCreate.setId(UUID.randomUUID());
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

    public Result<Comment> updateOneById(UUID id, Comment commentToUpdate) {
        Comment content = null;
        String status = null;
        try {
            content = commentRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                commentToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = commentRepository.updateOneById(id, commentToUpdate);
                content = commentToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Comment> patchOneById(UUID id, Comment commentToPatch) {
        Comment content = null;
        String status = null;
        try {
            Result<Comment> readResult = this.readOneById(id);
            status = readResult.getStatus();

            if (status.equals("read")) {
                Comment commentToUpdate = readResult.getContent();
                commentToUpdate.setPostId(commentToPatch.getPostId() == null ? commentToUpdate.getPostId() : commentToPatch.getPostId());
                commentToUpdate.setCreatorAccountId(commentToPatch.getCreatorAccountId() == null ? commentToUpdate.getCreatorAccountId() : commentToPatch.getCreatorAccountId());
                commentToUpdate.setContent(commentToPatch.getContent() == null ? commentToUpdate.getContent() : commentToPatch.getContent());
                commentToUpdate.setCreatedAt(commentToPatch.getCreatedAt() == null ? commentToUpdate.getCreatedAt() : commentToPatch.getCreatedAt());
                commentToUpdate.setUpdatedAt(commentToPatch.getUpdatedAt() == null ? commentToUpdate.getUpdatedAt() : commentToPatch.getUpdatedAt());

                Result<Comment> updateResult = this.updateOneById(id, commentToUpdate);
                status = updateResult.getStatus();

                if (updateResult.getStatus().equals("updated")) {
                    content = updateResult.getContent();
                    status = "patched";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Comment> deleteOneById(UUID id) {
        Comment content = null;
        String status = null;
        try {
            content = commentRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = commentRepository.deleteOneById(id);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
