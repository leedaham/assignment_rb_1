package me.hamtom.redblue.assignment.temp;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BulkTest {

    @Autowired BulkInsertRepository bulkInsertRepository;

    @Test
    @Transactional
    @Rollback(false)
    void jdbc_bulk_test() {
        List<Dummy> dummies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Dummy dummy = Dummy.builder()
                    .name("ks"+i)
                    .build();
            dummies.add(dummy);
        }
        bulkInsertRepository.saveAll(dummies);
    }

}