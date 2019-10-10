package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.show.Show;

import javax.swing.*;

/**
 * An UI component that displays information of a {@code Show}.
 */
public class ShowCard extends UiPart<Region> {

    private static final String FXML = "ShowCard.fxml";

    public final Show show;

    @FXML
    private Label showIndex;
    @FXML
    private Label showName;
    @FXML
    private HBox cardPane;
    @FXML
    private Label dateOfRelease;
    @FXML
    private Label runningTime;
    @FXML
    private Label actors;
    @FXML
    private Label description;
    @FXML
    private JCheckBox isWatched;

    public ShowCard(Show show, int displayedIndex) {
        super(FXML);
        this.show = show;
        showIndex.setText("" + displayedIndex);
        showName.setText(show.getName().toString());
    }
}
