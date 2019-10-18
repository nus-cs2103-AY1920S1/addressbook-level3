package dream.fcard.util.code;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class JavaRunnerTest {

    @Test
    void compileAndRun() throws IOException {
        String code = "package dream.fcard.util.data;\n"
                + "\n"
                + "public class Test {\n"
                + "    public static void main(String[] args) {\n"
                + "        System.out.println(\"Hello from JavaRunnerTest\");\n"
                + "    }\n"
                + "}\n";
        File file = new File(".\\src\\test"
                + "\\java\\dream\\fcard\\util\\data\\Test.java");
        FileWriter fw = new FileWriter(file);
        fw.write(code);
        fw.close();
        String result = JavaRunner.compileAndRun(".\\src\\test"
                + "\\java\\dream\\fcard\\util\\data\\Test.java");
        assertEquals("Hello from JavaRunnerTest\n", result);
    }
}
