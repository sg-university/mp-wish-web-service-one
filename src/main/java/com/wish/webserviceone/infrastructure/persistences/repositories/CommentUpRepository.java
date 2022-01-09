package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wish.webserviceone.infrastructure.persistences.entities.Account;
import com.wish.webserviceone.infrastructure.persistences.entities.CommentUp;
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
public class CommentUpRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public CommentUpRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<CommentUp> readAll(Map<String, String> filter) throws JsonProcessingException, IllegalAccessException {
        String sql = "select id, post_id, upper_account_id, created_at, updated_at from comment_up";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        Map<String, Object> filterObject = queryTool.convertFilterForUnNamedParameters(filter, CommentUp.class);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(CommentUp.class), filterObject.values().toArray());
    }

    public CommentUp readOneById(UUID id) {
        String sql = "select id, post_id, upper_account_id, created_at, updated_at from comment_up where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(CommentUp.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer createOne(CommentUp commentUpToCreate) {
        String sql = "insert into comment_up (id, post_id, upper_account_id, created_at, updated_at) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, commentUpToCreate.getId(), commentUpToCreate.getPostId(), commentUpToCreate.getUpperAccountId(), commentUpToCreate.getCreatedAt(), commentUpToCreate.getUpdatedAt());
    }

    public Integer updateOneById(UUID id, CommentUp commentUpToUpdate) {
        String sql = "update comment_up set post_id = ?, upper_account_id = ?, created_at = ?, updated_at = ? where id = ?";
        return jdbcTemplate.update(sql, commentUpToUpdate.getPostId(), commentUpToUpdate.getUpperAccountId(), commentUpToUpdate.getCreatedAt(), commentUpToUpdate.getUpdatedAt(), id);
    }

    public Integer deleteOneById(UUID id) {
        String sql = "delete from comment_up where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
