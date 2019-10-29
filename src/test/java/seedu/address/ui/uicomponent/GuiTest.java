package seedu.address.ui.uicomponent;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;
import seedu.address.ui.PersonCard;

public class GuiTest extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Person alice = TypicalPersons.ALICE;
        PersonCard personCard = new PersonCard(new PersonDisplay(alice));
        Scene scene = new Scene(personCard.getRoot());
        stage.setScene(scene);
        stage.show();
    }
}
