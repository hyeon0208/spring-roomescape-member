package roomescape.time.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.domain.Name;
import roomescape.reservation.domain.Reservation;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.time.domain.ReservationTime;

@JdbcTest
@Import({ReservationTimeRepository.class, ReservationRepository.class})
class ReservationTimeRepositoryTest {

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    @DisplayName("id 로 엔티티를 찾는다.")
    void findByIdTest() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.now());
        Long timeId = reservationTimeRepository.save(reservationTime);
        ReservationTime findReservationTime = reservationTimeRepository.findById(timeId).get();

        assertThat(findReservationTime.getId()).isEqualTo(timeId);
    }

    @Test
    @DisplayName("전체 엔티티를 조회한다.")
    void findAllTest() {
        ReservationTime reservationTime1 = new ReservationTime(LocalTime.now());
        ReservationTime reservationTime2 = new ReservationTime(LocalTime.now());
        reservationTimeRepository.save(reservationTime1);
        reservationTimeRepository.save(reservationTime2);
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("해당 시간을 참조하는 Reservation이 있는지 찾는다.")
    void findReservationInSameIdTest() {
        Long timeId = reservationTimeRepository.save(new ReservationTime(LocalTime.now()));
        ReservationTime reservationTime = reservationTimeRepository.findById(timeId).get();
        Reservation reservation = new Reservation(new Name("kaki"), LocalDate.now(), reservationTime);
        Long reservationId = reservationRepository.save(reservation);
        boolean exist = reservationTimeRepository.findReservationInSameId(timeId).isPresent();

        assertThat(exist).isTrue();
    }

    @Test
    @DisplayName("id를 받아 삭제한다.")
    void deleteTest() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.now());
        Long timeId = reservationTimeRepository.save(reservationTime);
        reservationTimeRepository.delete(timeId);
        List<ReservationTime> reservationTimes = reservationTimeRepository.findAll();

        assertThat(reservationTimes.size()).isEqualTo(0);
    }
}