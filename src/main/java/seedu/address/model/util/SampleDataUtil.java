package seedu.address.model.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import seedu.address.commons.core.UserSettings;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.BorrowerRecords;
import seedu.address.model.Catalog;
import seedu.address.model.LoanRecords;
import seedu.address.model.ReadOnlyBorrowerRecords;
import seedu.address.model.ReadOnlyCatalog;
import seedu.address.model.ReadOnlyLoanRecords;
import seedu.address.model.book.Author;
import seedu.address.model.book.Book;
import seedu.address.model.book.SerialNumber;
import seedu.address.model.book.SerialNumberGenerator;
import seedu.address.model.book.Title;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.borrower.BorrowerId;
import seedu.address.model.borrower.BorrowerIdGenerator;
import seedu.address.model.borrower.Email;
import seedu.address.model.borrower.Name;
import seedu.address.model.borrower.Phone;
import seedu.address.model.genre.Genre;
import seedu.address.model.loan.Loan;
import seedu.address.model.loan.LoanId;
import seedu.address.model.loan.LoanIdGenerator;
import seedu.address.model.loan.LoanList;

/**
 * Contains utility methods for populating {@code Liberry} with sample data.
 */
public class SampleDataUtil {

    private static final int DAYS_AGO_30 = 30;
    private static final int DAYS_AGO_28 = 28;
    private static final int DAYS_AGO_15 = 15;
    private static final int DAYS_AGO_14 = 14;
    private static final int DAYS_AGO_9 = 9;
    private static final int DAYS_AGO_1 = 1;

    public static Book[] getSampleBooks() {
        SerialNumberGenerator.setCatalog(new Catalog());
        return new Book[]{
            new Book(new Title("Harry Botter"), SerialNumberGenerator.generateSerialNumber(),
                new Author("J K Rowling"), null, getGenreSet("FICTION")),
            new Book(new Title("Legend of the Condor Heroes"), SerialNumberGenerator.generateSerialNumber(),
                new Author("Jin Yong"), null, getGenreSet("FICTION", "HISTORY")),
            new Book(new Title("Animal the Farm"), SerialNumberGenerator.generateSerialNumber(),
                new Author("George Orwell"), null, getGenreSet("FICTION")),
            new Book(new Title("Harry Botter and the Full Blood Prince"), SerialNumberGenerator.generateSerialNumber(),
                new Author("J K Rowling"), null, getGenreSet("FICTION", "ACTION")),
            new Book(new Title("Man's Search for Munning"), SerialNumberGenerator.generateSerialNumber(),
                new Author("Viktor Frankel"), null, getGenreSet("NON-FICTION", "BIOGRAPHY")),
            new Book(new Title("Stefe Jobz"), SerialNumberGenerator.generateSerialNumber(),
                new Author("Walter Izakson"), null, getGenreSet("NON-FICTION", "BIOGRAPHY")),
            new Book(new Title("Behaves"), SerialNumberGenerator.generateSerialNumber(),
                new Author("Robert Sapoloksy"), null, getGenreSet("NON-FICTION", "POPULAR-SCIENCE", "PSYCHOLOGY")),
            new Book(new Title("A Brief History of Space"), SerialNumberGenerator.generateSerialNumber(),
                new Author("Stephen Birding"), null, getGenreSet("NON-FICTION", "POPULAR-SCIENCE")),
            new Book(new Title("Painting with Bobby Ross"), SerialNumberGenerator.generateSerialNumber(),
                new Author("Bobby Ross"), null, getGenreSet("NON-FICTION", "ART", "HOW-TO")),
        };
    }

    public static Borrower[] getSampleBorrowers() {
        BorrowerIdGenerator.setBorrowers(new BorrowerRecords());
        return new Borrower[]{
            new Borrower(new Name("Xo Xo"), new Phone("60606060"), new Email("xoxo@youtube.com"),
                BorrowerIdGenerator.generateBorrowerId()),
            new Borrower(new Name("Luke X Y Z"), new Phone("111111890"), new Email("luke@alphabet.com"),
                BorrowerIdGenerator.generateBorrowerId()),
            new Borrower(new Name("Ching Chong Santa"), new Phone("25122109"), new Email("santa@prc.gov.cn"),
                BorrowerIdGenerator.generateBorrowerId()),
            new Borrower(new Name("Hiap Seng"), new Phone("67522122"), new Email("pokkagoingdown@yoz.com.sg"),
                BorrowerIdGenerator.generateBorrowerId()),
            new Borrower(new Name("Yabobani Shima"), new Phone("998765432"), new Email("shima@griiko.yogaruto"),
                BorrowerIdGenerator.generateBorrowerId()),
            new Borrower(new Name("Im not wanted"), new Phone("91119222"), new Email("banana@baba.com"),
                new BorrowerId("K0069")),
        };
    }

    /**
     * Generates a function that returns a default loan using default user settings
     *
     * @param serialNumber   serialNumber of Book in int format, within 1 and 9 inclusive
     * @param borrowerId     borrowerId of Borrower in int format, within 1 and 9 inclusive
     * @param daysSinceLoan  number of days since the loan was made (using today as reference)
     * @param returnedOnDay  day on which book is returned (with loan day = 0). Book not returned if equal -1.
     * @param hasBeenRenewed whether book has been renewed once
     */
    private static Function<LoanId, Loan> getDefaultLoan(int serialNumber,
                                                         int borrowerId,
                                                         int daysSinceLoan,
                                                         int returnedOnDay,
                                                         boolean hasBeenRenewed) {
        assert serialNumber < 10 && serialNumber > 0 : "serialNumber not within 1 and 9 inclusive";
        assert borrowerId < 10 && borrowerId > 0 : "borrowerId not within 1 and 9 inclusive";

        LocalDate dayBorrowed = DateUtil.getTodayMinusDays(daysSinceLoan);
        LocalDate dayReturned = returnedOnDay == -1
            ? null
            : DateUtil.extendDate(dayBorrowed, returnedOnDay);
        LocalDate dayDue = hasBeenRenewed
            ? DateUtil.extendDate(dayBorrowed,
            UserSettings.DEFAULT_LOAN_PERIOD + UserSettings.DEFAULT_RENEW_PERIOD)
            : DateUtil.extendDate(dayBorrowed, UserSettings.DEFAULT_LOAN_PERIOD);

        return (LoanId loanId) -> new Loan(loanId,
            new SerialNumber("B0000" + serialNumber), new BorrowerId("K000" + borrowerId),
            dayBorrowed,
            dayDue,
            dayReturned,
            hasBeenRenewed ? 1 : 0,
            dayReturned == null
                ? DateUtil.isDateBeforeToday(dayDue)
                ? DateUtil.getNumOfDaysBetween(dayDue, DateUtil.getTodayDate())
                * UserSettings.DEFAULT_FINE_INCREMENT
                : 0
                : DateUtil.getNumOfDaysOverdue(dayDue, dayReturned) * UserSettings.DEFAULT_FINE_INCREMENT,
            0);
    }

    public static List<Function<LoanId, Loan>> getSampleLoans() {
        return new ArrayList<>(List.of(
            getDefaultLoan(1, 1, DAYS_AGO_30, 1, false),
            getDefaultLoan(2, 2, DAYS_AGO_30, 20, true),
            getDefaultLoan(3, 3, DAYS_AGO_30, 20, false),
            getDefaultLoan(4, 4, DAYS_AGO_30, -1, true),
            getDefaultLoan(5, 5, DAYS_AGO_30, -1, false),
            getDefaultLoan(1, 3, DAYS_AGO_28, 28, true),
            getDefaultLoan(6, 4, DAYS_AGO_28, 25, false),
            getDefaultLoan(7, 2, DAYS_AGO_15, -1, false),
            getDefaultLoan(8, 1, DAYS_AGO_14, -1, false),
            getDefaultLoan(9, 1, DAYS_AGO_14, -1, true),
            getDefaultLoan(2, 2, DAYS_AGO_9, -1, true),
            getDefaultLoan(3, 1, DAYS_AGO_9, 8, false),
            getDefaultLoan(6, 3, DAYS_AGO_1, 8, false)
        ));
    }

    /**
     * Returns a sample {@code Catalog} populated with books corresponding to the given loanRecords
     *
     * @param loanRecords loan records to populate loan history of books in sample catalog
     */
    public static ReadOnlyCatalog getSampleCatalog(ReadOnlyLoanRecords loanRecords) {
        Catalog catalog = new Catalog();
        Arrays.stream(getSampleBooks()).map(
            book -> {
                // Populate book with the relevant loan records
                LoanList loanList = new LoanList();
                Book updatedBook = new Book(
                    book.getTitle(),
                    book.getSerialNumber(),
                    book.getAuthor(),
                    null,
                    book.getGenres()
                );
                List<Loan> listOfLoans = loanRecords.getLoanCollection().stream().filter(
                    loan -> loan.getBookSerialNumber().equals(book.getSerialNumber()))
                    .collect(Collectors.toList());
                // add current Loan
                for (Loan loan : listOfLoans) {
                    if (loan.getReturnDate() == null) {
                        updatedBook = book.loanOut(loan);
                    }
                }
                listOfLoans.forEach(loanList::add);
                return updatedBook.addToLoanHistory(loanList);
            }
        ).forEach(catalog::addBook);
        return catalog;
    }

    /**
     * Returns a genre set containing the list of strings given,
     * after formatting the strings to UPPERCASE
     */
    public static Set<Genre> getGenreSet(String... strings) {
        return Arrays.stream(strings)
            .map(genreName -> genreName.trim().toUpperCase())
            .map(Genre::new)
            .collect(Collectors.toSet());
    }

    public static ReadOnlyLoanRecords getSampleLoanRecords() {
        LoanRecords loanRecords = new LoanRecords();
        LoanIdGenerator.setLoanRecords(loanRecords);
        getSampleLoans().forEach(loanMaker -> loanRecords.addLoan(loanMaker.apply(LoanIdGenerator.generateLoanId())));
        return loanRecords;
    }

    /**
     * Returns a sample {@code BorrowerRecords} populated with books corresponding to the given loanRecords
     *
     * @param loanRecords loan records to populate loan history of borrowers in sample borrower records
     */
    public static ReadOnlyBorrowerRecords getSampleBorrowerRecords(ReadOnlyLoanRecords loanRecords) {
        BorrowerRecords borrowerRecords = new BorrowerRecords();
        Arrays.stream(getSampleBorrowers())
            .map(borrower -> {
                    // Populate the borrower with the relevant loan records
                    LoanList currentLoanList = new LoanList();
                    LoanList returnedLoanList = new LoanList();
                    loanRecords.getLoanCollection().stream().filter(
                        loan -> loan.getBorrowerId().equals(borrower.getBorrowerId()))
                        .forEach(loan -> {
                            if (loan.getReturnDate() == null) {
                                currentLoanList.add(loan);
                            } else {
                                returnedLoanList.add(loan);
                            }
                        });
                return new Borrower(
                    borrower.getName(),
                    borrower.getPhone(),
                    borrower.getEmail(),
                    borrower.getBorrowerId(),
                    currentLoanList,
                    returnedLoanList);
                }
            ).forEach(borrowerRecords::addBorrower);
        return borrowerRecords;
    }
}
