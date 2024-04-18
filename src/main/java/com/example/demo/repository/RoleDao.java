package com.example.demo.repository;

import com.example.demo.domain.Role;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.simple.SimpleJdbcInsertOperations;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

// Spring JDBC를 이용해서 Database 프로그래밍
// @Repository는 @Component 이고 컨테이너가 관리하는 Bean이 된다.
@Repository
public class RoleDao {
    private final NamedParameterJdbcTemplate jdbcTemplate; // 필드를 final로 선언하면 반드시 생성자에서 초기화 한다.

    private SimpleJdbcInsertOperations insertAction; // insert를 쉽게 하도록 도와주는 인터페이스

    // 생성자에 파라미터를 넣어주면 스프링 부트가 자동으로 주입한다. 생성자 주입.
    public RoleDao(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource); // DataSource를 넣어줘야 한다.
        insertAction = new SimpleJdbcInsert(dataSource)
                .withTableName("role"); // insert할 테이블 이름을 넣어준다.
    }

    // Role 테이블에 한건 저장, 저장을 성공하면 true, 실패하면 false를 반환한다.
    public boolean addRole(Role role) {
//        String sql = "INSERT INTO role(role_id, name) VALUES(?, ?)";
//        int result = jdbcTemplate.update(sql, role.getRoleId(), role.getName()); // update 메소드는 insert, update, delete SQL문을 실행할 때 사용한다.
//        return result == 1;

        // role은 프로퍼티 roleId, name
        // INSERT INTO role(role_id, name) VALUES(:roleId, :name)
        // 위와 같은 SQL을 SimpleJdbcInsert가 내부적으로 만든다.
        // Role클래스의 프로퍼티이름과 칼럼명이 규칙이 맞아야 한다. 예> role_id 칼럼명 = roleId 프로퍼티
        SqlParameterSource params = new BeanPropertySqlParameterSource(role); // role 객체의 필드명과 테이블의 컬럼명이 같아야 한다.
        int result = insertAction.execute(params);
        return result == 1;
    }

    public boolean deleteRole(int roleId) {
        String sql = "DELETE FROM role WHERE role_id = :roleId";
        SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
        int result = jdbcTemplate.update(sql, params);
        return result == 1;
    }

    public Role getRole(int roleId) {
        String sql = "SELECT role_id, name FROM role WHERE role_id = :roleId"; // role_id는 PK이기에 1건 또는 0건의 데이터가 조회된다.
        // queryForObject는 1건 또는 0건을 읽어보는 메소드
        // queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
        SqlParameterSource params = new MapSqlParameterSource("roleId", roleId);
        RowMapper<Role> roleRowMapper = BeanPropertyRowMapper.newInstance(Role.class);
        return jdbcTemplate.queryForObject(sql, params, roleRowMapper);
    }

    public List<Role> getRoles() {
        String sql = "SELECT role_id, name FROM role";
        // query메소드는 여러건의 결과를 구할 때 사용하는 메소드
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Role role = new Role();
            role.setRoleId(rs.getInt("role_id"));
            role.setName(rs.getString("name"));
            return role;
        });
    }
}
