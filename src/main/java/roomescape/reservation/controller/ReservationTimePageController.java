package roomescape.reservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReservationTimePageController {

    @GetMapping("/admin/time")
    public String timePage() {
        return "admin/time";
    }
}
