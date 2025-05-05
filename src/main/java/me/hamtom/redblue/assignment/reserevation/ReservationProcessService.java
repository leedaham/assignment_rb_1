package me.hamtom.redblue.assignment.reserevation;

import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.reserevation.entity.GroupLesson;
import me.hamtom.redblue.assignment.reserevation.entity.Reservation;
import me.hamtom.redblue.assignment.reserevation.entity.Ticket;
import me.hamtom.redblue.assignment.reserevation.mq.MessageDto;
import me.hamtom.redblue.assignment.reserevation.mq.ProducerService;
import me.hamtom.redblue.assignment.reserevation.repository.GroupLessonRepository;
import me.hamtom.redblue.assignment.reserevation.repository.ReservationRepository;
import me.hamtom.redblue.assignment.reserevation.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReservationProcessService {

    private final TicketRepository ticketRepository;
    private final GroupLessonRepository groupLessonRepository;
    private final ReservationRepository reservationRepository;


    @Transactional
    public boolean reservationProcess(ReservationDto reservationDto) {
        Ticket ticket = ticketRepository.findById(reservationDto.getSeqTicket()).get();
        GroupLesson groupLesson = groupLessonRepository.findById(reservationDto.getSeqGroupLesson()).get();

        ticket.useTicket();
        groupLesson.takeLesson();
        Reservation reservation = Reservation.createReservation(0, reservationDto.getOwnerId(), groupLesson.getOccupiedNumber(), LocalDateTime.now(), ticket.getSeqTicket());
        reservationRepository.save(reservation);
        return true;
    }




}

