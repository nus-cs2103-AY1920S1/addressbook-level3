//@@author CarbonGrid
package seedu.address.ui;

import java.util.HashSet;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends OmniPanel<Person> {

    public PersonListPanel(ObservableList<Person> personList, HashSet<Runnable> deferredUntilMouseClickOuter) {
        super(deferredUntilMouseClickOuter);
        super.setItems(personList);
        super.setCellFactory(listView -> new PersonListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class PersonListViewCell extends ListCell<Person> {

        public PersonListViewCell() {
            setFocusTraversable(true);
        }

        @Override
        protected void updateItem(Person person, boolean empty) {
            super.updateItem(person, empty);
            if (empty || person == null) {
                Platform.runLater(() -> setGraphic(null));
            } else {
                Platform.runLater(() -> setGraphic(new PersonCard(person, getIndex() + 1).getRoot()));
            }
        }
    }
}
