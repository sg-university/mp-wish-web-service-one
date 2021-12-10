package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.wish.webserviceone.infrastructure.persistences.entities.Fund;
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
public class FundRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public FundRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<Fund> readAll(Map<String, String> filter) {
        String sql = "select id, post_id as postID, sponsor_account_id as sponsorAccountID, amount, created_at, updated_at from fund";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Fund.class), filter.values().toArray());
    }

    public Fund readOneByID(UUID ID) {
        String sql = "select id, post_id as postID, sponsor_account_id as sponsorAccountID, amount, created_at, updated_at from fund where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Fund.class), ID);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer createOne(Fund fundToCreate) {
        String sql = "insert into fund (id, post_id, sponsor_account_id, amount, created_at, updated_at) values (?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, fundToCreate.getID(), fundToCreate.getPostID(), fundToCreate.getSponsorAccountID(), fundToCreate.getAmount(), fundToCreate.getCreatedAt(), fundToCreate.getUpdatedAt());
    }

    public Integer updateOneByID(UUID ID, Fund fundToUpdate) {
        String sql = "update fund set post_id=?, sponsor_account_id=?, amount=?, created_at=?, updated_at=? where id=?";
        return jdbcTemplate.update(sql, fundToUpdate.getPostID(), fundToUpdate.getSponsorAccountID(), fundToUpdate.getAmount(), fundToUpdate.getCreatedAt(), fundToUpdate.getUpdatedAt(), ID);
    }

    public Integer deleteOneByID(UUID ID) {
        String sql = "delete from fund where id=?";
        return jdbcTemplate.update(sql, ID);
    }
}
