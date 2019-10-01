package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.model.person.Person;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Changes the details view to this person */
    void changeView(Person person);
}
