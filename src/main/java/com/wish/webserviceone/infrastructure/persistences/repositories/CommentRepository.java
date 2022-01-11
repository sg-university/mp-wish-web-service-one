package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wish.webserviceone.infrastructure.persistences.entities.Comment;
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
public class CommentRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public CommentRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<Comment> readAll(Map<String, String> filter) throws JsonProcessingException, IllegalAccessException {
        String sql = "select id, creator_account_id, post_id, content, created_at, updated_at from comment";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        Map<String, Object> filterObject = queryTool.convertFilterForUnNamedParameters(filter, Comment.class);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Comment.class), filterObject.values().toArray());
    }

    public Comment readOneById(UUID id) {
        String sql = "select id, creator_account_id, post_id, content, created_at, updated_at from comment where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Comment.class), id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer createOne(Comment commentToCreate) {
        String sql = "insert into comment (id, creator_account_id, post_id, content, created_at, updated_at) values (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, commentToCreate.getId(), commentToCreate.getCreatorAccountId(), commentToCreate.getPostId(), commentToCreate.getContent(), commentToCreate.getCreatedAt(), commentToCreate.getUpdatedAt());
    }

    public Integer updateOneById(UUID id, Comment commentToUpdate) {
        String sql = "update comment set creator_account_id=?, post_id=?, content=?, created_at=?, updated_at=? where id=?";
        return jdbcTemplate.update(sql, commentToUpdate.getCreatorAccountId(), commentToUpdate.getPostId(), commentToUpdate.getContent(), commentToUpdate.getCreatedAt(), commentToUpdate.getUpdatedAt(), id);
    }

    public Integer deleteOneById(UUID id) {
        String sql = "delete from comment where id=?";
        return jdbcTemplate.update(sql, id);
    }

}
