package seedu.address.model.pdfmanager.pdfdeliveryorder;

import java.util.Optional;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;

import seedu.address.model.company.Company;
import seedu.address.model.company.GstRegistrationNumber;
import seedu.address.model.company.RegistrationNumber;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Represents the canvas that contains the company information.
 */
public class PdfCompanyCanvas extends PdfCanvasLayout {

    private static final float TITLE_FONT_SIZE = 25;
    private static final float SUB_TITLE_FONT_SIZE = 10;

    private Company company;

    public PdfCompanyCanvas(PdfCanvas pdfCanvas, PdfDocument pdfDocument, Rectangle rectangle, Company company) {
        super(pdfCanvas, pdfDocument, rectangle);
        this.company = company;
    }

    /**
     * Generates a layout of the company information.
     */
    public void generate() {
        Table table = new Table(1).setFixedLayout().useAllAvailableWidth();
        Cell companyName = getCompanyName(company.getName());
        Cell companyInformation = getCompanyInformation(company.getAddress(), company.getPhone(),
                company.getFax(), company.getEmail(), company.getRegistrationNumber(),
                company.getGstRegistrationNumber());

        table.addCell(companyName);
        table.addCell(companyInformation);

        canvas.add(table);

        canvas.close();
    }

    private Cell getCompanyName(Name name) {
        Cell companyName = createCell(1, 1, createName(name));

        //styling
        companyName.setFontSize(TITLE_FONT_SIZE);
        companyName.setBold();
        companyName.setHeight(40);
        companyName.setTextAlignment(TextAlignment.CENTER);

        return companyName;
    }

    private Cell getCompanyInformation(Address address, Phone phone, Phone fax, Email email,
                                       RegistrationNumber regNo, Optional<GstRegistrationNumber> gstRegNo) {
        StringBuilder companyInfo = new StringBuilder();
        companyInfo.append(createAddress(address)).append("\n")
                .append(createPhone(phone)).append("  ").append(createFax(fax)).append("\n")
                .append(createEmail(email)).append("\n")
                .append(createRegistrationNumber(regNo)).append("  ").append(createGstRegistrationNumber(gstRegNo));
        Cell companyInfoCell = createCell(1, 1, companyInfo.toString());

        //styling
        companyInfoCell.setFontSize(SUB_TITLE_FONT_SIZE);
        companyInfoCell.setTextAlignment(TextAlignment.CENTER);
        companyInfoCell.setFontColor(ColorConstants.DARK_GRAY);

        return companyInfoCell;
    }

    private String createName(Name name) {
        return name.toString();
    }

    private String createAddress(Address address) {
        return address.toString();
    }

    private String createPhone(Phone phone) {
        return "Tel: " + phone;
    }

    private String createFax(Phone fax) {
        return "Fax: " + fax;
    }

    private String createEmail(Email email) {
        return "Email: " + email;
    }

    private String createRegistrationNumber(RegistrationNumber regNo) {
        return "Co Reg. No.: " + regNo;
    }

    /**
     * Creates a representation to display in the layout.
     */
    private String createGstRegistrationNumber(Optional<GstRegistrationNumber> gstRegNo) {
        String label = "Co Reg. No.: ";
        String gstRegNoPrint;

        if (gstRegNo.isEmpty()) {
            gstRegNoPrint = label + GstRegistrationNumber.EMPTY_REPRESENTATION;
        } else {
            gstRegNoPrint = label + gstRegNo.get();
        }

        return gstRegNoPrint;
    }
}
