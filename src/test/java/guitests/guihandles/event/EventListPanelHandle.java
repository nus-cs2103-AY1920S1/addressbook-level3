package guitests.guihandles.event;

import guitests.guihandles.ListHandle;
import javafx.scene.control.ListView;
import seedu.moolah.model.expense.Event;

/**
 * Provides a handle for {@code EventListPanel} containing the list of {@code Event}.
 *
 */
public class EventListPanelHandle extends ListHandle<EventCardHandle, Event> {

    public static final String EVENT_LIST_VIEW_ID = "#listView";
    public static final String EVENT_LIST_CARD_ID = "#expenseCardPane";

    public EventListPanelHandle(ListView<Event> listViewPanel) {
        super(listViewPanel, EVENT_LIST_CARD_ID, EventCardHandle::new);
    }
}
