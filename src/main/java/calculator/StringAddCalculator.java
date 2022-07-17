package calculator;

public class StringAddCalculator {
    public static int splitAndSum(String input) {
        return new Numbers(StringSeparator.split(StringUtils.readString(input))).sum();
    }
}
