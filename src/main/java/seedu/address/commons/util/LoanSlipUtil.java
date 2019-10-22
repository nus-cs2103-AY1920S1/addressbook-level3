package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Table;

import seedu.address.commons.exceptions.LoanSlipException;
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

    private static Loan currentLoan;
    private static Book currentBook;
    private static Borrower currentBorrower;

    private static File currentFile;

    private static boolean isMounted = false;
    private static boolean isGenerated = false;


    /**
     * Mounts a Loan slip in preparation for creating a pdf version of it.
     *
     * @param loan Loan to be represented in the loan slip.
     * @param book Book associated to current loan.
     * @param borrower Borrower associated to current loan.
     */
    public static void mountLoanSlip(Loan loan, Book book, Borrower borrower) throws LoanSlipException {
        if (isMounted) {
            unmountLoanSlip();
        }
        if (!loan.getBorrowerId().equals(borrower.getBorrowerId())) {
            throw new LoanSlipException("Borrower and Loan do not match!");
        }
        if (!loan.getBookSerialNumber().equals(book.getSerialNumber())) {
            throw new LoanSlipException("Book and Loan do not match!");
        }
        currentLoan = loan;
        currentBook = book;
        currentBorrower = borrower;
        isMounted = true;
        isGenerated = false;
    }

    /**
     * Unmounts a Loan slip after creating a pdf of it.
     */
    public static void unmountLoanSlip() {
        if (isMounted) {
            currentLoan = null;
            currentBook = null;
            currentBorrower = null;
            currentFile = null;
            isMounted = false;
            isGenerated = false;
        }
    }

    /**
     * Creates a pdf version of the loan slip that is currently mounted.
     *
     * @throws LoanSlipException if there is an error reading/writing to the pdf file.
     */
    public static void createLoanSlipInDirectory() throws LoanSlipException {
        if (!isMounted) {
            throw new LoanSlipException("No Loan slip mounted yet");
        }
        try {
            requireNonNull(currentLoan);
            requireNonNull(currentBook);
            requireNonNull(currentBorrower);
            Document document = createDocument(currentLoan.getLoanId().toString());
            float [] pointColumnWidths = {FIRST_ROW_WIDTH, SECOND_ROW_WIDTH, THIRD_ROW_WIDTH};
            Table table = new Table(pointColumnWidths);
            LoanSlipDocument doc = new LoanSlipDocument(document, table);
            generateLiberryLoanSlip(doc);
        } catch (IOException e) {
            throw new LoanSlipException(e.getMessage());
        }
    }

    /**
     * Creates a {@code File} object to be populated with information.
     *
     * @param docName name of the new file object.
     * @return a {@code Document} object representing the file.
     * @throws IOException if there are errors in creating the new file.
     */
    private static Document createDocument(String docName) throws IOException, LoanSlipException {
        if (!isMounted) {
            throw new LoanSlipException("No Loan slip mounted yet");
        }
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
    private static void generateLiberryLoanSlip(LoanSlipDocument doc) throws LoanSlipException {
        if (!isMounted) {
            throw new LoanSlipException("No Loan slip mounted yet");
        }
        //write logo
        doc.writeLogo();
        doc.writeLine();
        //Add paragraph to the document
        doc.writeHeader(currentBorrower.getName().toString());
        doc.writeLeftParagraph(currentBorrower.getBorrowerId().toString());
        doc.writeLine();
        doc.writeMidHeader("Books borrowed");
        //populate table
        String[] headerRow = new String[]{"S/N", "Book", "Due By"};
        doc.writeRow(headerRow);
        doc.writeRow(createBookRow());
        //add table to document
        doc.submitTable();
        doc.writeLine();
        doc.writeCentralisedParagraph(BYE_MESSAGE);
        //close document
        doc.closeDoc();
        isGenerated = true;
    }

    /**
     * Creates an array using the details from the mounted loan slip.
     *
     * @return an array of string representing a row of the table.
     */
    private static String[] createBookRow() throws LoanSlipException {
        if (!isMounted) {
            throw new LoanSlipException("No Loan slip mounted yet");
        }
        String[] currentBookDetails = new String[3];
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
    public static void openLoanSlip() throws LoanSlipException {
        if (!isMounted || !isGenerated) {
            throw new LoanSlipException("Loan slip is not generated");
        }
        try {
            Desktop.getDesktop().open(currentFile);
        } catch (IOException e) {
            throw new LoanSlipException("Error in opening loan slip");
        }
    }

    /**
     * For testing purposes.
     *
     * @return Current loan mounted, or null if unmounted.
     */
    public static Loan getCurrentLoan() {
        return currentLoan;
    }
}
