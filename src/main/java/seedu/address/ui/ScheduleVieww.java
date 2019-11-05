package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.FreeTimeslot;
import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.model.person.ScheduleStub;
import seedu.address.ui.util.ColorGenerator;
import seedu.address.ui.util.DateFormatter;
import seedu.address.ui.util.TimeFormatter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScheduleVieww extends UiPart<Region> {
    protected static final int ONE_HOUR_LENGTH = 120;
    protected static final int PREFERRED_WIDTH = 140;
    protected static final int START_TIME = 8;
    protected static final int END_TIME = 20;
    private static final String FXML = "ScheduleVieww.fxml";

    protected double blockWidth = 140;

    @FXML
    private Label title;

    @FXML
    private GridPane scheduleHeaderContainer;

    @FXML
    private GridPane scheduleContentContainer;

    private List<WeekSchedule> schedulesShown;
    private List<String> colors;
    private LocalDate now;
    private HashMap<DayOfWeek, StackPane> dayStackPanes;

    public ScheduleVieww(List<WeekSchedule> weekSchedules, List<String> colors, String title, LocalDate date) {
        super(FXML);
        this.dayStackPanes = new HashMap<>();
        this.schedulesShown = weekSchedules;
        this.colors = colors;
        this.now = date;
        this.title.setText(title);
        initialiseDayHeaders();
        initialiseTimeSlotHeaders();
        initialiseEmptyTableContents();
        initialiseSchedule();
    }

    private void initialiseDayHeaders() {
        //Put blank header at the first row and first column.
        Region emptyHeader = new Region();
        scheduleHeaderContainer.add(emptyHeader, 0, 0);
        for (int i = 1; i <= 7; i++) {
            LocalDate currDay = now.plusDays(i - 1);
            VBox dayHeader = createDayHeader(currDay);
            scheduleHeaderContainer.add(dayHeader, i, 0);
        }
    }

    private void initialiseTimeSlotHeaders() {
        for (int i = START_TIME; i <= END_TIME; i++) {
            String time = TimeFormatter.formatIntToTime(i);
            StackPane timeSlotHeader = createTimeSlotHeader(time);
            scheduleContentContainer.add(timeSlotHeader, 0, i - START_TIME);
        }
    }

    private void initialiseEmptyTableContents() {
        for (int i = 1; i <= 7; i++) {
            StackPane dayTimeSlotStackPane = new StackPane();
            VBox verticalTableCells = new VBox();
            for (int j = START_TIME; j <= END_TIME; j++) {
                verticalTableCells.getChildren().add(createEmptyTableCell());
            }
            dayTimeSlotStackPane.getChildren().add(verticalTableCells);
            dayStackPanes.put(DayOfWeek.of(i), dayTimeSlotStackPane);
        }
    }

    private void initialiseSchedule() {
        for (int i = 1; i <= 7; i++) {
            HBox combinedSchedules = new HBox();
            for (int j = 0; j < schedulesShown.size(); j++) {
                ArrayList<PersonTimeslot> eventsToday = schedulesShown.get(j).get(now.plusDays(i - 1).getDayOfWeek());
                VBox individualSchedule = getDayVBoxOfIndividualSchedule(eventsToday, colors.get(j));
                combinedSchedules.getChildren().add(individualSchedule);
            }
            dayStackPanes.get(now.plusDays(i - 1).getDayOfWeek()).getChildren().add(combinedSchedules);
        }
    }

    private VBox createDayHeader(LocalDate date) {
        Label dayLabel = new Label(date.getDayOfWeek().toString());
        Label dateLabel = new Label(DateFormatter.formatToString(date));
        VBox dayLabelContainer = new VBox();
        dayLabelContainer.getChildren().addAll(dayLabel, dateLabel);
        //set the id for styling purposes.
        dayLabel.setId("dayText");
        dateLabel.setId("dayDate");
        dayLabelContainer.setId("dayLabelContainer");
        return dayLabelContainer;
    }

    private StackPane createTimeSlotHeader(String time) {
        StackPane timeLabelContainer = new StackPane();
        Label timeLabel = new Label(time);
        timeLabelContainer.getChildren().add(timeLabel);
        timeLabelContainer.setId("timeslotLabelContainer");
        return timeLabelContainer;
    }

    private VBox createEmptyTableCell() {
        VBox tableCell = new VBox();
        VBox halfTableCell = new VBox();
        tableCell.getChildren().add(halfTableCell);
        tableCell.setPrefHeight(ONE_HOUR_LENGTH);
        halfTableCell.setPrefHeight(ONE_HOUR_LENGTH / 2);
        tableCell.setId("timeslotMajorLine");
        halfTableCell.setId("timeslotMinorLine");
        return tableCell;
    }

    private int getTimeDifference(int startTime, int endTime) {
        int startTimeHours = startTime / 100;
        int endTimeHours = endTime / 100;
        int hours = endTimeHours - startTimeHours;
        int startTimeMinutes = startTime % 100;
        int endTimeMinutes = endTime % 100;
        int minutes = endTimeMinutes - startTimeMinutes;
        return hours * 60 + minutes;
    }

    /**
     * Method to create a block that represents the occupied/busy time slots of an individual for a particular day.
     * @param daySchedule List that contains all occupied time slots of the individual on a particular day.
     * @param color Color that the block should be.
     * @return VBox that represents the individual's busy time slots for this particular day.
     */
    private VBox getDayVBoxOfIndividualSchedule(ArrayList<PersonTimeslot> daySchedule, String color) {
        VBox timeslotContainer = new VBox();
        timeslotContainer.setId("timeslotContainer");
        timeslotContainer.getChildren().add(new ScheduleVieww.Block(ONE_HOUR_LENGTH / 2).makeEmptyTimeslot());
        int originalTimeStamp = START_TIME * 100;
        for (int j = 0; j < daySchedule.size(); j++) {
            PersonTimeslot timeslot = daySchedule.get(j);
            int startTime = TimeFormatter.formatTimeToInt(timeslot.getStartTime());
            int endTime = TimeFormatter.formatTimeToInt(timeslot.getEndTime());
            Region busyTimeslot = new ScheduleVieww.Block(getTimeDifference(startTime, endTime)).makeColouredTimeslot(color);
            if (originalTimeStamp != startTime) {
                int timeUntilBusy = getTimeDifference(originalTimeStamp, startTime);
                Region freeTimeslot = new ScheduleVieww.Block(timeUntilBusy).makeEmptyTimeslot();
                timeslotContainer.getChildren().add(freeTimeslot);
            }
            timeslotContainer.getChildren().add(busyTimeslot);
            originalTimeStamp = endTime;
        }
        return timeslotContainer;
    }

    /**
     * Method to create a block that represents free time in groups on a particular day.
     * Only called for group schedules. Free time is shown by a translucent green rectangle on the UI.
     * @param freeSchedule The common free time among groups for a particular day.
     * @return VBox that represents the free time block.
     */
    private VBox getDayVBoxOfFreeSchedule(ArrayList<FreeTimeslot> freeSchedule) {
        VBox timeslotContainer = new VBox();
        timeslotContainer.getChildren().add(new ScheduleVieww.Block(ONE_HOUR_LENGTH / 2).makeEmptyTimeslot());
        int originalTimeStamp = START_TIME * 100;
        for (int j = 0; j < freeSchedule.size(); j++) {
            FreeTimeslot timeslot = freeSchedule.get(j);
            int startTime = TimeFormatter.formatTimeToInt(timeslot.getStartTime());
            int endTime = TimeFormatter.formatTimeToInt(timeslot.getEndTime());
            int timeslotId = timeslot.getId();
            StackPane freeTime = new ScheduleVieww.Block(getTimeDifference(startTime, endTime))
                    .makeFreeTimeslot("" + timeslotId);
            if (originalTimeStamp != startTime) {
                int timeUntilNext = getTimeDifference(originalTimeStamp, startTime);
                Region untilNext = new ScheduleVieww.Block(timeUntilNext).makeEmptyTimeslot();
                timeslotContainer.getChildren().add(untilNext);
            }
            timeslotContainer.getChildren().add(freeTime);
            originalTimeStamp = endTime;
        }
        return timeslotContainer;
    }

    /**
     * A class to create a time slot block in the UI.
     */
    class Block {
        private int duration;
        private double heightOfTimeslot;
        public Block(int duration) {
            this.duration = duration;
            int hours = duration / 60;
            double minutes = duration % 60;
            this.heightOfTimeslot = hours * ONE_HOUR_LENGTH + (minutes / 60.0) * ONE_HOUR_LENGTH;
        }

        /**
         * Method to obtain a coloured timeslot;
         */
        private Region makeColouredTimeslot(String color) {
            Region result = new Region();
            result.setPrefSize(blockWidth, heightOfTimeslot);
            result.setStyle("-fx-background-color: " + getLinearGradient(color)
                    + "-fx-background-radius: " + (blockWidth / 28.0));
            result.setId("colouredTimeslot");
            return result;
        }

        private String getLinearGradient(String color) {
            Color lighterTone = Color.web(color).desaturate();
            String lighterToneHex = "#" + Integer.toHexString(lighterTone.hashCode());
            String linearGradient = "linear-gradient(" + lighterToneHex + " 0%, " + color + " 20%, "
                    + color + " 80%, " + lighterToneHex + " 100%);";
            return linearGradient;
        }

        /**
         * Method to create a transparent block in the table view to indicate free time.
         * @return Region that represents the free time in the schedule.
         */
        private Region makeEmptyTimeslot() {
            Region result = new Region();
            result.setPrefSize(blockWidth, heightOfTimeslot);
            return result;
        }

        /**
         * Method to create a free time slot block with an id.
         * @param text The id to be set in the block.
         * @return StackPane.
         */
        private StackPane makeFreeTimeslot(String text) {
            StackPane freeTimeslot = new StackPane();
            Label label = new Label(text);
            label.setPrefSize(blockWidth, heightOfTimeslot);
            Region region = makeColouredTimeslot("lightgreen");
            region.setId("freeTimeslotBlock");
            freeTimeslot.setId("freeTimeslot");
            freeTimeslot.getChildren().addAll(label, region);
            //Need to handle cases when timeslot height is less than 15px otherwise
            //the result block size will be inaccurate.
            if (heightOfTimeslot < 15) {
                label.setStyle("-fx-font-size: " + (heightOfTimeslot - 5) + ";");
            }
            return freeTimeslot;
        }
    }
}
