package butvan.egestats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        List<List<String>> lines = Files.lines(Paths.get("data.txt"))
                .map(w -> Arrays.asList(w.split(",")))
                .filter(w -> Double.parseDouble(w.get(2)) == 84)
                .collect(Collectors.toList());
        lines.stream().map(w -> String.join(",", w)).forEach(System.out::println);
        OptionalDouble avg = lines.stream().mapToDouble(w -> Double.parseDouble(w.get(1))).average();
        System.out.println(avg.isPresent() ? "\nAverage for above: " + avg.getAsDouble() : "No matched lines");
    }
}