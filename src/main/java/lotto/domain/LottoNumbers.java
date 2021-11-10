package lotto.domain;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LottoNumbers {
    private static final int MAX_SIZE = 6;
    private static final String DEFAULT_DELIMITER = ",";
    private static final List<LottoNumber> DEFAULT_LOTTO_NUMBERS =
            IntStream.rangeClosed(LottoNumber.MIN_NUMBER, LottoNumber.MAX_NUMBER)
                    .mapToObj(LottoNumber::of)
                    .collect(Collectors.toList());

    private final List<LottoNumber> numbers;
    private final Boolean isManual;

    public LottoNumbers(List<LottoNumber> numbers, boolean isManual) {
        validateDuplication(numbers);
        this.isManual = isManual;
        this.numbers = numbers;
    }

    public static LottoNumbers create(Shuffleable shuffler) {
        List<LottoNumber> numbers = new ArrayList<>(DEFAULT_LOTTO_NUMBERS);
        shuffler.shuffle(numbers);
        return new LottoNumbers(numbers.subList(0, MAX_SIZE), false);
    }

    public static LottoNumbers of(String text) {
        String[] numberStrings = text.split(DEFAULT_DELIMITER);
        validateStringNumbersLength(text, numberStrings);
        List<LottoNumber> numbers = new ArrayList<>();
        for (String number : numberStrings) {
            numbers.add(LottoNumber.of(number));
        }
        return new LottoNumbers(numbers, true);
    }

    private static void validateDuplication(List<LottoNumber> numbers) {
        Set<LottoNumber> numberSet = new HashSet<>(numbers);
        if (numberSet.size() != MAX_SIZE) {
            throw new IllegalArgumentException(String.format("'%s'로 나누어지는 %d자리 숫자를 입력하세요", DEFAULT_DELIMITER, MAX_SIZE));
        }
    }

    private static void validateStringNumbersLength(String text, String[] numberStrings) {
        if (text.isEmpty() || numberStrings.length != MAX_SIZE) {
            throw new IllegalArgumentException(String.format("'%s'로 나누어지는 %d자리 숫자를 입력하세요", DEFAULT_DELIMITER, MAX_SIZE));
        }
    }

    public List<LottoNumber> getNumbers() {
        return Collections.unmodifiableList(numbers);
    }

    public boolean contains(LottoNumber lottoNumber) {
        return numbers.contains(lottoNumber);
    }

    public boolean isManual() {
        return this.isManual;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LottoNumbers that = (LottoNumbers) o;
        return Objects.equals(numbers, that.numbers) && Objects.equals(isManual, that.isManual);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numbers, isManual);
    }
}
