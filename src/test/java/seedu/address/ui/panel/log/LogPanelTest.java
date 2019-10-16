package seedu.address.ui.panel.log;

import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.application.Application;

import seedu.address.MainApp;

public class LogPanelTest {

    private static boolean threadFlag = true;

    @BeforeAll
    static void setUpTest() {
        Thread t = new Thread("JavaFX Init Thread") {
            public void run() {
                while (threadFlag) {
                    Application.launch(MainApp.class, new String[0]);
                }
            }
        };
        t.setDaemon(true);
        t.start();

    }

    @Test
    void createLogBoxTest() {
        try {
            LogPanel logPanel = new LogPanel();
            logPanel.createLogBox("Feedback to the user", "-primaryColor");
        } catch (Exception e) {
            fail(e.getMessage());
        }
        threadFlag = false;
    }

}
