package com.wish.webserviceone.infrastructure.persistences.repositories;

import com.wish.webserviceone.infrastructure.persistences.entities.Account;
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
public class AccountRepository {
    private final JdbcTemplate jdbcTemplate;
    private final QueryTool queryTool;

    @Autowired
    public AccountRepository(JdbcTemplate jdbcTemplate, QueryTool queryTool) {
        this.jdbcTemplate = jdbcTemplate;
        this.queryTool = queryTool;
    }

    public List<Account> readAll(Map<String, String> filter) {
        String sql = "select id, username, name, email, password, created_at, updated_at from account";
        sql = queryTool.addFilterForUnNamedParameters(sql, filter);
        return jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(Account.class), filter.values().toArray());
    }

    public Account readOneByID(UUID ID) {
        String sql = "select id, username, name, email, password, created_at, updated_at from account where id=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Account.class), ID);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account readOneByEmailAndPassword(String email, String password) {
        String sql = "select id, username, name, email, password, created_at, updated_at from account where email=? and password=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Account.class), email, password);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account readOneByEmail(String email) {
        String sql = "select id, username, name, email, password, created_at, updated_at from account where email=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Account.class), email);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Account readOneByUsername(String username) {
        String sql = "select id, username, name, email, password, created_at, updated_at from account where username=?";
        try {
            return jdbcTemplate.queryForObject(sql, BeanPropertyRowMapper.newInstance(Account.class), username);
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Integer createOne(Account accountToCreate) {
        String sql = "insert into account (id, username, name, email, password, created_at, updated_at) values (?, ?, ?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, accountToCreate.getID(), accountToCreate.getUsername(), accountToCreate.getName(), accountToCreate.getEmail(), accountToCreate.getPassword(), accountToCreate.getCreatedAt(), accountToCreate.getUpdatedAt());
    }

    public Integer updateOneByID(UUID ID, Account accountToUpdate) {
        String sql = "update account set username=?, name=?, email=?, password=?, created_at=?, updated_at=? where id=?";
        return jdbcTemplate.update(sql, accountToUpdate.getUsername(), accountToUpdate.getName(), accountToUpdate.getEmail(), accountToUpdate.getPassword(), accountToUpdate.getCreatedAt(), accountToUpdate.getUpdatedAt(), ID);
    }

    public Integer deleteOneByID(UUID ID) {
        String sql = "delete from account where id=?";
        return jdbcTemplate.update(sql, ID);
    }

}
