package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.wish.webserviceone.infrastructure.persistences.entities.PostUp;
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
public class PostUpRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public PostUpRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<PostUp> readAll(Map<String, String> filter) {
        String sql = "select id, post_id as postID, upper_account_id as upperAccountID, created_at, updated_at from post_up";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(PostUp.class), filter.values().toArray());
    }

    public PostUp readOneByID(UUID ID) {
        String sql = "select id, post_id as postID, upper_account_id as upperAccountID, created_at, updated_at from post_up where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(PostUp.class), ID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer createOne(PostUp postUpToCreate) {
        String sql = "insert into post_up (id, post_id, upper_account_id, created_at, updated_at) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, postUpToCreate.getID(), postUpToCreate.getPostID(), postUpToCreate.getUpperAccountID(), postUpToCreate.getCreatedAt(), postUpToCreate.getUpdatedAt());
    }

    public Integer updateOneByID(UUID ID, PostUp postUpToUpdate) {
        String sql = "update post_up set post_id = ?, upper_account_id = ?, created_at = ?, updated_at = ? where id = ?";
        return jdbcTemplate.update(sql, postUpToUpdate.getPostID(), postUpToUpdate.getUpperAccountID(), postUpToUpdate.getCreatedAt(), postUpToUpdate.getUpdatedAt(), ID);
    }

    public Integer deleteOneByID(UUID ID) {
        String sql = "delete from post_up where id = ?";
        return jdbcTemplate.update(sql, ID);
    }
}