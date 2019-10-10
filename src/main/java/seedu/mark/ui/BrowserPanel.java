package seedu.mark.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.mark.MainApp;
import seedu.mark.commons.core.LogsCenter;
import seedu.mark.model.bookmark.Url;

/**
 * The Browser Panel of Mark.
 */
public class BrowserPanel extends UiPart<Region> {

    /** Default html page to be loaded when not connected to internet. */
    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class
                    .getResource(FXML_FILE_FOLDER + "defaultOfflinePage.html"));
    /** Redirection html page to be loaded when invalid url is given. */
    public static final URL REDIRECTION_PAGE =
            requireNonNull(MainApp.class
                    .getResource(FXML_FILE_FOLDER + "redirectionPage.html"));

    /** Name of corresponding fxml file. */
    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private TextField addressBar;
    @FXML
    private ImageView homepageLogo;
    @FXML
    private Button homeButton;

    private WebEngine webEngine;
    private String currentPageUrl;

    public BrowserPanel() {
        super(FXML);

        loadGuiAddress();
        loadGuiGoogleButton();
        loadGuiBrowser();

        gotoHomepage();
    }

    /**
     * Initialises the address bar.
     */
    private void loadGuiAddress() {
        addressBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue) {
                    logger.info("Address bar in focus");
                    addressBar.selectAll();
                } else {
                    logger.info("Address bar out of focus");
                    if (!currentPageUrl.equals(addressBar.getText())) {
                        addressBar.setText(currentPageUrl);
                    }
                }
            });
        });
    }

    /**
     * Initialises the button that leads to google home page when pressed.
     */
    private void loadGuiGoogleButton() {
        homepageLogo.setImage(new Image(
                MainWindow.class.getResourceAsStream("/images/googleLogo.png")
        ));
    }

    /**
     * Initialises the embedded browser.
     */
    private void loadGuiBrowser() {
        webEngine = browser.getEngine();

        webEngine.getLoadWorker()
                .stateProperty()
                .addListener(
                        new ChangeListener<State>() {
                            @Override
                            public void changed(ObservableValue<? extends State> observable,
                                                State oldValue,
                                                State newValue) {

                                currentPageUrl = webEngine.getLocation();
                                if (newValue == State.FAILED) {
                                    logger.info("browser: unable to connect to internet");
                                    loadDefaultPage();
                                }

                                if (webEngine.getLocation().equals("file://" + DEFAULT_PAGE.getPath())) {
                                    showAddressOnAddressBar(
                                            "No internet connection detected. Please try again later.");
                                } else if (webEngine.getLocation().equals("file://" + REDIRECTION_PAGE.getPath())) {
                                    Platform.runLater(() -> redirectToGoogle());
                                } else {
                                    showAddressOnAddressBar(currentPageUrl);
                                }
                            }
                        });
    }

    ////////////////////// MAIN METHODS /////////////////////////

    /**
     * Loads page with url on the webview.
     * @param url valid url
     */
    public void loadPage(String url) {
        assert isValidUrl(url) : "invalid url passed to webEngine";

        Platform.runLater(() -> webEngine.load(url));
    }

    /**
     * Gets the url of the page the browser currently shows.
     * @return url of current page.
     */
    public String getCurrentPageUrl() {
        return webEngine.getLocation();
    }

    /**
     * Sets address bar text as url.
     * @param url url to set address bar content to.
     */
    private void showAddressOnAddressBar(String url) {
        addressBar.setText(url);
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE.toExternalForm());
    }

    /**
     * Loads a redirection page HTML file with a background that matches the general theme.
     */
    private void loadRedirectionPage() {
        loadPage(REDIRECTION_PAGE.toExternalForm());
    }

    /**
     * Loads google after sleeping for
     */
    private void redirectToGoogle() {
        try {
            Thread.sleep(500);
            gotoGoogle();
        } catch (InterruptedException e) {
            gotoHomepage();
        }
    }

    /**
     * Changes webview to site based on input entered in the address bar.
     */
    @FXML
    private void handleAddressBarInput() {
        //TODO: link address bar to web engine
        //if address legit then load
        //if not legit h
        String input = addressBar.getText();

        //logger.info("Checking validity of input URL: " + isValidUrl(input));
        String url = makeValidUrl(input);
        if (url == null) {
            logger.info(input + "\t is not a valid Url. Redirecting...");
            loadRedirectionPage();
        }
        loadPage(url);
        //dunnid clear input
    }

    /**
     * Checks if given url is a valid url.
     * @param url suspected url
     * @return true if url is valid; else false.
     */
    private boolean isValidUrl(String url) {
        //TODO: check Url.isValidUrl is appropriate for this (parse and check if the url is a valid url)
        //check if have protocol in front
        //if true then test out by creating a url and catching malinformedurlexception?
        return Url.isValidUrl(url);
    }

    /**
     * Parses input into a valid url.
     * If the input is simply lacking a protocol, http:// is prepended.
     * Otherwise, the input is passed into google search.
     * @param input non-url input
     * @return a valid url based on given input.
     */
    private String makeValidUrl(String input) {
        //TODO: parse a non url into a valid url (either by adding protocol or doing google search)
        //if is url without protocol, add protocol http://
        if (isValidUrl(input)) {
            return input;
        }
        //whack with http://
        String newurl = "http://" + input;
        if (isValidUrl(newurl)) {
            return newurl;
        }

        //else ask users to google search it; give alert and redirect to google
        return null; //dummy code
    }

    /**
     * Changes webview to google search page.
     */
    @FXML
    private void gotoHomepage() {
        loadPage("https://google.com.sg");
    }

    private void gotoGoogle() {
        loadPage("https://google.com.sg");
    }
}
