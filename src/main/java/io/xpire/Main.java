package io.xpire;

import javafx.application.Application;

/**
 * The main entry point to the application.
 *
 * This is a workaround for the following error when MainApp is made the
 * entry point of the application:
 *
 *     Error: JavaFX runtime components are missing, and are required to run this application
 *
 * The reason is that MainApp extends Application. In that case, the
 * LauncherHelper will check for the javafx.graphics module to be present
 * as a named module. We don't use JavaFX via the module system so it can't
 * find the javafx.graphics module, and so the launch is aborted.
 *
 * By having a separate main class (Main) that doesn't extend Application
 * to be the entry point of the application, we avoid this issue.
 */
public class Main {
    /**
     * Entry point.
     */
    public static void main(String[] args) {

        // Comment/uncomment the lines to execute either Xpire or login page.
        Application.launch(MainApp.class, args);
        //Application.launch(Login.class, args);
    }
}
