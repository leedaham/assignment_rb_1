package me.hamtom.redblue.assignment.reserevation;

import lombok.RequiredArgsConstructor;
import me.hamtom.redblue.assignment.common.response.Result;
import me.hamtom.redblue.assignment.common.response.SuccessResult;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationRequestService reservationRequestService;

    @PostMapping("/api/reservation")
    public ResponseEntity<Result> reservation(
                                                @RequestParam int ownerId,
                                                @RequestParam int seqTicket,
                                                @RequestParam int seqGroupLesson) {

        ReservationDto reservationDto = new ReservationDto(ownerId, seqTicket, seqGroupLesson);
        boolean b = reservationRequestService.reservationRequest(reservationDto);


        return ResponseEntity.ok(new SuccessResult(b));
    }

}
