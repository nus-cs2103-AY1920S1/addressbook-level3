package seedu.exercise.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Represents a list panel that contains all of the custom property information of an exercise.
 */
public class CustomPropertyListPanel extends UiPart<Region> {
    private static final String FXML = "CustomPropertyListPanel.fxml";

    private final ObservableList<String> customPropertiesList;

    @FXML
    private ListView<String> customPropertiesListView;

    public CustomPropertyListPanel(ObservableList<String> customPropertiesList) {
        super(FXML);
        this.customPropertiesList = customPropertiesList;
        customPropertiesListView.setItems(customPropertiesList);
        customPropertiesListView.setCellFactory(listView -> new CustomPropertyListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a custom property value using a
     * {@code CustomPropertyCard}.
     */
    class CustomPropertyListViewCell extends ListCell<String> {

        @Override
        protected void updateItem(String customPropertyDisplayResult, boolean empty) {
            super.updateItem(customPropertyDisplayResult, empty);

            if (empty || customPropertyDisplayResult == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CustomPropertyCard(customPropertyDisplayResult).getRoot());
            }
        }
    }


}
