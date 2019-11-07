package seedu.revision.ui.bar;

import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import seedu.revision.ui.UiPart;

/**
 * Shows a bar to the user to indicate current progress through quiz.
 */
public class ProgressIndicatorBar extends UiPart<Region> {
    private static final String FXML = "ProgressIndicatorBar.fxml";
    private static final int DEFAULT_LABEL_PADDING = 5;

    @FXML
    protected ProgressBar bar;
    @FXML
    protected Text text = new Text();

    private final ReadOnlyDoubleProperty currentScore;
    private final double totalScore;
    private final String labelFormatSpecifier;

    public ProgressIndicatorBar(final ReadOnlyDoubleProperty currentScore, final double totalScore,
                                final String labelFormatSpecifier) {
        super(FXML);
        this.currentScore = currentScore;
        this.totalScore = totalScore;
        this.labelFormatSpecifier = labelFormatSpecifier;
        bar.getStyleClass().add("progress-bar");

        syncProgress();
        currentScore.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                syncProgress();
            }
        });


        bar.setMaxWidth(Double.MAX_VALUE); // allows the progress bar to expand to fill available horizontal space.
    }

    /**
     * Synchronizes the progress indicated with the work done.
     */
    private void syncProgress() {
        //Run on the JavaFX Application Thread.
        Platform.runLater(() -> {
            if (currentScore == null || totalScore == 0) {
                text.setText("");
                bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
            } else {
                text.setText(String.format(labelFormatSpecifier, Math.ceil(currentScore.get())));
                bar.setProgress(currentScore.get() / totalScore);
            }

            bar.setMinHeight(text.getBoundsInLocal().getHeight() + DEFAULT_LABEL_PADDING * 2);
            bar.setMinWidth(text.getBoundsInLocal().getWidth() + DEFAULT_LABEL_PADDING * 2);
        });
    }

    public ProgressBar getBar() {
        return bar;
    }

    public Text getText() {
        return text;
    }
}
