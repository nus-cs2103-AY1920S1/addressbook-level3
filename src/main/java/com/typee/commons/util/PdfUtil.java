package com.typee.commons.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.commons.io.FilenameUtils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.typee.commons.core.LogsCenter;
import com.typee.logic.commands.exceptions.DeleteDocumentException;
import com.typee.model.engagement.Appointment;
import com.typee.model.engagement.AttendeeList;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.Interview;
import com.typee.model.engagement.Location;
import com.typee.model.engagement.Meeting;
import com.typee.model.engagement.TimeSlot;
import com.typee.model.person.Person;
import com.typee.model.report.Report;

/**
 * Generates report of engagement with a specified template.
 */
public class PdfUtil {

    public static final String FOLDER_PATH = "reports/";
    private static Properties docProp;
    private static final Logger logger = LogsCenter.getLogger(PdfUtil.class);
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd-MM-YY_HH-mm");

    /**
     * Generates a {@code Report} in .pdf format and opens the file.
     */
    public static Path generateReport(Path fileDir, Report report) throws DocumentException, IOException {
        docProp = FileUtil.loadProperties();
        Engagement engagement = report.getEngagement();

        String fileName = fileDir.toString() + "/" + generateFileName(report.getTo().getName().fullName,
                report.getFrom().getName().fullName,
                engagement.getTimeSlot().getStartTime(),
                engagement.getDescription());
        report.setFilePath(Paths.get(fileName));

        Document document = initDoc(fileName, engagement, report.getTo());
        TimeSlot timeSlot = engagement.getTimeSlot();

        document = addIntroductionPar(document, engagement);
        document.add(createAttendeesTable(engagement.getDescription(), engagement.getLocation(),
                engagement.getAttendees(), timeSlot.getStartTime(), timeSlot.getEndTime()));
        addConclusion(document, report.getFrom());
        document.close();
        logger.info("Document: " + fileName + " generated");
        return Paths.get(fileName);
    }

    /**
     * Checks if document is already being generated (not implemented yet).
     */
    public static boolean checkIfDocumentExists(Path dirPath, String to,
                                                String from, LocalDateTime start, String desc) {
        String fileName = generateFileName(to, from, start, desc);
        if (Files.notExists(dirPath)) {
            dirPath.toFile().mkdir();
        }
        File[] files = dirPath.toFile().listFiles();

        boolean isExisting = Stream.of(files)
                .map(file -> file.getName())
                .filter(f -> FilenameUtils.getExtension(f).equals("pdf")
                        && f.equals(fileName))
                .count() == 1;
        logger.info("Check if document exists: " + fileName + ": " + isExisting);
        return isExisting;
    }

    /**
     * Opens a document that only accepts pdf format.
     */
    public static boolean openDocument(Path documentPath) throws IOException {
        if (FilenameUtils.getExtension(documentPath.toFile().getName()).equals("pdf")) {
            Desktop.getDesktop().open(documentPath.toFile());
            return true;
        }
        return false;
    }

    /**
     * Deletes the document of a give file name in the directory.
     */
    public static boolean deleteDocument(Path dirPath, String to, String from, LocalDateTime start, String desc)
            throws DeleteDocumentException {
        String fileName = generateFileName(to, from, start, desc);
        logger.info(fileName);

        if (Files.notExists(dirPath)) {
            dirPath.toFile().mkdir();
        }

        File[] files = dirPath.toFile().listFiles();
        Optional<File> fileToDelete = Optional.empty();
        for (File f: files) {
            if (f.getName().equals(fileName)) {
                fileToDelete = Optional.of(f);
            }
        }

        if (!fileToDelete.isEmpty()) {
            return fileToDelete.get().delete();
        }
        throw new DeleteDocumentException();
    }

    /**
     * Initialise and instantiates the {@code PdfWriter}.
     */
    private static Document initDoc(String fileName, Engagement engagement, Person to) throws IOException,
            DocumentException {
        logger.info(fileName);
        Document doc = new Document();

        if (Files.notExists(Paths.get(FOLDER_PATH))) {
            Paths.get(FOLDER_PATH).toFile().mkdir();
        }

        try {
            PdfWriter.getInstance(doc, new FileOutputStream(fileName));
        } catch (DocumentException e) {
            logger.info(e.getMessage());
        }

        doc.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        addCompanyLogo(doc);

        Paragraph par = new Paragraph(docProp.getProperty("document.header") + " " + to + ",", font);
        par.setSpacingBefore(50);
        par.setSpacingAfter(28);
        doc.add(par);
        return doc;
    }

    /**
     * Adds a company logo image in the beginning of the document
     */
    private static void addCompanyLogo(Document doc) throws IOException, DocumentException {
        Image logo = Image.getInstance(PdfUtil.class.getClassLoader()
                .getResource("images/company_logo_sample.png"));
        logo.scaleToFit(250, 100);
        logo.setAlignment(Element.ALIGN_LEFT);

        Paragraph par = new Paragraph();
        par.add(logo);
        doc.add(logo);
    }

    /**
     * Adds the conclusion and footer paragraph of the document.
     */
    private static void addConclusion(Document doc, Person from) throws DocumentException {
        Paragraph par = new Paragraph(docProp.getProperty("document.conclusion") + "\n"
                + from + "\n"
                + docProp.getProperty("document.sender.profile") + "\n"
                + docProp.getProperty("document.sender.contact") + "\n"
                + docProp.getProperty("document.company.address") + "\n"
                + docProp.getProperty("document.company.name"));
        par.setSpacingBefore(70);
        par.setSpacingAfter(30);

        doc.add(par);
        doc.add(new Paragraph(docProp.getProperty("document.footer")));
    }

    /**
     * Adds first introduction paragraph of the document.
     */
    private static Document addIntroductionPar(Document doc, Engagement engagement) throws DocumentException {
        String introduction = "";
        if (engagement instanceof Appointment) {
            introduction = docProp.getProperty("appointment.introduction");
        } else if (engagement instanceof Interview) {
            introduction = docProp.getProperty("interview.introduction");
        } else if (engagement instanceof Meeting) {
            introduction = docProp.getProperty("meeting.introduction");
        }
        Paragraph par = new Paragraph(introduction);
        par.setSpacingAfter(10);
        doc.add(par);
        return doc;
    }

    /**
     * Returns a {@code PdfPTable} for Engagement Information
     */
    private static Paragraph createAttendeesTable(String desc, Location venue,
                                                  AttendeeList attendeeList,
                                                  LocalDateTime startTime, LocalDateTime endTime) {
        PdfPTable table = new PdfPTable(2);
        table.getDefaultCell().setPadding(10);

        PdfPCell cell1 = new PdfPCell(new Paragraph("Engagement Description:"));
        PdfPCell cellDesc = new PdfPCell(new Paragraph(desc));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Venue:"));
        PdfPCell cellVenue = new PdfPCell(new Paragraph(venue.toString()));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Time:"));
        PdfPCell cellTime = new PdfPCell(new Paragraph(startTime.format(dateFormat)
                + " - " + endTime.format(dateFormat)));
        PdfPCell cell4 = new PdfPCell(new Paragraph("Attendees"));

        table.addCell(cell1);
        table.addCell(cellDesc);
        table.addCell(cell2);
        table.addCell(cellVenue);
        table.addCell(cell3);
        table.addCell(cellTime);
        table.addCell(cell4);

        PdfPCell cellAttendees = new PdfPCell();
        List orderedAttendeesList = new List();
        for (Person person : attendeeList.getAttendees()) {
            orderedAttendeesList.add(new ListItem(person.getName().fullName));
        }

        cellAttendees.addElement(orderedAttendeesList);
        table.addCell(cellAttendees);
        Paragraph par = new Paragraph();
        par.setSpacingBefore(10);
        par.add(table);
        return par;
    }

    /**
     * Returns a {@code String} of report file name with date followed by description.
     */
    private static String generateFileName(String to, String from, LocalDateTime start, String desc) {
        String startTime = start.format(dateFormat);
        return to + "_" + from + "_" + startTime + "_" + desc.split(" ")[0] + ".pdf";
    }
}
