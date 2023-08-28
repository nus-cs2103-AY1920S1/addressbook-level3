package seedu.address.ui;

import java.util.logging.Logger;

import org.controlsfx.control.MasterDetailPane;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;

//@@author shaoyi1997
/**
 * Master Detail Pane for list of bodies and a selected body's details.
 */

public class BodyMasterDetailPane extends UiPart<Region> {

    private static final String FXML = "BodyMasterDetailPane.fxml";
    private final Logger logger = LogsCenter.getLogger(BodyMasterDetailPane.class);

    @FXML
    private MasterDetailPane bodyMasterDetailPane;

    public BodyMasterDetailPane(BodyTableView bodyTableView, BodyCardSelected bodyCardSelected) {
        super(FXML);
        bodyMasterDetailPane.setMasterNode(bodyTableView.getRoot());
        bodyMasterDetailPane.setDetailNode(bodyCardSelected.getRoot());
    }
}
//@@author
