package dream.fcard.core;

import dream.fcard.MainApp;
import dream.fcard.util.RegexUtil;
import java.util.ArrayList;
import javafx.application.Application;

/**
 * The main entry point to the application.
 * <p>
 * This is a workaround for the following error when MainApp is made the
 * entry point of the application:
 * <p>
 * Error: JavaFX runtime components are missing, and are required to run this application
 * <p>
 * The reason is that MainApp extends Application. In that case, the
 * LauncherHelper will check for the javafx.graphics module to be present
 * as a named module. We don't use JavaFX via the module system so it can't
 * find the javafx.graphics module, and so the launch is aborted.
 * <p>
 * By having a separate main class (Main) that doesn't extend Application
 * to be the entry point of the application, we avoid this issue.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println(RegexUtil.commandFormatRegex("create", new String[] {"front/", "back/", "choice/"}));
        for (ArrayList<String> arg : RegexUtil.parseCommandFormat("", new String[]{"back/", "front/"}, "front/ asd back/ 123 back/ 456")) {
            for(String s : arg) {
                System.out.print(s + ", ");
            }
            System.out.println("");
        }
        //Application.launch(MainApp.class, args);
    }
}
