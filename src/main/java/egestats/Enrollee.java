package egestats;

//** ** Created by DeveloperHacker ** **//
//* https://github.com/DeveloperHacker *//

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
}
