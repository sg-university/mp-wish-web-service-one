package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wish.webserviceone.infrastructure.persistences.entities.CommentUp;
import com.wish.webserviceone.infrastructure.persistences.entities.PostGovernor;
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
public class PostGovernorRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public PostGovernorRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<PostGovernor> readAll(Map<String, String> filter) throws JsonProcessingException, IllegalAccessException {
        String sql = "select id, post_id, governor_account_id, created_at, updated_at from post_governor";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        Map<String, Object> filterObject = queryTool.convertFilterForUnNamedParameters(filter, PostGovernor.class);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(PostGovernor.class), filterObject.values().toArray());
    }

    public PostGovernor readOneById(UUID id) {
        String sql = "select id, post_id, governor_account_id, created_at, updated_at from post_governor where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(PostGovernor.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Integer createOne(PostGovernor postGovernorToCreate) {
        String sql = "insert into post_governor (id, post_id, governor_account_id, created_at, updated_at) values (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, postGovernorToCreate.getId(), postGovernorToCreate.getPostId(), postGovernorToCreate.getGovernorAccountId(), postGovernorToCreate.getCreatedAt(), postGovernorToCreate.getUpdatedAt());
    }

    public Integer updateOneById(UUID id, PostGovernor postGovernorToUpdate) {
        String sql = "update post_governor set post_id = ?, governor_account_id = ?, created_at = ?, updated_at = ? where id = ?";
        return jdbcTemplate.update(sql, postGovernorToUpdate.getPostId(), postGovernorToUpdate.getGovernorAccountId(), postGovernorToUpdate.getCreatedAt(), postGovernorToUpdate.getUpdatedAt(), id);
    }

    public Integer deleteOneById(UUID id) {
        String sql = "delete from post_governor where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
