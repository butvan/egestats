package butvan.egestats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> lines = Files.lines(Paths.get("data.txt"))
                .filter(w -> Double.parseDouble(w.split(",")[2]) == 84)
                .collect(Collectors.toList());
        lines.stream().forEach(System.out::println);
        OptionalDouble avg = lines.stream().mapToDouble(w -> Double.parseDouble(w.split(",")[1])).average();
        System.out.println(avg.isPresent() ? "\nAverage for above: " + avg.getAsDouble() : "No matched lines");
    }
}