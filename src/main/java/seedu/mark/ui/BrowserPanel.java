package seedu.mark.ui;

import static java.util.Objects.requireNonNull;

import java.net.URL;
import java.util.logging.Logger;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seedu.mark.MainApp;
import seedu.mark.commons.core.LogsCenter;

/**
 * The Browser Panel of Mark.
 */
public class BrowserPanel extends UiPart<Region> {

    public static final URL DEFAULT_PAGE =
            requireNonNull(MainApp.class
                    .getResource(FXML_FILE_FOLDER + "default.html"));

    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private WebView browser;
    @FXML
    private TextField addressBar;
    @FXML
    private ImageView googleLogo;
    @FXML
    private Button googleButton;

    private WebEngine webEngine;
    private String currentPageUrl;

    public BrowserPanel() {
        super(FXML);

        loadGuiAddress();
        loadGuiGoogleButton();
        loadGuiBrowser();

        loadPage("https://google.com.sg");
    }

    private void loadGuiAddress() {
        addressBar.focusedProperty().addListener((observable, oldValue, newValue) -> {
            Platform.runLater(() -> {
                if (newValue) {
                    logger.info("Address bar status: onfocus");
                    addressBar.selectAll();
                } else {
                    logger.info("Address bar status: outfocus");
                    if (!currentPageUrl.equals(addressBar.getText())) {
                        addressBar.setText(currentPageUrl);
                    }
                }
            });
        });
    }

    private void loadGuiGoogleButton() {
        googleLogo.setImage(new Image(
                MainWindow.class.getResourceAsStream("/images/gLogo.png")
        ));
    }

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
                                showAddressOnAddressBar(currentPageUrl);
                            }
                        }
                );
    }

    ////////////////////// MAIN METHODS /////////////////////////

    /**
     * Loads page with url on the webview.
     * @param url valid url
     */
    public void loadPage(String url) {
        assert checkUrlValid(url) : "invalid url passed to webEngine";

        Platform.runLater(() -> webEngine.load(url));
    }

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
     * Changes webview to site based on input entered in the address bar.
     */
    @FXML
    private void handleAddressBarInput() {
        //if address legit then load
        //if not legit h
        String input = addressBar.getText();
        logger.info("address bar read: " + input);

        logger.info("Is given input a valid URL? " + checkUrlValid(input));
        //dunnid clear input
    }

    /**
     * Checks if given url is a valid url.
     * @param url suspected url
     * @return true if url is valid; else false.
     */
    private boolean checkUrlValid(String url) {
        //check if have protocol in front
        //if true then test out by creating a url and catching malinformedurlexception?
        return true;
    }

    /**
     * Parses input into a valid url.
     * If the input is simply lacking a protocol, http:// is prepended.
     * Otherwise, the input is passed into google search.
     * @param input non-url input
     * @return a valid url based on given input.
     */
    private String parseNonUrl(String input) {
        //if is url without protocol, add protocol http://
        //else google search it... how?
        return "";
    }

    /**
     * Changes webview to google search page.
     */
    @FXML
    private void handleGotoGoogle() {
        loadPage("https://google.com.sg");
    }
}
