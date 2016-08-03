package egestats;

//** ** Created by DeveloperHacker ** **//
//* https://github.com/DeveloperHacker *//

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Enrollee {
    public final int positionalNum;
    public final int serialNum;
    public final int id;
    public final String name;
    public final String birthday;
    public final String type;
    public final int priority;
    public final float sCommon;
    public final float sMain;
    public final float[] subjects;
    public final float sExtra;
    public final String originals;
    public final String achievements;
    public final String notes;

    public Enrollee(int positionalNum, int serialNum, int id, String name, String birthday, String type, int priority, float sCommon, float sMain, float[] subjects, float sExtra, String originals, String achievements, String notes) {
        this.positionalNum = positionalNum;
        this.serialNum = serialNum;
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.type = type;
        this.priority = priority;
        this.sCommon = sCommon;
        this.sMain = sMain;
        this.subjects = subjects;
        this.sExtra = sExtra;
        this.originals = originals;
        this.achievements = achievements;
        this.notes = notes;
    }

    public static final int qComponents = 15;

    public static final int qSubjects = 3;

    public static Enrollee create(int positionalNum, List<String> fields) {
        if (fields.size() != qComponents) {
            throw new IllegalArgumentException("Enrollee : wrond columns in table except " + qComponents + " but " + fields.size());
        }
        try {
            int i = -1;
            int serialNum = (fields.get(++i).length() == 0) ? 0 : parseInt(fields.get(i));
            int id = (fields.get(++i).length() == 0) ? 0 : parseInt(fields.get(i));
            String name = fields.get(++i);
            String birthday = fields.get(++i);
            String type = fields.get(++i);
            int priority = (fields.get(++i).length() == 0) ? 0 : parseInt(fields.get(i));
            float sCommon = (fields.get(++i).length() == 0) ? 0f : parseFloat(fields.get(i).replace(',', '.'));
            float sMain = (fields.get(++i).length() == 0) ? 0f : parseFloat(fields.get(i).replace(',', '.'));
            float subj1 = (fields.get(++i).length() == 0) ? 0f : parseFloat(fields.get(i).replace(',', '.'));
            float subj2 = (fields.get(++i).length() == 0) ? 0f : parseFloat(fields.get(i).replace(',', '.'));
            float subj3 = (fields.get(++i).length() == 0) ? 0f : parseFloat(fields.get(i).replace(',', '.'));
            float sExtra = (fields.get(++i).length() == 0) ? 0f : parseFloat(fields.get(i).replace(',', '.'));
            String originals = fields.get(++i);
            String achievements = fields.get(++i);
            String notes = fields.get(++i);
            return new Enrollee(positionalNum, serialNum, id, name, birthday, type, priority, sCommon, sMain, new float[]{subj1, subj2, subj3}, sExtra, originals, achievements, notes);
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Enrollee: Wrong data value in row = " + fields.get(0) + " and posNum = " + positionalNum);
        }
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
        if (floats.size() == 1) return floats.get(0);
        return parseInt(str);
    }
}
