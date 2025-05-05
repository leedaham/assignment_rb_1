package me.hamtom.redblue.assignment.reserevation.repository;

import me.hamtom.redblue.assignment.reserevation.entity.GroupLesson;
import me.hamtom.redblue.assignment.reserevation.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
