package dream.fcard.util.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class CodeProcessingTest {

    @Test
    void evaluateFromFile_success() {
        String output = CodeProcessing.evaluateFromFile("C:\\Users\\User\\Documents\\GitHub"
                + "\\main\\src\\test\\java\\dream\\fcard\\util\\code\\testData.js");
        System.out.println(output);
        assertEquals(output, "undefined");
    }
}
