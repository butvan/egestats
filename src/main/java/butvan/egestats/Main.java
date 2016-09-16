import java.util.StringTokenizer;
import java.io.*;

public class Main{
	public static void main(String[] args){
		String[][] data = getData();
		String[][] met = getMatched(data);

		view(met);
		viewAverage(met);

		System.exit(0);
	}

	public static String[][] getMatched(String[][] all){
		String[][] met;

		String temp = "";
		for(int i = 0; i < all.length; i++){
			double v = Double.parseDouble(all[i][2]);

			if(v == 84.0)
				temp += all[i][0] + "," + all[i][1] + "," + all[i][2] + ";";
		}

		StringTokenizer tok = new StringTokenizer(temp, ";");
		met = new String[tok.countTokens()][];

		for(int i = 0; i < met.length; i++)
			met[i] = parse(tok.nextToken());

		return met;
	}

	public static void view(String[][] data){
		for(int i = 0; i < data.length; i++)
			for(int ii = 0; ii < data[i].length; ii++)
				if(ii + 1 < data[i].length)
					System.out.print(data[i][ii] + " ");
				else
					System.out.println(data[i][ii]);
	}

	public static void viewAverage(String[][] data){
		double count = 0.0;

		for(int i = 0; i < data.length; i++)
			count += Double.parseDouble(data[i][1]);

		System.out.println("\nAverage for above: " + (count / data.length));
	}

	public static String[][] getData(){
		String[] raw = getData("data.txt");

		String[][] data = new String[raw.length][];
		for(int i = 0; i < data.length; i++)
			data[i] = parse(raw[i]);

		return data;
	}
	private static String[] getData(String filename){
		String[] raw = new String[countLines(filename)];

		try{
			FileReader fr = new FileReader(filename);
			BufferedReader file = new BufferedReader(fr);

			for(int i = 0; i < raw.length; i++)
				raw[i] = file.readLine();

			file.close();
		}catch(IOException e){
		}

		return raw;
	}
	private static int countLines(String filename){
		int lines = 0;

		try{
			FileReader fr = new FileReader(filename);
			BufferedReader file = new BufferedReader(fr);

			String input = "";
			while(input != null){
				input = file.readLine();

				if(input != null)
					lines++;
			}

			file.close();
		}catch(IOException e){
		}

		return lines;
	}
	private static String[] parse(String line){
		StringTokenizer tok = new StringTokenizer(line, ",");

		String[] data = new String[tok.countTokens()];
		for(int i = 0; i < data.length; i++)
			data[i] = tok.nextToken();

		return data;
	}
}
/**package butvan.egestats;

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
}*/
