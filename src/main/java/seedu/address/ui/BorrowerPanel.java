package seedu.address.ui;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.util.FineUtil;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;

/**
 * A Ui component containing the borrower's information.
 */
public class BorrowerPanel extends UiPart<Region> {
    private static final String FXML = "BorrowerPanel.fxml";

    private boolean isDarkTheme;

    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label email;
    @FXML
    private Label fines;

    @FXML
    private ListView<Book> bookListView;

    /** Constructor when not in Serve Mode */
    public BorrowerPanel(boolean isDarkTheme) {
        super(FXML);
        name.setText("");
        id.setText("");
        phone.setText("");
        email.setText("");
        fines.setText("");
        VBox.setVgrow(bookListView, Priority.ALWAYS);
        this.isDarkTheme = isDarkTheme;
    }

    /**
     * Sets a borrower when transiting into serve mode
     *
     * @param borrower Borrower being served/
     * @param observableBookList List of books the borrower loaned from the library.
     */
    public void setBorrower(Borrower borrower, ObservableList<Book> observableBookList, boolean isDarkTheme) {
        requireNonNull(borrower);
        this.isDarkTheme = isDarkTheme;
        name.setText("Borrower: " + borrower.getName().toString());
        id.setText("ID: " + borrower.getBorrowerId().toString());
        phone.setText("Phone: " + borrower.getPhone().toString());
        email.setText("Email: " + borrower.getEmail().toString());
        fines.setText("Fines: " + FineUtil.centsToDollarString(borrower.getOutstandingFineAmount()));
        bookListView.setItems(observableBookList);
        bookListView.setCellFactory(listView -> new BorrowerPanel.BookListViewCell());
    }

    /**
     * Resets the borrower panel when Done command is invoked.
     */
    public void reset() {
        name.setText("");
        id.setText("");
        phone.setText("");
        email.setText("");
        fines.setText("");
        ObservableList<Book> nullList = FXCollections.observableArrayList(new ArrayList<>());
        bookListView.setItems(nullList);
        bookListView.setCellFactory(listView -> new BorrowerPanel.BookListViewCell());
    }

    /**
     * Updates the list of books whenever the borrower loans/returns a book.
     *
     * @param observableBookList List of books the borrower loaned from the library.
     */
    public void updateBooks(ObservableList<Book> observableBookList) {
        bookListView.setItems(observableBookList);
        bookListView.setCellFactory(listView -> new BorrowerPanel.BookListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Book} using a {@code BookCard}.
     */
    class BookListViewCell extends ListCell<Book> {
        @Override
        protected void updateItem(Book book, boolean empty) {
            super.updateItem(book, empty);

            if (empty || book == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new BookCard(book, getIndex() + 1, isDarkTheme).getRoot());
            }
        }
    }
}
