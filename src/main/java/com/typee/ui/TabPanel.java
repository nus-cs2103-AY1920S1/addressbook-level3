package com.typee.ui;

import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.model.Tab;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

/**
 * Panel containing the list of tabs/menus.
 */
public class TabPanel extends UiPart<Region> {
    private static final Logger logger = LogsCenter.getLogger(TabPanel.class);
    private static final String FXML = "TabPanel.fxml";

    @FXML
    private ListView<Tab> tabListView;

    public TabPanel(ObservableList<Tab> tabList) {
        super(FXML);
        logger.info("Tabs generated: " + tabList.toString());
        tabListView.setItems(tabList);
        tabListView.setCellFactory(param -> new ListCell<Tab>() {
            @Override
            protected void updateItem(Tab tab, boolean empty) {
                super.updateItem(tab, empty);
                if (empty || tab == null || tab.getName() == null) {
                    setText(null);
                } else {
                    setText(tab.getName());
                }
            }
        });
    }
}
