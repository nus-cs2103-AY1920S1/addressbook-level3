package seedu.ifridge.ui;

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
import seedu.ifridge.commons.core.LogsCenter;
import seedu.ifridge.model.food.GroceryItem;


/**
 * Panel for waste list.
 */
public class WasteListPanel extends UiPart<Region> {
    private static final String FXML = "WasteListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WasteListPanel.class);

    private int numOfDays;

    @FXML
    private ListView<GroceryItem> wasteListView;

    public WasteListPanel(ObservableList<GroceryItem> foodList, String numOfDays) {
        super(FXML);
        wasteListView.setItems(foodList);
        wasteListView.setCellFactory(listView -> new WasteListViewCell());
        this.numOfDays = Integer.valueOf(numOfDays);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class WasteListViewCell extends ListCell<GroceryItem> {
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
         * Checks if the grocery item is expiring within default number of days.
         */
        public boolean isExpiring(Date date) {
            Calendar cal = Calendar.getInstance();
            Date current = cal.getTime();
            int diffDays = (int) ((date.getTime() - current.getTime()) / (24 * 60 * 60 * 1000));
            return diffDays <= numOfDays;
        }
    }

}
