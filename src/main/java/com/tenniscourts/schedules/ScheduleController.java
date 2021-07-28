package com.tenniscourts.schedules;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.exceptions.EntityNotFoundException;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
public class ScheduleController extends BaseRestController {

	@Autowired
    private final ScheduleService scheduleService;

    @PostMapping("/createSchedule")
    @ApiOperation(value = "creates a schedule based on the given tennis court id and time", response = ScheduleDTO.class)
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully created schedule"),	       
	        @ApiResponse(code = 404, message = "Invalid court id, no tennis court exists with the provided id")
	})
    public ResponseEntity<ScheduleDTO> addScheduleTennisCourt(@RequestBody CreateScheduleRequestDTO createScheduleRequestDTO) {
    	try {
    		ScheduleDTO scheduleDto= scheduleService.addSchedule(createScheduleRequestDTO.getTennisCourtId(), createScheduleRequestDTO);
    		return new ResponseEntity<ScheduleDTO>(scheduleDto, HttpStatus.CREATED);
    	} catch(Exception e) {
    		throw new EntityNotFoundException("No tennis court exists with the id: "+createScheduleRequestDTO.getTennisCourtId());
    	}
    }

    //TODO: implement rest and swagger
    public ResponseEntity<List<ScheduleDTO>> findSchedulesByDates(LocalDate startDate,
                                                                  LocalDate endDate) {
        return ResponseEntity.ok(scheduleService.findSchedulesByDates(LocalDateTime.of(startDate, LocalTime.of(0, 0)), LocalDateTime.of(endDate, LocalTime.of(23, 59))));
    }

    //TODO: implement rest and swagger
    public ResponseEntity<ScheduleDTO> findByScheduleId(Long scheduleId) {
        return ResponseEntity.ok(scheduleService.findSchedule(scheduleId));
    }
}
