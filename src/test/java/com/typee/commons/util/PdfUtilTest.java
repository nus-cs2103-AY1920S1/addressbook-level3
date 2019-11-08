package com.typee.commons.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

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
    void generateReport() {

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
}
