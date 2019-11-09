package com.typee.commons.util;

import static com.typee.testutil.TypicalReports.TYPICAL_REPORT;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import com.itextpdf.text.DocumentException;
import com.typee.logic.commands.exceptions.DeleteDocumentException;

class PdfUtilTest {
    public static final String DOC_TEST_NAME = "/Jason_Gihun_07-11-19_08-49_UNIT.pdf";
    public static final LocalDateTime START = LocalDateTime.of(2019, Month.NOVEMBER,
            7, 8, 49);

    @TempDir
    public static File tempDir;

    @BeforeEach
    void beforeEach() throws IOException {
        File file = new File(tempDir.getPath() + DOC_TEST_NAME);
        file.createNewFile();
    }

    @Test
    void generateReport_invalid() {
        assertThrows(IOException.class, () -> PdfUtil.generateReport(Paths.get("invalid/path/"),
                TYPICAL_REPORT));
    }

    @Test
    @Order(1)
    void checkIfDocumentExists() {
        assertTrue(PdfUtil.checkIfDocumentExists(tempDir.toPath(), "Jason", "Gihun", START, "UNIT"));
    }

    @Test
    @Order(2)
    void deleteDocument() throws DeleteDocumentException {
        PdfUtil.deleteDocument(tempDir.toPath(), "Jason", "Gihun", START, "UNIT");
    }

    @Test
    @Order(3)
    void checkIfDocumentExists_invalid() throws IOException, DocumentException {
        PdfUtil.generateReport(tempDir.toPath(), TYPICAL_REPORT);
        assertTrue(PdfUtil.checkIfDocumentExists(tempDir.toPath(), TYPICAL_REPORT.getTo().getName().fullName,
                TYPICAL_REPORT.getFrom().getName().fullName,
                TYPICAL_REPORT.getEngagement().getTimeSlot().getStartTime(),
                TYPICAL_REPORT.getEngagement().getDescription()));
    }
}
