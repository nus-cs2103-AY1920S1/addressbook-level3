package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.junit.jupiter.api.Assertions.fail;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_BOOK;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import javafx.util.Pair;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.UserSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.loan.Loan;
import seedu.address.testutil.BookBuilder;


public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_bookAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingBookAdded modelStub = new ModelStubAcceptingBookAdded();
        Book validBook = new BookBuilder().build();

        CommandResult commandResult = new AddCommand(validBook).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validBook), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validBook), modelStub.booksAdded);
    }

    @Test
    public void execute_duplicateBook_throwsCommandException() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);
        ModelStub modelStub = new ModelStubWithBook(validBook);

        assertThrows(CommandException.class, MESSAGE_DUPLICATE_BOOK, () -> addCommand.execute(modelStub));
    }

    @Test
    public void getUndoCommand_beforeExecute_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);

        assertThrows(NullPointerException.class, addCommand::getUndoCommand);
    }

    @Test
    public void getUndoCommand_afterExecuteInvalidCommand_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);
        Model model = new ModelManager();
        model.addBook(validBook);

        try {
            addCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, addCommand::getUndoCommand);
    }

    @Test
    public void getUndoCommand_afterExecuteValidCommand_returnsUndoCommand() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);
        Model model = new ModelManager();

        try {
            addCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(addCommand.getUndoCommand(), new DeleteBySerialNumberCommand(validBook.getSerialNumber()));
    }

    @Test
    public void getRedoCommand_beforeExecute_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);

        assertThrows(NullPointerException.class, addCommand::getRedoCommand);
    }

    @Test
    public void getRedoCommand_afterExecuteInvalidCommand_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);
        Model model = new ModelManager();
        model.addBook(validBook);

        try {
            addCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, addCommand::getRedoCommand);
    }

    @Test
    public void getRedoCommand_afterExecuteValidCommand_returnsRedoCommand() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);
        Model model = new ModelManager();

        try {
            addCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(addCommand.getRedoCommand(), addCommand);
    }

    @Test
    public void getCommandResult_beforeExecute_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);

        assertThrows(NullPointerException.class, addCommand::getCommandResult);
    }

    @Test
    public void getCommandResult_afterExecuteInvalidCommand_throwsNullPointerException() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);
        Model model = new ModelManager();
        model.addBook(validBook);

        try {
            addCommand.execute(model);
        } catch (CommandException e) {
            System.out.println(e);
        }

        assertThrows(NullPointerException.class, addCommand::getCommandResult);
    }

    @Test
    public void getCommandResult_afterExecuteValidCommand_returnsCommandResult() {
        Book validBook = new BookBuilder().build();
        AddCommand addCommand = new AddCommand(validBook);
        Model model = new ModelManager();
        CommandResult expectedCommandResult = new CommandResult("Test");

        try {
            expectedCommandResult = addCommand.execute(model);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(addCommand.getCommandResult(), expectedCommandResult);
    }

    @Test
    public void equals() {
        Book a = new BookBuilder().withTitle("A").withSerialNumber("B00001").build();
        Book b = new BookBuilder().withTitle("B").withSerialNumber("B00002").build();
        AddCommand addACommand = new AddCommand(a);
        AddCommand addBCommand = new AddCommand(b);

        // same object -> returns true
        assertTrue(addACommand.equals(addACommand));

        // same values -> returns true
        AddCommand addACommandCopy = new AddCommand(a);
        assertTrue(addACommand.equals(addACommandCopy));

        // different types -> returns false
        assertFalse(addACommand.equals(1));

        // null -> returns false
        assertFalse(addACommand.equals(null));

        // different person -> returns false
        assertFalse(addACommand.equals(addBCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void toggleGuiSettingsTheme() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UserSettings getUserSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setUserSettings(UserSettings userSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getCatalogFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCatalogFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Book getBook(SerialNumber bookSn) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCatalog(ReadOnlyCatalog newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCatalog getCatalog() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBook(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBook(SerialNumber bookSn) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteBook(Book target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBook(Book target, Book editedBook) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Book> getOverdueBooks() {
            throw new AssertionError("This method should not be called");
        }

        @Override
        public ObservableList<Book> getFilteredBookList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetFilteredBookList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredBookList(Predicate<Book> predicate) {
            throw new AssertionError("This method should not be called.");
        }



        @Override
        public Path getLoanRecordsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setLoanRecordsFilePath(Path loanRecordsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyLoanRecords getLoanRecords() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeLoan(Loan loan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateLoan(Loan existingLoan, Loan returnedLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public int payFines(int amountInCents) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getBorrowerRecordsFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public BorrowerRecords getBorrowerRecords() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getLoanHistoryOfBookAsString(Book book) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Model excludeBookBeingReplaced(Book toBeReplaced) {
            throw new AssertionError(" This method should not be called.");
        }

        @Override
        public Borrower getServingBorrower() {
            throw new AssertionError(" This method should not be called.");
        }

        @Override
        public boolean isServeMode() {
            throw new AssertionError(" This method should not be called.");
        }

        @Override
        public boolean hasBorrower(Borrower borrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void registerBorrower(Borrower borrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetGenerator() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setServingBorrower(BorrowerId borrowerId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setServingBorrower(Borrower borrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void servingBorrowerNewLoan(Loan newLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void servingBorrowerRemoveLoan(Loan removeLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void servingBorrowerReturnLoan(Loan loanToBeReturned, Loan returnedLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void servingBorrowerUnreturnLoan(Loan loanToBeReturned, Loan returnedLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void servingBorrowerRenewLoan(Loan loanToBeRenewed, Loan renewedLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void servingBorrowerUnrenewLoan(Loan loanToBeUnrenewed, Loan unrenewedLoan) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasBorrowerId(BorrowerId borrowerId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void exitsServeMode() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Book> getBorrowerBooks() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Borrower getBorrowerFromId(BorrowerId borrowerId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setBorrower(Borrower borrowerToEdit, Borrower editedBorrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasDuplicatedBorrower(Borrower editedBorrower) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void unregisterBorrower(Borrower toUnregister) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitCommand(ReversibleCommand command) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Pair<Command, ReversibleCommand> getUndoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Command getRedoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoCommand() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetCommandHistory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getLoadStatus() {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithBook extends ModelStub {
        private final Book book;

        ModelStubWithBook(Book book) {
            requireNonNull(book);
            this.book = book;
        }

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return this.book.equals(book);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingBookAdded extends ModelStub {
        final ArrayList<Book> booksAdded = new ArrayList<>();

        @Override
        public boolean hasBook(Book book) {
            requireNonNull(book);
            return booksAdded.stream().anyMatch(book::equals);
        }

        @Override
        public void addBook(Book book) {
            requireNonNull(book);
            booksAdded.add(book);
        }

        @Override
        public ReadOnlyCatalog getCatalog() {
            return new Catalog();
        }
    }

}
