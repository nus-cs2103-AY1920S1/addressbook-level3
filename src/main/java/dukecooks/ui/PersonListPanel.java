package dukecooks.ui;

import java.util.logging.Logger;

import dukecooks.commons.core.LogsCenter;
import dukecooks.model.profile.person.Person;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private VBox personCard;

    public PersonListPanel(ObservableList<Person> personList) {
        super(FXML);
        setUpCard(personList);
    }

    void setUpCard(ObservableList<Person> personList) {
        addDetails(personList);

        //add listener for new record changes
        personList.addListener((ListChangeListener<Person>) c -> {
            personCard.getChildren().clear();
            addDetails(personList);
        });
    }

    /**
     * Fills user's information in the profile placeholder.
     */
    void addDetails(ObservableList<Person> personList) {
        if (personList.isEmpty()) {
            personCard.getChildren().add(new Label("No User Profile"));
        } else {
            for (Person person: personList) {
                personCard.getChildren().clear();
                personCard.getChildren().add(new ProfileCard(person).getRoot());
            }
        }
    }
}
