package seedu.elisa.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.elisa.commons.core.item.Item;

public class OpenItem extends UiPart<Region> {
    private static final String FXML = "OpenItem.fxml";

    @FXML
    public final Item item;

    @FXML
    private HBox cardPane;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label priority;
    @FXML
    private Label startdate;
    @FXML
    private Label enddate;
    @FXML
    private FlowPane tags;

    public OpenItem(Item item) {
        super(FXML);
        this.item = item;
        description.setText(item.getItemDescription().toString());
        priority.setText("Priority: " + item.getPriority().toString());
        startdate.setText("Start Date: " + item.getEvent().get().getStartDateTime().toString());
        enddate.setText("End Date: " + item.getEvent().get().getEndDateTime().toString());
    }
}