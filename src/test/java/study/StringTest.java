package study;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class StringTest {
    @DisplayName("\"1,2\"을 ,로 split 했을 때 1과 2로 분리되는지 확인한다.")
    @Test
    void splitByComma() {
        // given
        String input = "1,2";

        // when
        String[] actual = input.split(",");

        // then
        assertThat(actual).contains("1", "2");
    }

    @DisplayName("\"1\"을 ,로 split 했을 때 1만을 포함하는 배열이 반환되는지 확인한다.")
    @Test
    void containsExactly() {
        // given
        String input = "1";

        // when
        String[] actual = input.split(",");

        // then
        assertThat(actual).containsExactly("1");
    }

    @DisplayName("\"(1,2)\" 값이 주어졌을 때 ()을 제거하고 \"1,2\"를 반환한다.")
    @Test
    void substring() {
        // given
        String input = "(1,2)";

        // when
        String actual = input.substring(1, input.length() - 1);

        // then
        assertThat(actual).isEqualTo("1,2");
    }

    @DisplayName("\"abc\" 값이 주어졌을 때 특정 위치의 문자를 가져오도록 한다.")
    @ParameterizedTest
    @CsvSource(value = {"0:a", "1:b", "2:c"}, delimiter = ':')
    void charAt(int index, char expected) {
        // given
        String input = "abc";

        // when
        char actual = input.charAt(index);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("특정 위치의 문자를 가져올 때 위치 값을 벗어나면 StringIndexOutOfBoundsException 이 발생한다.")
    @Test
    void charAt_throwException_givenIndexGreaterThanLength() {
        // given
        String input = "abc";

        // when & then
        assertThatThrownBy(() -> input.charAt(3)).isInstanceOf(StringIndexOutOfBoundsException.class)
                                                 .hasMessageContaining("String index out of range: 3");

        // when & then
        assertThatExceptionOfType(StringIndexOutOfBoundsException.class)
                .isThrownBy(() -> input.charAt(3))
                .withMessageMatching("String index out of range: \\d+");
    }
}
