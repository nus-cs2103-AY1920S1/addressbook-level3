package seedu.address.model.pdfmanager.pdfdeliveryorder;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;

import seedu.address.model.person.Address;
import seedu.address.model.person.Customer;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Represents the canvas that contains the customer information.
 */
public class PdfCustomerCanvas extends PdfCanvasLayout {

    private Customer customer;

    public PdfCustomerCanvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle,
                             Customer customer) {
        super(pdfCanvas, pdfDocument, rectangle);
        this.customer = customer;
    }

    /**
     * Generates a layout of the customer information.
     */
    public void generate() {
        Table table = new Table(1).setFixedLayout().useAllAvailableWidth();
        Cell title = createTitle("Delivery Address:");
        Cell address = createAddress(customer.getAddress());
        Cell name = createName(customer.getName());
        Cell phone = createPhone(customer.getPhone());

        table.addCell(title);
        table.addCell(address);
        table.addCell(name);
        table.addCell(phone);
        insertBorder();

        canvas.add(table);

        canvas.close();
    }

    /**
     * Creates customer information title layout.
     */
    private Cell createTitle(String title) {
        Cell titleCell = createCell(1, 1, title);

        //styling
        titleCell.setItalic();
        titleCell.setFontColor(ColorConstants.LIGHT_GRAY);
        styleCell(titleCell);

        return titleCell;
    }

    /**
     * Creates delivery address layout.
     */
    private Cell createAddress(Address address) {
        Cell addressCell = createCell(1, 1, address.toString());
        //styling
        addressCell.setBold();
        addressCell.setUnderline();
        styleCell(addressCell);
        addressCell.setMaxHeight(40);

        return addressCell;
    }

    /**
     * Creates customer name layout.
     */
    private Cell createName(Name name) {
        String nameStr = "Attn: " + name;
        Cell nameCell = createCell(1, 1, nameStr);
        styleCell(nameCell);
        return nameCell;
    }

    /**
     * Creates customer's phone number layout.
     */
    private Cell createPhone(Phone phone) {
        String phoneStr = "Tel No.: " + phone;
        Cell phoneCell = createCell(1, 1, phoneStr);
        styleCell(phoneCell);

        return phoneCell;
    }

    /**
     * Fixes the cell height and introduce padding on the left and right of the cell.
     */
    private void styleCell(Cell cell) {
        cell.setMarginBottom(0);
        cell.setPaddingLeft(5);
        cell.setPaddingRight(5);
        cell.setBorder(Border.NO_BORDER);
        cell.setMaxHeight(contentHolder.getHeight() / 4);
    }
}
