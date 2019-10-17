package dream.fcard.gui;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainWindow {
    Stage primaryStage;

    // components of MainWindow (independent ui parts)

    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    void show() {
        primaryStage.show();
    }

    // consider renaming fillInnerParts
    void fillInnerParts() {

    }

    // methods I can consider omitting or refactoring
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    // private void setAccelerators()
    // private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination)
    // private void setWindowDefaultSize(GuiSettings guisSettings)

    // FXML methods
    // public void handleHelp()
    // private void handleExit()
}
