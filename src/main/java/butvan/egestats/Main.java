package butvan.egestats;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class Main {
    private static final String URL = "https://cabinet.spbu.ru/Lists/1k_EntryLists/list_97a05ded-f28d-4929-aead-b6dc7cfc9f99.html";

    public static void main(String[] args) throws IOException {
        String enrolleeId = args[0];
        Document doc = Jsoup.connect(URL).get();

        boolean found = false;
        String fio = "";
        int origCount = 1;
        int origWithPriorityCount = 1;

        for (Element trEl : doc.select("tbody").select("tr")) {
            Elements tdEls = trEl.select("td");
            Element nameEl = tdEls.get(2);
            if (nameEl.select("a").first().attr("name").trim().equals(enrolleeId)) {
                fio = nameEl.html().replaceFirst("<a.*>", "");
                found = true;
                break;
            }
            if (!tdEls.get(12).html().trim().equals("Да"))
                continue;
            if (Integer.valueOf(tdEls.get(5).html()) == 1)
                origWithPriorityCount++;
            origCount++;
        }

        if (found) {
            System.out.println(fio);
            System.out.println("Место в списке сдавших оригинал: " + origCount);
            System.out.println("Место в списке сдавших оригинал c приоритетом 1: " + origWithPriorityCount);
        } else {
            System.out.println("Абитуриент с идентификатом " + enrolleeId + " не найден");
        }
    }
}
