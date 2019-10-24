package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.model.category.Category;

/**
 * An UI component that displays information of a {@code Category}.
 */
public class CategoryPanel extends UiPart<Region> {

    private static final String FXML = "CategoryListCard.fxml";

    public final Category category;

    @FXML
    private Label categoryFx;


    public CategoryPanel(Category category, int displayedIndex) {
        super(FXML);
        this.category = category;
        categoryFx.setText(category.categoryName);

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CategoryPanel)) {
            return false;
        }

        // state check
        CategoryPanel card = (CategoryPanel) other;
        return category.equals(card.category);
    }

}
