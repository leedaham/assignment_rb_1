package me.hamtom.redblue.assignment.reserevation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Ticket {
    @Id
    private int seqTicket;
    private int reservationUnit;
    private int ownerId;
    private int usedNumber;

    public boolean isUsableTicket() {
        return 0 < (reservationUnit - usedNumber);
    }

    public void useTicket() {
        usedNumber -= 1;
    }

    public static Ticket createTicket(int seqTicket, int reservationUnit, int ownerId, int usedNumber) {
        Ticket ticket = new Ticket();
        ticket.setSeqTicket(seqTicket);
        ticket.setReservationUnit(reservationUnit);
        ticket.setOwnerId(ownerId);
        ticket.setUsedNumber(usedNumber);
        return ticket;
    }
}
