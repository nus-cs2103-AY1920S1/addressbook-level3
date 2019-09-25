package seedu.address.model.person;

import javafx.util.Pair;

import java.util.ArrayList;

public class ScheduleStub {
    private ArrayList<ArrayList<Pair<Integer, Integer>>> schedule;
    public ScheduleStub() {
        schedule = new ArrayList<ArrayList<Pair<Integer, Integer>>>();
        for (int i = 0; i < 7; i++) {
            ArrayList<Pair<Integer, Integer>> daySchedule = new ArrayList<Pair<Integer, Integer>>();
            if (i % 2 == 1) {
                daySchedule.add(new Pair(1100, 1300));
                daySchedule.add(new Pair(1500, 1600));
            }
            schedule.add(daySchedule);
        }
    }

    public ArrayList<Pair<Integer, Integer>> getDaySchedule(int dayNumber) {
        return schedule.get(dayNumber - 1);
    }
}