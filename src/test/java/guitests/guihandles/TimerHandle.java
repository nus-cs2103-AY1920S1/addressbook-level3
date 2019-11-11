package guitests.guihandles;

import javafx.scene.control.Label;

/**
 * A handler for the {@code ProgressIndicatorBar} of the UI
 */
public class TimerHandle extends NodeHandle<Label> {

    public static final String TIMER_LABEL_ID = "#timerLabel";

    public TimerHandle(Label timerLabel) {
        super(timerLabel);
    }

    /**
     * Returns the text of the timer label.
     */
    public String getText() {
        return getRootNode().getText();
    }

}
