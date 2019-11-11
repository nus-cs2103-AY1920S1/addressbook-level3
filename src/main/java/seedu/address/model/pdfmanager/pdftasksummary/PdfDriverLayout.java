package seedu.address.model.pdfmanager.pdftasksummary;

import java.time.LocalDate;

import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import seedu.address.model.person.Driver;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

/**
 * Represents a driver's information in a table format in the PDF document.
 */
public class PdfDriverLayout {

    private Driver driver;
    private Document document;
    private LocalDate dateOfDelivery;

    public PdfDriverLayout(Document document, Driver driver, LocalDate dateOfDelivery) {
        this.driver = driver;
        this.document = document;
        this.dateOfDelivery = dateOfDelivery;
    }

    /**
     * Creates a paragraph that consists of the driver's information in the PDF document.
     */
    public void createDriverDetails() {
        //populate Paragraph
        Paragraph dateParagraph = getDateOfDelivery(dateOfDelivery);
        Paragraph driverIdParagraph = getDriverIdCell(driver.getId());
        Paragraph nameParagraph = getNameCell(driver.getName());
        Paragraph phoneNumberParagraph = getPhoneNumberCell(driver.getPhone());

        document.add(dateParagraph);
        document.add(driverIdParagraph);
        document.add(nameParagraph);
        document.add(phoneNumberParagraph);
    }

    private Paragraph getDriverIdCell(int id) {
        String idStr = "Driver ID: " + id;
        return PdfLayout.createParagraph(idStr);
    }

    private Paragraph getNameCell(Name name) {
        String nameStr = "Driver: " + name;
        return PdfLayout.createParagraph(nameStr);
    }

    private Paragraph getPhoneNumberCell(Phone phone) {
        String phoneStr = "Contact No: " + phone;
        return PdfLayout.createParagraph(phoneStr);
    }

    private Paragraph getDateOfDelivery(LocalDate date) {
        String dateOfDelivery = "Date of Delivery: " + date;
        return PdfLayout.createParagraph(dateOfDelivery);
    }
}
