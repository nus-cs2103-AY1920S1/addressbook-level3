package seedu.address.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.address.model.person.ScheduleStub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ScheduleView extends Application {
    //Schedule to be received from logic MUST be in chronological order from Monday -> Friday and events must be
    //in chronological order as well.
    private ArrayList<String> dayNames = new ArrayList<String>(List.of("Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"));
    private ArrayList<String> listOfColors = new ArrayList<String>(List.of("darkred", "navy", "darkgreen",
            "darkorange", "lightslategray", "orchid", "teal", "darkmagenta"));
    private GridPane scheduleView;
    private ScheduleStub schedule;
    private ArrayList<StackPane> dayTimeslotStackPanes = new ArrayList<StackPane>();
    private int oneHourLength = 48;
    private int preferredWidth = 100;
    private double blockWidth = 100;
    private int startTime = 8;
    private int endTime = 20;

    public ScheduleView initialise() {
        scheduleView = new GridPane();
        scheduleView.setStyle("-fx-border-width: 2; -fx-border-color: black;");
        return this;
    }

    private void initialiseHeaders() {
        //initialise headers
        Region placeHolder = new Region();
        placeHolder.setStyle("-fx-background-color: lightgrey");
        scheduleView.add(placeHolder, 0, 0);
        ColumnConstraints colCOffset = new ColumnConstraints();
        colCOffset.setPercentWidth(12.5);
        scheduleView.getColumnConstraints().add(colCOffset);
        //day headers
        for (int i = 0; i < dayNames.size(); i++) {
            StackPane sp = new StackPane();
            Text dayText = new Text(dayNames.get(i));
            Region dayLabelRegion = new Region();
            dayLabelRegion.setPrefSize(preferredWidth, 50);
            ColumnConstraints colC = new ColumnConstraints();
            colC.setPercentWidth(12.5);
            scheduleView.getColumnConstraints().add(colC);
            dayLabelRegion.setStyle("-fx-background-color: white; -fx-border-width: 2");
            sp.getChildren().addAll(dayLabelRegion, dayText);
            scheduleView.add(sp, i + 1, 0);
        }
        //timeslot headers
        for (int j = startTime; j < endTime; j++) {
            Region timeslotLabelContainer = new Region();
            timeslotLabelContainer.setPrefSize(preferredWidth, oneHourLength);
            timeslotLabelContainer.setStyle("-fx-background-color: white; -fx-border-color: white;");
            Text timeslotText = new Text(j * 100 + "");
            timeslotText.setTranslateX(preferredWidth / 3);
            StackPane stack = new StackPane();
            stack.getChildren().addAll(timeslotLabelContainer, timeslotText);
            scheduleView.add(stack, 0, j - startTime + 1);
        }
    }

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
                timeslotMinorRegion1.setStyle("-fx-border-color: lightgrey; -fx-border-style: dashed none none dashed");
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

    private Region makeEmptyTimeslot(int durationMinutes) {
        Region result = new Region();
        int hours = durationMinutes / 60;
        int minutes = durationMinutes % 60;
        double heightOfTimeslot = hours * oneHourLength + (minutes / 60.0) * oneHourLength;
        result.setPrefSize(blockWidth, heightOfTimeslot);
        return result;
    }

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

    public GridPane showIndividualSchedule(ScheduleStub scheduleStub, String color) {
        for (int i = 1; i <= 7; i++) {
            ArrayList<Pair<Integer, Integer>> daySchedule = scheduleStub.getDaySchedule(i);
            //Sunday -> 0, Monday -> 1.. for dayStackPane.
            StackPane dayStackPane = dayTimeslotStackPanes.get(i - 1);
            VBox timeslotContainer = getDayVBoxOfIndividualSchedule(daySchedule, color);
            dayStackPane.getChildren().add(timeslotContainer);
        }
        return scheduleView;
    }

    public GridPane showGroupSchedule(ArrayList<ScheduleStub> schedules) {
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
                ScheduleStub currIndividualSchedule = schedules.get(j);
                ArrayList<Pair<Integer, Integer>> daySchedule = currIndividualSchedule.getDaySchedule(i + 1);
                VBox dayScheduleVBox = getDayVBoxOfIndividualSchedule(daySchedule, colors.get(j));
                HBox.setHgrow(dayScheduleVBox, Priority.ALWAYS);
                groupTimeslot.getChildren().add(dayScheduleVBox);
            }
            dayStackPane.getChildren().add(groupTimeslot);
        }
        return scheduleView;
    }

    public ScheduleStub getSchedule() {
        return this.schedule;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        } else {
            if (other instanceof ScheduleView) {
                return ((ScheduleView) other).getSchedule().equals(this.schedule);
            } else {
                return false;
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        initialise();
        initialiseHeaders();
        initialiseTableCells();
        ScheduleStub s1 = new ScheduleStub(1);
        ScheduleStub s2 = new ScheduleStub(2);
        ScrollPane mainLayout = new ScrollPane();
        //mainLayout.setContent(showIndividualSchedule(s1, "darkblue"));
        mainLayout.setContent(showGroupSchedule(new ArrayList<ScheduleStub>(List.of(s1, s2))));
        //mainLayout.setContent(scheduleView);
        mainLayout.setFitToHeight(true);
        mainLayout.setFitToWidth(true);
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(ScheduleView.class, args);
    }
}
