package seedu.address.commons.util;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import java.awt.Desktop;

import java.io.File;
import java.io.IOException;

import seedu.address.model.book.Book;
import seedu.address.model.borrower.Borrower;
import seedu.address.model.loan.Loan;

public class LoanSlipUtil {
    private static String DEST = "./data/loanSlips/";

    public static void createLoanSlip(Loan loan, Book book, Borrower borrower) throws IOException {
        String finalDest = DEST + loan.getLoanId().toString() + ".pdf";
        File file = new File(finalDest);
        file.getParentFile().mkdirs();
        PdfWriter writer = new PdfWriter(finalDest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);
        LoanSlipDocument doc = new LoanSlipDocument(document);

        doc.writeLogo();
        //Add paragraph to the document
        doc.writeHeader("LiBerry");
        String result = "Borrower: " + borrower.getName() + "\n"
                + "Borrower ID: " + borrower.getBorrowerId() + "\n"
                + "\n"
                + "Book borrowed: " + book.getTitle() + "\n"
                + "Please return the book by: " + loan.getDueDate() + "\n"
                + "We hope to see you again!";
        doc.writeParagraph(result);

        //Close document
        document.close();
        openLoanSlip(file);
    }

    /**
     * Opens loan slip pdf to allow ease of printing loan slip.
     *
     * @param file File object of loan slip.
     */
    private static void openLoanSlip(File file) {
        try {
            Desktop.getDesktop().open(file);
        } catch (IOException e) {
            e.printStackTrace(); // handle exception later
        }
    }
}
