package seedu.revision.ui;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class ProgressIndicatorBar extends UiPart<Region> {
    private static final String FXML = "ProgressIndicatorBar.fxml";
    private final static int DEFAULT_LABEL_PADDING = 5;
    private final ReadOnlyDoubleProperty workDone;
    private final double totalWork;

    @FXML
    private StackPane pane;
    @FXML
    private ProgressBar bar;
    @FXML
    private Text text = new Text();
    private final String labelFormatSpecifier;


    public ProgressIndicatorBar(final ReadOnlyDoubleProperty workDone, final double totalWork, final String labelFormatSpecifier) {
        super(FXML);
        this.workDone  = workDone;
        this.totalWork = totalWork;
        this.labelFormatSpecifier = labelFormatSpecifier;

        syncProgress();
        workDone.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                syncProgress();
            }
        });

        bar.setMaxWidth(Double.MAX_VALUE); // allows the progress bar to expand to fill available horizontal space.

        pane.getChildren().setAll(bar, text);
    }

    // synchronizes the progress indicated with the work done.
    private void syncProgress() {
        if (workDone == null || totalWork == 0) {
            text.setText("");
            bar.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        } else {
            text.setText(String.format(labelFormatSpecifier, Math.ceil(workDone.get())));
            bar.setProgress(workDone.get() / totalWork);
        }

        bar.setMinHeight(text.getBoundsInLocal().getHeight() + DEFAULT_LABEL_PADDING * 2);
        bar.setMinWidth (text.getBoundsInLocal().getWidth()  + DEFAULT_LABEL_PADDING * 2);
    }
}
