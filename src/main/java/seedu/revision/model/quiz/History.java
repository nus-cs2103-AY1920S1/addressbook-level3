package seedu.revision.model.quiz;

import java.util.ArrayList;

public class History {

    private static ArrayList<Statistics> History = new ArrayList<>();

    public static void updateHistory(Statistics statistics) {
        History.add(statistics);
    }
}
