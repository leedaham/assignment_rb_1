package me.hamtom.redblue.assignment.reserevation.repository;

import me.hamtom.redblue.assignment.reserevation.entity.GroupLesson;
import me.hamtom.redblue.assignment.reserevation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
