package seedu.address.commons.util;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

public class LoanSlipDocument {
    private Document doc;
    private static final String FONT = "src/main/resources/font/Lato-Black.ttf";
    private static final String LOGO_PATH = "src/main/resources/images/LiBerryLogo.png";
    private static final int HEADER_FONT_SIZE = 20;
    private static final int PARAGRAPH_FONT_SIZE = 12;

    public LoanSlipDocument(Document doc) {
        this.doc = doc;
    }

    public void writeLogo() {
        try {
            ImageData imageData = ImageDataFactory.create(LOGO_PATH);
            Image pdfImg = new Image(imageData);
            double newWidth = pdfImg.getImageWidth() * 0.1;
            double newHeight = pdfImg.getImageHeight() * 0.1;
            pdfImg.scaleToFit((float) newWidth, (float) newHeight);
            pdfImg.setHorizontalAlignment(HorizontalAlignment.CENTER);
            doc.add(pdfImg);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void writeHeader(String text) {
        writeToDoc(text, HEADER_FONT_SIZE);
    }

    public void writeParagraph(String text) {
        writeToDoc(text, PARAGRAPH_FONT_SIZE);
    }

    public void writeToDoc(String text, int fontSize) {
        try {
            PdfFont font = PdfFontFactory.createFont(FONT, PdfEncodings.WINANSI, true);
            Paragraph p = new Paragraph();
            p.setFont(font);
            p.setTextAlignment(TextAlignment.CENTER);
            p.add(text).setFontSize(fontSize);
            doc.add(p);
        } catch (IOException e) {
            e.printStackTrace(); //handle exception later
        }
    }
}
