package seedu.moolah.ui.alias;

import static seedu.moolah.testutil.AliasTestUtil.VALID_ALIAS_MAPPINGS;
import static seedu.moolah.ui.testutil.GuiTestAssert.assertCardDisplaysAlias;

import org.junit.jupiter.api.Test;

import guitests.guihandles.alias.AliasCardHandle;
import guitests.guihandles.alias.AliasListPanelHandle;
import seedu.moolah.model.alias.Alias;
import seedu.moolah.model.alias.AliasMappings;
import seedu.moolah.ui.GuiUnitTest;

/**
 * Contains tests for {@code AliasListPanel}.
 */
public class AliasListPanelTest extends GuiUnitTest {
    private AliasListPanelHandle aliasListPanelHandle;

    @Test
    public void display() {
        initUi(VALID_ALIAS_MAPPINGS);
        VALID_ALIAS_MAPPINGS.getAliases();

        for (int i = 0; i < VALID_ALIAS_MAPPINGS.getAliases().size(); i++) {
            aliasListPanelHandle.navigateToCard(VALID_ALIAS_MAPPINGS.getAliases().get(i));
            Alias expectedAlias = VALID_ALIAS_MAPPINGS.getAliases().get(i);
            AliasCardHandle actualCard = aliasListPanelHandle.getCardHandle(i);
            assertCardDisplaysAlias(expectedAlias, actualCard);
        }
    }

    /**
     * Initializes {@code aliasListPanelHandle} with a {@code AliasListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code AliasListPanel}.
     */
    private void initUi(AliasMappings aliasMappings) {
        AliasListPanel listPanel =
                new AliasListPanel(aliasMappings);
        uiPartExtension.setUiPart(listPanel);
        aliasListPanelHandle = new AliasListPanelHandle(getChildNode(listPanel.getRoot(),
                AliasListPanelHandle.ALIAS_LIST_VIEW_ID));
    }
}
