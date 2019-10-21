package seedu.address.ui.panel.calendar;

import javafx.scene.layout.Region;

import seedu.address.model.events.EventSource;
import seedu.address.ui.UiPart;

import java.util.List;

public abstract class TimelineView extends UiPart<Region> {

    static final Integer SPACING = 62;
    static final Integer TIMING = 10;

    public TimelineView(String FXML) {
        super(FXML);
    }
    abstract void resizeTimelineView();
    abstract void eventChange(List<EventSource> eventList);
}
