package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.recipe.Recipe;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Recipe> personListView;

    public PersonListPanel(ObservableList<Recipe> recipeList) {
        super(FXML);
        personListView.setItems(recipeList);
        personListView.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Recipe} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Recipe> {
        @Override
        protected void updateItem(Recipe recipe, boolean empty) {
            super.updateItem(recipe, empty);

            if (empty || recipe == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(recipe, getIndex() + 1).getRoot());
            }
        }
    }

}
