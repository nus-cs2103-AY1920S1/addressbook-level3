package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;

import seedu.address.model.password.Password;

/**
 * Panel containing the list of passwords.
 */
public class PasswordListPanel extends UiPart<Region> {
    private static final String FXML = "PasswordListPanel.fxml";

    @FXML
    private ListView<Password> passwordListView;

    public PasswordListPanel(ObservableList<Password> passwordList) {
        super(FXML);
        passwordListView.setItems(passwordList);
        passwordListView.setCellFactory(listView -> new PasswordListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Password} using a {@code PasswordCard}.
     */
    class PasswordListViewCell extends ListCell<Password> {
        @Override
        protected void updateItem(Password password, boolean empty) {
            super.updateItem(password, empty);

            if (empty || password == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PasswordCard(password, getIndex() + 1).getRoot());
            }
        }
    }
}
