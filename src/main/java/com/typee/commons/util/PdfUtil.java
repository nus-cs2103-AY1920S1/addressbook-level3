package com.typee.commons.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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
    /**
     * Generates a {@code Report} in .pdf format and opens the file.
     */
    public static void generateReport(Report report) throws IOException, DocumentException {
        Engagement engagement = report.getEngagement();
        String fileName = FOLDER_PATH + generateFileName(engagement);
        if (engagement instanceof Appointment) {

        } else if (engagement instanceof Interview) {

        } else if (engagement instanceof Meeting) {

        }
        Document document = new Document();
        if (Files.notExists(Paths.get(FOLDER_PATH))) {
            Files.createFile(Files.createDirectories(Paths.get(FOLDER_PATH))).toFile();
        }
        PdfWriter.getInstance(document, new FileOutputStream(fileName));

        document.open();
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 14, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.add(createAttendeesTable(engagement));
        document.close();
    }

    /**
     * Returns a {@code PdfPTable} for attendees section
     */
    private static PdfPTable createAttendeesTable(Engagement engagement) {
        PdfPTable table = new PdfPTable(new float[] {2, 1, 2});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Attendees");
        table.setHeaderRows(1);
        for (Person p : engagement.getAttendees().getAttendees()) {

        }
        return table;
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
