package egestats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    private static final float DEFAULT_LOWER = 200f;
    private static final float DEFAULT_UPPER = 300f;
    private static final float DEFAULT_STEP = 10f;

    public static void main(String[] args) throws IOException {
        try {
            if (args.length == 0 || (args.length == 1 && (args[0].equals("-h") || args[0].equals("--help")))) {
                System.out.println("egestats-\\.*.jar <direct> [<lower> <upper> <step>]");
                return;
            } else if (args.length != 1 && args.length != 4) {
                throw new IllegalArgumentException("Main: Incorrect input: args.size");
            }
            String direct = args[0];
            String url;
            if (direct.equals("mathematics"))
                url = "https://cabinet.spbu.ru/Lists/1k_EntryLists/list_a9c0349c-00f4-4801-9bb3-80cecc0a53e8.html";
            else if (direct.equals("physics"))
                url = "https://cabinet.spbu.ru/Lists/1k_EntryLists/list_462f7f85-e9fc-4535-a90b-b4562766bbc7.html";
            else if (direct.equals("informatics"))
                url = "https://cabinet.spbu.ru/Lists/1k_EntryLists/list_97a05ded-f28d-4929-aead-b6dc7cfc9f99.html";
            else
                throw new IllegalArgumentException("Main: Undefined input direct: " + direct);
            float lower = DEFAULT_LOWER;
            float upper = DEFAULT_UPPER;
            float step = DEFAULT_STEP;
            if (args.length == 4) {
                lower = Parser.parseFloat(args[1]);
                upper = Parser.parseFloat(args[2]);
                step = Parser.parseFloat(args[3]);
            }
            Table table = new Table(Parser.parseTable(url));
            printTable(table, direct, lower, upper, step);
        } catch (IOException ex) {
            System.out.println("Main: Connection error");
        } catch (NumberFormatException ex) {
            System.out.println("Main: Incorrect input: data is not integer");
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void printTable(Table table, String direct, float lower, float upper, float step) {
        if (direct.equals("mathematics"))
            System.out.println("Range   Length   Mathematics   Informatics   Russian");
        else if (direct.equals("physics"))
            System.out.println("Range   Length   Physics   Mathematics   Russian");
        else if (direct.equals("informatics"))
            System.out.println("Range   Length   Mathematics   Informatics   Russian");
        int columns = (int) Math.floor((upper - lower) / step);
        List<List<Enrollee>> groups = new ArrayList<>();
        for (int i = 0; i < columns; ++i) groups.add(new LinkedList<Enrollee>());
        for (Enrollee enrollee : table.enrolls()) {
            int index = Math.round((enrollee.sMain - lower) / step);
            if (0 <= index && index < columns) groups.get(index).add(enrollee);
        }
        for (int i = 0; i < columns; ++i) {
            List<Enrollee> group = groups.get(i);
            float left = i * step + lower;
            float right = left + step;
            System.out.print(String.format("%3.1f-%3.1f    %3d", left, right, group.size()));
            for (int j = 0; j < Enrollee.qSubjects; ++j) {
                float sum = 0;
                for (Enrollee enrollee : group) sum += enrollee.subjects[j];
                float mean = sum / group.size();
                System.out.print(String.format("    %3.1f", mean));
            }
            System.out.println();
        }
    }
}
