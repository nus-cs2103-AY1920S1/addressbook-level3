package seedu.module.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.module.commons.core.LogsCenter;
import seedu.module.model.module.Module;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    @FXML
    private ListView<Module> moduleListView;

    public ModuleListPanel(ObservableList<Module> displayedList) {
        super(FXML);
        moduleListView.setItems(displayedList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code ModuleCard}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module trackedModule, boolean empty) {
            super.updateItem(trackedModule, empty);

            if (empty || trackedModule == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new ModuleCard(trackedModule, getIndex() + 1).getRoot());
            }
        }
    }

}
