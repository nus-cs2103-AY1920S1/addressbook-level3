package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysModule;

import org.junit.jupiter.api.Test;

import guitests.guihandles.SimpleModuleCardHandle;
import seedu.address.model.module.Module;
import seedu.address.testutil.ModuleBuilder;

public class SimpleModuleCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Module moduleWithTags = new ModuleBuilder().build();
        SimpleModuleCard moduleCard = new SimpleModuleCard(moduleWithTags);
        uiPartExtension.setUiPart(moduleCard);
        assertCardDisplay(moduleCard, moduleWithTags);
    }

    @Test
    public void equals() {
        Module module = new ModuleBuilder().build();
        SimpleModuleCard moduleCard = new SimpleModuleCard(module);

        SimpleModuleCard copy = new SimpleModuleCard(module);
        assertTrue(moduleCard.equals(copy));

        assertTrue(moduleCard.equals(moduleCard));

        assertFalse(moduleCard.equals(null));

        assertFalse(moduleCard.equals(0));
    }

    /**
     * Asserts that {@code moduleCard} displays the details of {@code expectedModule} correctly.
     */
    private void assertCardDisplay(SimpleModuleCard moduleCard, Module expectedModule) {
        guiRobot.pauseForHuman();

        SimpleModuleCardHandle moduleCardHandle = new SimpleModuleCardHandle(moduleCard.getRoot());

        // verify module details are displayed correctly
        assertCardDisplaysModule(expectedModule, moduleCardHandle);
    }
}
