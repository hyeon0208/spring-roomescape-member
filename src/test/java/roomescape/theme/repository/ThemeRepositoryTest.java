package roomescape.theme.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import roomescape.reservation.domain.Name;
import roomescape.theme.domain.Theme;

@JdbcTest
@Import(ThemeRepository.class)
class ThemeRepositoryTest {

    @Autowired
    private ThemeRepository themeRepository;

    @Test
    @DisplayName("id 로 엔티티를 찾는다.")
    void findByIdTest() {
        Theme theme = new Theme(new Name("공포"), "무서운 테마", "https://i.pinimg.com/236x.jpg");
        Long themeId = themeRepository.save(theme);
        Theme findTheme = themeRepository.findById(themeId).get();

        assertThat(findTheme.getId()).isEqualTo(themeId);
    }

    @Test
    @DisplayName("전체 엔티티를 조회한다.")
    void findAllTest() {
        Theme theme1 = new Theme(new Name("공포"), "무서운 테마", "https://i.pinimg.com/236x.jpg");
        Theme theme2 = new Theme(new Name("SF"), "미래 테마", "https://i.pinimg.com/123x.jpg");
        themeRepository.save(theme1);
        themeRepository.save(theme2);
        List<Theme> themes = themeRepository.findAll();

        assertThat(themes.size()).isEqualTo(2);
    }

    @Test
    @Sql(scripts = "classpath:data.sql")
    @DisplayName("예약이 많은 순으로 10개의 테마를 조회한다.")
    void findPopularThemeLimitTen() {
        List<Theme> themes = themeRepository.findPopularThemeLimitTen();

        assertAll(
                () -> assertThat(themes.get(0).getName()).isEqualTo("c"),
                () -> assertThat(themes.size()).isEqualTo(10)
        );
    }

    @Test
    @DisplayName("id를 받아 삭제한다.")
    void deleteTest() {
        Theme theme = new Theme(new Name("공포"), "무서운 테마", "https://i.pinimg.com/236x.jpg");
        Long themeId = themeRepository.save(theme);
        themeRepository.delete(themeId);
        List<Theme> themes = themeRepository.findAll();

        assertThat(themes.size()).isEqualTo(0);
    }
}
