package seedu.address.ui;

import static seedu.address.testutil.TypicalModule.CS1101S;
import static seedu.address.testutil.TypicalModule.CS2102;
import static seedu.address.testutil.TypicalModule.ST2334;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysModule;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import guitests.guihandles.ModuleListPanelHandle;
import guitests.guihandles.SimpleModuleCardHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.Module;

public class ModuleListPanelTest extends GuiUnitTest {
    private static final ObservableList<Module> TYPICAL_MODULES =
            FXCollections.observableList(new ArrayList<>(Arrays.asList(CS1101S, CS2102, ST2334)));

    private ModuleListPanelHandle moduleListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_MODULES);

        for (int i = 0; i < TYPICAL_MODULES.size(); i++) {
            moduleListPanelHandle.navigateToCard(TYPICAL_MODULES.get(i));
            Module expectedModule = TYPICAL_MODULES.get(i);
            SimpleModuleCardHandle actualCard = moduleListPanelHandle.getModuleCardHandle(i);

            assertCardDisplaysModule(expectedModule, actualCard);
        }
    }

    /**
     * Initializes {@code ModuleListPanelHandler} with a {@code ModuleListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ModuleListPanel}.
     */
    private void initUi(ObservableList<Module> backingList) {
        ModuleListPanel moduleListPanel = new ModuleListPanel(backingList);
        uiPartExtension.setUiPart(moduleListPanel);

        moduleListPanelHandle = new ModuleListPanelHandle(getChildNode(moduleListPanel.getRoot(),
                guitests.guihandles.ModuleListPanelHandle.MODULE_LIST_VIEW_ID));
    }
}
