package com.wish.webserviceone.infrastructure.persistences.repositories;

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

    public List<Comment> readAll(Map<String, String> filter) {
        String sql = "select id, creator_account_id as creatorAccountID, post_id as postID, content, created_at, updated_at from comment";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Comment.class), filter.values().toArray());
    }

    public Comment readOneByID(UUID ID) {
        String sql = "select id, creator_account_id as creatorAccountID, post_id as postID, content, created_at, updated_at from comment where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Comment.class), ID);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer createOne(Comment commentToCreate) {
        String sql = "insert into comment (id, creator_account_id, post_id, content, created_at, updated_at) values (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, commentToCreate.getID(), commentToCreate.getCreatorAccountID(), commentToCreate.getContent(), commentToCreate.getCreatedAt(), commentToCreate.getUpdatedAt());
    }

    public Integer updateOneByID(UUID ID, Comment commentToUpdate) {
        String sql = "update comment set creator_account_id=?, post_id=?, content=?, created_at=?, updated_at=? where id=?";
        return jdbcTemplate.update(sql, commentToUpdate.getCreatorAccountID(), commentToUpdate.getContent(), commentToUpdate.getCreatedAt(), commentToUpdate.getUpdatedAt(), ID);
    }

    public Integer deleteOneByID(UUID ID) {
        String sql = "delete from comment where id=?";
        return jdbcTemplate.update(sql, ID);
    }

}
