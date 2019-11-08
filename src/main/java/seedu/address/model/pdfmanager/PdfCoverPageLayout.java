package seedu.address.model.pdfmanager;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;

/**
 * Represents the cover page in the PDF document.
 */
public class PdfCoverPageLayout {

    private Document document;

    public PdfCoverPageLayout(Document document) {
        this.document = document;
    }

    /**
     * Adds a cover page to the PDF document.
     *
     * @param title title.
     * @param subTitle secondary title.
     */
    public void addCoverPage(String title, String subTitle) {
        Paragraph titleParagraph = createTitle(title);
        Paragraph subTitleParagraph = createSubTitle(subTitle);
        document.add(PdfLayout.alignParagraphMiddle(titleParagraph));
        document.add(PdfLayout.alignParagraphMiddle(subTitleParagraph));

        Table sampleTable = createSampleTable();
        document.add(sampleTable);
    }

    /**
     * Creates a {@code Paragraph} object that consists of the title.
     * The main title is being position in the center of the page.
     *
     * @param title secondary title.
     */
    public Paragraph createTitle(String title) {
        Text titleText = new Text(title);
        titleText.setFontColor(ColorConstants.RED);
        titleText.setFontSize(48);
        titleText.setBold();

        Paragraph titleParagraph = new Paragraph(titleText);
        titleParagraph.setFixedPosition(60, 550, 500);

        return titleParagraph;
    }

    /**
     * Creates a {@code Paragraph} object that consists of the secondary title.
     * The secondary title is being position in the center of the page, below the main title.
     *
     * @param subTitle secondary title.
     */
    public Paragraph createSubTitle(String subTitle) {
        Text subTitleText = new Text(subTitle);
        subTitleText.setFontColor(ColorConstants.BLUE);
        subTitleText.setItalic();

        Paragraph subTitleParagraph = new Paragraph(subTitleText);
        subTitleParagraph.setFixedPosition(60, 530, 500);

        return subTitleParagraph;
    }

    /**
     * Creates a table used to display a sample layout of the task information.
     */
    public Table createSampleTable() {
        PdfSampleLayout sampleLayout = new PdfSampleLayout();
        Table sampleTable = sampleLayout.createTable();
        sampleTable.setFixedPosition(60, 200, sampleTable.getWidth());

        return sampleTable;
    }
}
