package me.hamtom.redblue.assignment.reserevation.mq;

import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.reserevation.ReservationDto;
import me.hamtom.redblue.assignment.reserevation.ReservationProcessService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsumerService {

    private final ReservationProcessService reservationProcessService;

    public void recieveMessage(String msg) {
        ReservationDto reservationDto = ReservationDto.fromJson(msg);
        boolean b = reservationProcessService.reservationProcess(reservationDto);
    }
}
