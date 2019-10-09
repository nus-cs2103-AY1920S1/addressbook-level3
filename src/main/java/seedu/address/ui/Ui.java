package seedu.address.ui;

import javafx.stage.Stage;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Changes the details view to this person. */
    void changeView(PersonDisplay person);

    /** Changes the details view to this group. */
    void changeView(Group group);

    /**Changes the details view to this string. */
    void changeView(String message);

    /**Exports visual representation for person. */
    void exportVisual(PersonDisplay person);

    /** Exports visual representation for groups. */
    void exportVisual(Group group);
}
