package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysModule;

import org.junit.jupiter.api.Test;

import guitests.guihandles.ModuleCardHandle;
import seedu.address.model.module.Module;
import seedu.address.testutil.ModuleBuilder;

public class ModuleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Module moduleWithTags = new ModuleBuilder().build();
        ModuleCard moduleCard = new ModuleCard(moduleWithTags);
        uiPartExtension.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithTags);
    }

    @Test
    public void equals() {
        Module module = new ModuleBuilder().build();
        ModuleCard moduleCard = new ModuleCard(module);

        ModuleCard copy = new ModuleCard(module);
        assertTrue(moduleCard.equals(copy));

        assertTrue(moduleCard.equals(moduleCard));

        assertFalse(moduleCard.equals(null));

        assertFalse(moduleCard.equals(0));
    }

    /**
     * Asserts that {@code moduleCard} displays the details of {@code expectedModule} correctly.
     */
    private void assertCardDisplay(ModuleCard moduleCard, Module expectedModule) {
        guiRobot.pauseForHuman();

        ModuleCardHandle moduleCardHandle = new ModuleCardHandle(moduleCard.getRoot());

        // verify module details are displayed correctly
        assertCardDisplaysModule(expectedModule, moduleCardHandle);
    }
}
