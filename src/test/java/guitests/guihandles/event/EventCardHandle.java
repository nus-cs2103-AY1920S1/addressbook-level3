package guitests.guihandles.event;

import guitests.guihandles.CardHandle;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import seedu.moolah.model.expense.Event;

/**
 * Provides a handle for {@code EventListPanel} containing the list of {@code Event}.
 *
 * Adapted from with modifications:
 * https://github.com/se-edu/addressbook-level4/blob/master/src/test/java/guitests/guihandles/PersonCardHandle.java
 */
public class EventCardHandle extends CardHandle<Event> {

    private static final String INDEX_FX_ID = "#index";
    private static final String DESCRIPTION_FX_ID = "#description";
    private static final String PRICE_FX_ID = "#price";
    private static final String CATEGORY_FX_ID = "#categories";
    private static final String DATE_FX_ID = "#date";
    private static final String TIME_FX_ID = "#time";

    private final Label index;
    private final Label description;
    private final Label date;
    private final Label time;
    private final Label price;
    private final Label category;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        index = getChildNode(INDEX_FX_ID);
        date = getChildNode(DATE_FX_ID);
        time = getChildNode(TIME_FX_ID);
        price = getChildNode(PRICE_FX_ID);
        description = getChildNode(DESCRIPTION_FX_ID);
        FlowPane categories = getChildNode(CATEGORY_FX_ID);
        category = (Label) categories.getChildren().get(0);
    }

    public String getIndex() {
        return index.getText();
    }

    public String getDescription() {
        return description.getText();
    }

    public String getDate() {
        return date.getText();
    }

    public String getTime() {
        return time.getText();
    }

    public String getPrice() {
        return price.getText();
    }

    public String getCategory() {
        return category.getText();
    }

    @Override
    public boolean wraps(Event event) {
        return getDescription().equals(event.getDescription().fullDescription)
                && getCategory().equals(event.getCategory().getCategoryName());
    }
}
