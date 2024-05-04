package roomescape.reservation.controller;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.reservation.dto.TimeResponse;
import roomescape.reservation.dto.TimeSaveRequest;
import roomescape.reservation.service.ReservationTimeService;

@RestController
public class ReservationTimeApiController {

    private final ReservationTimeService reservationTimeService;

    public ReservationTimeApiController(ReservationTimeService reservationTimeService) {
        this.reservationTimeService = reservationTimeService;
    }

    @GetMapping("/times")
    public ResponseEntity<List<TimeResponse>> findAll() {
        List<TimeResponse> times = reservationTimeService.findAll();

        return ResponseEntity.ok(times);
    }

    @PostMapping("/times")
    public ResponseEntity<TimeResponse> save(@Valid @RequestBody TimeSaveRequest timeSaveRequest) {
        Long saveId = reservationTimeService.save(timeSaveRequest);
        TimeResponse timeResponse = reservationTimeService.findById(saveId);

        return ResponseEntity.created(URI.create("/times/" + saveId)).body(timeResponse);
    }

    @DeleteMapping("/times/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservationTimeService.delete(id);

        return ResponseEntity.noContent().build();
    }
}
