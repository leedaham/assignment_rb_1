package me.hamtom.redblue.assignment.reserevation;

import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.reserevation.entity.GroupLesson;
import me.hamtom.redblue.assignment.reserevation.entity.Ticket;
import me.hamtom.redblue.assignment.reserevation.mq.MessageDto;
import me.hamtom.redblue.assignment.reserevation.mq.ProducerService;
import me.hamtom.redblue.assignment.reserevation.repository.GroupLessonRepository;
import me.hamtom.redblue.assignment.reserevation.repository.TicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationRequestService {

    private final TicketRepository ticketRepository;
    private final GroupLessonRepository groupLessonRepository;

    private final ProducerService producerService;

    @Transactional(readOnly = true)
    public boolean reservationRequest(ReservationDto reservationDto) {
        Ticket ticket = ticketRepository.findById(reservationDto.getSeqTicket()).get();
        checkTicket(ticket);
        GroupLesson groupLesson = groupLessonRepository.findById(reservationDto.getSeqGroupLesson()).get();
        checkGroupTakable(groupLesson);
        producerService.sendMessageQueue(new MessageDto());
        return true;
    }

    private void checkTicket(Ticket ticket) {
        if(!ticket.isUsableTicket()){
            throw new RuntimeException();
        }
    }

    private void checkGroupTakable(GroupLesson groupLesson) {
        if(!groupLesson.isTakableLesson()){
            throw new RuntimeException();
        }
    }
}

