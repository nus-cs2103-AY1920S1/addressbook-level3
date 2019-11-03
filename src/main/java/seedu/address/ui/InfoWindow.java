package seedu.address.ui;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.book.Book;

/**
 * Controller for a help page
 */
public class InfoWindow extends UiPart<Stage> implements Initializable {

    private static final Logger logger = LogsCenter.getLogger(InfoWindow.class);
    private static final String FXML = "InfoWindow.fxml";

    public Book book;

    @FXML
    private Pane infoPanel;

    @FXML
    private Label loanHistory;

    // Book related information
    @FXML
    private HBox cardPane;
    @FXML
    private Label title;
    @FXML
    private Label id;
    @FXML
    private Label serialNumber;
    @FXML
    private Label author;
    @FXML
    private FlowPane genres;
    @FXML
    private Label loanStatus;
    @FXML
    private Label dueDate;
    @FXML
    private Label renewCount;

    /**
     * Creates a new HelpWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public InfoWindow(Stage root) {
        super(FXML, root);
        this.book = null;
    }

    /**
     * Creates a new InfoWindow.
     */
    public InfoWindow() {
        this(new Stage());
    }

    public void initialize(URL url, ResourceBundle rb) {

    }

    /**
     * Updates the info window with the book of choice.
     */
    public void updateData(Book book, String loanHistoryString) {
        updateBook(book);
        loanHistory.setText(loanHistoryString);
    }

    private void updateBook(Book book) {
        this.book = book;
        id.setText("");
        title.setText(book.getTitle().value);
        serialNumber.setText(book.getSerialNumber().value);
        author.setText(book.getAuthor().value);
        book.getGenres().stream()
                .sorted(Comparator.comparing(tag -> tag.genreName))
                .forEach(tag -> genres.getChildren().add(new Label(tag.genreName)));
        if (book.isCurrentlyLoanedOut()) {
            loanStatus.setText("On Loan");
            dueDate.setText("Due: " + DateUtil.formatDate(book.getLoan().get().getDueDate()));
            renewCount.setText("Renewed: " + book.getLoan().get().getRenewCount() + " times");
        }
    }

    /**
     * Shows the info window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing info page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the info window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the info window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the info window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

}
