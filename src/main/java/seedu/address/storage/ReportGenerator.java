package seedu.address.storage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
 * Represents a report generator and the ability to be generate aå report.
 */
public class ReportGenerator {

    /**
     * Generates a PDF report for the specific body.
     *
     * @param body which is used to generate the report.
     */
    public static void generate(Body body) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document,
                    new FileOutputStream(String.format("Report %s.pdf", body.getIdNum())));
            document.open();
            Font bold = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
            document.add(new Paragraph(String.format("Mortago Report for %s", body.getIdNum()), bold));
            document.add(new Paragraph("\n"));

            Image logo = Image.getInstance("docs/images/Logo.png");
            logo.setAbsolutePosition(450, 715);
            logo.scaleAbsolute(100, 100);
            document.add(logo);

            document.add(new Paragraph("Personal Details:"));
            PdfPTable personalDetails = new PdfPTable(2);
            personalDetails.setWidthPercentage(70);
            personalDetails.setSpacingBefore(10f);
            personalDetails.setSpacingAfter(10f);
            float[] columnWidths = {0.5f, 0.5f};
            personalDetails.setWidths(columnWidths);
            personalDetails.setHorizontalAlignment(Element.ALIGN_LEFT);

            personalDetails.addCell(new PdfPCell(new Paragraph("ID Number:")));
            personalDetails.addCell(new PdfPCell(new Paragraph(body.getIdNum().toString())));
            personalDetails.addCell(new PdfPCell(new Paragraph("Name:")));
            personalDetails.addCell(new PdfPCell(new Paragraph(body.getName().toString())));
            personalDetails.addCell(new PdfPCell(new Paragraph("Sex:")));
            personalDetails.addCell(new PdfPCell(new Paragraph(body.getSex().toString())));
            personalDetails.addCell(new PdfPCell(new Paragraph("Date of Admission:")));
            personalDetails.addCell(new PdfPCell(new Paragraph(body.getDateOfAdmission().toString())));
            personalDetails.addCell(new PdfPCell(new Paragraph("Date of Birth:")));
            personalDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getDateOfBirth().orElse(null)))));
            personalDetails.addCell(new PdfPCell(new Paragraph("Date of Death:")));
            personalDetails.addCell(new PdfPCell(new Paragraph(body.getDateOfDeath().toString())));
            personalDetails.addCell(new PdfPCell(new Paragraph("NRIC/FIN Number:")));
            personalDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getNric().orElse(null)))));
            personalDetails.addCell(new PdfPCell(new Paragraph("Religion:")));
            personalDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getReligion().orElse(null)))));

            document.add(personalDetails);

            document.add(new Paragraph("Next of Kin:"));
            PdfPTable nokDetails = new PdfPTable(2); // 2 columns.
            nokDetails.setWidthPercentage(70); //Width 70%
            nokDetails.setSpacingBefore(10f); //Space before table
            nokDetails.setSpacingAfter(10f); //Space after table
            nokDetails.setWidths(columnWidths);
            nokDetails.setHorizontalAlignment(Element.ALIGN_LEFT);

            nokDetails.addCell(new PdfPCell(new Paragraph("Next of Kin:")));
            nokDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getNextOfKin().orElse(null)))));
            nokDetails.addCell(new PdfPCell(new Paragraph("Relationship:")));
            nokDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getRelationship().orElse(null)))));
            nokDetails.addCell(new PdfPCell(new Paragraph("Contact Number:")));
            nokDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getKinPhoneNumber().orElse(null)))));

            document.add(nokDetails);

            document.add(new Paragraph("Other Details:"));
            PdfPTable otherDetails = new PdfPTable(2);
            otherDetails.setWidthPercentage(70);
            otherDetails.setSpacingBefore(10f);
            otherDetails.setSpacingAfter(10f);
            otherDetails.setWidths(columnWidths);
            otherDetails.setHorizontalAlignment(Element.ALIGN_LEFT);

            otherDetails.addCell(new Paragraph("Cause of Death:"));
            otherDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getCauseOfDeath().orElse(null)))));
            List organList = new List();
            if(body.getOrgansForDonation().isPresent()) {
                for (String organ : body.getOrgansForDonation().get()) {
                    organList.add(new ListItem(organ));
                }
                if (organList.isEmpty()) {
                    organList.add("No organs for donation.");
                }
            }
            PdfPCell cell = new PdfPCell();
            cell.addElement(organList);
            otherDetails.addCell(new Paragraph("Organs for Donation:"));
            otherDetails.addCell(cell);
            otherDetails.addCell(new Paragraph("Body Status:"));
            otherDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getBodyStatus().orElse(null)))));
            otherDetails.addCell(new Paragraph("Fridge ID:"));
            otherDetails.addCell(
                    new PdfPCell(new Paragraph(String.format("%s", body.getFridgeId().orElse(null)))));

            document.add(otherDetails);

            document.add(new Paragraph("\n"));
            document.add(new Paragraph("© This report is generated by Mortago."));
            document.close();
            writer.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /*document.add(new Paragraph(String.format("ID Number: %s", body.getIdNum())));
            document.add(new Paragraph(String.format("Name: %s", body.getName())));
            document.add(new Paragraph(String.format("Sex: %s", body.getSex())));
            document.add(new Paragraph(String.format("Date of Admission: %s", body.getDateOfAdmission())));
            document.add(new Paragraph(String.format("Date of Birth: %s", body.getDateOfBirth().orElse(null))));
            document.add(new Paragraph(String.format("Date of Death: %s", body.getDateOfDeath())));
            document.add(new Paragraph(String.format("NRIC/FIN: %s", body.getNric().orElse(null))));
            document.add(new Paragraph(String.format("Religion: %s", body.getReligion().orElse(null))));
            document.add(new Paragraph(String.format("Next of Kin: %s", body.getNextOfKin().orElse(null))));
            document.add(new Paragraph(String.format("NOK Relationship: %s", body.getRelationship().orElse(null))));
            document.add(new Paragraph(String.format("NOK Contact Number: %s", body.getKinPhoneNumber().orElse(null))));
            document.add(new Paragraph(String.format("Cause of Death: %s", body.getCauseOfDeath().orElse(null))));
            document.add(new Paragraph(String.format("Body Status: %s", body.getBodyStatus().orElse(null))));
            document.add(new Paragraph(String.format("Fridge ID: %s", body.getFridgeId().orElse(null))));*/
}
