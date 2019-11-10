package guitests.guihandles.alias;

import guitests.guihandles.ListHandle;
import javafx.scene.control.ListView;
import seedu.moolah.model.alias.Alias;

/**
 * Provides a handle for {@code ExpenseListPanel} containing the list of {@code Expense}.
 */
public class AliasListPanelHandle extends ListHandle<AliasCardHandle, Alias> {

    public static final String ALIAS_LIST_VIEW_ID = "#aliasListView";
    public static final String ALIAS_LIST_CARD_ID = "#aliasCardPane";

    public AliasListPanelHandle(ListView<Alias> listViewPanel) {
        super(listViewPanel, ALIAS_LIST_CARD_ID, AliasCardHandle::new);
    }
}
