package roomescape.reservation.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import roomescape.reservation.dto.RequestReservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.repository.ReservationTimeRepository;

@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private ReservationTimeRepository reservationTimeRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("존재하지 않는 시간에 예약을 하면 예외가 발생한다.")
    void saveExceptionTest() {
        Long timeId = 1L;

        doReturn(Optional.empty()).when(reservationTimeRepository)
                .findById(timeId);

        RequestReservation requestReservation = new RequestReservation("hogi", LocalDate.now(), timeId);

        assertThatThrownBy(() -> reservationService.save(requestReservation))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("존재하지 않는 시간에 예약 아이디일 경우 예외가 발생한다.")
    void findByIdExceptionTest() {
        Long reservationId = 1L;

        doReturn(Optional.empty()).when(reservationRepository)
                .findById(reservationId);

        assertThatThrownBy(() -> reservationService.findById(reservationId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
