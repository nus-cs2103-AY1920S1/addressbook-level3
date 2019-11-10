package guitests.guihandles;

import javafx.scene.control.ProgressBar;

/**
 * A handler for the {@code ProgressIndicatorBar} of the UI
 */
public class ProgressIndicatorBarHandle extends NodeHandle<ProgressBar> {

    public static final String PROGRESS_BAR_ID = "#bar";

    public ProgressIndicatorBarHandle(ProgressBar progressBarNode) {
        super(progressBarNode);
    }

    /**
     * Returns the progress of the progress bar.
     */
    public double getProgress() {
        return getRootNode().getProgress();
    }

}
