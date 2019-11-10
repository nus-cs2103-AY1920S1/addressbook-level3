package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.util.Pair;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.UserSettings;
import seedu.address.commons.util.DateUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ReversibleCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.SerialNumberGenerator;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.BorrowerIdGenerator;
import seedu.address.model.exceptions.NotInServeModeException;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanIdGenerator;
import seedu.address.model.loan.LoanList;

/**
 * Represents the in-memory model of the Library data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;
    private final LoanRecords loanRecords;
    private final Catalog catalog;
    private final BorrowerRecords borrowerRecords;
    private final FilteredList<Book> filteredBooks;
    private final CommandHistory commandHistory;

    private Optional<Borrower> servingBorrower;

    /**
     * Initializes a ModelManager with the given catalog, loan records, borrower records and userPrefs.
     */
    public ModelManager(ReadOnlyCatalog catalog,
                        ReadOnlyLoanRecords loanRecords,
                        ReadOnlyBorrowerRecords borrowerRecords,
                        ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(userPrefs, loanRecords, catalog, borrowerRecords);

        logger.fine("Initializing with catalog: " + catalog + " and user prefs " + userPrefs);

        this.userPrefs = new UserPrefs(userPrefs);
        // testing loan records
        this.loanRecords = new LoanRecords(loanRecords);
        LoanIdGenerator.setLoanRecords(this.loanRecords);
        // testing
        this.catalog = new Catalog(catalog);
        SerialNumberGenerator.setCatalog((Catalog) catalog);
        // testing
        this.borrowerRecords = new BorrowerRecords(borrowerRecords);
        filteredBooks = new FilteredList<>(this.catalog.getBookList());

        this.commandHistory = new CommandHistory();

        this.servingBorrower = Optional.empty();
    }

    public ModelManager() {
        this(new Catalog(), new LoanRecords(), new BorrowerRecords(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public UserSettings getUserSettings() {
        return userPrefs.getUserSettings();
    }

    @Override
    public void setUserSettings(UserSettings userSettings) {
        requireNonNull(userSettings);
        userPrefs.setUserSettings(userSettings);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public void toggleGuiSettingsTheme() {
        userPrefs.getGuiSettings().toggleTheme();
    }

    public Path getLoanRecordsFilePath() {
        return userPrefs.getLoanRecordsFilePath();
    }

    @Override
    public void setLoanRecordsFilePath(Path loanRecordsFilePath) {
        requireNonNull(loanRecordsFilePath);
        userPrefs.setLoanRecordsFilePath(loanRecordsFilePath);
    }

    @Override
    public Path getCatalogFilePath() {
        return userPrefs.getCatalogFilePath();
    }

    @Override
    public void setCatalogFilePath(Path catalogFilePath) {
        requireNonNull(catalogFilePath);
        userPrefs.setCatalogFilePath(catalogFilePath);
    }

    @Override
    public Path getBorrowerRecordsFilePath() {
        return userPrefs.getBorrowerRecordsFilePath();
    }

    @Override
    public void setBorrowerRecordsFilePath(Path borrowerRecordsFilePath) {
        requireNonNull(borrowerRecordsFilePath);
        userPrefs.setBorrowerRecordsFilePath(borrowerRecordsFilePath);
    }

    //=========== Loan Records ===============================================================================

    public ReadOnlyLoanRecords getLoanRecords() {
        return loanRecords;
    }

    /**
     * Adds a <code>Loan</code> object to the loan records.
     *
     * @param loan <code>Loan</code> object to be added.
     */
    public void addLoan(Loan loan) {
        requireNonNull(loan);
        loanRecords.addLoan(loan);
    }

    /**
     * Removes a <code>Loan</code> object from the loan records.
     *
     * @param loan <code>Loan</code> object to be removed.
     */
    public void removeLoan(Loan loan) {
        requireNonNull(loan);
        loanRecords.removeLoan(loan);
    }

    /**
     * Replaces an existing {@code Loan} object in LoanRecords with an edited one.
     *
     * @param existingLoan Existing {@code Loan} object to be replaced.
     * @param updatedLoan Updated {@code Loan} object.
     */
    @Override
    public void updateLoan(Loan existingLoan, Loan updatedLoan) {
        requireAllNonNull(existingLoan, updatedLoan);
        loanRecords.updateLoan(existingLoan, updatedLoan);
    }

    /**
     * Pays the outstanding fines of the servingBorrower.
     *
     * @param amountInCents Amount borrower is paying in cents.
     * @return Leftover amount in cents, i.e., change to be given.
     */
    @Override
    public int payFines(int amountInCents) {
        Borrower serving = getServingBorrower();
        LoanList updatedReturnedLoanList = new LoanList();

        int change = payLoansFine(serving.getReturnedLoanList(), updatedReturnedLoanList, amountInCents);

        Borrower updatedBorrower = new Borrower(serving.getName(), serving.getPhone(), serving.getEmail(),
                serving.getBorrowerId(), serving.getCurrentLoanList(), updatedReturnedLoanList);
        borrowerRecords.setBorrower(serving, updatedBorrower);

        setServingBorrower(updatedBorrower);

        return change;
    }

    /**
     * Private helper method to help iterate through each Loan in the returnedLoanList and then to update each Loan
     * object with the maximum possible amount paid.
     * Returns the leftover amount, i.e., the change.
     */
    private int payLoansFine(LoanList origReturnedLoanList, LoanList updatedReturnedLoanList, int amountInCents) {
        int payingAmount = amountInCents;
        for (Loan loan : origReturnedLoanList) {
            int remainingFineAmount = loan.getRemainingFineAmount();
            if (payingAmount == 0 || remainingFineAmount == 0) { // no payingAmount left or no remainingFine to pay
                updatedReturnedLoanList.add(loan); // just add to new copy
            } else {
                Loan updatedLoan;
                if (payingAmount >= remainingFineAmount) { // can fully pay off this fine
                    updatedLoan = loan.payFine(remainingFineAmount);
                    payingAmount -= remainingFineAmount;
                } else {
                    updatedLoan = loan.payFine(payingAmount);
                    payingAmount = 0;
                }
                updatedReturnedLoanList.add(updatedLoan);
                updateLoan(loan, updatedLoan);
            }
        }

        return payingAmount;
    }

    //=========== Catalog ===============================================================================


    @Override
    public void setCatalog(ReadOnlyCatalog catalog) {
        this.catalog.resetData(catalog);
    }

    @Override
    public ReadOnlyCatalog getCatalog() {
        return catalog;
    }

    @Override
    public boolean hasBook(Book book) {
        requireNonNull(book);
        return catalog.hasBook(book);
    }

    @Override
    public boolean hasBook(SerialNumber bookSn) {
        requireNonNull(bookSn);
        return catalog.serialNumberExists(bookSn);
    }

    @Override
    public void deleteBook(Book target) {
        requireNonNull(target);
        catalog.removeBook(target);
        SerialNumberGenerator.setCatalog(catalog);
    }

    @Override
    public void addBook(Book book) {
        requireNonNull(book);
        catalog.addBook(book);
        SerialNumberGenerator.setCatalog(catalog);
        updateFilteredBookList(PREDICATE_SHOW_ALL_BOOKS);
    }

    @Override
    public Book getBook(SerialNumber bookSn) {
        assert hasBook(bookSn) : "Book does not exist in catalog";
        return catalog.getBook(bookSn);
    }

    @Override
    public void setBook(Book target, Book editedBook) {
        requireAllNonNull(target, editedBook);
        catalog.setBook(target, editedBook);
        SerialNumberGenerator.setCatalog(catalog);
    }

    /**
     * Returns a list of overdue books in the catalog.
     *
     * @return an <code>ObservableList</code> of overdue books.
     */
    @Override
    public ObservableList<Book> getOverdueBooks() {
        return catalog.getOverdueBooks();
    }

    @Override
    public String getLoanHistoryOfBookAsString(Book target) {
        ArrayList<Loan> loanStream = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("Loan History:\n");
        if (target.getLoanHistory().isEmpty()) {
            sb.append("No loan history!");
        } else {
            target.getLoanHistory().forEach(loan -> loanStream.add(loan));
            Collections.sort(loanStream);
            Collections.reverse(loanStream); // To make latest loan go on top
            loanStream.stream()
                    .map(loan -> singleLoanHistoryString(
                            loan, target.isCurrentlyLoanedOut() && target.getLoan().get().equals(loan)))
                    .forEach(history -> sb.append(history + "\n"));
        }
        return sb.toString();
    }

    /**
     * Helper method to generate a loan history as a string.
     *
     * @param loan loan history to be generated.
     * @param isCurrent if this loan is currently the loan associated with the book at the moment.
     * @return String representation of loan history.
     */
    private String singleLoanHistoryString(Loan loan, boolean isCurrent) {
        String startString = DateUtil.formatDate(loan.getStartDate());
        String endString = isCurrent
                ? DateUtil.formatDate(loan.getDueDate())
                : DateUtil.formatDate(loan.getReturnDate());
        String nameString = getBorrowerFromId(loan.getBorrowerId()).getName().toString();
        String borrowerIdString = "[" + loan.getBorrowerId().toString() + "]";
        if (isCurrent) {
            return startString + " - " + endString + " by " + borrowerIdString + " " + nameString + " (Current loan)";
        } else {
            return startString + " - " + endString + " by " + borrowerIdString + " " + nameString;
        }
    }

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Book> getFilteredBookList() {
        return filteredBooks;
    }

    @Override
    public void updateFilteredBookList(Predicate<Book> predicate) {
        requireNonNull(predicate);
        filteredBooks.setPredicate(predicate);
    }

    public void resetFilteredBookList() {
        filteredBooks.setPredicate(x -> true);
    }

    @Override
    public Model excludeBookBeingReplaced(Book toBeReplaced) {
        Catalog tempCatalog = new Catalog(this.getCatalog());
        tempCatalog.removeBook(toBeReplaced);
        return new ModelManager(tempCatalog, this.getLoanRecords(), this.getBorrowerRecords(), this.getUserPrefs());
    }

    //=========== BorrowerRecords ===============================================================================

    @Override
    public ReadOnlyBorrowerRecords getBorrowerRecords() {
        return borrowerRecords;
    }


    @Override
    public Borrower getServingBorrower() {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }
        return servingBorrower.get();
    }

    @Override
    public boolean isServeMode() {
        return servingBorrower.isPresent();
    }

    @Override
    public boolean hasBorrower(Borrower borrower) {
        return borrowerRecords.hasBorrower(borrower);
    }

    @Override
    public void registerBorrower(Borrower borrower) {
        borrowerRecords.addBorrower(borrower);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return userPrefs.equals(other.userPrefs)
                && loanRecords.equals(other.loanRecords)
                && catalog.equals(other.catalog)
                && borrowerRecords.equals(other.borrowerRecords);
    }

    @Override
    public void resetGenerator() {
        BorrowerIdGenerator.setBorrowers(borrowerRecords);
    }

    @Override
    public void setServingBorrower(BorrowerId borrowerId) {
        Borrower borrower = borrowerRecords.getBorrowerFromId(borrowerId);
        setServingBorrower(borrower);
    }

    @Override
    public void setServingBorrower(Borrower borrower) {
        this.servingBorrower = Optional.of(borrower);
    }

    /**
     * Adds a new {@code Loan} object to a new copy of servingBorrower and its currentLoanList.
     * This method is called only when in Serve mode.
     *
     * @param newLoan New {@code Loan} object to be added.
     */
    @Override
    public void servingBorrowerNewLoan(Loan newLoan) {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }

        Borrower serving = servingBorrower.get();
        Borrower loanAddedBorrower = new Borrower(serving.getName(), serving.getPhone(), serving.getEmail(),
                serving.getBorrowerId(), serving.getAddedCurrentLoanList(newLoan), serving.getReturnedLoanList());
        borrowerRecords.setBorrower(serving, loanAddedBorrower);

        setServingBorrower(loanAddedBorrower);
    }

    /**
     * Removes a {@code Loan} object from a new copy of servingBorrower and its currentLoanList.
     * This method is called only when in Serve mode.
     *
     * @param removeLoan {@code Loan} object to be removed.
     */
    @Override
    public void servingBorrowerRemoveLoan(Loan removeLoan) {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }

        Borrower serving = servingBorrower.get();
        Borrower loanRemovedBorrower = new Borrower(serving.getName(), serving.getPhone(), serving.getEmail(),
                serving.getBorrowerId(), serving.getRemovedCurrentLoanList(removeLoan), serving.getReturnedLoanList());
        borrowerRecords.setBorrower(serving, loanRemovedBorrower);

        setServingBorrower(loanRemovedBorrower);
    }

    /**
     * Removes {@code loanToBeReturned} from {@code servingBorrower}'s currentLoanList and
     * adds {@code returnedLoan} to its returnedLoanList.
     * This method is called only when in Serve mode.
     * {@code servingBorrower} should have the {@code loanToBeReturned} object in its currentLoanList.
     *
     * @param loanToBeReturned {@code Loan} object in servingBorrower's currentLoanList.
     * @param returnedLoan Updated {@code Loan} object to be added to servingBorrower's returnedLoanList.
     */
    @Override
    public void servingBorrowerReturnLoan(Loan loanToBeReturned, Loan returnedLoan) {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }

        Borrower serving = servingBorrower.get();

        assert serving.hasCurrentLoan(loanToBeReturned) : "Borrower does not have the loan to be returned.";

        Borrower loanReturnedBorrower = new Borrower(serving.getName(), serving.getPhone(), serving.getEmail(),
                serving.getBorrowerId(), serving.getRemovedCurrentLoanList(loanToBeReturned),
                serving.getAddedReturnedLoanList(returnedLoan));
        borrowerRecords.setBorrower(serving, loanReturnedBorrower);

        setServingBorrower(loanReturnedBorrower);
    }

    /**
     * Adds {@code loanToBeUnreturned} to {@code servingBorrower}'s currentLoanList and
     * removes {@code unreturnedLoan} to its returnedLoanList.
     * This method is called only when in Serve mode.
     * {@code servingBorrower} should have the {@code loanToBeUnreturned} object in its currentLoanList.
     *
     * @param loanToBeUnreturned {@code Loan} object in servingBorrower's currentLoanList.
     * @param unreturnedLoan Updated {@code Loan} object to be removed to servingBorrower's returnedLoanList.
     */
    @Override
    public void servingBorrowerUnreturnLoan(Loan loanToBeUnreturned, Loan unreturnedLoan) {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }

        Borrower serving = servingBorrower.get();

        assert !serving.hasCurrentLoan(unreturnedLoan) : "Borrower has the loan to be unreturned.";

        Borrower loanUnreturnedBorrower = new Borrower(serving.getName(), serving.getPhone(), serving.getEmail(),
                serving.getBorrowerId(), serving.getAddedCurrentLoanList(unreturnedLoan),
                serving.getRemovedReturnedLoanList(loanToBeUnreturned));
        borrowerRecords.setBorrower(serving, loanUnreturnedBorrower);

        setServingBorrower(loanUnreturnedBorrower);
    }

    /**
     * Replaces the {@code loanToBeRenewed} in {@code servingBorrower}'s currentLoanList
     * with {@code renewedLoan}.
     * This method is called only when in Serve mode.
     * {@code servingBorrower} should have the {@code loanToBeReturned} object in its currentLoanList.
     *
     * @param loanToBeRenewed {@code Loan} object in servingBorrower's currentLoanList.
     * @param renewedLoan Updated {@code Loan} object with dueDate extended.
     */
    @Override
    public void servingBorrowerRenewLoan(Loan loanToBeRenewed, Loan renewedLoan) {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }

        Borrower serving = servingBorrower.get();

        assert serving.hasCurrentLoan(loanToBeRenewed) : "Borrower does not have the loan to be returned.";

        Borrower loanRenewedBorrower = new Borrower(serving.getName(), serving.getPhone(), serving.getEmail(),
                serving.getBorrowerId(), serving.getReplacedCurrentLoanList(loanToBeRenewed, renewedLoan),
                serving.getReturnedLoanList());
        borrowerRecords.setBorrower(serving, loanRenewedBorrower);

        setServingBorrower(loanRenewedBorrower);
    }

    /**
     * Replaces the {@code loanToBeUnrenewed} in {@code servingBorrower}'s currentLoanList
     * with {@code unrenewedLoan}.
     * This method is called only when in Serve mode.
     * {@code servingBorrower} should have the {@code loanToBeUnrenewed} object in its currentLoanList.
     *
     * @param loanToBeUnrenewed {@code Loan} object in servingBorrower's currentLoanList.
     * @param unrenewedLoan updated {@code Loan} object with dueDate returned to previous state.
     */
    @Override
    public void servingBorrowerUnrenewLoan(Loan loanToBeUnrenewed, Loan unrenewedLoan) {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }

        Borrower serving = servingBorrower.get();

        assert serving.hasCurrentLoan(loanToBeUnrenewed) : "Borrower does not have the loan to be returned.";

        Borrower loanRenewedBorrower = new Borrower(serving.getName(), serving.getPhone(), serving.getEmail(),
                serving.getBorrowerId(), serving.getReplacedCurrentLoanList(loanToBeUnrenewed, unrenewedLoan),
                serving.getReturnedLoanList());
        borrowerRecords.setBorrower(serving, loanRenewedBorrower);

        setServingBorrower(loanRenewedBorrower);
    }

    @Override
    public boolean hasBorrowerId(BorrowerId borrowerId) {
        return borrowerRecords.checkIfBorrowerIdExists(borrowerId);
    }

    @Override
    public void exitsServeMode() {
        this.servingBorrower = Optional.empty();
    }

    @Override
    public List<Book> getBorrowerBooks() {
        if (!isServeMode()) {
            throw new NotInServeModeException();
        }

        if (servingBorrower.get().getCurrentLoanList().isEmpty()) {
            return new ArrayList<>();
        }

        ArrayList<Loan> loans = new ArrayList<>();
        servingBorrower.get()
                .getCurrentLoanList()
                .forEach(loan -> loans.add(loan));
        return loans.stream()
                .map(loan -> loan.getBookSerialNumber())
                .map(sn -> catalog.getBook(sn))
                .collect(Collectors.toList());
    }

    @Override
    public Borrower getBorrowerFromId(BorrowerId borrowerId) {
        return borrowerRecords.getBorrowerFromId(borrowerId);
    }

    @Override
    public void setBorrower(Borrower borrowerToEdit, Borrower editedBorrower) {
        borrowerRecords.removeBorrower(borrowerToEdit);
        borrowerRecords.addBorrower(editedBorrower);
    }

    @Override
    public boolean hasDuplicatedBorrower(Borrower editedBorrower) {
        return borrowerRecords.hasDuplicateBorrower(editedBorrower);
    }

    @Override
    public void unregisterBorrower(Borrower toUnregister) {
        assert toUnregister.getCurrentLoanList().isEmpty() : "Books still on loan, cannot unregister";
        borrowerRecords.removeBorrower(toUnregister);
    }

    //=========== CommandHistory ===============================================================================

    @Override
    public boolean canUndoCommand() {
        return commandHistory.canUndo();
    }

    @Override
    public boolean canRedoCommand() {
        return commandHistory.canRedo();
    }

    @Override
    public void commitCommand(ReversibleCommand command) {
        commandHistory.commit(command);
    }

    @Override
    public Pair<CommandResult, CommandResult> undoCommand() throws CommandException {
        return commandHistory.undo(this);
    }

    @Override
    public CommandResult redoCommand() throws CommandException {
        return commandHistory.redo(this);
    }

    @Override
    public void resetCommandHistory() {
        commandHistory.reset();
    }

}
