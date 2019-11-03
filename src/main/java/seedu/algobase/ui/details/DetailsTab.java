package seedu.algobase.ui.details;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.Region;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.TabData;
import seedu.algobase.model.gui.WriteOnlyTabManager;
import seedu.algobase.ui.UiPart;

/**
 * An UI component that displays tab content.
 */
public class DetailsTab extends UiPart<Region> {

    private static final String FXML = "DetailsTab.fxml";

    private final Id modelId;
    private final ModelType modelType;

    @FXML
    private Tab tabContentPlaceholder;

    public DetailsTab(String name) {
        super(FXML);
        tabContentPlaceholder = new Tab(name);
        this.modelId = Id.generateId();
        this.modelType = ModelType.PROBLEM;
    }

    public DetailsTab(
        String name,
        UiPart<Region> uiPart,
        ModelType modelType,
        Id modelId,
        WriteOnlyTabManager writeOnlyTabManager
    ) {
        super(FXML);
        tabContentPlaceholder = new Tab(name, uiPart.getRoot());
        this.modelId = modelId;
        this.modelType = modelType;

        addOnCloseRequestListener(writeOnlyTabManager);
    }

    public Tab getTab() {
        return tabContentPlaceholder;
    }

    /**
     * Adds an listener that registers when the tab closes.
     */
    private void addOnCloseRequestListener(WriteOnlyTabManager writeOnlyTabManager) {
        tabContentPlaceholder.setOnCloseRequest(e -> {
            writeOnlyTabManager.closeDetailsTab(new TabData(modelType, modelId));
            e.consume();
        });
    }
}
