package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalBodies.JOHN;

import org.junit.jupiter.api.Test;

import seedu.address.model.entity.fridge.Fridge;
import seedu.address.testutil.FridgeBuilder;

class FridgeCardTest extends GuiUnitTest {

    @Test
    void equals_sameFridge_true() {
        Fridge fridge = new FridgeBuilder().build();
        FridgeCard fridgeCard = new FridgeCard(fridge);
        Fridge otherFridge = fridge;
        FridgeCard otherFridgeCard = new FridgeCard(otherFridge);
        assertTrue(fridgeCard.equals(otherFridgeCard));
    }

    @Test
    void equals_differentFridge_false() {
        Fridge fridge = new FridgeBuilder().build();
        FridgeCard fridgeCard = new FridgeCard(fridge);
        Fridge otherFridge = new FridgeBuilder().withBody(JOHN).build();
        FridgeCard otherFridgeCard = new FridgeCard(otherFridge);
        assertFalse(fridgeCard.equals(otherFridgeCard));
    }
}
