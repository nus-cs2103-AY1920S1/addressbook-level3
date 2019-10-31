package seedu.address.storage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import seedu.address.model.entity.body.Body;

//@@author bernicechio
/**
 * Represents a report generator and the ability to be generate a report.
 */
public class ReportGenerator {

    private static Font titleFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
    private static Font subheadingFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);

    /**
     * Generates a PDF summary report for all bodies registered in Mortago.
     *
     */
    public boolean generateSummary(java.util.List<Body> bodyList) {
        if (bodyList == null || bodyList.isEmpty()) {
            return false;
        }
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream("Report Summary.pdf"));
            addHeader(document, "Report Summary");
            document.add(new Paragraph(String.format("Total number of bodies: %s", bodyList.size())));

            PdfPTable allBodiesTable = createFourColumnTable();
            allBodiesTable.addCell(new PdfPCell(new Paragraph("ID NUMBER", subheadingFont)));
            allBodiesTable.addCell(new PdfPCell(new Paragraph("NAME", subheadingFont)));
            allBodiesTable.addCell(new PdfPCell(new Paragraph("BODY STATUS", subheadingFont)));
            allBodiesTable.addCell(new PdfPCell(new Paragraph("DATE OF ADMISSION", subheadingFont)));

            for (Body body : bodyList) {
                allBodiesTable.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getIdNum()))));
                allBodiesTable.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getName()))));
                if (body.getBodyStatus().isEmpty()) {
                    allBodiesTable.addCell(new PdfPCell(new Paragraph("No body status")));
                } else {
                    allBodiesTable.addCell(new PdfPCell(
                            new Paragraph(String.format("%s", body.getBodyStatus().get()))));
                }
                allBodiesTable.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getDateOfAdmission()))));
            }
            document.add(allBodiesTable);
            addFooter(document);
            document.close();
            writer.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Generates a PDF report for all bodies registered in Mortago.
     *
     */
    public boolean generateAll(java.util.List<Body> bodyList) {
        if (bodyList == null || bodyList.isEmpty()) {
            return false;
        }
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream("Report (ALL BODIES).pdf"));
            document.open();

            for (Body body: bodyList) {
                addHeader(document, String.format("Report for %s", body.getIdNum()));
                addPersonalDetails(document, body);
                addNokDetails(document, body);
                addOtherDetails(document, body);

                addFooter(document);
                document.newPage();
            }
            document.close();
            writer.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Adds end details to the report.
     *
     * @param document which is the report.
     * @param title which is title of the pdf report.
     */
    private void addHeader(Document document, String title) throws DocumentException, IOException {
        document.open();
        Paragraph preface = new Paragraph(title, titleFont);
        preface.setAlignment(Element.ALIGN_CENTER);
        document.add(preface);
        document.add(new Paragraph("\n"));
        try {
            //Image logo = Image.getInstance("docs/images/logo.png");
            //Image logo = Image.getInstance(getClass().getClassLoader().getResource("/logo.png"));  //null
            String imageUrl =
                    "https://raw.githubusercontent.com/AY1920S1-CS2103T-T13-2/main/master/docs/images/logo.png";
            Image logo = Image.getInstance(new URL(imageUrl));
            logo.setAbsolutePosition(40, 770); //set logo top left
            //logo.setAbsolutePosition(450, 800); top right
            logo.scaleAbsolute(100, 70);
            document.add(logo);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds end details to the report.
     *
     * @param document which is the report.
     */
    private static void addFooter(Document document) throws DocumentException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        document.add(new Paragraph("\n"));
        String formatDate = "                                                                      " + dtf.format(now);
        document.add(new Paragraph(formatDate));
        document.add(new Paragraph("___________________________                _______________"));
        document.add(new Paragraph("Manager Signature                                        Date"));
        document.add(new Paragraph("\n"));
        document.add(new Paragraph("© 2019 This report is generated by Mortago."));
    }

    /**
     * Generates a PDF report for the specific body.
     *
     * @param body which is used to generate the report.
     * @return boolean which returns true when report is generated successfully.
     */
    public boolean generate(Body body) {
        if (body == null) {
            return false;
        }
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(String.format("Report %s.pdf", body.getIdNum())));
            addHeader(document, String.format("Report for %s", body.getIdNum()));

            addPersonalDetails(document, body);
            addNokDetails(document, body);
            addOtherDetails(document, body);

            addFooter(document);
            document.close();
            writer.close();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * Adds other details of the specific body to the PDF report.
     *
     * @param document which is the report.
     * @param body which is used to generate the report.
     */
    private static void addOtherDetails(Document document, Body body) throws DocumentException {
        document.add(new Paragraph("Other Details:", subheadingFont));
        PdfPTable otherDetails = createTable();

        otherDetails.addCell(new Paragraph("Cause of Death:"));
        if (body.getCauseOfDeath().isEmpty()) {
            otherDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            otherDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getCauseOfDeath().get()))));
        }
        List organList = new List();
        for (String organ : body.getOrgansForDonation()) {
            organList.add(new ListItem(organ));
        }
        if (organList.isEmpty()) {
            organList.add("No organs for donation.");
        }
        PdfPCell cell = new PdfPCell();
        cell.addElement(organList);
        otherDetails.addCell(new Paragraph("Organs for Donation:"));
        otherDetails.addCell(cell);
        otherDetails.addCell(new Paragraph("Body Status:"));
        if (body.getBodyStatus().isEmpty()) {
            otherDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            otherDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getBodyStatus().get()))));
        }
        otherDetails.addCell(new Paragraph("Fridge ID:"));
        if (body.getFridgeId().isEmpty()) {
            otherDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            otherDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getFridgeId().get()))));
        }

        document.add(otherDetails);
    }

    /**
     * Creates a new table for the report PDF.
     *
     * @return table which is an empty table with 2 columns
     */
    private static PdfPTable createTable() throws DocumentException {
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        float[] columnWidths = {0.5f, 0.5f};
        table.setWidths(columnWidths);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        return table;
    }

    /**
     * Creates a new table with 4 columns for the report PDF.
     *
     * @return table which is an empty table with 4
     * columns
     */
    private static PdfPTable createFourColumnTable() throws DocumentException {
        PdfPTable table = new PdfPTable(4);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);
        float[] columnWidths = {0.3f, 0.3f, 0.5f, 1f};
        table.setWidths(columnWidths);
        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        return table;
    }

    /**
     * Adds next of kin details of the specific body to the PDF report.
     *
     * @param document which is the report.
     * @param body which is used to generate the report.
     */
    private static void addNokDetails(Document document, Body body) throws DocumentException {
        document.add(new Paragraph("Next of Kin:", subheadingFont));
        PdfPTable nokDetails = createTable();

        nokDetails.addCell(new PdfPCell(new Paragraph("Next of Kin:")));
        if (body.getNextOfKin().isEmpty()) {
            nokDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            nokDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getNextOfKin().get()))));
        }
        nokDetails.addCell(new PdfPCell(new Paragraph("Relationship:")));
        if (body.getRelationship().isEmpty()) {
            nokDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            nokDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getRelationship().get()))));
        }
        nokDetails.addCell(new PdfPCell(new Paragraph("Contact Number:")));
        if (body.getKinPhoneNumber().isEmpty()) {
            nokDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            nokDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getKinPhoneNumber().get()))));
        }

        document.add(nokDetails);
    }

    /**
     * Adds personal details of the specific body to the PDF report.
     *
     * @param document which is the report.
     * @param body which is used to generate the report.
     */
    private static void addPersonalDetails(Document document, Body body) throws DocumentException {
        document.add(new Paragraph("Personal Details:", subheadingFont));
        PdfPTable personalDetails = createTable();

        personalDetails.addCell(new PdfPCell(new Paragraph("ID Number:")));
        personalDetails.addCell(new PdfPCell(new Paragraph(body.getIdNum().toString())));
        personalDetails.addCell(new PdfPCell(new Paragraph("Name:")));
        personalDetails.addCell(new PdfPCell(new Paragraph(body.getName().toString())));
        personalDetails.addCell(new PdfPCell(new Paragraph("Sex:")));
        personalDetails.addCell(new PdfPCell(new Paragraph(body.getSex().toString())));
        personalDetails.addCell(new PdfPCell(new Paragraph("Date of Admission:")));
        personalDetails.addCell(new PdfPCell(new Paragraph(body.getDateOfAdmission().toString())));
        personalDetails.addCell(new PdfPCell(new Paragraph("Date of Birth:")));
        if (body.getDateOfBirth().isEmpty()) {
            personalDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            personalDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getDateOfBirth().get()))));
        }
        personalDetails.addCell(new PdfPCell(new Paragraph("Date of Death:")));
        if (body.getDateOfDeath().isEmpty()) {
            personalDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            personalDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getDateOfDeath().get()))));
        }
        personalDetails.addCell(new PdfPCell(new Paragraph("NRIC/FIN Number:")));
        if (body.getNric().isEmpty()) {
            personalDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            personalDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getNric().get()))));
        }
        personalDetails.addCell(new PdfPCell(new Paragraph("Religion:")));
        if (body.getReligion().isEmpty()) {
            personalDetails.addCell(new PdfPCell(new Paragraph("N.A.")));
        } else {
            personalDetails.addCell(new PdfPCell(new Paragraph(String.format("%s", body.getReligion().get()))));
        }

        document.add(personalDetails);

    }

    /**
     * Returns true when an object is an instance of ReportGenerator
     *
     * @param other object
     * @return true when object is an instance of ReportGenerator
     */
    public boolean isSameReportGenerator(Object other) {
        return other instanceof ReportGenerator;
    }
}
