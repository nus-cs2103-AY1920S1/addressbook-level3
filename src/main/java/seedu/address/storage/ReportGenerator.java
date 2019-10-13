package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import seedu.address.model.entity.body.Body;

public class ReportGenerator {

    public static void execute() {  //pass in body as parameter
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream("Report.pdf")); //body.getBodyIdNum()
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
