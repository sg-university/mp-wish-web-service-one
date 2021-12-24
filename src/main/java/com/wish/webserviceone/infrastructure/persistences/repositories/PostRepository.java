package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.wish.webserviceone.infrastructure.persistences.entities.Post;
import com.wish.webserviceone.infrastructure.persistences.tools.QueryTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class PostRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public PostRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<Post> readAll(Map<String, String> filter) {
        String sql = "select id, creator_account_id, title, content, created_at, updated_at from post";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Post.class), filter.values().toArray());
    }

    public Post readOneById(UUID Id) {
        String sql = "select id, creator_account_id, title, content, created_at, updated_at from post where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Post.class), Id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer createOne(Post postToCreate) {
        String sql = "insert into post (id, creator_account_id, title, content, created_at, updated_at) values (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, postToCreate.getId(), postToCreate.getCreatorAccountId(), postToCreate.getTitle(), postToCreate.getContent(), postToCreate.getCreatedAt(), postToCreate.getUpdatedAt());
    }

    public Integer updateOneById(UUID Id, Post postToUpdate) {
        String sql = "update post set creator_account_id=?, title=?, content=?, created_at=?, updated_at=? where id=?";
        return jdbcTemplate.update(sql, postToUpdate.getCreatorAccountId(), postToUpdate.getTitle(), postToUpdate.getContent(), postToUpdate.getCreatedAt(), postToUpdate.getUpdatedAt(), Id);
    }

    public Integer deleteOneById(UUID Id) {
        String sql = "delete from post where id=?";
        return jdbcTemplate.update(sql, Id);
    }

}
