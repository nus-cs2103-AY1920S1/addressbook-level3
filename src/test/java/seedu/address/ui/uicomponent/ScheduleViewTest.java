package seedu.address.ui.uicomponent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.util.NodeQueryUtils.hasText;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.api.FxToolkit;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.framework.junit5.Stop;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import seedu.address.model.display.detailwindow.PersonTimeslot;
import seedu.address.model.display.schedulewindow.WeekSchedule;
import seedu.address.ui.schedule.ScheduleView;
import seedu.address.ui.util.ColorGenerator;
import seedu.address.ui.util.DateFormatter;
import seedu.address.ui.util.TimeUtil;


@ExtendWith(ApplicationExtension.class)
public class ScheduleViewTest extends ApplicationTest {
    private static final String TITLE_ID = "#scheduleView #title";
    private static final String SCHEDULE_HEADERS_ID = "#scheduleView #scheduleHeaderWrapper #scheduleHeaderContainer";
    private static final String SCHEDULE_CONTENT_ID = "#scheduleView #scheduleContentContainer";

    private static final WeekSchedule TEST_SCHEDULE = new WeekScheduleStub(LocalDate.now()).getSampleSchedule();
    private static final List<String> TEST_EVENT_NAMES = WeekScheduleStub.EVENT_NAMES;
    private static final String TEST_SCHEDULE_TITLE = "TEST SCHEDULE";
    private static final LocalDate now = LocalDate.now();

    @AfterEach
    public void pause(FxRobot robot) {
        robot.sleep(500);
    }

    @Stop
    void tearDown() throws TimeoutException {
        FxToolkit.cleanupStages();
    }

    /**
     * Initialise person card test with a stage.
     * @param stage
     */
    @Start
    public void start(Stage stage) {
        ScheduleView scheduleView = new ScheduleView(List.of(TEST_SCHEDULE),
                ColorGenerator.generateColorList(), TEST_SCHEDULE_TITLE, LocalDate.now());
        //Generate schedule using the TEST_SCHEDULE.
        scheduleView.generateSchedule();
        Parent sceneRoot = scheduleView.getRoot();
        Scene scene = new Scene(sceneRoot);
        scene.getStylesheets().add("/view/DarkTheme.css");
        stage.setScene(scene);
        stage.show();
    }

    @Test
    public void checkIfTitleDisplayedCorrectly() {
        verifyThat(TITLE_ID, hasText(TEST_SCHEDULE_TITLE + "'s Schedule"));
    }

    @Test
    public void checkIfDayHeadersDisplayedCorrectlyInOrder() {
        String dayHeadersTextId = SCHEDULE_HEADERS_ID + " #dayLabelContainer #dayText";
        String dayHeadersDateId = SCHEDULE_HEADERS_ID + " #dayLabelContainer #dayDate";
        ArrayList<Node> dayHeaders = new ArrayList<>(lookup(dayHeadersTextId).queryAll());
        ArrayList<Node> dateHeaders = new ArrayList<>(lookup(dayHeadersDateId).queryAll());
        //There are seven day and date headers.
        assertEquals(dayHeaders.size(), 7);
        assertEquals(dateHeaders.size(), 7);
        //Day and date headers are placed in the correct order.
        for (int i = 0; i < 7; i++) {
            verifyThat(dayHeaders.get(i), hasText(now.plusDays(i).getDayOfWeek().toString()));
            verifyThat(dateHeaders.get(i), hasText(DateFormatter.formatToString(now.plusDays(i))));
        }
    }

    @Test
    public void checkIfTimeSlotsTitleDisplayedInOrder() {
        String timeSlotLabelsId = SCHEDULE_CONTENT_ID + " #timeslotLabelContainer .label";
        ArrayList<Node> timeSlotLabel = new ArrayList<>(lookup(timeSlotLabelsId).queryAll());
        //Titles are labelled in ascending order.
        for (int i = 0; i < timeSlotLabel.size(); i++) {
            String timeTitle = TimeUtil.formatIntToTime(ScheduleView.START_TIME + i);
            assertEquals(timeTitle, ((Label) timeSlotLabel.get(i)).getText());
        }
    }

    @Test
    public void checkIfTimeSlotsLabelEquallySpaced() {
        String timeSlotLabelContainerId = SCHEDULE_CONTENT_ID + " #timeslotLabelContainer";
        ArrayList<Node> timeSlotLabelContainers = new ArrayList<>(lookup(timeSlotLabelContainerId).queryAll());
        for (Node titleContainer : timeSlotLabelContainers) {
            assertEquals(ScheduleView.ONE_HOUR_LENGTH, ((StackPane) titleContainer).getHeight());
        }
    }

    @Test
    public void checkIfColouredTimeSlotsBlockHasTextDisplayedCorrectly() {
        String colouredTimeSlotBlockId = SCHEDULE_CONTENT_ID + " #timeslotContainer #colouredBlockWithText";
        String colouredTimeSlotBlockTextId = colouredTimeSlotBlockId + " .label";
        ArrayList<Node> colouredBlocksText = new ArrayList<>(lookup(colouredTimeSlotBlockTextId).queryAll());
        //Check that text inside block is displayed correctly.
        for (int i = 0; i < colouredBlocksText.size(); i++) {
            verifyThat(colouredBlocksText.get(i), hasText(TEST_EVENT_NAMES.get(i)));
        }
    }

    @Test
    public void checkIfNumberOfColouredTimeSlotBlocksAreCorrect() {
        String colouredTimeSlotBlockId = SCHEDULE_CONTENT_ID + " #timeslotContainer #colouredBlockWithText";
        ArrayList<Node> colouredBlocks = new ArrayList<>(lookup(colouredTimeSlotBlockId).queryAll());
        // Check to see the number of blocks correspond to the total number of time slots occupied.
        int totalNumOfEventsInSchedule = 0;
        for (int i = 1; i <= 7; i++) {
            totalNumOfEventsInSchedule += TEST_SCHEDULE.get(DayOfWeek.of(i)).size();
        }
        assertEquals(colouredBlocks.size(), totalNumOfEventsInSchedule);
    }

    @Test
    public void checkIfColouredTimeSlotBlocksHaveCorrectSize() {
        String colouredTimeSlotBlockId = SCHEDULE_CONTENT_ID + " #timeslotContainer #colouredBlockWithText";
        ArrayList<Node> colouredBlocks = new ArrayList<>(lookup(colouredTimeSlotBlockId).queryAll());
        //Check to see if the height (in px) corresponds to the duration of the block.
        ArrayList<PersonTimeslot> flattenedTimeSlots = new ArrayList<>();
        //Flatten time slots into array list starting from "now".
        for (int i = 1; i <= 7; i++) {
            ArrayList<PersonTimeslot> timeslots = TEST_SCHEDULE.get(now.plusDays(i - 1).getDayOfWeek());
            for (int j = 0; j < timeslots.size(); j++) {
                flattenedTimeSlots.add(timeslots.get(j));
            }
        }
        for (int i = 0; i < colouredBlocks.size(); i++) {
            double blockSize = ((StackPane) colouredBlocks.get(i)).getHeight();
            double duration = TimeUtil.getTimeDifference(flattenedTimeSlots.get(i).getStartTime(),
                    flattenedTimeSlots.get(i).getEndTime());
            assertEquals((int) duration, (int) blockSize);
        }
    }


    @Test
    public void checkIfNumberOfEmptyBlocksAreCorrect() {
        String emptyTimeSlotBlockId = SCHEDULE_CONTENT_ID + " #timeslotContainer #emptyBlock";
        //Calculate the spaces between time slots.
        int spacesBetweenTimeSlot = 0;
        for (int i = 1; i <= 7; i++) {
            ArrayList<PersonTimeslot> timeslots = TEST_SCHEDULE.get(now.plusDays(i - 1).getDayOfWeek());
            LocalTime curr = LocalTime.of(ScheduleView.START_TIME, 0);
            for (int j = 0; j < timeslots.size(); j++) {
                if (timeslots.get(j).getStartTime().isAfter(curr)) {
                    spacesBetweenTimeSlot++;
                }
                curr = timeslots.get(j).getEndTime();
            }
        }
        ArrayList<Node> emptyBlocks = new ArrayList<>(lookup(emptyTimeSlotBlockId).queryAll());
        assertEquals(spacesBetweenTimeSlot, emptyBlocks.size());
    }

    @Test
    public void checkIfEmptyBlocksHaveCorrectSize() {
        String emptyTimeSlotBlockId = SCHEDULE_CONTENT_ID + " #timeslotContainer #emptyBlock";
        ArrayList<Node> emptyBlocks = new ArrayList<>(lookup(emptyTimeSlotBlockId).queryAll());
        //Calculate the duration of every empty block.
        ArrayList<Double> emptyBlockDuration = new ArrayList<>();
        for (int i = 1; i <= 7; i++) {
            ArrayList<PersonTimeslot> timeslots = TEST_SCHEDULE.get(now.plusDays(i - 1).getDayOfWeek());
            LocalTime curr = LocalTime.of(ScheduleView.START_TIME, 0);
            for (int j = 0; j < timeslots.size(); j++) {
                if (timeslots.get(j).getStartTime().isAfter(curr)) {
                    emptyBlockDuration.add(
                            (double) TimeUtil.getTimeDifference(curr, timeslots.get(j).getStartTime()));
                }
                curr = timeslots.get(j).getEndTime();
            }
        }
        for (int i = 0; i < emptyBlocks.size(); i++) {
            assertEquals(((Region) emptyBlocks.get(i)).getHeight(), emptyBlockDuration.get(i));
        }
    }

    @Test
    public void checkIfBlocksArePlacedCorrectly() {
        String colouredTimeSlotBlockId = SCHEDULE_CONTENT_ID + " #timeslotContainer #colouredBlockWithText";
        String emptyTimeSlotBlockId = SCHEDULE_CONTENT_ID + " #timeslotContainer #emptyBlock";
        //Way to test is to read the blocks' position and convert it to actual start and end times.
        //Strategy: Put all the nodes in one list and the time slots in another list. Map one to one equivalence.
        //Test using stub's fixed data set.
        ArrayList<Node> emptyBlocks = new ArrayList<>(lookup(emptyTimeSlotBlockId).queryAll());
        ArrayList<Node> colouredBlocks = new ArrayList<>(lookup(colouredTimeSlotBlockId).queryAll());

        ArrayList<PersonTimeslot> flattenedTimeSlots = new ArrayList<>();
        //Flatten time slots into array list starting from "now".
        for (int i = 1; i <= 7; i++) {
            ArrayList<PersonTimeslot> timeslots = TEST_SCHEDULE.get(now.plusDays(i - 1).getDayOfWeek());
            for (int j = 0; j < timeslots.size(); j++) {
                flattenedTimeSlots.add(timeslots.get(j));
            }
        }
        //From stub: Every 3 empty block and coloured block belong to one day from now until now + 3 days.
        for (int i = 0; i < 4; i++) {
            LocalTime start = LocalTime.of(ScheduleView.START_TIME, 0);
            for (int j = 0; j < 3; j++) {
                PersonTimeslot timeslot = flattenedTimeSlots.remove(0);
                StackPane colouredBlock = (StackPane) colouredBlocks.remove(0);
                if (timeslot.getStartTime().isAfter(start)) {
                    //Need to include empty block.
                    Region emptyBlock = ((Region) emptyBlocks.remove(0));
                    double emptyBlockHeight = emptyBlock.getHeight();
                    LocalTime eventStartTime = start.plusMinutes((long) emptyBlockHeight);
                    LocalTime eventEndTime = eventStartTime.plusMinutes((long) colouredBlock.getHeight());
                    assertEquals(timeslot.getStartTime(), eventStartTime);
                    assertEquals(timeslot.getEndTime(), eventEndTime);
                } else {
                    //No need to include empty block.
                    LocalTime eventStartTime = start.plusMinutes(0);
                    LocalTime eventEndTime = eventStartTime.plusMinutes((long) colouredBlock.getHeight());
                    assertEquals(timeslot.getStartTime(), eventStartTime);
                    assertEquals(timeslot.getEndTime(), eventEndTime);
                }
                start = timeslot.getEndTime();
            }
        }
    }
}
