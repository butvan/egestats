package egestats;
//** ** Created by DeveloperHacker ** **//
//* https://github.com/DeveloperHacker *//

import egestats.builders.Function;
import egestats.builders.Predicate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class Table {

    private Map<Integer, Enrollee> store;
    private List<Integer> ids;

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
            enrolls.add(Enrollee.create(++positionalNum, fields));
        }
        return enrolls;
    }

    public Table(List<Enrollee> enrollees) {
        store = new HashMap<>(enrollees.size());
        ids = new ArrayList<>(enrollees.size());
        for (Enrollee enrollee : enrollees) {
            ids.add(enrollee.positionalNum, enrollee.id);
            store.put(enrollee.id, enrollee);
        }
    }

    public List<Enrollee> upper(int enrolleeId, Predicate<Enrollee> predicate) {
        List<Enrollee> result = new LinkedList<>();
        if (!store.containsKey(enrolleeId)) return result;
        for (int i = 0; i < store.get(enrolleeId).positionalNum; ++i) {
            Enrollee cur = store.get(ids.get(i));
            if (predicate.test(cur)) result.add(cur);
        }
        return result;
    }

    public List<Enrollee> lower(int enrolleeId, Predicate<Enrollee> predicate) {
        List<Enrollee> result = new LinkedList<>();
        if (!store.containsKey(enrolleeId)) return result;
        for (int i = store.get(enrolleeId).positionalNum; i < ids.size(); ++i) {
            Enrollee cur = store.get(ids.get(i));
            if (predicate.test(cur)) result.add(cur);
        }
        return result;
    }

    public List<Enrollee> get(Predicate<Enrollee> predicate) {
        List<Enrollee> enrolls = new LinkedList<>();
        for (Enrollee enrollee : store.values()) {
            if (predicate.test(enrollee)) enrolls.add(enrollee);
        }
        return enrolls;
    }

    public List<List<Float>> get(float lower, float upper, int subjectId) {
        if (lower >= upper) {
            throw new IllegalArgumentException("Table: Wrong lower and upper values");
        }
        if (0 > subjectId || subjectId >= Enrollee.qSubjects) {
            throw new IllegalArgumentException("Table: Wrong subject id");
        }
        List<List<Float>> data = new ArrayList<>(Enrollee.qSubjects);
        for (int i = 0; i < Enrollee.qSubjects; ++i) data.add(new LinkedList<Float>());
        for (Enrollee enrollee : store.values()) {
            float value = enrollee.subjects[subjectId];
            if (lower <= value && value <= upper) {
                for (int i = 0; i < Enrollee.qSubjects; ++i) data.get(i).add(enrollee.subjects[i]);
            }
        }
        return data;
    }
}
