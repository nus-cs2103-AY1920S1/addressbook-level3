package com.typee.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import com.typee.commons.core.LogsCenter;
import com.typee.model.engagement.Engagement;
import java.util.logging.Logger;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Engagement> personListView;

    public PersonListPanel(ObservableList<Engagement> personList) {
        super(FXML);
        personListView.setItems(personList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Engagement> {
        @Override
        protected void updateItem(Engagement engagement, boolean empty) {
            super.updateItem(engagement, empty);

            if (empty || engagement == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(engagement, getIndex() + 1).getRoot());
            }
        }
    }

}
