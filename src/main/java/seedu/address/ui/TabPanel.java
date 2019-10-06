package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Region;

/**
 * Class to create a tab panel.
 */
public class TabPanel extends UiPart<Region> {
    private static final String FXML = "TabPanel.fxml";

    @FXML
    private TabPane tabPanel;

    @FXML
    private Tab personsTab;

    @FXML
    private Tab groupsTab;

    public TabPanel() {
        super(FXML);
        this.tabPanel = new TabPane();
        this.personsTab = new Tab("Person");
        personsTab.setClosable(false);
        this.groupsTab = new Tab("Groups");
        groupsTab.setClosable(false);
        tabPanel.getTabs().addAll(personsTab, groupsTab);
    }

    public void setContent(Node personList) {
        personsTab.setContent(personList);
    }

    public TabPane getTabs() {
         return this.tabPanel;
    }
}
