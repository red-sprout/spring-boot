package com.example.demo.repository;

import com.example.demo.domain.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class UserDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private SimpleJdbcInsertOperations insertOperations;

    public UserDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        insertOperations = new SimpleJdbcInsert(dataSource).withTableName("user");
    }

    public boolean insertUser(User user) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(user);
        int result = insertOperations.execute(params);
        return result == 1;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE user SET email = :email, name = :name, password = :password WHERE user_id = :userId";
        Map<String, Object> params = new HashMap<>();
        params.put("email", user.getEmail());
        params.put("name", user.getName());
        params.put("password", user.getPassword());
        params.put("userId", user.getUserId());
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE user_id = :userId";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    public User getUser(int userId) {
        String sql = "SELECT user_id, email, name, password, regdate FROM user WHERE user_id = :userId";
        SqlParameterSource params = new MapSqlParameterSource("userId", userId);
        RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
        return jdbcTemplate.queryForObject(sql, params, userRowMapper);
    }

    public List<User> getUsers() {
        String sql = "SELECT user_id, email, name, password, regdate FROM user";
        RowMapper<User> userRowMapper = BeanPropertyRowMapper.newInstance(User.class);
        return jdbcTemplate.query(sql, userRowMapper);
    }
}
