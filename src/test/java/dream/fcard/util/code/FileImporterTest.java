package dream.fcard.util.code;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;

import org.junit.jupiter.api.Test;

class FileImporterTest {

    @Test
    void readJsFile_success() {
        BufferedReader br = FileImporter.readJsFile("C:\\Users\\User\\Documents\\GitHub"
                + "\\main\\src\\test\\java\\dream\\fcard\\util\\code\\testData.js");
        assertNotNull(br);

    }
}
