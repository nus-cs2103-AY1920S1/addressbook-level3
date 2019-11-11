package seedu.guilttrip.ui.wishlist;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.guilttrip.commons.core.LogsCenter;
import seedu.guilttrip.model.entry.Wish;
import seedu.guilttrip.ui.UiPart;

/**
 * Side panel for wishes in wishlist.
 */
public class WishListPanel extends UiPart<Region> {
    private static final String FXML = "wishlist/WishListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(WishListPanel.class);

    @FXML
    private ListView<Wish> wishListView;

    public WishListPanel(ObservableList<Wish> wishList) {
        super(FXML);
        wishListView.setItems(wishList);
        wishListView.setCellFactory(listView -> new WishListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class WishListViewCell extends ListCell<Wish> {
        @Override
        protected void updateItem(Wish wish, boolean empty) {
            super.updateItem(wish, empty);

            if (empty || wish == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new WishListCard(wish, getIndex() + 1).getRoot());
            }
        }
    }
}
