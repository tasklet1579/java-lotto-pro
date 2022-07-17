package study;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SetTest {
    private Set<Integer> numbers;

    @BeforeEach
    void setUp() {
        numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
    }

    @DisplayName("Set 의 size() 메소드를 활용해 Set 의 크기를 확인한다.")
    @Test
    void size() {
        // given
        int expected = 3;

        // when & then
        assertThat(numbers).hasSize(expected);
    }

    @DisplayName("Set 의 contains() 메소드를 활용해 1, 2, 3의 값이 존재하는지를 확인한다.")
    @Test
    void contains() {
        // when & then
        assertThat(numbers.contains(1)).isTrue();
        assertThat(numbers.contains(2)).isTrue();
        assertThat(numbers.contains(3)).isTrue();
    }

    @DisplayName("ParameterizedTest 를 활용해 중복 코드를 제거해 본다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void parameterizedTest(int expected) {
        // when & then
        assertThat(numbers.contains(expected)).isTrue();
    }

    @DisplayName("입력 값에 따라 결과 값이 다른 경우에 대한 테스트도 가능하도록 한다.")
    @ParameterizedTest
    @CsvSource(value = {"1:true", "2:true", "3:true", "4:false", "5:false"}, delimiter = ':')
    void parameterizedTest(int input, boolean expected) {
        assertThat(numbers.contains(input)).isEqualTo(expected);
    }
}
