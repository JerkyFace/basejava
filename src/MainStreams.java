import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    private static final int DECIMAL_DIVIDER = 10;

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 1, 4, 1, 2, 1); //1111
        oddOrEven(integers).forEach(System.out::print);

        System.out.println();

        int[] array = {7, 7, 2, 4, 4, 3}; //2347
        System.out.println(minValue(array));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().reduce(0, Integer::sum) % 2 == 0 ?
                integers.stream().filter(v -> v % 2 != 0).collect(Collectors.toList()) :
                integers.stream().filter(v -> v % 2 == 0).collect(Collectors.toList());
    }

    private static int minValue(int[] values) {
        int minValue = 0;
        int[] arrayWithoutDublicates = Arrays.stream(values).distinct().sorted().toArray();
        int power = (int) Math.pow(DECIMAL_DIVIDER, arrayWithoutDublicates.length - 1);
        for (int value : arrayWithoutDublicates) {
            minValue += value * power;
            power /= DECIMAL_DIVIDER;
        }
        return minValue;
    }
}
