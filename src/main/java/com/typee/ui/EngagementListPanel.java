package com.typee.ui;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.model.engagement.Engagement;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of persons.
 */
public class EngagementListPanel extends UiPart<Region> {
    public static final String FXML = "EngagementListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(EngagementListPanel.class);

    @FXML
    private ListView<Engagement> engagementListView;

    public EngagementListPanel(ObservableList<Engagement> engagementList) {
        super(FXML);
        engagementListView.setItems(engagementList);
        engagementListView.setCellFactory(listView -> new EngagementListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of an {@code Engagement} using an {@code EngagementCard}.
     */
    class EngagementListViewCell extends ListCell<Engagement> {
        @Override
        protected void updateItem(Engagement engagement, boolean empty) {
            super.updateItem(engagement, empty);

            if (empty || engagement == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EngagementCard(engagement, getIndex() + 1).getRoot());
            }
        }
    }

}
