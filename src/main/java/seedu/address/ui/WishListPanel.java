package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Entry;
//import seedu.address.model.person.Wish;

/**
 * Side panel for wishes in wishlist.
 */
public class WishListPanel extends UiPart<Region> {
    private static final String FXML = "WishListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WishListPanel.class);

    @FXML
    private ListView<Entry> wishListView;

    public WishListPanel(ObservableList<Entry> wishList) {
        super(FXML);
        wishListView.setItems(wishList);
        wishListView.setCellFactory(listView -> new WishListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class WishListViewCell extends ListCell<Entry> {
        @Override
        protected void updateItem(Entry entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WishListCard(entry, getIndex() + 1).getRoot());
            }
        }
    }
}
