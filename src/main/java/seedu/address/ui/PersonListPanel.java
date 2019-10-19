package seedu.address.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.food.GroceryItem;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<GroceryItem> personListView;

    public PersonListPanel(ObservableList<GroceryItem> foodList) {
        super(FXML);
        personListView.setItems(foodList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<GroceryItem> {
        @Override
        protected void updateItem(GroceryItem food, boolean empty) {
            super.updateItem(food, empty);
            if (empty || food == null) {
                setGraphic(null);
                setText(null);
            } else {
                Date date = null;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(food.getExpiryDate().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (hasExpired(date)) {
                    setGraphic(new ExpiredGroceryCard(food, getIndex() + 1).getRoot());
                } else if (isExpiring(date)) {
                    setGraphic(new IsExpiringGroceryCard(food, getIndex() + 1).getRoot());
                } else {
                    setGraphic(new NotExpiringGroceryCard(food, getIndex() + 1).getRoot());
                }
            }
        }

        /**
         * Checks if the grocery item is expired.
         */
        public boolean hasExpired(Date date) {
            Calendar cal = Calendar.getInstance();
            Date current = cal.getTime();
            return date.before(current);
        }

        /**
         * Checks if the grocery item is expiring within 3 days.
         */
        public boolean isExpiring(Date date) {
            Calendar cal = Calendar.getInstance();
            Date current = cal.getTime();
            int diffDays = (int) ((date.getTime() - current.getTime()) / (24 * 60 * 60 * 1000));
            return diffDays <= 3;
        }
    }

}
