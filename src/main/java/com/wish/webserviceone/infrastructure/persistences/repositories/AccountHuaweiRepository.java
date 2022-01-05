package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.wish.webserviceone.infrastructure.persistences.entities.AccountHuawei;
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
public class AccountHuaweiRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public AccountHuaweiRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<AccountHuawei> readAll(Map<String, String> filter) {
        String sql = "select id, account_id, union_id, open_id, authorization_code, access_token, created_at, updated_at from account_huawei";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(AccountHuawei.class), filter.values().toArray());
    }

    public AccountHuawei readOneById(UUID id) {
        String sql = "select id, account_id, union_id, open_id, authorization_code, access_token, created_at, updated_at from account_huawei where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(AccountHuawei.class), id);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer createOne(AccountHuawei accountToCreate) {
        String sql = "insert into account_huawei (id, account_id, union_id, open_id, authorization_code, access_token, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, accountToCreate.getId(), accountToCreate.getAccountId(), accountToCreate.getUnionId(), accountToCreate.getOpenId(), accountToCreate.getAuthorizationCode(), accountToCreate.getAccessToken(), accountToCreate.getCreatedAt(), accountToCreate.getUpdatedAt());
    }

    public Integer updateOneById(UUID id, AccountHuawei accountToUpdate) {
        String sql = "update account_huawei set account_id=?, union_id=?, open_id=?, authorization_code=?, access_token=?, created_at=?, updated_at=? where id=?";
        return jdbcTemplate.update(sql, accountToUpdate.getAccountId(), accountToUpdate.getUnionId(), accountToUpdate.getOpenId(), accountToUpdate.getAuthorizationCode(), accountToUpdate.getAccessToken(), accountToUpdate.getCreatedAt(), accountToUpdate.getUpdatedAt(), id);
    }

    public Integer deleteOneById(UUID I) {
        String sql = "delete from account_huawei where id=?";
        return jdbcTemplate.update(sql, I);
    }

}
