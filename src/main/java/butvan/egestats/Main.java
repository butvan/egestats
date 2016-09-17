package butvan.egestats;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.OptionalDouble;

public class Main {
    public static void main(String[] args) throws IOException {
        Files.lines(Paths.get("data.txt"))
                .filter(w -> Double.parseDouble(w.split(",")[2]) == 84)
                .forEach(System.out::println);
        OptionalDouble avg = Files.lines(Paths.get("data.txt"))
                .filter(w -> Double.parseDouble(w.split(",")[2]) == 84)
                .mapToDouble(w -> Double.parseDouble(w.split(",")[1]))
                .average();
        System.out.println(avg.isPresent() ? "\nAverage for above: " + avg.getAsDouble() : "No matched lines");
    }
}