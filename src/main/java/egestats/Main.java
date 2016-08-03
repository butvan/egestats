package egestats;

import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        try {
            if (args.length != 2 && args.length != 5) {
                throw new IllegalArgumentException("Main: Incorrect input: args.size");
            }
            String url = args[0];
            int subjectsId = Integer.parseInt(args[1]);
            float lower = 50;
            float upper = 100;
            int qColumns = 10;
            if (args.length == 5) {
                lower = Float.parseFloat(args[2]);
                upper = Float.parseFloat(args[3]);
                qColumns = Integer.parseInt(args[4]);
            }
            Table table = new Table(Table.parseTable(url));
            List<List<Float>> data = table.get(lower, upper, subjectsId);
            for (int i = 0; i < Enrollee.qSubjects; ++i) {
                if (i == subjectsId) System.out.println(String.format("%.2f", Histogram.mean(data.get(i))));
                else System.out.println(new Histogram(data.get(i), qColumns).absolute());
            }
        } catch (IOException ex) {
            System.out.println("Main: Connection error");
        } catch (NumberFormatException ex) {
            System.out.println("Main: Incorrect input: data is not integer");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
