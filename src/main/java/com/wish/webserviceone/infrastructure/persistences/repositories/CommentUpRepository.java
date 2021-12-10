package com.wish.webserviceone.infrastructure.persistences.repositories;

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

    public List<CommentUp> readAll(Map<String, String> filter) {
        String sql = "select id, post_id as postID, upper_account_id as upperAccountID, created_at, updated_at from comment_up";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(CommentUp.class), filter.values().toArray());
    }

    public CommentUp readOneByID(UUID ID) {
        String sql = "select id, post_id as postID, upper_account_id as upperAccountID, created_at, updated_at from comment_up where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(CommentUp.class), ID);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer createOne(CommentUp commentUpToCreate) {
        String sql = "insert into comment_up (id, post_id, upper_account_id, created_at, updated_at) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, commentUpToCreate.getID(), commentUpToCreate.getPostID(), commentUpToCreate.getUpperAccountID(), commentUpToCreate.getCreatedAt(), commentUpToCreate.getUpdatedAt());
    }

    public Integer updateOneByID(UUID ID, CommentUp commentUpToUpdate) {
        String sql = "update comment_up set post_id = ?, upper_account_id = ?, created_at = ?, updated_at = ? where id = ?";
        return jdbcTemplate.update(sql, commentUpToUpdate.getPostID(), commentUpToUpdate.getUpperAccountID(), commentUpToUpdate.getCreatedAt(), commentUpToUpdate.getUpdatedAt(), ID);
    }

    public Integer deleteOneByID(UUID ID) {
        String sql = "delete from comment_up where id = ?";
        return jdbcTemplate.update(sql, ID);
    }
}
