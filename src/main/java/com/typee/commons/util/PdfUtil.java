package com.typee.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.logging.Logger;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.typee.commons.core.LogsCenter;
import com.typee.model.engagement.Appointment;
import com.typee.model.engagement.Engagement;
import com.typee.model.engagement.Interview;
import com.typee.model.engagement.Meeting;
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

        document = addIntroductionPar(document, engagement);
        document.add(createAttendeesTable(engagement));
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
        par.setSpacingAfter(28);
        doc.add(par);
        return doc;
    }

    private static void addCompanyLogo(Document doc) throws IOException, DocumentException {
        Image logo = Image.getInstance(PdfUtil.class.getClassLoader().getResource("images/company_logo_sample.png"));
        logo.scaleToFit(250, 100);
        logo.setAlignment(Element.ALIGN_LEFT);

        Paragraph par = new Paragraph();
        par.setSpacingAfter(60);
        par.add(logo);

        doc.add(logo);
    }

    private static Document addIntroductionPar(Document doc, Engagement engagement) throws DocumentException {
        Paragraph par = new Paragraph(doc_prop.getProperty("appointment.introduction"));
        par.setSpacingAfter(10);
        doc.add(par);
        return doc;
    }

    /**
     * Returns a {@code PdfPTable} for attendees section
     */
    private static Paragraph createAttendeesTable(Engagement engagement) {
        PdfPTable table = new PdfPTable(3);
        PdfPCell cell1 = new PdfPCell(new Paragraph("Cell 1"));
        PdfPCell cell2 = new PdfPCell(new Paragraph("Cell 2"));
        PdfPCell cell3 = new PdfPCell(new Paragraph("Cell 3"));

        table.addCell(cell1);
        table.addCell(cell2);
        table.addCell(cell3);

        Paragraph par = new Paragraph();
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
