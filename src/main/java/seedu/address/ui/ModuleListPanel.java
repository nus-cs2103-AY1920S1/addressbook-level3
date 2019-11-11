package seedu.address.ui;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;

/**
 * Panel containing the list of modules.
 */
public class ModuleListPanel extends UiPart<Region> {
    private static final String FXML = "ModuleListPanel.fxml";

    @FXML
    private ListView<Module> moduleListView;

    public ModuleListPanel(ObservableList<Module> modules) {
        super(FXML);
        List<Module> sortedModules = modules.stream()
                .sorted(Comparator.comparing(module -> module.getModuleCode().toString()))
                .collect(Collectors.toList());
        UniqueModuleList uniqueModuleList = new UniqueModuleList();
        uniqueModuleList.setModules(sortedModules);
        moduleListView.setItems(uniqueModuleList.asUnmodifiableObservableList());
        moduleListView.setCellFactory(listView -> new ModuleListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Module} using a {@code Label}.
     */
    class ModuleListViewCell extends ListCell<Module> {
        @Override
        protected void updateItem(Module module, boolean empty) {
            super.updateItem(module, empty);

            if (empty || module == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new SimpleModuleCard(module).getRoot());
            }
        }
    }

}
