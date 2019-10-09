package tagline.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import tagline.model.person.Person;

/**
 * The UI component that displays the contact list as a result.
 */
public class ContactResultView extends ResultView {

    private static final String FXML = "ContactResultView.fxml";

    private PersonListPanel personListPanel;

    @FXML
    private StackPane personListPanelPlaceholder;

    public ContactResultView() {
        super(FXML);
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts(ObservableList<Person> personList) {
        personListPanel = new PersonListPanel(personList);
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
    }
}
