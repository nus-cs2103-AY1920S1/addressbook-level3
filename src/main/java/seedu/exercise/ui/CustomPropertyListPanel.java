package seedu.exercise.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
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

    @FXML
    private ListView<String> customPropertiesListView;

    public CustomPropertyListPanel(Map<String, String> customPropertiesMap) {
        super(FXML);
        ObservableList<String> customPropertiesList = toObservableList(customPropertiesMap);
        customPropertiesListView.setItems(customPropertiesList);
        customPropertiesListView.setCellFactory(listView -> new CustomPropertyListViewCell());

    }

    /**
     * Converts the input {@code map} into an ObservableList for display in the UI.
     */
    private ObservableList<String> toObservableList(Map<String, String> map) {
        List<String> newList = new ArrayList<>();
        List<String> customPropertyNames = new ArrayList<>(map.keySet());
        Collections.sort(customPropertyNames);
        for (String name : customPropertyNames) {
            String toAdd = name + ": " + map.get(name);
            newList.add(toAdd);
        }
        return FXCollections.observableList(newList);
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
