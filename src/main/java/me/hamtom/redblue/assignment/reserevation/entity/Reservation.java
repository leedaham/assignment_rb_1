package me.hamtom.redblue.assignment.reserevation.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    private int seqSchedule;
    private int ownerId;
    private int seatNumber;
    private LocalDateTime regDtm;
    private int seqTicket;

    public static Reservation createReservation(int seqSchedule, int ownerId, int seatNumber, LocalDateTime regDtm, int seqTicket) {
        Reservation reservation = new Reservation();
        reservation.setSeqSchedule(seqSchedule);
        reservation.setOwnerId(ownerId);
        reservation.setSeatNumber(seatNumber);
        reservation.setRegDtm(regDtm);
        reservation.setSeqTicket(seqTicket);
        return reservation;
    }
}
