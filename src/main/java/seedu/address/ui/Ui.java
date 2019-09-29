package seedu.address.ui;

import javafx.stage.Stage;

/**
 * API of UI component
 */
public interface Ui {

    /** Starts the UI (and the App).  */
    void start(Stage primaryStage);

    /** Switches the current UI page of the app. */
    void switchWindow(Class<? extends MainWindow> mainWindowClass, Stage primaryStage);

}
