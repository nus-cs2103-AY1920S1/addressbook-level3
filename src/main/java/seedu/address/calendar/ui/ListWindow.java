package seedu.address.calendar.ui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import seedu.address.calendar.logic.commands.ListCommand;
import seedu.address.ui.PageManager;
import seedu.address.ui.UiPart;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

/**
 * Pop-up window for list view. The pop-up disappears when it is out of focus and reappears with the default
 * height and width.
 */
public class ListWindow extends UiPart<Stage> {
    private static final String FXML = "CalendarListWindow.fxml";
    private static final double DEFAULT_WINDOW_HEIGHT = 300;
    private static final double DEFAULT_WINDOW_WIDTH = 450;

    private final Stage listWindow;
    private final EventCard commitmentCard;
    private final EventCard holidayCard;
    private final EventCard schoolBreakCard;
    private final EventCard tripCard;

    @FXML
    private VBox commitments;
    @FXML
    private VBox holidays;
    @FXML
    private VBox schoolBreaks;
    @FXML
    private VBox trips;
    @FXML
    private Label emptyList;
    @FXML
    private Text commitmentContent;
    @FXML
    private Text holidayContent;
    @FXML
    private Text schoolBreakContent;
    @FXML
    private VBox eventsContainer;
    @FXML
    private Text tripContent;
    @FXML
    private ScrollPane scrollPane;

    public ListWindow() {
        super(FXML);
        listWindow = getRoot();

        listWindow.focusedProperty().addListener(((observable, oldValue, newValue) -> {
            if (!newValue) {
                PauseTransition delay = new PauseTransition(Duration.seconds(0.2));
                delay.setOnFinished(event -> listWindow.hide());
                delay.play();
            }
        }));

        listWindow.setOnShowing(event -> {
            listWindow.setHeight(DEFAULT_WINDOW_HEIGHT);
            listWindow.setWidth(DEFAULT_WINDOW_WIDTH);
            // centralise
            listWindow.setX(PageManager.getXPosition() - DEFAULT_WINDOW_WIDTH / 2);
            listWindow.setY(PageManager.getYPosition() - DEFAULT_WINDOW_HEIGHT / 2);
        });

        // create cards
        commitmentCard = new EventCard(commitments, commitmentContent);
        holidayCard = new EventCard(holidays, holidayContent);
        schoolBreakCard = new EventCard(schoolBreaks, schoolBreakContent);
        tripCard = new EventCard(trips, tripContent);
    }

    boolean isShowing() {
        return listWindow.isShowing();
    }

    void show(String eventsToShow) {
        showEvents(eventsToShow);
        scrollPane.setVvalue(0); // scroll to top
        listWindow.show();
    }

    void showEvents(String eventsToShow) {
        clearAll();

        if (eventsToShow.equals(ListCommand.MESSAGE_LIST_FAILED)) {
            setInvisible(commitmentCard, holidayCard, schoolBreakCard, tripCard);
            showEmpty();
            return;
        }

        Stream.of(eventsToShow.split("\\n"))
                .forEach(eventToShow -> {
                    EventTypeDeterminer eventTypeDeterminer = new EventTypeDeterminer(eventToShow);

                    if (eventTypeDeterminer.isCommitment()) {
                        commitmentCard.add(eventToShow);
                    } else if (eventTypeDeterminer.isHoliday()) {
                        holidayCard.add(eventToShow);
                    } else if (eventTypeDeterminer.isSchoolBreak()) {
                        schoolBreakCard.add(eventToShow);
                    } else if (eventTypeDeterminer.isTrip()) {
                        tripCard.add(eventToShow);
                    } else {
                        // assert false : "Every event should have a valid type";
                    }
                });

        if (commitmentCard.hasContent()) {
            commitmentCard.makeVisible();
        }

        if (holidayCard.hasContent()) {
            holidayCard.makeVisible();
        }

        if (schoolBreakCard.hasContent()) {
            schoolBreakCard.makeVisible();
        }

        if (tripCard.hasContent()) {
            tripCard.makeVisible();
        }

        showEventsContainer();
    }

    void clearAll() {
        commitmentCard.makeInvisible();
        schoolBreakCard.makeInvisible();
        tripCard.makeInvisible();
        holidayCard.makeInvisible();
        hideEmpty();
        hideEventsContainer();
    }

    void showEmpty() {
        emptyList.setVisible(true);
        emptyList.setManaged(true);
    }

    void hideEmpty() {
        emptyList.setVisible(false);
        emptyList.setManaged(false);
    }

    void hideEventsContainer() {
        eventsContainer.setVisible(false);
        eventsContainer.setManaged(false);
    }

    void showEventsContainer() {
        eventsContainer.setVisible(true);
        eventsContainer.setManaged(true);
    }

    void setInvisible(EventCard... eventCards) {
        Stream.of(eventCards)
                .forEach(EventCard::makeInvisible);
    }

    void requestFocus() {
        listWindow.requestFocus();
    }

    /**
     * Determiner helps to determine how to categorise and layout all the events.
     */
    private class EventTypeDeterminer {
        final String FORMAT_COMMITMENT = "(commitment\\sfrom\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s"
                + "\\p{Alnum}++\\sto\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)" + "|"
                + "(commitment\\son\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)";
        final Pattern PATTERN_COMMITMENT = Pattern.compile(FORMAT_COMMITMENT);
        final String FORMAT_HOLIDAY = "(holiday\\sfrom\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s"
                + "\\p{Alnum}++\\sto\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)" + "|"
                + "(holiday\\son\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)";
        final Pattern PATTERN_HOLIDAY = Pattern.compile(FORMAT_HOLIDAY);
        final String FORMAT_SCHOOL_BREAK = "(school\\sbreak\\sfrom\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s"
                + "\\p{Alnum}++\\sto\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)" + "|"
                + "(school\\sbreak\\son\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)";
        final Pattern PATTERN_SCHOOL_BREAK = Pattern.compile(FORMAT_SCHOOL_BREAK);
        final String FORMAT_TRIP = "(trip\\sfrom\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s"
                + "\\p{Alnum}++\\sto\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)" + "|"
                + "(trip\\son\\s\\p{Alnum}{3},\\s\\p{Alnum}++\\s\\p{Alnum}++\\s\\p{Alnum}++)";;
        final Pattern PATTERN_TRIP = Pattern.compile(FORMAT_TRIP);
        final String TERMINATOR = "'";

        String typeString;

        EventTypeDeterminer(String string) {
            this.typeString = getTypeString(string);
        }

        boolean isCommitment() {
            Matcher matcherCommitment = PATTERN_COMMITMENT.matcher(typeString);

            return matcherCommitment.matches();
        }

        boolean isHoliday() {
            Matcher matcherHoliday = PATTERN_HOLIDAY.matcher(typeString);

            return matcherHoliday.matches();
        }

        boolean isSchoolBreak() {
            Matcher matcherSchoolBreak = PATTERN_SCHOOL_BREAK.matcher(typeString);

            return matcherSchoolBreak.matches();
        }

        boolean isTrip() {
            Matcher matcherTrip = PATTERN_TRIP.matcher(typeString);

            return matcherTrip.matches();
        }

        String getTypeString(String string) {
            int lastApostropheIndex = findLastApostropheIndex(string);
            return string.substring(lastApostropheIndex + 1).trim();
        }

        int findLastApostropheIndex(String string) {
            return string.lastIndexOf(TERMINATOR);
        }

    }
}
