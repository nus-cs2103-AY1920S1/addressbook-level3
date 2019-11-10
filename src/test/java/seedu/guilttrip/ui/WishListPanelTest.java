package seedu.guilttrip.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalWishes;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.guilttrip.model.entry.Amount;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Description;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.ui.gui.guihandles.WishListCardHandle;
import seedu.guilttrip.ui.gui.guihandles.WishListPanelHandle;
import seedu.guilttrip.ui.wishlist.WishListPanel;

/**
 * Unit test for wish list panel.
 */
public class WishListPanelTest extends GuiUnitTest {
    private static final ObservableList<Wish> TYPICAL_WISHES = FXCollections.observableList(getTypicalWishes());

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 5000;

    private final SimpleObjectProperty<Wish> selectedWish = new SimpleObjectProperty<>();
    private WishListPanelHandle wishListPanelHandle;

    /*@Test
    public void display() {
        initUi(TYPICAL_WISHES);

        for (int i = 0; i < TYPICAL_WISHES.size(); i++) {
            wishListPanelHandle.navigateToCard(TYPICAL_WISHES.get(i));
            Wish expectedWish = TYPICAL_WISHES.get(i);

            WishListCardHandle actualCard = wishListPanelHandle.getWishListCardHandle(i);

            assertCardDisplaysEntry(expectedWish, actualCard);
            assertEquals((i + 1) + ". ", actualCard.getId());
        }
    }*/

    private void assertCardDisplaysEntry(Wish expectedWish, WishListCardHandle actualCard) {
    }

    /*@Test
    public void selection_modelSelectedWishChanged_selectionChanges() {
        initUi(TYPICAL_WISHES);
        Wish secondWish = TYPICAL_WISHES.get(INDEX_SECOND_ENTRY.getZeroBased());
        guiRobot.interact(() -> selectedWish
                .set(secondWish));
        guiRobot.pauseForHuman();

        WishListCardHandle expectedWish = wishListPanelHandle.getWishListCardHandle(INDEX_SECOND_ENTRY.getZeroBased());
        WishListCardHandle selectedWish = wishListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedWish, selectedWish);
    }*/

    /**
     * Verifies that creating and deleting large number of entries in {@code WishListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() {
        ObservableList<Wish> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of wish cards exceeded time limit");
    }

    /**
     * Returns a list of entries containing {@code wishCount} entries that is used to populate the
     * {@code WishListPanel}.
     */
    private ObservableList<Wish> createBackingList(int wishCount) {
        ObservableList<Wish> backingList = FXCollections.observableArrayList();
        for (int i = 0; i < wishCount; i++) {
            Description desc = new Description(i + "a");
            Amount amt = new Amount("20");
            Category category = new Category("Shopping", "Expense");
            Date date = new Date("2019 11 09");
            Wish wish = new Wish(category, desc, date, amt, Collections.emptySet());
            backingList.add(wish);
        }
        return backingList;
    }

    /**
     * Initializes {@code wishListPanelHandle} with a {@code WishListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code WishListPanel}.
     */
    private void initUi(ObservableList<Wish> backingList) {
        WishListPanel wishListPanel =
                new WishListPanel(backingList);
        uiPartExtension.setUiPart(wishListPanel);

        wishListPanelHandle = new WishListPanelHandle(getChildNode(wishListPanel.getRoot(),
                WishListPanelHandle.WISH_LIST_VIEW_ID));
    }
}
