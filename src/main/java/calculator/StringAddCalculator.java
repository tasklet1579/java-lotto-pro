package calculator;

public class StringAddCalculator {
    public static int splitAndSum(String input) {
        String[] split = StringSeparator.split(StringReader.readString(input));
        Numbers numbers = new Numbers();
        numbers.makeNumbers(split);
        return numbers.sum();
    }
}
