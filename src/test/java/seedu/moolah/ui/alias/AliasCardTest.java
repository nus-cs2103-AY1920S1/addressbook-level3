package seedu.moolah.ui.alias;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static seedu.moolah.ui.testutil.GuiTestAssert.assertCardDisplaysAlias;

import org.junit.jupiter.api.Test;

import guitests.guihandles.alias.AliasCardHandle;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.testutil.AliasTestUtil;
import seedu.moolah.ui.GuiUnitTest;

/**
 * Contains tests for {@code AliasCard}.
 */
public class AliasCardTest extends GuiUnitTest {

    @Test
    public void display() {
        int maxLength = 10;

        // sample value
        Alias defaultSampleExpense = AliasTestUtil.ALIAS_A_TO_B;
        AliasCard aliasCard = new AliasCard(defaultSampleExpense, maxLength);
        uiPartExtension.setUiPart(aliasCard);
        assertCardDisplay(aliasCard, defaultSampleExpense);
    }

    @Test
    public void equals() {
        int maxLength = 20;
        Alias alias = AliasTestUtil.ALIAS_A_TO_B;
        AliasCard aliasCard = new AliasCard(alias, maxLength);

        // same expense, same index -> returns true
        AliasCard copy = new AliasCard(alias, maxLength);
        assertEquals(aliasCard, copy);

        // same object -> returns true
        assertEquals(aliasCard, aliasCard);

        // null -> returns false
        assertNotEquals(null, aliasCard);

        // different types -> returns false
        assertNotEquals(0, aliasCard);
    }

    /**
     * Asserts that {@code personCard} displays the details of {@code expectedPerson} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(AliasCard aliasCard, Alias expectedAlias) {
        guiRobot.pauseForHuman();

        AliasCardHandle aliasCardHandle = new AliasCardHandle(aliasCard.getRoot());

        // verify person details are displayed correctly
        assertCardDisplaysAlias(expectedAlias, aliasCardHandle);
    }
}
