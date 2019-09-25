package seedu.address.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.address.model.person.ScheduleStub;

import java.util.ArrayList;
import java.util.List;

public class ScheduleView extends Application {
    private ArrayList<String> dayNames = new ArrayList<String>(List.of("Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"));
    private GridPane scheduleView = new GridPane();
    private ArrayList<StackPane> timeslotStackPanes = new ArrayList<StackPane>();
    private int oneHourLength = 50;
    private int preferredWidth = 100;
    private int blockWidth = 20;
    private int startTime = 8;
    private int endTime = 20;

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
            //Region is used for border and background color control.
            Region timeslotLabelContainer = new Region();
            timeslotLabelContainer.setPrefSize(preferredWidth, oneHourLength);
            timeslotLabelContainer.setStyle("-fx-background-color: grey; -fx-border-color: white;");
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
                //Line timeslotMinorRegion = new Line(0.0, oneHourLength / 2, preferredWidth, oneHourLength/2);
                //timeslotMinorRegion.getStrokeDashArray().addAll(2d);
                //timeslotMinorRegion.setStyle("-fx-stroke: silver;");
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
            timeslotStackPanes.add(stackPane);
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
        result.setStyle("-fx-background-color: " + color + "; -fx-border-width: 2; -fx-background-radius: 5;");
        return result;
    }

    private int getTimeDifference(int startTime, int endTime) {
        int hours = (endTime - startTime) / 100;
        int minutes = (endTime - startTime) % 100;
        return hours * 60 + minutes;
    }

    public void initialise(ScheduleStub scheduleStub) {
        for (int i = 1; i <= 7; i++) {
            ArrayList<Pair<Integer, Integer>> daySchedule = scheduleStub.getDaySchedule(i);
            StackPane dayStackPane = timeslotStackPanes.get(i - 1);
            VBox timeslotContainer = new VBox();
            timeslotContainer.setStyle("-fx-padding: 0");
            timeslotContainer.getChildren().add(makeEmptyTimeslot(30));
            int originalTimeStamp = startTime * 100;
            for (int j = 0; j < daySchedule.size(); j++) {
                Pair<Integer, Integer> timeslot = daySchedule.get(j);
                int startTime = (int) timeslot.getKey();
                int endTime = (int) timeslot.getValue();
                Region busyTimeslot = makeColouredTimeslot(getTimeDifference(startTime, endTime), "red");
                if (originalTimeStamp != startTime) {
                    int timeUntilBusy = getTimeDifference(originalTimeStamp, startTime);
                    Region freeTimeslot = makeEmptyTimeslot(timeUntilBusy);
                    timeslotContainer.getChildren().add(freeTimeslot);
                }
                timeslotContainer.getChildren().add(busyTimeslot);
                originalTimeStamp = endTime;
            }
            dayStackPane.getChildren().add(timeslotContainer);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        initialiseHeaders();
        initialiseTableCells();
        ScheduleStub ss = new ScheduleStub();
        initialise(ss);
        ScrollPane mainLayout = new ScrollPane();
        mainLayout.setContent(scheduleView);
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
