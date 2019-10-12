package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import javafx.fxml.FXML;

import seedu.address.model.password.Password;

public class PasswordListPanel extends UiPart<Region> {
    private static final String FXML = "PasswordListPanel.fxml";

    @FXML
    private ListView<Password> passwordListView;

    public PasswordListPanel(ObservableList<Password> passwordList) {
        super(FXML);
        passwordListView.setItems(passwordList);
        passwordListView.setCellFactory(listView -> new PasswordListViewCell());
    }

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
