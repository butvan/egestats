package egestats;

//** ** Created by DeveloperHacker ** **//
//* https://github.com/DeveloperHacker *//

import java.util.*;

public class Table {

    private Map<Integer, Enrollee> store;
    private List<Integer> ids;

    public Table(List<Enrollee> enrolls) {
        store = new HashMap<>(enrolls.size());
        ids = new ArrayList<>(enrolls.size());
        for (Enrollee enrollee : enrolls) {
            ids.add(enrollee.positionalNum, enrollee.id);
            store.put(enrollee.id, enrollee);
        }
    }

    public Collection<Enrollee> enrolls() {
        return store.values();
    }
}
