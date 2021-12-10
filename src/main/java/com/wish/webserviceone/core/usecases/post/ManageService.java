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

    public Result<Post> readOneByID(UUID ID) {
        Post content = null;
        String status = null;
        try {
            content = postRepository.readOneByID(ID);
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
            postToCreate.setID(UUID.randomUUID());
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

    public Result<Post> updateOneByID(UUID ID, Post postToUpdate) {
        Post content = null;
        String status = null;
        try {
            content = postRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                postToUpdate.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
                Integer rowAffected = postRepository.updateOneByID(ID, postToUpdate);
                content = postToUpdate;
                status = "updated";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }

    public Result<Post> deleteOneByID(UUID ID) {
        Post content = null;
        String status = null;
        try {
            content = postRepository.readOneByID(ID);
            if (content == null) {
                status = "not_found";
            } else {
                Integer rowAffected = postRepository.deleteOneByID(ID);
                status = "deleted";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "error";
        }
        return new Result<>(content, status);
    }
}
