package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.TypicalFridges.getTypicalFridges;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysFridge;

import org.junit.jupiter.api.Test;

import guitests.guihandles.FridgeCardHandle;
import guitests.guihandles.FridgeGridViewHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.entity.fridge.Fridge;

//@@author shaoyi1997
public class FridgeGridViewTest extends GuiUnitTest {
    private static final ObservableList<Fridge> TYPICAL_FRIDGES =
        FXCollections.observableList(getTypicalFridges());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 10000;

    private FridgeGridViewHandle fridgeGridViewHandle;

    @Test
    public void display() {
        initUi(TYPICAL_FRIDGES);

        for (int i = 0; i < TYPICAL_FRIDGES.size(); i++) {
            Fridge expectedFridge = TYPICAL_FRIDGES.get(i);
            FridgeCardHandle actualCard = fridgeGridViewHandle.getFridgeCardHandle(i);
            assertCardDisplaysFridge(expectedFridge, actualCard);
        }
    }

    /**
     * Verifies that creating and deleting large number of workers in {@code WorkerListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Fridge> backingList = createBackingList(500);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of fridges cards exceeded time limit");
    }

    /**
     * Returns a list of fridges containing {@code fridgeCount} workers that is used to populate the
     * {@code fridgeGridView}.
     */
    private ObservableList<Fridge> createBackingList(int fridgeCount) {
        ObservableList<Fridge> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < fridgeCount; i++) {
            backingList.add(new Fridge());
        }
        return backingList;
    }

    /**
     * Initializes {@code fridgeGridViewHandle} with a {@code FridgeGridView} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code FridgeGridView}.
     */
    private void initUi(ObservableList<Fridge> backingList) {
        FridgeGridView fridgeGridView = new FridgeGridView(backingList);
        uiPartExtension.setUiPart(fridgeGridView);

        fridgeGridViewHandle = new FridgeGridViewHandle(getChildNode(fridgeGridView.getRoot(),
            FridgeGridViewHandle.FRIDGE_GRID_VIEW_ID));
    }
}
//@@author
