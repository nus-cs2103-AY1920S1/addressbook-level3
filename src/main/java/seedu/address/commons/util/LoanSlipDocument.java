package seedu.address.commons.util;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager;

/**
 * Instance class to handle a single pdf document.
 */
public class LoanSlipDocument {

    private static final int HEADER_FONT_SIZE = 32;
    private static final int MID_HEADER_FONT_SIZE = 28;
    private static final int PARAGRAPH_FONT_SIZE = 20;
    private static final int ROW_SIZE = 14;
    private static final double SCALE_RATIO = 0.1;

    private static final String FONT = "/font/Lato-Black.ttf";
    private static final String LOGO_PATH = "/images/LiBerryLogo.png";

    private static final String LINE_DIVIDER = "_______________________________________________________";
    private static final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private Document doc;
    private Table table;

    /**
     * Constructor for LoanSlipDocument object.
     *
     * @param doc iText Document to be wrapped.
     */
    public LoanSlipDocument(Document doc, Table table) {
        this.doc = doc;
        this.table = table;
    }


    /**
     * Appends the logo to the pdf document.
     */
    public void writeLogo() {
        try {
            Image pdfImg = createImage();
            double newWidth = pdfImg.getImageWidth() * SCALE_RATIO;
            double newHeight = pdfImg.getImageHeight() * SCALE_RATIO;
            pdfImg.scaleToFit((float) newWidth, (float) newHeight);
            pdfImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
            doc.add(pdfImg);
        } catch (IOException ioe) {
            assert false;
            ioe.printStackTrace();
        }
    }

    /**
     * Writes the divider line for the loan slip.
     */
    public void writeLine() {
        writeToDocAlignLeft(LINE_DIVIDER, PARAGRAPH_FONT_SIZE);
    }

    /**
     * Writes the header of the document.
     *
     * @param text Text content of header.
     */
    public void writeHeader(String text) {
        writeToDocAlignLeft(text, HEADER_FONT_SIZE);
    }

    /**
     * Writes the mid header of the document.
     *
     * @param text Text content of header.
     */
    public void writeMidHeader(String text) {
        writeToDocAlignLeft(text, MID_HEADER_FONT_SIZE);
    }

    /**
     * Writes a paragraph of the document, aligned to left.
     *
     * @param text Text content of header.
     */
    public void writeLeftParagraph(String text) {
        writeToDocAlignLeft(text, PARAGRAPH_FONT_SIZE);
    }


    /**
     * Writes a paragraph of the document, centralised.
     *
     * @param text Text content of header.
     */
    public void writeCentralisedParagraph(String text) {
        writeToDocCentralised(text, PARAGRAPH_FONT_SIZE);
    }

    /**
     * Adds a cell to the table.
     *
     * @param text text to be added to the cell.
     */
    public void addCell(String text) {
        Cell cell = new Cell();
        cell.setBorder(Border.NO_BORDER);
        cell.add(customLeftParagraph(text, ROW_SIZE));
        table.addCell(cell);
    }

    /**
     * Writes a row to the table.
     *
     * @param elements row of elements.
     */
    public void writeRow(String[] elements) {
        int noOfCols = table.getNumberOfColumns();
        if (elements.length == noOfCols) {
            Arrays.stream(elements).forEach(e -> addCell(e));
        }
    }

    /**
     * Adds the table to the document in preparation for generation of pdf.
     */
    public void submitTable() {
        doc.add(table);
    }

    /**
     * Closes the document.
     */
    public void closeDoc() {
        doc.close();
    }

    /**
     * Writes left-aligned text to document with custom font size.
     *
     * @param text Text to be written.
     * @param fontSize font size of the text.
     */
    private void writeToDocAlignLeft(String text, int fontSize) {
        Paragraph p = customLeftParagraph(text, fontSize);
        doc.add(p);
    }

    /**
     * Writes centralised text to document with custom font size.
     *
     * @param text Text to be written.
     * @param fontSize font size of the text.
     */
    private void writeToDocCentralised(String text, int fontSize) {
        Paragraph p = alignParagraph(TextAlignment.CENTER);
        p.add(text).setFontSize(fontSize);
        doc.add(p);
    }

    /**
     * Writes a paragraph to the document.
     * Allows customization of text and font size.
     *
     * @param text text to be added to the paragraph.
     * @param fontSize font size of text.
     * @return {@code Paragraph} object to be added to document.
     */
    private Paragraph customLeftParagraph(String text, int fontSize) {
        Paragraph p = alignParagraph(TextAlignment.LEFT);
        p.add(text).setFontSize(fontSize);
        return p;
    }

    /**
     * Creates a paragraph with the given alignment.
     *
     * @param textAlignment Alignment of the paragraph to be created.
     * @return a {@code Paragraph} Object with the custom alignment specified.
     */
    private Paragraph alignParagraph(TextAlignment textAlignment) {
        Paragraph p = new Paragraph();
        try {
            PdfFont font = PdfFontFactory.createFont(
                    getClass().getResource(FONT).toString(), PdfEncodings.WINANSI, true);
            p.setFont(font);
        } catch (IOException e) {
            logger.info("Error in loading Font");
            // error occur while loading font, use default font
            e.printStackTrace();
        }
        p.setTextAlignment(textAlignment);
        return p;
    }

    /**
     * Creates an image to be appended to the document.
     *
     * @return an {@code Image} Object.
     */
    private Image createImage() throws IOException {
        logger.info("Creating logo");
        URL url = getClass().getResource(LOGO_PATH);
        InputStream is = url.openStream();
        java.awt.Image image = ImageIO.read(is);
        ImageData imageData = ImageDataFactory.create(image, Color.WHITE);
        logger.info("success");
        return new Image(imageData);
    }

}
