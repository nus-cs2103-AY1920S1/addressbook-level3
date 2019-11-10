package seedu.moolah.ui.event;

import static seedu.moolah.commons.util.AppUtil.getImage;

import java.time.format.DateTimeFormatter;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Event;
import seedu.moolah.ui.UiPart;

/**
 * An UI component that displays information of a {@code Event}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    private static final Category FOOD = new Category("FOOD");
    private static final Category TRAVEL = new Category("TRAVEL");
    private static final Category TRANSPORT = new Category("TRANSPORT");
    private static final Category SHOPPING = new Category("SHOPPING");
    private static final Category UTILITIES = new Category("UTILITIES");
    private static final Category HEALTHCARE = new Category("HEALTHCARE");
    private static final Category ENTERTAINMENT = new Category("ENTERTAINMENT");
    private static final Category EDUCATION = new Category("EDUCATION");

    // icons (except other.png) made by Freepik from Flaticon.com
    private static final String FOOD_ICON = "/images/category/food.png";
    private static final String SHOPPING_ICON = "/images/category/shopping.png";
    private static final String TRANSPORT_ICON = "/images/category/transport.png";
    private static final String UTILITIES_ICON = "/images/category/utilities.png";
    private static final String TRAVEL_ICON = "/images/category/travel.png";
    private static final String EDUCATION_ICON = "/images/category/education.png";
    private static final String ENTERTAINMENT_ICON = "/images/category/entertainment.png";
    private static final String HEALTHCARE_ICON = "/images/category/healthcare.png";
    private static final String OTHERS_ICON = "/images/category/others.png";

    private static final String DATE_PATTERN = "dd MMM yyyy";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on MooLah level 4</a>
     */

    public final Event event;

    @FXML
    private AnchorPane eventCardPane;
    @FXML
    private Label description;
    @FXML
    private Label index;
    @FXML
    private Label price;
    @FXML
    private Label date;
    @FXML
    private Label time;
    @FXML
    private Label budgetName;
    @FXML
    private FlowPane categories;
    @FXML
    private Circle icon;
    @FXML
    private Circle iconBackground;

    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        index.setText(Integer.toString(displayedIndex));
        description.setText(event.getDescription().fullDescription);
        price.setText(String.format("%s%,.2f", "$", event.getPrice().getAsDouble()));
        categories.getChildren().add(new Label(event.getCategory().getCategoryName()));
        date.setText(event.getTimestamp().fullTimestamp.format(DateTimeFormatter.ofPattern(DATE_PATTERN)));
        time.setText(null);
        budgetName.setText(event.getBudgetName().fullDescription);

        setIcon(icon, event.getCategory());
    }

    /**
     * Sets the {@code Event Card} icon's {@code ImagePattern} fill based on the {@code Category} of the {@code Event}.
     */
    private void setIcon(Circle icon, Category category) {
        if (FOOD.equals(category)) {
            icon.setFill(new ImagePattern(getImage(FOOD_ICON)));
        } else if (TRAVEL.equals(category)) {
            icon.setFill(new ImagePattern(getImage(TRAVEL_ICON)));
        } else if (HEALTHCARE.equals(category)) {
            icon.setFill(new ImagePattern(getImage(HEALTHCARE_ICON)));
        } else if (EDUCATION.equals(category)) {
            icon.setFill(new ImagePattern(getImage(EDUCATION_ICON)));
        } else if (ENTERTAINMENT.equals(category)) {
            icon.setFill(new ImagePattern(getImage(ENTERTAINMENT_ICON)));
        } else if (UTILITIES.equals(category)) {
            icon.setFill(new ImagePattern(getImage(UTILITIES_ICON)));
        } else if (SHOPPING.equals(category)) {
            icon.setFill(new ImagePattern(getImage(SHOPPING_ICON)));
        } else if (TRANSPORT.equals(category)) {
            icon.setFill(new ImagePattern(getImage(TRANSPORT_ICON)));
        } else {
            icon.setFill(new ImagePattern(getImage(OTHERS_ICON)));
        }
        icon.toFront();
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventCard)) {
            return false;
        }

        // state check
        EventCard card = (EventCard) other;
        return index.getText().equals(card.index.getText())
                && event.equals(card.event);
    }
}
