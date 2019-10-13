package dream.fcard.util.code;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.BufferedReader;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class FileImporterTest {

    @Test
    void readJsFile_success() {
        Path path = Paths.get(".\\src\\test\\java\\dream\\fcard\\util\\code\\testData.js");
        BufferedReader br = FileImporter.readJsFile(path.toString());
        assertNotNull(br);

    }
}
