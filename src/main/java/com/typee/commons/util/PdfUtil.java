package com.typee.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Logger;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.typee.commons.core.LogsCenter;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.Location;
import com.typee.model.person.Person;
import com.typee.model.report.Report;

/**
 * Generates report of engagement with a specified template.
 */
public class PdfUtil {

    private static final String FOLDER_PATH = "reports/";
    private static Properties doc_prop;
    private final static Logger logger = LogsCenter.getLogger(PdfUtil.class);

    /**
     * Generates a {@code Report} in .pdf format and opens the file.
     */
    public static void generateReport(Report report) throws DocumentException, IOException {
        doc_prop = FileUtil.loadProperties();
        Engagement engagement = report.getEngagement();
        Document document = initDoc(engagement, report.getTo());

        document = addIntroductionPar(document);
        document.add(createAttendeesTable(engagement.getDescription(), engagement.getLocation(),
                engagement.getStartTime(), engagement.getEndTime()));
        addConclusion(document, report.getFrom());
        document.close();
    }

    private static Document initDoc(Engagement engagement, Person to) throws IOException, DocumentException {
        String fileName = FOLDER_PATH + generateFileName(engagement);
        Document doc = new Document();

        if (Files.notExists(Paths.get(FOLDER_PATH))) {
            Files.createFile(Files.createDirectories(Paths.get(FOLDER_PATH))).toFile();
        }
        PdfWriter.getInstance(doc, new FileOutputStream(fileName));

        doc.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        addCompanyLogo(doc);

        Paragraph par = new Paragraph(doc_prop.getProperty("document.header") + " " + to + ",", font);
        par.setSpacingBefore(50);
        par.setSpacingAfter(28);
        doc.add(par);
        return doc;
    }

    private static void addCompanyLogo(Document doc) throws IOException, DocumentException {
        Image logo = Image.getInstance(PdfUtil.class.getClassLoader()
                .getResource("images/company_logo_sample.png"));
        logo.scaleToFit(250, 100);
        logo.setAlignment(Element.ALIGN_LEFT);

        Paragraph par = new Paragraph();
        par.add(logo);
        doc.add(logo);
    }

    private static void addConclusion(Document doc, Person from) throws DocumentException {
        Paragraph par = new Paragraph(doc_prop.getProperty("document.conclusion") + "\n"
                + from + "\n"
                + doc_prop.getProperty("document.sender.profile") + "\n"
                + doc_prop.getProperty("document.sender.contact") + "\n"
                + doc_prop.getProperty("document.company.address") + "\n"
                + doc_prop.getProperty("document.company.name"));
        par.setSpacingBefore(100);
        doc.add(par);
    }

    private static Document addIntroductionPar(Document doc) throws DocumentException {
        Paragraph par = new Paragraph(doc_prop.getProperty("appointment.introduction"));
        par.setSpacingAfter(10);
        doc.add(par);
        return doc;
    }

    /**
     * Returns a {@code PdfPTable} for Engagement Information
     */
    private static Paragraph createAttendeesTable(String desc, Location venue,
                                                  LocalDateTime startTime, LocalDateTime endTime) {
        PdfPTable table = new PdfPTable(2);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Engagement Description:"));
        PdfPCell cellDesc = new PdfPCell(new Paragraph(desc));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Venue:"));
        PdfPCell cellVenue = new PdfPCell(new Paragraph(venue.toString()));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Time:"));
        PdfPCell cellTime = new PdfPCell(new Paragraph(startTime.toString()
                + " - " + endTime.toString()));
        PdfPCell cellAttendees = new PdfPCell(new Paragraph("Attendees"));

        table.addCell(cell1);
        table.addCell(cellDesc);
        table.addCell(cell2);
        table.addCell(cellVenue);
        table.addCell(cell3);
        table.addCell(cellTime);
        table.addCell(cellAttendees);
        
        Paragraph par = new Paragraph();
        par.setSpacingBefore(10);
        par.add(table);
        return par;
    }

    /**
     * Returns a {@code String} of report file name with date followed by description.
     */
    private static String generateFileName(Engagement engagement) {
        String startTime = engagement.getStartTime().format(DateTimeFormatter.ofPattern("dd-MM-YY_HH:mm"));
        return startTime + "_" + engagement.getDescription() + ".pdf";
    }

    private boolean checkIfDocumentExists(String fileName) {
        return false;
    }
}
