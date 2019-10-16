package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.category.Category;


/**
 * Panel containing the list of Categories
 */
public class CategoryListPanel extends UiPart<Region> {
    private static final String FXML = "CategoryListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CategoryListPanel.class);

    @FXML
    private ListView<Category> categoryListView;

    public CategoryListPanel(ObservableList<Category> categoryList) {
        super(FXML);
        categoryListView.setItems(categoryList);
        categoryListView.setCellFactory(listview -> new CategoryListViewCell());
    }
    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Category} using a {@code Category}.
     */
    class CategoryListViewCell extends ListCell<Category> {
        @Override
        protected void updateItem(Category category, boolean empty) {
            super.updateItem(category, empty);

            if (empty || category == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new CategoryPanel(category, getIndex() + 1).getRoot());
            }
        }
    }
}
