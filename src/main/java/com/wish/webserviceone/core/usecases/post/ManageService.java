package com.wish.webserviceone.core.usecases.post;

import com.wish.webserviceone.infrastructure.deliveries.contracts.Result;
import com.wish.webserviceone.infrastructure.persistences.entities.Post;
import com.wish.webserviceone.infrastructure.persistences.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("post-manage")
public class ManageService {
    PostRepository postRepository;

    @Autowired
    public ManageService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Result<List<Post>> readAll(Map<String, String> filter) {
        List<Post> content = null;
        String status = null;
        try {
            content = postRepository.readAll(filter);
            status = "read";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Post> readOneById(UUID id) {
        Post content = null;
        String status = null;
        try {
            content = postRepository.readOneById(id);
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

    public Result<Post> createOne(Post postToCreate) {
        Post content = null;
        String status = null;
        try {
            postToCreate.setId(UUID.randomUUID());
            postToCreate.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            postToCreate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            Integer rowAffected = postRepository.createOne(postToCreate);
            content = postToCreate;
            status = "created";
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Post> updateOneById(UUID id, Post postToUpdate) {
        Post content = null;
        String status = null;
        try {
            content = postRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                postToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = postRepository.updateOneById(id, postToUpdate);
                content = postToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Post> patchOneById(UUID id, Post postToPatch) {
        Post content = null;
        String status = null;
        try {
            Result<Post> readResult = this.readOneById(id);
            status = readResult.getStatus();

            if (status.equals("read")) {
                Post postToUpdate = readResult.getContent();
                postToUpdate.setCreatorAccountId(postToPatch.getCreatorAccountId() == null ? postToUpdate.getCreatorAccountId() : postToPatch.getCreatorAccountId());
                postToUpdate.setTitle(postToPatch.getTitle() == null ? postToUpdate.getTitle() : postToPatch.getTitle());
                postToUpdate.setContent(postToPatch.getContent() == null ? postToUpdate.getContent() : postToPatch.getContent());
                postToUpdate.setCreatedAt(postToPatch.getCreatedAt() == null ? postToUpdate.getCreatedAt() : postToPatch.getCreatedAt());
                postToUpdate.setUpdatedAt(postToPatch.getUpdatedAt() == null ? postToUpdate.getUpdatedAt() : postToPatch.getUpdatedAt());

                Result<Post> updateResult = this.updateOneById(id, postToUpdate);
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

    public Result<Post> deleteOneById(UUID id) {
        Post content = null;
        String status = null;
        try {
            content = postRepository.readOneById(id);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = postRepository.deleteOneById(id);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
