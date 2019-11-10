package seedu.moolah.ui.event;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import guitests.guihandles.event.EventListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Description;
import seedu.moolah.model.expense.Event;
import seedu.moolah.model.expense.Price;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.ui.GuiUnitTest;


/**
 * Contains tests for {@code EventListPanel}.
 *
 * Refactored from:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/seedu/address/ui/PersonListPanelTest.java
 */
public class EventListPanelTest extends GuiUnitTest {

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 3000;

    private EventListPanelHandle eventListPanelHandle;


    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Event> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Event> createBackingList(int eventCount) {
        ObservableList<Event> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < eventCount; i++) {
            Description name = new Description(i + "a");
            Description budgetName = new Description("budgey");
            Price price = new Price("1");
            Category category = new Category("FOOD");
            Timestamp timestamp = new Timestamp(LocalDateTime.of(3000, 11, 1, 0, 0));
            backingList.add(new Event(name, price, category, timestamp, budgetName));
        }
        return backingList;
    }

    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<Event> backingList) {
        EventListPanel listPanel =
                new EventListPanel(backingList, true);

        uiPartExtension.setUiPart(listPanel);
        eventListPanelHandle = new EventListPanelHandle(getChildNode(listPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }
}
