package me.hamtom.redblue.assignment.bulk.repository;

import me.hamtom.redblue.assignment.bulk.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
