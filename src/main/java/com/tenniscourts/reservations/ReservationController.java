package com.tenniscourts.reservations;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping("/bookReservation")
    @ApiOperation(value = "books a reservation for a specific guest at a specified schedule", response = Long.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Booking successful"),	       
	        @ApiResponse(code = 404, message = "Invalid guest/schedule id")
	})
    public ResponseEntity<Long> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    public ResponseEntity<ReservationDTO> findReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }
    
    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @DeleteMapping("/rescheduleReservation")
    @ApiOperation(value = "reschedules a reservation with id to a given schedule id", response = ReservationDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Cancellation successful"),	       
	        @ApiResponse(code = 404, message = "Invalid reservation id or cannot reschedule to the same schedule")
	})
    public ResponseEntity<ReservationDTO> rescheduleReservation(@RequestParam Long reservationId, @RequestParam  Long scheduleId) {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
