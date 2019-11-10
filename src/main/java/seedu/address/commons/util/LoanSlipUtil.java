package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.logging.Logger;
import java.util.stream.IntStream;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.LoanSlipException;
import seedu.address.logic.LogicManager;
import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

/**
 * Loan slip util class to generate a Loan Slip to pdf using iText7.
 */
public class LoanSlipUtil {
    private static final String PDF_EXTENSION = ".pdf";
    private static final String BYE_MESSAGE = "We hope to see you again!";
    private static final String DEST = "./data/loan_slips/";

    private static final float FIRST_ROW_WIDTH = 75F;
    private static final float SECOND_ROW_WIDTH = 325F;
    private static final float THIRD_ROW_WIDTH = 125F;

    private static final int FIRST_INDEX = 0;

    private static HashSet<Loan> loansInCurrentSession = new HashSet<>();
    private static ArrayList<Loan> currentLoans = new ArrayList<>();
    private static ArrayList<Book> currentBooks = new ArrayList<>();
    private static Borrower currentBorrower;

    private static File currentFile;

    private static boolean isMounted = false;
    private static boolean isGenerated = false;

    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);

    /**
     * Mounts a Loan slip in preparation for creating a pdf version of it.
     *
     * @param loan Loan to be represented in the loan slip.
     * @param book Book associated to current loan.
     * @param borrower Borrower associated to current loan.
     */
    public static void mountLoan(Loan loan, Book book, Borrower borrower) throws LoanSlipException {
        if (!loan.getBorrowerId().equals(borrower.getBorrowerId())) {
            throw new LoanSlipException("Borrower and Loan do not match!");
        }
        if (!loan.getBookSerialNumber().equals(book.getSerialNumber())) {
            throw new LoanSlipException("Book and Loan do not match!");
        }
        currentLoans.add(loan);
        currentBooks.add(book);
        loansInCurrentSession.add(loan);
        if (currentBorrower != null) {
            assert currentBorrower.equals(borrower) : "Wrong borrower";
        } else {
            currentBorrower = borrower;
        }
        isMounted = true;
        isGenerated = false;
    }

    /**
     * Unmounts a Loan slip after creating a pdf of it.
     */
    private static void unmountLoans() {
        if (isMounted) {
            currentLoans = new ArrayList<>();
            currentBooks = new ArrayList<>();
            currentBorrower = null;
            currentFile = null;
            isMounted = false;
            isGenerated = false;
        }
    }

    /**
     * Clears the current Serve Mode session.
     */
    public static void clearSession() {
        if (isMounted) {
            unmountLoans();
        }
        if (loansInCurrentSession != null) {
            loansInCurrentSession.clear();
        }
    }

    /**
     * Returns true if loan is in current session.
     *
     * @param loan loan to check against
     * @return true if loan is in current session.
     */
    public static boolean loanIsInSession(Loan loan) {
        return loansInCurrentSession.contains(loan);
    }

    /**
     * To be used only with unloan commands.
     *
     * @param loan loan to be removed
     */
    public static void removeLoanFromSession(Loan loan) {
        assert loanIsInSession(loan);
        loansInCurrentSession.remove(loan);
    }

    /**
     * Returns true if book is in current session.
     * @param book book to check against
     * @return true if book is in current session.
     */
    public static boolean bookIsInSession(Book book) {
        return loansInCurrentSession.stream()
                .filter(loan -> loan.getBookSerialNumber().equals(book.getSerialNumber()))
                .count() == 1;
    }

    /**
     * Unmounts a specific loan from the loan slip
     *
     * @param loan loan to be unmounted.
     * @param book Book associated with this loan.
     */
    public static void unmountSpecificLoan(Loan loan, Book book) {
        if (isMounted && currentLoans.contains(loan) && currentBooks.contains(book)) {
            currentLoans.remove(loan);
            currentBooks.remove(book);
        }
        if (isMounted && currentBooks.isEmpty() && currentLoans.isEmpty()) {
            unmountLoans();
        }
    }

    /**
     * Creates a pdf version of the loan slip that is currently mounted.
     *
     * @throws LoanSlipException if there is an error reading/writing to the pdf file.
     */
    public static void createLoanSlipInDirectory() throws LoanSlipException {
        assert isMounted : "No loan slip mounted";
        try {
            requireNonNull(currentLoans);
            requireNonNull(currentBooks);
            requireNonNull(currentBorrower);
            Document document = createDocument(createFileNameFromLoan());
            float[] pointColumnWidths = {FIRST_ROW_WIDTH, SECOND_ROW_WIDTH, THIRD_ROW_WIDTH};
            Table table = new Table(pointColumnWidths);
            LoanSlipDocument doc = new LoanSlipDocument(document, table);
            generateLiberryLoanSlip(doc);
        } catch (IOException e) {
            throw new LoanSlipException(e.getMessage());
        }
    }

    /**
     * Helper method to assist in generating a file name based on the first loan of the entire loan slip.
     *
     * @return a String representation of the file name generated.
     */
    private static String createFileNameFromLoan() {
        assert isMounted : "No loans mounted";
        assert !currentLoans.isEmpty() : "No loans in list";
        Loan firstLoan = currentLoans.get(FIRST_INDEX);
        return firstLoan.getLoanId().toString();
    }

    /**
     * Creates a {@code File} object to be populated with information.
     *
     * @param docName name of the new file object.
     * @return a {@code Document} object representing the file.
     * @throws IOException if there are errors in creating the new file.
     */
    private static Document createDocument(String docName) throws IOException {
        assert isMounted : "No loan slip mounted";
        String finalDest = DEST + docName + PDF_EXTENSION;
        File file = new File(finalDest);
        file.getParentFile().mkdirs();
        PdfWriter writer = new PdfWriter(finalDest);
        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);
        // Initialize document
        currentFile = file;
        return new Document(pdf);
    }

    /**
     * Populates the pdf file with information, creating the loan slip.
     *
     * @param doc {@code LoanSlipDocument} object to be populated with data.
     */
    private static void generateLiberryLoanSlip(LoanSlipDocument doc) {
        assert isMounted : "No loan slip mounted";
        writeLogoToDoc(doc);
        writeHeaderToDoc(doc);
        populateTableInDoc(doc);
        addTableToDocument(doc);
        doc.closeDoc();
        isGenerated = true;
    }

    /**
     * Helper method to write the logo section of the document.
     *
     * @param doc {@code LoanSlipDocument} to be written to.
     */
    private static void writeLogoToDoc(LoanSlipDocument doc) {
        doc.writeLogo();
        doc.writeLine();
    }

    /**
     * Helper method to write the header section of the document.
     *
     * @param doc {@code LoanSlipDocument} to be written to.
     */
    private static void writeHeaderToDoc(LoanSlipDocument doc) {
        doc.writeHeader(currentBorrower.getName().toString());
        doc.writeLeftParagraph(currentBorrower.getBorrowerId().toString());
        doc.writeLeftParagraph(DateUtil.getTodayFormattedDate());
        doc.writeLine();
        doc.writeMidHeader("Books borrowed");
    }

    /**
     * Helper method to populate the table of the document.
     *
     * @param doc {@code LoanSlipDocument} to be written to.
     */
    private static void populateTableInDoc(LoanSlipDocument doc) {
        String[] headerRow = new String[]{"S/N", "Book", "Due By"};
        doc.writeRow(headerRow);
        int numberOfBooks = currentBooks.size();
        int numberOfLoans = currentLoans.size();
        assert numberOfBooks == numberOfLoans : "Number of books and loans are not consistent";
        populateTableWithAllBooks(numberOfBooks, doc);
    }

    /**
     * Helper method to populate the table with all books being loaned out.
     *
     * @param noOfBooks number of books loaned out.
     * @param doc {@code LoanSlipDocument} to be written to.
     */
    private static void populateTableWithAllBooks(int noOfBooks, LoanSlipDocument doc) {
        IntStream.range(0, noOfBooks)
                .forEach(index -> doc.writeRow(createBookRow(index)));
    }

    /**
     * Helper method to add the table to the document.
     *
     * @param doc {@code LoanSlipDocument} to be written to.
     */
    private static void addTableToDocument(LoanSlipDocument doc) {
        doc.submitTable();
        doc.writeLine();
        doc.writeCentralisedParagraph(BYE_MESSAGE);
    }

    /**
     * Creates an array using the details from the mounted loan slip.
     *
     * @return an array of string representing a row of the table.
     */
    private static String[] createBookRow(int index) {
        assert isMounted : "No loan slip mounted";
        String[] currentBookDetails = new String[3];
        Book currentBook = currentBooks.get(index);
        Loan currentLoan = currentLoans.get(index);
        currentBookDetails[0] = currentBook.getSerialNumber().toString();
        currentBookDetails[1] = currentBook.getTitle().toString();
        currentBookDetails[2] = DateUtil.formatDate(currentLoan.getDueDate());
        return currentBookDetails;
    }

    /**
     * Returns true if a loan slip is currently mounted.
     *
     * @return true if a loan slip is currently mounted.
     */
    public static boolean isMounted() {
        return isMounted;
    }

    /**
     * Opens loan slip pdf to allow ease of printing loan slip.
     * (Cannot be tested autonomously)
     */
    public static void openGeneratedLoanSlip() throws LoanSlipException {
        if (!isMounted || !isGenerated) {
            throw new LoanSlipException("Loan slip is not generated");
        }
        try {
            Desktop.getDesktop().open(currentFile);
        } catch (IOException e) {
            logger.info("Error in opening loan slip");
        }
    }
}
