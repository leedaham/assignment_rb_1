package me.hamtom.redblue.assignment.temp;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BulkInsertRepository {

    private final JdbcTemplate jdbcTemplate;

    @Transactional
    public void saveAll(List<Dummy> dummies) {
        jdbcTemplate.batchUpdate("INSERT INTO dummy(name) values (?)",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setString(1, dummies.get(i).getName());
                    }

                    @Override
                    public int getBatchSize() {
                        return dummies.size();
                    }
                });
    }
}
