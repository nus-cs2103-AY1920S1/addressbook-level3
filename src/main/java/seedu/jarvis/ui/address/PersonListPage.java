package seedu.jarvis.ui.address;

import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.template.Page;

public class PersonListPage extends Page<AnchorPane> {
    private static final String FXML = "PersonListPanel.fxml";

    @FXML
    private ListView<Person> personListView;


    public PersonListPage(MainWindow mainWindow, Logic logic, Model model) {
        super(FXML, mainWindow, logic, model);
    }

    @Override
    public void fillPage() {
        personListView.setItems(model.getFilteredPersonList());
        personListView.setCellFactory(listView -> new PersonListViewCell());

    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {
        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
            }
        }
    }
}
