package seedu.address.ui;

import java.util.logging.Logger;

import org.controlsfx.control.MasterDetailPane;
import org.controlsfx.control.PropertySheet;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

public class BodyMasterDetailPane extends UiPart<Region> {

    private static final String FXML = "BodyMasterDetailPane.fxml";
    private final Logger logger = LogsCenter.getLogger(BodyMasterDetailPane.class);

    @FXML
    private MasterDetailPane bodyMasterDetailPane;

    public BodyMasterDetailPane(BodyTableView bodyTableView) {
        super(FXML);
        bodyMasterDetailPane.setMasterNode(bodyTableView.getRoot());
        bodyMasterDetailPane.setDetailNode(new PropertySheet());
    }
}
