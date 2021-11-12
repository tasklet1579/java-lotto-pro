package step3;

import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import step3.common.exception.InvalidParamException;
import step3.domain.LottoNumber;
import step3.domain.LottoNumbers;
import step3.domain.WinningLotto;
import step3.domain.constance.LottoConstant;
import step3.domain.factory.LottoNumbersFactory;

public class WinningLottoTest {

    @Test
    @DisplayName("우승 번호에 보너스 번호 포함시 예외 발생")
    void valid() {
        assertThatExceptionOfType(InvalidParamException.class)
            .isThrownBy(() -> {
                // when
                WinningLotto.of(LottoNumbersFactory.createManualLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6)),
                    LottoNumber.of(6));
            }) // then
            .withMessageMatching(LottoConstant.BONUS_NUMBER_ALREADY_EXIST_MESSAGE);
    }

    @ParameterizedTest
    @MethodSource("getContainCountData")
    @DisplayName("우승로또 번호와, 구입한 로또번호 매칭하여 카운팅 테스트")
    void containCount(List<Integer> winningLottoNumbers, LottoNumbers buyLottoNumbers, int bonusNumber,
        int containCountExpected) {

        // when
        WinningLotto winningLotto = WinningLotto.of(
            LottoNumbersFactory.createLottoNumbers(winningLottoNumbers), LottoNumber.of(bonusNumber));

        // then
        assertThat(winningLotto.containCount(buyLottoNumbers)).isEqualTo(containCountExpected);
    }

    @Test
    @DisplayName("보너스 일치 여부 체크")
    void bonusMatch_contain() {
        // given
        LottoNumbers winningLottoNumbers = LottoNumbersFactory.createLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        LottoNumbers buyLottoNumbers = LottoNumbersFactory.createLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 7));

        // when
        WinningLotto winningLotto = WinningLotto.of(winningLottoNumbers, LottoNumber.of(7));

        // then
        assertThat(winningLotto.bonusMatch(buyLottoNumbers)).isEqualTo(true);
    }

    @Test
    @DisplayName("보너스 미일치 체크")
    void bonusMatch_not_contain() {
        // given
        LottoNumbers winningLottoNumbers = LottoNumbersFactory.createLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 6));
        LottoNumbers buyLottoNumbers = LottoNumbersFactory.createLottoNumbers(Arrays.asList(1, 2, 3, 4, 5, 7));

        // when
        WinningLotto winningLotto = WinningLotto.of(winningLottoNumbers, LottoNumber.of(45));

        // then
        assertThat(winningLotto.bonusMatch(buyLottoNumbers)).isEqualTo(false);
    }

    private static Stream<Arguments> getContainCountData() {
        return Stream.of(
            Arguments.of( // 1등
                Arrays.asList(1, 2, 3, 4, 5, 6),
                LottoNumbers.of(LottoNumbersFactory.createManualLottoNumbersToList(Arrays.asList(1, 2, 3, 4, 5, 6))),
                45,
                6
            ),
            Arguments.of( // 2등
                Arrays.asList(1, 2, 3, 4, 5, 6),
                LottoNumbers.of(LottoNumbersFactory.createManualLottoNumbersToList(Arrays.asList(1, 2, 3, 4, 5, 7))),
                7,
                5
            ),
            Arguments.of( // 3등
                Arrays.asList(1, 2, 3, 4, 5, 6),
                LottoNumbers.of(LottoNumbersFactory.createManualLottoNumbersToList(Arrays.asList(1, 2, 3, 4, 5, 7))),
                45,
                5
            ),
            Arguments.of( // 4등
                Arrays.asList(1, 2, 3, 4, 5, 6),
                LottoNumbers.of(LottoNumbersFactory.createManualLottoNumbersToList(Arrays.asList(1, 2, 3, 4, 8, 7))),
                45,
                4
            )
        );
    }
}