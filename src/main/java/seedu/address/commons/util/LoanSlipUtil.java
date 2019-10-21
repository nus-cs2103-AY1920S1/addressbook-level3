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
            String finalDest = DEST + currentLoan.getLoanId().toString() + PDF_EXTENSION;
            File file = new File(finalDest);
            file.getParentFile().mkdirs();
            PdfWriter writer = new PdfWriter(finalDest);

            //Initialize PDF document
            PdfDocument pdf = new PdfDocument(writer);

            // Initialize document
            Document document = new Document(pdf);
            float [] pointColumnWidths = {75F, 325F, 125F};
            Table table = new Table(pointColumnWidths);
            LoanSlipDocument doc = new LoanSlipDocument(document, table);
            //write logo
            doc.writeLogo();
            doc.writeLine();

            //Add paragraph to the document
            doc.writeHeader(currentBorrower.getName().toString());
            doc.writeSubHeader(currentBorrower.getBorrowerId().toString());
            doc.writeLine();
            doc.writeHeader("Books borrowed");


            doc.addCell("S/N");
            doc.addCell("Book");
            doc.addCell("Due by");
            doc.addCell(currentBook.getSerialNumber().toString());
            doc.addCell(currentBook.getTitle().toString());
            doc.addCell(formatDate(currentLoan.getDueDate().toString()));

            doc.submitTable();
            doc.writeLine();
            doc.writeParagraph(BYE_MESSAGE);

            //Close document
            currentFile = file;
            isGenerated = true;
            doc.closeDoc();
        } catch (IOException e) {
            throw new LoanSlipException(e.getMessage());
        }
    }

    /**
     * Formats the date to be printed on the loan slip.
     *
     * @param date date to be formatted.
     * @return a formatted date as a {@code String}
     */
    private static String formatDate(String date) {
        String[] pieces = date.split("-");
        String result = "";
        for (String s : pieces) {
            result = s + "-" + result;
        }
        return result.substring(0, result.length() - 1);
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
     * Helper method to assist in generating content for the loan slip.
     *
     * @param borrower Borrower associated with the loan.
     * @param book Book associated with the loan.
     * @param loan Loan object representing the loan.
     * @return {@Code String} to be printed on the loan slip.
     */
    private static String generateLoanSlipContent(Borrower borrower, Book book, Loan loan) {
        String result = "Summary of loan:\n"
                + "Please return the book by: " + loan.getDueDate() + "\n"
                + "We hope to see you again!";
        return result;
    }

    /**
     * Opens loan slip pdf to allow ease of printing loan slip.
     */
    public static void openLoanSlip() {
        if (isMounted && isGenerated) {
            try {
                Desktop.getDesktop().open(currentFile);
            } catch (IOException e) {
                e.printStackTrace(); // handle exception later
            }
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
