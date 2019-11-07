package seedu.address.ics;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class IcsExporterTest {

    @Test
    public void getExportFileName_noArgumentsRequired_valueAsExpected() {
        String filename = IcsExporter.getExportFileName();
        assertNotEquals("", filename);
        assertTrue(filename.endsWith(".ics"));
        assertTrue(filename.startsWith("Horo_export_"));
    }
}
