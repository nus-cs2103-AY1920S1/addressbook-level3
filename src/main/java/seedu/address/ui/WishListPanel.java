package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Wish;

public class WishListPanel extends UiPart<Region> {
    private static final String FXML = "WishListPanel.fxml";
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
        protected void updateItem(Wish entry, boolean empty) {
            super.updateItem(entry, empty);

            if (empty || entry == null) {

                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new EntryCard(entry, getIndex() + 1).getRoot());
            }
        }
    }
}
