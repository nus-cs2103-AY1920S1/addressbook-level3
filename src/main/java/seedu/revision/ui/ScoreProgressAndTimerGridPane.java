package seedu.revision.ui;


import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seedu.revision.commons.core.LogsCenter;


public class ScoreProgressAndTimerGridPane extends UiPart<Region> {
    private final Logger logger = LogsCenter.getLogger(ScoreProgressAndTimerGridPane.class);
    private static final String FXML = "ScoreProgressAndTimerGridPane.fxml";

    private ProgressIndicatorBar progressIndicatorBar;
    @FXML
    private Label timer;
    @FXML
    private StackPane pane;
    @FXML
    private ProgressBar bar;
    @FXML
    protected Text text;



    public ScoreProgressAndTimerGridPane(ProgressIndicatorBar progressIndicatorBar, int time) {
        super(FXML);
        this.progressIndicatorBar = progressIndicatorBar;
        bar = progressIndicatorBar.bar;
        text = progressIndicatorBar.text;
        pane.getChildren().setAll(bar, text);
        timer.setText("Time left: " + time);
    }

}
