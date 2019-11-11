package seedu.address.model.pdfmanager.pdfdeliveryorder;

import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import seedu.address.model.company.Company;
import seedu.address.model.person.Name;

/**
 * Represents the canvas that contains the company signature holder.
 */
public class PdfCompanySignatureCanvas extends PdfCanvasLayout {

    private Company company;

    public PdfCompanySignatureCanvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle,
                                     Company company) {
        super(pdfCanvas, pdfDocument, rectangle);
        this.company = company;
    }

    /**
     * Generates a layout for company personnel signature.
     */
    public void generate() {
        Table table = new Table(12).setFixedLayout().useAllAvailableWidth();

        Cell title = createTitle(company.getName());
        Cell signStoreKeeper = createLabel("StoreKeeper");
        Cell signDriver = createLabel("Driver");
        Cell signSales = createLabel("Sales");

        table.addCell(title);
        table.addCell(signStoreKeeper);
        table.addCell(spaceHolder());
        table.addCell(signDriver);
        table.addCell(spaceHolder());
        table.addCell(signSales);

        canvas.add(table);
        canvas.close();
    }

    /**
     * Creates a layout for title.
     */
    private Cell createTitle(Name name) {
        Cell nameCell = createCell(1, 12, name.toString());

        //styling
        nameCell.setBold();
        styleCell(nameCell);

        return nameCell;
    }

    /**
     * Creates label layout for company signature personnel.
     */
    private Cell createLabel(String label) {
        Cell labelCell = createCell(4, 4, label);

        //styling
        labelCell.setBorderTop(new SolidBorder(0.5f));
        styleCell(labelCell);

        return labelCell;
    }

    /**
     * Fixes to cell height and center the content.
     */
    private void styleCell(Cell cell) {
        cell.setTextAlignment(TextAlignment.CENTER);
        cell.setMaxHeight(contentHolder.getHeight() / 2);
        cell.setMinHeight(contentHolder.getHeight() / 2);
    }

    private Cell spaceHolder() {
        return createCell(1, 1, "");
    }
}
