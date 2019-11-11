package seedu.tarence.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.tarence.commons.core.LogsCenter;
import seedu.tarence.model.module.Module;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ModuleListPanel.class);

    private int scrollPosition = 0;

    @FXML
    private ListView<Module> moduleListView;

    public ModuleListPanel(ObservableList<Module> moduleList) {
        super(FXML);
        moduleListView.setItems(moduleList);
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
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
            } else {
                setGraphic(new ModuleCard(module, getIndex() + 1).getRoot());
            }
        }
    }

    /**
     * Scrolls through the module list panel in the given direction.
     */
    void scrollPanel(String direction) {
        if (direction.equals("down") && scrollPosition < moduleListView.getItems().size() - 1) {
            scrollPosition += 1;
        }
        if (direction.equals("up") && scrollPosition > 0) {
            scrollPosition -= 1;
        }
        moduleListView.scrollTo(scrollPosition);
    }

}
