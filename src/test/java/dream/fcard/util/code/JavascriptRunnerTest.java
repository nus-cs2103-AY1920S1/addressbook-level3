package dream.fcard.util.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

class JavascriptRunnerTest {

    @Test
    void evaluateFromFile_success() throws IOException {
        Path path = Paths.get("./src/test/java/dream/fcard/util/code/testData.js");
        String output = JavascriptRunner.evaluateFromFile(path.toString());
        System.out.println(output);
        assertEquals(output, "undefined");
    }

    @Test
    void evaluateString() {
        String code = "const x = x => x + 5; x(4);";
        assertEquals("9", JavascriptRunner.evaluateString(code));
    }
}
