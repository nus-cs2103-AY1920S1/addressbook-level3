package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.book.Book;

/**
 * An UI component that displays information of a {@code Book}.
 */
public class BookCard extends UiPart<Region> {

    private static final String FXML = "BookListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Book book;

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
    private VBox loanStatusBox;
    @FXML
    private Label loanStatus;
    @FXML
    private Label dueDate;
    @FXML
    private Label renewCount;

    public BookCard(Book book, int displayedIndex, boolean isDarkTheme) {
        super(FXML);
        this.book = book;
        id.setText(displayedIndex + ". ");
        title.setText(book.getTitle().value);
        serialNumber.setText(book.getSerialNumber().value);
        author.setText(book.getAuthor().value);

        book.getGenres().stream()
                .sorted(Comparator.comparing(tag -> tag.genreName))
                .forEach(tag -> genres.getChildren().add(new Label(tag.genreName)));

        if (book.isCurrentlyLoanedOut()) {
            if (isDarkTheme) {
                setLoanStatusBoxTheme(GuiSettings.COLOR_DARK_THEME_LOAN_LABEL);
            } else {
                setLoanStatusBoxTheme(GuiSettings.COLOR_LIBERRY_THEME_LOAN_LABEL);
            }
            loanStatus.setText("On Loan");
            dueDate.setText("Due: " + DateUtil.formatDate(book.getLoan().get().getDueDate()));
            renewCount.setText("Renewed: " + book.getLoan().get().getRenewCount() + " times");
        }
        if (book.isOverdue()) {
            if (isDarkTheme) {
                setLoanStatusBoxTheme(GuiSettings.COLOR_DARK_THEME_ALERT_1);
            } else {
                setLoanStatusBoxTheme(GuiSettings.COLOR_LIBERRY_THEME_ALERT_1);
            }
        }
    }

    public void setLoanStatusBoxTheme(String theme) {
        loanStatusBox.setStyle("-fx-background-color: " + theme + ";");
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof BookCard)) {
            return false;
        }

        // state check
        BookCard card = (BookCard) other;
        return id.getText().equals(card.id.getText())
                && book.equals(card.book);
    }
}
