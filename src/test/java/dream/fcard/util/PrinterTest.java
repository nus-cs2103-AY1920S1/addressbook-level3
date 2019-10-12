package dream.fcard.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class PrinterTest {
    @Test
    void testSurroundQuotes() {
        assertEquals("\"asd\"", Printer.surroundQuotes("asd"));
    }

    @Test
    void testIndentString() {
        assertEquals("    asd\n", Printer.indentString("asd"));
        assertEquals("    asd\n    123\n", Printer.indentString("asd\n123"));
    }

    @Test
    void testRepeatChar() {
        assertEquals("111", Printer.repeatChar(3, '1'));
        assertEquals("   ", Printer.repeatChar(3, ' '));
        assertEquals("", Printer.repeatChar(0, ' '));
    }
}
