package seedu.jarvis.ui.homepage;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.jarvis.commons.core.LogsCenter;
import seedu.jarvis.logic.Logic;
import seedu.jarvis.model.Model;
import seedu.jarvis.model.address.person.Person;
import seedu.jarvis.model.cca.Cca;
import seedu.jarvis.ui.MainWindow;
import seedu.jarvis.ui.UiPart;
import seedu.jarvis.ui.address.PersonListPanel;
import seedu.jarvis.ui.template.Page;

import java.net.URL;
import java.util.logging.Logger;

public class HomePage extends UiPart<Region> {

    private static final String FXML = "homepage/HomePage.fxml";

    public HomePage() {
        super(FXML);
    }
}
