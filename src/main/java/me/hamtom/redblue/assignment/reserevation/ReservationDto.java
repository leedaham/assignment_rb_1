package me.hamtom.redblue.assignment.reserevation;

import lombok.Data;

@Data
public class ReservationDto {
    private int ownerId;
    private int seqTicket;
    private int seqGroupLesson;

    public ReservationDto(int ownerId, int seqTicket, int seqGroupLesson) {
        this.ownerId = ownerId;
        this.seqTicket = seqTicket;
        this.seqGroupLesson = seqGroupLesson;
    }

    public static ReservationDto fromJson(String jsonStr) {
        return null;
    }
}
