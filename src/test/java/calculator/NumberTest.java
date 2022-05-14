package calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberTest {
    @DisplayName("양의 정수, 0 입력")
    @ParameterizedTest
    @ValueSource(ints = {112, 119, 0, 1234567890})
    void POSITIVE_INTEGER_AND_ZERO(final int number) {
        assertThatCode(() -> new Number(number)).doesNotThrowAnyException();
    }

    @DisplayName("음의 정수 입력")
    @ParameterizedTest
    @ValueSource(ints = {-1, -2, -3, -1})
    void NEGATIVE_INTEGER(final int number) {
        assertThatThrownBy(() -> new Number(number))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("파싱 에러")
    @ParameterizedTest
    @ValueSource(strings = {"abc1", "가나다라"})
    void INTEGER_PARSING_ERROR(final String number) {
        assertThatThrownBy(() -> new Number(number))
                .isInstanceOf(NumberFormatException.class);
    }
}