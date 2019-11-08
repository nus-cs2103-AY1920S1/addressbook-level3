package seedu.address.ui.schedule;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.FreeSchedule;
import seedu.address.model.display.schedulewindow.FreeTimeslot;
import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.ui.UiPart;
import seedu.address.ui.util.DateFormatter;
import seedu.address.ui.util.TimeUtil;
import seedu.address.ui.util.ToolTipFormatter;

/**
 * Class that creates the schedule view for the user to see combined schedules.
 */
public class ScheduleView extends UiPart<Region> {
    public static final int START_TIME = 8;
    public static final int END_TIME = 20;
    public static final double ONE_HOUR_LENGTH = 60;
    protected static final int HALF_HOUR = 30;
    protected static final int BLOCK_WIDTH = 140;
    private static final String FXML = "ScheduleView.fxml";

    @FXML
    private Label title;

    @FXML
    private GridPane scheduleHeaderContainer;

    @FXML
    private ScrollPane scheduleContents;

    @FXML
    private GridPane scheduleContentContainer;

    private List<WeekSchedule> schedulesShown;
    private List<String> colors;
    private LocalDate now;
    private HashMap<LocalDate, StackPane> dateStackPanes;

    public ScheduleView(List<WeekSchedule> weekSchedules, List<String> colors, String title, LocalDate date) {
        super(FXML);
        this.dateStackPanes = new HashMap<>();
        this.schedulesShown = weekSchedules;
        this.colors = colors;
        this.now = date;
        this.title.setText(title + "'s Schedule");
        //Set up empty cells and headers.
        initialiseDayHeaders();
        initialiseTimeSlotHeaders();
        initialiseEmptyTableContents();
    }

    /**
     * Initialises the day headers of the schedule view.
     */
    private void initialiseDayHeaders() {
        //Put blank header at the first row and first column.
        Region emptyHeader = new Block(HALF_HOUR).makeEmptyBlock();
        scheduleHeaderContainer.add(emptyHeader, 0, 0);
        for (int i = 1; i <= 7; i++) {
            LocalDate currDay = now.plusDays(i - 1);
            VBox dayHeader = createDayHeader(currDay);
            scheduleHeaderContainer.add(dayHeader, i, 0);
        }
    }

    /**
     * Initialises the time slot headers of the schedule view.
     */
    private void initialiseTimeSlotHeaders() {
        for (int i = START_TIME; i <= END_TIME; i++) {
            String time = TimeUtil.formatIntToTime(i);
            StackPane timeSlotHeader = createTimeSlotHeader(time);
            scheduleContentContainer.add(timeSlotHeader, 0, i - START_TIME);
        }
    }

    /**
     * Initialises the empty table cells and sets up stack panes to add schedules later.
     */
    private void initialiseEmptyTableContents() {
        for (int i = 1; i <= 7; i++) {
            StackPane dayTimeSlotStackPane = new StackPane();
            VBox verticalTableCells = new VBox();
            //Add half a cell to represent padding space / offset at the start of the time table.
            verticalTableCells.getChildren().add(createHalfTableCell());
            for (int j = START_TIME; j < END_TIME; j++) {
                verticalTableCells.getChildren().add(createEmptyTableCell());
            }
            //Add half a cell to represent padding space / offset at the end of the time table.
            verticalTableCells.getChildren().add(createHalfTableCell());
            dayTimeSlotStackPane.getChildren().add(verticalTableCells);
            scheduleContentContainer.add(dayTimeSlotStackPane, i, 0, 1, END_TIME - START_TIME + 1);
            dateStackPanes.put(now.plusDays(i - 1), dayTimeSlotStackPane);
        }
    }

    /**
     * Generates the schedule from the given List of WeekSchedules in the constructor.
     */
    public void generateSchedule() {
        for (int i = 1; i <= 7; i++) {
            HBox combinedSchedules = new HBox();
            for (int j = 0; j < schedulesShown.size(); j++) {
                ArrayList<PersonTimeslot> eventsToday = schedulesShown.get(j).get(now.plusDays(i - 1).getDayOfWeek());
                VBox individualSchedule = getDayVBoxOfIndividualSchedule(eventsToday, colors.get(j));
                //Change line 99 for collapsible blocks.
                individualSchedule.setPrefWidth(BLOCK_WIDTH / schedulesShown.size());
                combinedSchedules.getChildren().add(individualSchedule);
            }
            dateStackPanes.get(now.plusDays(i - 1)).getChildren().add(combinedSchedules);
        }
    }

    /**
     * Creates a cell with a day header of the date given.
     * @param date The date to be placed inside the cell.
     * @return VBox. The cell that contains the date.
     */
    private VBox createDayHeader(LocalDate date) {
        Label dayLabel = new Label(date.getDayOfWeek().toString());
        Label dateLabel = new Label(DateFormatter.formatToString(date));
        VBox dayLabelContainer = new VBox();
        dayLabelContainer.getChildren().addAll(dayLabel, dateLabel);
        //Set the id for styling purposes.
        dayLabel.setId("dayText");
        dateLabel.setId("dayDate");
        dayLabelContainer.setId("dayLabelContainer");
        dayLabelContainer.setPrefHeight(ONE_HOUR_LENGTH);
        return dayLabelContainer;
    }

    /**
     * Creates a cell with the given time.
     * @param header String representation of the time.
     * @return StackPane. The cell that contains this time;
     */
    private StackPane createTimeSlotHeader(String header) {
        StackPane timeLabelContainer = new StackPane();
        Label timeLabel = new Label(header);
        timeLabelContainer.getChildren().add(timeLabel);
        timeLabelContainer.setPrefHeight(ONE_HOUR_LENGTH);
        timeLabelContainer.setId("timeslotLabelContainer");
        return timeLabelContainer;
    }

    /**
     * Creates an empty table cell for the table to have dashed lines and solid lines.
     * @return VBox. The empty table cell.
     */
    private VBox createEmptyTableCell() {
        VBox tableCell = new VBox();
        VBox halfTableCell = new VBox();
        tableCell.getChildren().add(halfTableCell);
        tableCell.setPrefHeight(ONE_HOUR_LENGTH);
        halfTableCell.setPrefHeight(ONE_HOUR_LENGTH / 2);
        tableCell.setId("solidLine");
        halfTableCell.setId("dashedLine");
        return tableCell;
    }

    /**
     * Creates half a cell for offset purposes.
     * @return VBox. The offset cell.
     */
    private VBox createHalfTableCell() {
        VBox tableCell = new VBox();
        tableCell.setPrefHeight(ONE_HOUR_LENGTH / 2);
        tableCell.setId("offsetRegion");
        return tableCell;
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
        HBox.setHgrow(timeslotContainer, Priority.ALWAYS);
        timeslotContainer.getChildren().add(new Block(HALF_HOUR).makeEmptyBlock());
        LocalTime originalTimeStamp = LocalTime.of(START_TIME, 0);
        for (int j = 0; j < daySchedule.size(); j++) {
            PersonTimeslot timeslot = daySchedule.get(j);
            LocalTime startTime = timeslot.getStartTime();
            LocalTime endTime = timeslot.getEndTime();
            if (!originalTimeStamp.equals(startTime)) {
                int timeUntilBusy = TimeUtil.getTimeDifference(originalTimeStamp, startTime);
                Region freeTimeslot = new Block(timeUntilBusy).makeEmptyBlock();
                freeTimeslot.setId("emptyBlock");
                timeslotContainer.getChildren().add(freeTimeslot);
            }
            Region busyTimeslot = new Block(TimeUtil.getTimeDifference(startTime, endTime))
                    .makeColouredBlockWithText(color, timeslot.getEventName(),
                            ToolTipFormatter.formatTooltipMessage(timeslot));
            timeslotContainer.getChildren().add(busyTimeslot);
            originalTimeStamp = endTime;
        }
        return timeslotContainer;
    }

    /**
     * Method to append the free times of a group into the group schedule.
     * @param schedule A schedule that contains the available time for all group members.
     */
    public void setFreeTime(FreeSchedule schedule) {
        HashMap<DayOfWeek, ArrayList<FreeTimeslot>> vacantSchedule = schedule.getFreeSchedule();
        for (int i = 1; i <= 7; i++) {
            StackPane dateStackPane = dateStackPanes.get(now.plusDays(i - 1));
            ArrayList<FreeTimeslot> freeTimes = vacantSchedule.get(now.plusDays(i - 1).getDayOfWeek());
            VBox freeTimeVBox = getDayVBoxOfFreeSchedule(freeTimes);
            dateStackPane.getChildren().add(freeTimeVBox);
        }
    }


    /**
     * Method to create a block that represents free time in groups on a particular day.
     * Only called for group schedules. Free time is shown by a translucent green rectangle on the UI.
     * @param freeSchedule The common free time among groups for a particular day.
     * @return VBox that represents the free time block.
     */
    private VBox getDayVBoxOfFreeSchedule(ArrayList<FreeTimeslot> freeSchedule) {
        VBox timeslotContainer = new VBox();
        timeslotContainer.setId("freeTimeSlotContainer");
        timeslotContainer.getChildren().add(new Block(HALF_HOUR).makeEmptyBlock());
        LocalTime originalTimeStamp = LocalTime.of(START_TIME, 0);
        for (int j = 0; j < freeSchedule.size(); j++) {
            FreeTimeslot timeslot = freeSchedule.get(j);
            LocalTime startTime = timeslot.getStartTime();
            LocalTime endTime = timeslot.getEndTime();
            int timeslotId = timeslot.getId();
            StackPane freeTime = new Block(TimeUtil.getTimeDifference(startTime, endTime))
                    .makeFreeBlock("" + timeslotId);
            if (originalTimeStamp != startTime) {
                int timeUntilNext = TimeUtil.getTimeDifference(originalTimeStamp, startTime);
                Region untilNext = new Block(timeUntilNext).makeEmptyBlock();
                untilNext.setId("emptyBlock");
                timeslotContainer.getChildren().add(untilNext);
            }
            timeslotContainer.getChildren().add(freeTime);
            originalTimeStamp = endTime;
        }
        return timeslotContainer;
    }

    /**
     * Method to invoke scrolling events to the schedule view.
     */
    public void scrollNext() {
        if (scheduleContents.getVvalue() == 0) {
            scheduleContents.setVvalue(1);
        } else {
            scheduleContents.setVvalue(0);
        }
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
        private Region makeColouredBlock(String color) {
            Region colouredBlock = new Region();
            colouredBlock.setPrefSize(BLOCK_WIDTH, heightOfTimeslot);
            colouredBlock.setStyle("-fx-background-color: " + color
                    + "; -fx-background-radius: " + (BLOCK_WIDTH / (28.0 * schedulesShown.size())) + ";");
            colouredBlock.setId("colouredBlock");
            return colouredBlock;
        }

        /**
         * Creates a coloured block with the given color and places a text at the centre of the block. Binds a tooltip
         * to the block.
         * @param color The color of the block.
         * @param text The text to be placed at the centre of the block.
         * @param tooltipMessage The message for the tool tip to show.
         * @return StackPane.
         */
        private StackPane makeColouredBlockWithText(String color, String text, String tooltipMessage) {
            //Ensure that the block must be greater than 10px in height otherwise block will become distorted.
            assert heightOfTimeslot > 10;
            StackPane container = new StackPane();
            Region colouredBlock = makeColouredBlock(color);
            container.getChildren().addAll(colouredBlock, createLabel(text));
            Tooltip tooltip = new Tooltip(tooltipMessage);
            Tooltip.install(container, tooltip);
            container.setId("colouredBlockWithText");
            return container;
        }

        /**
         * Creates a label that will scale in size depending on the size of the block.
         * @param text The text to insert into the label.
         * @return
         */
        private Label createLabel(String text) {
            Label textLabel = new Label(text);
            if (heightOfTimeslot < 30) {
                double fontSize = heightOfTimeslot / 2;
                textLabel.setStyle("-fx-font-size: " + fontSize + ";");
            }
            return textLabel;
        }

        /**
         * Method to create a transparent block in the table view to indicate free time.
         * @return Region that represents the free time in the schedule.
         */
        private Region makeEmptyBlock() {
            Region result = new Region();
            result.setPrefSize(BLOCK_WIDTH, heightOfTimeslot);
            return result;
        }

        /**
         * Method to create a free time slot block with an id.
         * @param text The id to be set in the block.
         * @return StackPane.
         */
        private StackPane makeFreeBlock(String text) {
            //Assert that each height is greater than 10px;
            assert heightOfTimeslot > 10;

            StackPane freeTimeslot = new StackPane();
            Label label = createLabel(text);
            Region region = makeColouredBlock("lightgreen");
            region.setId("freeTimeslotBlock");
            freeTimeslot.setId("freeTimeslot");
            freeTimeslot.getChildren().addAll(label, region);
            return freeTimeslot;
        }
    }
}
