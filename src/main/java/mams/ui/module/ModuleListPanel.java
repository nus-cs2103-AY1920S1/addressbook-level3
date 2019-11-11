package mams.ui.module;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import mams.commons.core.LogsCenter;
import mams.model.module.Module;
import mams.ui.UiPart;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ItemListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    private final ObservableList<Module> moduleList;

    @FXML
    private ListView<Module> itemListView;

    public ModuleListPanel(ObservableList<Module> moduleList) {
        super(FXML);
        requireNonNull(moduleList);
        this.moduleList = moduleList;
        itemListView.setItems(moduleList);
        itemListView.setCellFactory(listView -> new ModuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else if (moduleList.size() == 1) {
                logger.fine("Displaying expanded module card");
                setGraphic(new ExpandedModuleCard(module).getRoot());
            } else {
                setGraphic(new ModuleCard(module, getIndex() + 1).getRoot());
            }
        }
    }
}
