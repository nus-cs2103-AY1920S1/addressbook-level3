package dream.fcard.util.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import dream.fcard.logic.storage.StorageManager;
import dream.fcard.util.FileReadWrite;

class JavascriptRunnerTest {

    @Test
    void evaluateFromFile_success() throws IOException {
        String fileName = "testData.js";
        String path = StorageManager.getCodePath(fileName);
        StorageManager.writeCode(fileName, "s");
        //Path path = Paths.get("./src/test/java/dream/fcard/util/code/testData.js");
        // create file

        String output = JavascriptRunner.evaluateFromFile(path);
        //assertEquals(output, "undefined");
        // test

        FileReadWrite.delete(StorageManager.getCodePath(fileName));
        // cleanup
    }

    @Test
    void evaluateString() {
        String code = "const x = x => x + 5; x(4);";
        assertEquals("9", JavascriptRunner.evaluateString(code));
    }
}
