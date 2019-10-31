package seedu.address.commons.util;

import java.util.Arrays;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

/**
 * Instance class to handle a single pdf document.
 */
public class LoanSlipDocument {

    private static final int HEADER_FONT_SIZE = 32;
    private static final int MID_HEADER_FONT_SIZE = 28;
    private static final int PARAGRAPH_FONT_SIZE = 20;
    private static final double SCALE_RATIO = 0.1;

    private static final String FONT = "/src/main/resources/font/Lato-Black.ttf";
    private static final String LOGO_PATH = "/src/main/resources/images/LiBerryLogo.png";
    private static final String LOGO_NAME = "LiBerryLogo.png";

    private static final String LINE_DIVIDER = "_______________________________________________________";
    private static final String TEMP_LINE_DIVIDER = "__________________________________________";

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
    /*
    public void writeLogo() {
        logger.info(getClass().getClassLoader().getResource(LOGO_PATH).toString());
        String path = getClass().getResource(LOGO_NAME).toString();
        Image pdfImg = createImage(path);
        double newWidth = pdfImg.getImageWidth() * SCALE_RATIO;
        double newHeight = pdfImg.getImageHeight() * SCALE_RATIO;
        pdfImg.scaleToFit((float) newWidth, (float) newHeight);
        pdfImg.setHorizontalAlignment(HorizontalAlignment.LEFT);
        doc.add(pdfImg);
        logger.info("end writing logo");
    }
    */

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
        cell.add(customLeftParagraph(text, 18));
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
     * Helper method to allow writing to document with custom font size.
     *
     * @param text Text to be written.
     * @param fontSize font size of the text.
     */
    private void writeToDocAlignLeft(String text, int fontSize) {
        Paragraph p = customLeftParagraph(text, fontSize);
        doc.add(p);
    }

    private void writeToDocCentralised(String text, int fontSize) {
        Paragraph p = alignParagraph(TextAlignment.CENTER);
        p.add(text).setFontSize(fontSize);
        doc.add(p);
    }

    /**
     * Helper method to write a paragraph to the document.
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
        /*
        try {
            PdfFont font = PdfFontFactory.createFont(
                    getClass().getResource(FONT).toString(), PdfEncodings.WINANSI, true);
            p.setFont(font);
        } catch (IOException e) {
            logger.info("font error");
            // error occur while loading font, use default font
            e.printStackTrace();
        }
        */
        p.setTextAlignment(textAlignment);
        return p;
    }

    /**
     * Creates an image to be appended to the document.
     *
     * @param path path of the image in local directory.
     * @return an {@code Image} Object.
     */
    /*
    private Image createImage(String path) {
        ImageData imageData = ImageDataFactory.create(path);
        return new Image(imageData);
    }
    */

}
