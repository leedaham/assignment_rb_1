package me.hamtom.redblue.assignment.bulk.repository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.bulk.entity.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserBatchRepository {
    private final JdbcTemplate jdbcTemplate;

        @Transactional
        public void saveAll(List<User> userList) {
            String sql = "INSERT INTO users (name) VALUES (?)";
            jdbcTemplate.batchUpdate(
                    sql,
                    userList,
                    500,
                    (PreparedStatement ps, User user) -> {
                        ps.setString(1, user.getName());
                    });
        }
}
