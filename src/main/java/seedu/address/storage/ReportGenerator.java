package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import seedu.address.model.entity.body.Body;

//@@author bernicechio
/**
 * Represents a report generator and the ability to be generate aå report.
 */
public class ReportGenerator {

    /**
     * Generates a PDF report for the specific body.
     *
     * @param body which is used to generate the report.
     */
    public static void generate(Body body) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream("Report.pdf"));
            document.open();
            document.add(new Paragraph("A Mortago report document."));
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
