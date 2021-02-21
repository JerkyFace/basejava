import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        oddOrEven(integers).forEach(System.out::print);

        System.out.println();

        int[] array = {7, 7, 2, 4, 4, 3}; //2347
        System.out.println(minValue(array));
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        boolean isEven = integers.stream().reduce(0, Integer::sum) % 2 == 0;
        return integers.stream().filter(v -> {
            if (isEven) {
                return v % 2 != 0;
            }
            return v % 2 == 0;
        }).collect(Collectors.toList());

    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce((a, b) -> a * 10 + b).orElse(0);
    }
}
