package egestats;

//** ** Created by DeveloperHacker ** **//
//* https://github.com/DeveloperHacker *//

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    public static List<Enrollee> parseTable(String url) throws IOException {
        List<Enrollee> enrolls = new LinkedList<>();
        Document doc = Jsoup.connect(url).get();
        Elements lines = doc.getElementsByTag("tr");
        int positionalNum = -1;
        for (Element line : lines) {
            if (line.getElementsByTag("th").size() != 0) continue;
            Elements cells = line.getElementsByTag("td");
            List<String> fields = new LinkedList<>();
            for (Element cell : cells) fields.add(cell.text().trim());
            enrolls.add(parseEnrollee(++positionalNum, fields));
        }
        return enrolls;
    }

    public static Enrollee parseEnrollee(int positionalNum, List<String> fields) {
        if (fields.size() != Enrollee.qComponents) {
            throw new IllegalArgumentException("Enrollee : wrond columns in table except " + Enrollee.qComponents + " but " + fields.size());
        }
        try {
            int i = -1;
            int serialNum = getInt(fields.get(++i));
            int id = getInt(fields.get(++i));
            String name = fields.get(++i);
            String birthday = fields.get(++i);
            String type = fields.get(++i);
            int priority = getInt(fields.get(++i));
            float sCommon = getFloat(fields.get(++i));
            float sMain = getFloat(fields.get(++i));
            float[] subjects = new float[Enrollee.qSubjects];
            for (int j = 0; j < Enrollee.qSubjects; ++j) subjects[j] = getFloat(fields.get(++i));
            float sExtra = getFloat(fields.get(++i));
            String originals = fields.get(++i);
            String achievements = fields.get(++i);
            String notes = fields.get(++i);
            return new Enrollee(positionalNum, serialNum, id, name, birthday, type, priority, sCommon, sMain, subjects, sExtra, originals, achievements, notes);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Enrollee: Wrong data value in row = " + fields.get(0) + " and posNum = " + positionalNum);
        }
    }

    public static int getInt(String str) {
        return (str.length() == 0) ? 0 : parseInt(str);
    }

    public static float getFloat(String str) {
        return (str.length() == 0) ? 0f : parseFloat(str.replace(',', '.'));
    }

    public static int parseInt(String str) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(str);
        List<Integer> ints = new LinkedList<>();
        while (matcher.find()) ints.add(Integer.valueOf(matcher.group()));
        if (ints.size() == 1) return ints.get(0);
        throw new NumberFormatException();
    }

    public static float parseFloat(String str) {
        Pattern pattern = Pattern.compile("\\d*\\.\\d+");
        Matcher matcher = pattern.matcher(str);
        List<Float> floats = new LinkedList<>();
        while (matcher.find()) floats.add(Float.valueOf(matcher.group()));
        switch (floats.size()) {
            case 0: return parseInt(str);
            case 1: return floats.get(0);
            default:
                throw new NumberFormatException();
        }
    }
}
