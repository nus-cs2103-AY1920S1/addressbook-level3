package seedu.address.ui;

import com.sun.javafx.scene.control.behavior.MenuButtonBehaviorBase;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

import java.util.logging.Logger;

public class NavigationBar extends UiPart<Region> {
    private static final String FXML = "NavigationBar.fxml";

    public NavigationBar() {
        super(FXML);
    }

    @FXML
    private void goToWatchlist() {

    }

    @FXML
    private void goToWatched() {

    }

    @FXML
    private void goToSearch() {

    }

    @FXML
    private void goToStatistics() {

    }
}
