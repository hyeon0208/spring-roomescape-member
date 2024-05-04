package roomescape.reservation.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ThemeNameTest {

    @Test
    @DisplayName("테마명에 null을 입력받으면 예외가 발생한다.")
    void createThemeNameByNullTest() {
        assertThatThrownBy(() -> new ThemeName(null))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @DisplayName("테마명에 빈 문자열, 공백 입력시 예외가 발생한다.")
    @ValueSource(strings = {"", " "})
    void createThemeNameByBlankTest(String input) {
        assertThatThrownBy(() -> new ThemeName(input))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("테마명에 공백 포함 한글, 영어, 숫자가 아닌 값을 입력시 예외가 발생한다.")
    void createThemeNameByInvalidFormat() {
        assertThatThrownBy(() -> new ThemeName("공-포"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("테마명이 10글자를 초과하면 예외가 발생한다.")
    void createThemeNameByLengthTest() {
        assertThatThrownBy(() -> new ThemeName("ABCDEFGHIJK"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
