package seedu.mark.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.mark.MainApp;
import seedu.mark.commons.core.LogsCenter;

public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class.getResource(FXML_FILE_FOLDER + "default.html"));
    //public static final URL GOOGLE_SEARCH = new URL("google.com.sg");

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    private WebEngine webEngine;

    public BrowserPanel() {
        super(FXML);

        //browser = new WebView(); //dunnid cus fxml loading will init i think
        webEngine = browser.getEngine();
        //webEngine.setJavaScriptEnabled(true);

        webEngine.getLoadWorker()
                .stateProperty()
                .addListener(
                        new ChangeListener<State>() {
                            @Override
                            public void changed(ObservableValue<? extends State> observable,
                                                State oldValue,
                                                State newValue) {

                                String pageUrl = webEngine.getLocation();
                                if (newValue == State.FAILED) {
                                    logger.info("browser: unable to connect to internet");
                                    loadDefaultPage();
                                    //logger.info("loading default page now");
                                    //tryLoadingAgain(pageUrl);
                                }
                            }
                        }
                );
        //webEngine.load("http://google.com.sg");
        //loadDefaultPage();
        loadPage("https://google.com.sg");
    }

    public void loadPage(String url) {
        Platform.runLater(() -> webEngine.load(url));
    }

    private void tryLoadingAgain(String url) {
        try {
            Thread.sleep(2000);
            loadPage(url);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
        }
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

}
