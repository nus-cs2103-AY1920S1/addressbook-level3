package seedu.revision.ui.bar;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import seedu.revision.commons.core.LogsCenter;
import seedu.revision.ui.UiPart;

/**
 * UI Part which contains the {@ProgressBar} and {@Timer} in a {@StackPane}.
 */
public class ScoreProgressAndTimerGridPane extends UiPart<Region> {
    private static final String FXML = "ScoreProgressAndTimerGridPane.fxml";

    @FXML
    protected Text text;

    private final Logger logger = LogsCenter.getLogger(ScoreProgressAndTimerGridPane.class);

    @FXML
    private Label timerLabel;
    @FXML
    private StackPane pane1;
    @FXML
    private StackPane pane2;
    @FXML
    private ProgressBar bar;
    private ProgressIndicatorBar progressIndicatorBar;
    private Timer timer;

    public ScoreProgressAndTimerGridPane(ProgressIndicatorBar progressIndicatorBar, Timer timer) {
        super(FXML);

        this.progressIndicatorBar = progressIndicatorBar;
        this.bar = progressIndicatorBar.bar;
        this.text = progressIndicatorBar.text;
        this.pane1.getChildren().setAll(bar, text);

        this.timer = timer;
        this.timerLabel = timer.getTimerLabel();
        this.pane2.getChildren().setAll(timerLabel);

        this.timer.startTimer();
    }



}
