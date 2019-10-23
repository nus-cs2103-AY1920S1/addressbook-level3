package dream.fcard.gui;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

//@@author nattanyz
public class MainWindowTest {
    // todo

    public void main(String args) {
        title_addTitle_noDuplicateTitle();
    }

    public void title_addTitle_noDuplicateTitle() {
        MainWindow mainWindow = new MainWindow(new Stage());
        mainWindow.setTitle("New title!");
    }
}
