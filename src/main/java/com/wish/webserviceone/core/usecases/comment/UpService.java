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

    public Result<CommentUp> readOneById(UUID id) {
        CommentUp content = null;
        String status = null;
        try {
            content = commentUpRepository.readOneById(id);
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
            commentUpToCreate.setId(UUID.randomUUID());
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

    public Result<CommentUp> updateOneById(UUID id, CommentUp commentUpToUpdate) {
        CommentUp content = null;
        String status = null;
        try {
            content = commentUpRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                commentUpToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = commentUpRepository.updateOneById(id, commentUpToUpdate);
                content = commentUpToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<CommentUp> patchOneById(UUID id, CommentUp commentUpToPatch) {
        CommentUp content = null;
        String status = null;
        try {
            Result<CommentUp> readResult = this.readOneById(id);
            status = readResult.getStatus();

            if (status.equals("read")) {
                CommentUp commentUpToUpdate = readResult.getContent();
                commentUpToUpdate.setPostId(commentUpToPatch.getPostId() == null ? commentUpToUpdate.getPostId() : commentUpToPatch.getPostId());
                commentUpToUpdate.setUpperAccountId(commentUpToPatch.getUpperAccountId() == null ? commentUpToUpdate.getUpperAccountId() : commentUpToPatch.getUpperAccountId());
                commentUpToUpdate.setCreatedAt(commentUpToPatch.getCreatedAt() == null ? commentUpToUpdate.getCreatedAt() : commentUpToPatch.getCreatedAt());
                commentUpToUpdate.setUpdatedAt(commentUpToPatch.getUpdatedAt() == null ? commentUpToUpdate.getUpdatedAt() : commentUpToPatch.getUpdatedAt());

                Result<CommentUp> updateResult = this.updateOneById(id, commentUpToUpdate);
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

    public Result<CommentUp> deleteOneById(UUID id) {
        CommentUp content = null;
        String status = null;
        try {
            content = commentUpRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = commentUpRepository.deleteOneById(id);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
