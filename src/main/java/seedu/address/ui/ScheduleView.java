package seedu.address.ui;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.schedule.Event;
import seedu.address.model.person.schedule.Schedule;
import seedu.address.model.person.schedule.Timeslot;

/**
 * A class to generate a schedule table (ui) from a Schedule object.
 */
public class ScheduleView extends UiPart<Region> {
    //Schedule to be received from logic MUST be in chronological order from Monday -> Friday and events must be
    //in chronological order as well.
    //ScheduleView must be wrapped in a scroll pane otherwise the view will become distorted.
    private static final String FXML = "ScheduleView.fxml";
    private static ArrayList<String> dayNames = new ArrayList<String>(List.of("Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
    private static ArrayList<String> listOfColors = new ArrayList<String>(List.of("darkred", "navy", "darkgreen",
            "darkorange", "lightslategray", "orchid", "teal", "darkmagenta"));
    @FXML
    private GridPane scheduleView;

    private Schedule schedule;
    private ArrayList<StackPane> dayTimeslotStackPanes = new ArrayList<StackPane>();
    private ArrayList<StackPane> timeHeaderStackPanes = new ArrayList<StackPane>();
    private int oneHourLength = 60;
    private int preferredWidth = 140;
    private double blockWidth = 140;
    private int startTime = 8;
    private int endTime = 20;


    public ScheduleView(Schedule schedule) {
        super(FXML);
        this.schedule = schedule;
        initialise();
        initialiseHeaders();
        initialiseTableCells();
        HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>> hashMap = getScheduleMap(schedule);
        showIndividualSchedule(hashMap, listOfColors.get((int) (Math.random() * (listOfColors.size() - 1))));
    }

    public ScheduleView(ArrayList<Schedule> schedules) {
        super(FXML);
        initialise();
        initialiseHeaders();;
        initialiseTableCells();
        ArrayList<HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>>> scheduleMapsList =
                new ArrayList<>();
        for (Schedule s : schedules) {
            HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>> scheduleMap = getScheduleMap(s);
            scheduleMapsList.add(scheduleMap);
        }
        showGroupSchedule(scheduleMapsList);
    }

    private ScheduleView initialise() {
        scheduleView = new GridPane();
        scheduleView.setStyle("-fx-border-width: 2; -fx-border-color: black; -fx-pref-width: 1100;");
        return this;
    }

    /**
     * Helper method to initialise the headers in the table view.
     */
    private void initialiseHeaders() {
        //initialise headers
        Region placeHolder = new Region();
        placeHolder.setStyle("-fx-background-color: lightgrey");
        scheduleView.add(placeHolder, 0, 0);
        Region secondPlaceHolder = new Region();
        secondPlaceHolder.setStyle("-fx-background-color: lightgrey");
        scheduleView.add(secondPlaceHolder, 8, 0);
        ColumnConstraints colCOffset = new ColumnConstraints();
        colCOffset.setPercentWidth(5);
        scheduleView.getColumnConstraints().add(colCOffset);
        //day headers
        for (int i = 0; i < dayNames.size(); i++) {
            StackPane sp = new StackPane();
            Label dayText = new Label(dayNames.get(i));
            Region dayLabelRegion = new Region();
            dayLabelRegion.setPrefSize(preferredWidth, 50);
            ColumnConstraints colC = new ColumnConstraints();
            colC.setPercentWidth(12.857);
            scheduleView.getColumnConstraints().add(colC);
            dayLabelRegion.setStyle("-fx-background-color: white; -fx-border-width: 2");
            sp.getChildren().addAll(dayLabelRegion, dayText);
            scheduleView.add(sp, i + 1, 0);
        }
        ColumnConstraints colCOffset2 = new ColumnConstraints();
        colCOffset2.setPercentWidth(5);
        scheduleView.getColumnConstraints().add(colCOffset2);
        //timeslot headers
        for (int j = startTime; j < endTime; j++) {
            //left-side headers
            Region timeslotLeftLabelContainer = new Region();
            timeslotLeftLabelContainer.setPrefSize(preferredWidth, oneHourLength);
            timeslotLeftLabelContainer.setStyle("-fx-background-color: white; -fx-border-color: white;");
            Label timeslotLeftText = new Label(j * 100 + "");
            StackPane leftTimeslotHeaderContainer = new StackPane();
            leftTimeslotHeaderContainer.getChildren().addAll(timeslotLeftLabelContainer, timeslotLeftText);
            scheduleView.add(leftTimeslotHeaderContainer, 0, j - startTime + 1);

            //right-side headers
            Region timeslotRightLabelContainer = new Region();
            timeslotRightLabelContainer.setPrefSize(preferredWidth, oneHourLength);
            timeslotRightLabelContainer.setStyle("-fx-background-color: white; -fx-border-color: white;");
            Label timeslotRightText = new Label(j * 100 + "");
            StackPane rightTimeslotHeaderContainer = new StackPane();
            rightTimeslotHeaderContainer.getChildren().addAll(timeslotRightLabelContainer, timeslotRightText);
            scheduleView.add(rightTimeslotHeaderContainer, 8, j - startTime + 1);
        }
    }

    /**
     * Helper method to initialise table cells and grid lines in the table view.
     */
    private void initialiseTableCells() {
        //timeslot data
        for (int l = 0; l < dayNames.size(); l++) {
            StackPane stackPane = new StackPane();
            VBox timeslotContainer = new VBox();
            Region firstRegionOffset = new Region();
            firstRegionOffset.setPrefSize(preferredWidth, oneHourLength / 2);
            firstRegionOffset.setStyle("-fx-border-color: lightgrey; -fx-border-style: solid none none solid");
            timeslotContainer.getChildren().add(firstRegionOffset);
            for (int k = startTime; k < endTime; k++) {
                StackPane timeslotRegion = new StackPane();
                Region timeslotMajorRegion = new Region();
                timeslotMajorRegion.setStyle("-fx-border-color: lightgrey; -fx-border-style: solid none none solid;");
                VBox timeslotMinorRegion = new VBox();
                Region offset = makeEmptyTimeslot(30);
                Region timeslotMinorRegion1 = new Region();
                timeslotMinorRegion1.setStyle("-fx-border-color: lightgrey; -fx-border-style: dotted none none dotted");
                timeslotMinorRegion1.setPrefSize(preferredWidth, oneHourLength / 2.0);
                timeslotMinorRegion.getChildren().addAll(offset, timeslotMinorRegion1);
                if (k == endTime - 1) {
                    timeslotMajorRegion.setPrefSize(preferredWidth, oneHourLength / 2);
                    timeslotRegion.getChildren().addAll(timeslotMajorRegion);
                    timeslotContainer.getChildren().add(timeslotRegion);
                } else {
                    timeslotMajorRegion.setPrefSize(preferredWidth, oneHourLength);
                    timeslotRegion.getChildren().addAll(timeslotMajorRegion, timeslotMinorRegion);
                    timeslotContainer.getChildren().add(timeslotRegion);
                }
            }
            stackPane.getChildren().add(timeslotContainer);
            dayTimeslotStackPanes.add(stackPane);
            scheduleView.add(stackPane, l + 1, 1, 1, endTime - startTime);
        }
    }

    /**
     * Method to create a transparent block in the table view to indicate free time.
     * @param durationMinutes Length of the block in minutes.
     * @return Region that represents the free time in the schedule.
     */
    private Region makeEmptyTimeslot(int durationMinutes) {
        Region result = new Region();
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        double heightOfTimeslot = hours * oneHourLength + (minutes / 60.0) * oneHourLength;
        result.setPrefSize(blockWidth, heightOfTimeslot);
        return result;
    }

    /**
     * Helper method to create a block of coloured timeslot in the table view.
     * @param durationMinutes Length of the block in minutes.
     * @param color Color of the block.
     * @return Region to be placed in the table view.
     */
    private Region makeColouredTimeslot(int durationMinutes, String color) {
        Region result = new Region();
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        double heightOfTimeslot = hours * oneHourLength + (minutes / 60.0) * oneHourLength;
        result.setPrefSize(blockWidth, heightOfTimeslot);
        //result.setMaxWidth(blockWidth); setsMaxWidth of busy timeslot.
        result.setStyle("-fx-background-color: " + color + "; -fx-border-width: 2; -fx-background-radius: 5;");
        return result;
    }

    private int getTimeDifference(int startTime, int endTime) {
        int hours = (endTime - startTime) / 100;
        int minutes = (endTime - startTime) % 100;
        return hours * 60 + minutes;
    }

    private VBox getDayVBoxOfIndividualSchedule(ArrayList<Pair<Integer, Integer>> daySchedule, String color) {
        VBox timeslotContainer = new VBox();
        timeslotContainer.setStyle("-fx-padding: 0 2 0 2; -fx-border-width: 2;");
        timeslotContainer.getChildren().add(makeEmptyTimeslot(30));
        int originalTimeStamp = startTime * 100;
        for (int j = 0; j < daySchedule.size(); j++) {
            Pair<Integer, Integer> timeslot = daySchedule.get(j);
            int startTime = (int) timeslot.getKey();
            int endTime = (int) timeslot.getValue();
            Region busyTimeslot = makeColouredTimeslot(getTimeDifference(startTime, endTime), color);
            if (originalTimeStamp != startTime) {
                int timeUntilBusy = getTimeDifference(originalTimeStamp, startTime);
                Region freeTimeslot = makeEmptyTimeslot(timeUntilBusy);
                timeslotContainer.getChildren().add(freeTimeslot);
            }
            timeslotContainer.getChildren().add(busyTimeslot);
            originalTimeStamp = endTime;
        }
        return timeslotContainer;
    }

    /**
     * Method to generate a random list of colors to tag each group member with a particular colour.
     * @param groupSize The group size.
     * @return A list of unique colors for the group's schedule view.
     */
    private ArrayList<String> generateColorList(int groupSize) {
        ArrayList<String> colors = new ArrayList<String>();
        HashSet<String> colorChecker = new HashSet<String>();
        int i = 0;
        while (i < groupSize) {
            int index = (int) (Math.random() * (listOfColors.size() - 1));
            if (colorChecker.contains(listOfColors.get(index))) {
                continue;
            }
            colors.add(listOfColors.get(index));
            colorChecker.add(listOfColors.get(index));
            i++;
        }
        return colors;
    }

    private HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>> getScheduleMap(Schedule schedule) {
        HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>> hashMap =
                new HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>>();
        //Initialise arraylist in hashmap. Pair<Integer, Integer> contains <startTime> and <endTime>.
        PersonId personId = schedule.getPersonId();

        for (int i = 1; i <= 7; i++) {
            DayOfWeek day = DayOfWeek.of(i);
            hashMap.put(day, new ArrayList<Pair<PersonId, Pair<Integer, Integer>>>());
        }

        //Looping through schedule and appending it to the correct place in the map.
        ArrayList<Event> events = schedule.getEvents();
        for (Event e : events) {
            ArrayList<Timeslot> timeslots = e.getTimeslots();
            for (Timeslot t : timeslots) {
                DayOfWeek day = t.getStartTime().getDayOfWeek();
                int startTime = t.getStartTime().getHour() * 100 + t.getStartTime().getMinute();
                int endTime = t.getEndTime().getHour() * 100 + t.getEndTime().getMinute();
                Pair<Integer, Integer> startAndEndTime = new Pair<>(startTime, endTime);
                Pair<PersonId, Pair<Integer, Integer>> dataValue = new Pair(personId, startAndEndTime);
                hashMap.get(day).add(dataValue);
            }
        }
        //Sorting the arraylist according to timestamps.
        Comparator<Pair<PersonId, Pair<Integer, Integer>>> comparator = new Comparator<Pair<PersonId,
                Pair<Integer, Integer>>>() {
            @Override
            public int compare(Pair<PersonId, Pair<Integer, Integer>> t1, Pair<PersonId, Pair<Integer, Integer>> t2) {
                int t1Value = (int) t1.getValue().getKey();
                int t2Value = (int) t2.getValue().getKey();
                return t1Value - t2Value;
            }
        };
        for (int i = 1; i <= 7; i++) {
            DayOfWeek day = DayOfWeek.of(i);
            hashMap.get(day).sort(comparator);
        }

        return hashMap;
    }

    /**
     * Method to obtain a table view of an individual's schedule.
     * @param map ScheduleMap obtained by calling getScheduleMap on a Schedule object.
     * @param color Color of the blocks in the table view.
     * @return  GridPane table view of the individual's schedule.
     */
    public GridPane showIndividualSchedule(HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>> map,
                                           String color) {
        for (int i = 1; i <= 7; i++) {
            ArrayList<Pair<PersonId, Pair<Integer, Integer>>> daySchedule = map.get(DayOfWeek.of(i));
            StackPane dayStackPane = dayTimeslotStackPanes.get(i - 1);
            ArrayList<Pair<Integer, Integer>> startEndTimes = daySchedule.stream()
                    .map(p -> p.getValue())
                    .collect(Collectors.toCollection(ArrayList::new));
            VBox timeslotContainer = getDayVBoxOfIndividualSchedule(startEndTimes, color);
            dayStackPane.getChildren().add(timeslotContainer);
        }
        return scheduleView;
    }

    /**
     * Method to obtain a table view of the all the schedules present in a group.
     * @param schedules An array list of schedule maps obtained from calling getScheduleMap on a Schedule Object.
     * @return  GridPane table view of schedules.
     */
    public GridPane showGroupSchedule(ArrayList<HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>>>
                                              schedules) {
        //Assign colors to each schedule.
        //Draw VBox of each individual's schedule.
        //Put VBoxes of all individuals' timeslot for the day into HBox.
        //Put HBox into dayStackPane.
        //Loop to next day.
        ArrayList<String> colors = generateColorList(schedules.size());
        blockWidth = blockWidth / (schedules.size());
        for (int i = 0; i < dayTimeslotStackPanes.size(); i++) {
            StackPane dayStackPane = dayTimeslotStackPanes.get(i);
            HBox groupTimeslot = new HBox();
            for (int j = 0; j < schedules.size(); j++) {
                HashMap<DayOfWeek, ArrayList<Pair<PersonId, Pair<Integer, Integer>>>> personSchedule = schedules.get(j);
                ArrayList<Pair<Integer, Integer>> daySchedule = personSchedule.get(DayOfWeek.of(i + 1)).stream()
                        .map(p -> p.getValue())
                        .collect(Collectors.toCollection(ArrayList::new));
                VBox dayScheduleVBox = getDayVBoxOfIndividualSchedule(daySchedule, colors.get(j));
                HBox.setHgrow(dayScheduleVBox, Priority.ALWAYS);
                groupTimeslot.getChildren().add(dayScheduleVBox);
            }
            dayStackPane.getChildren().add(groupTimeslot);
        }
        return scheduleView;
    }

    public GridPane getScheduleView() {
        return scheduleView;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            if (other instanceof ScheduleView) {
                return ((ScheduleView) other).schedule.equals(this.schedule);
            } else {
                return false;
            }
        }
    }
}
