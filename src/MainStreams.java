import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 1, 1, 1, 1, 1, 1);
        oddOrEven(integers).forEach(System.out::println);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().reduce(0, Integer::sum) % 2 == 0 ?
                integers.stream().filter(v -> v % 2 != 0).collect(Collectors.toList()) :
                integers.stream().filter(v -> v % 2 == 0).collect(Collectors.toList());
    }

    private static int minValue(int[] values) {
        return 0; //todo
    }
}
