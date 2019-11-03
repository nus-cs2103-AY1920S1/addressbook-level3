package seedu.address.logic.parser.statistics;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.parser.exceptions.ParseException;

public class ExcelParserTest {

    private static final String VALID_DATA =
            "src/test/data/SampleStatisticsData/ValidSampleStatistics.xlsx";
    private static final String NOT_EXCEL_DATA =
            "src/test/data/SampleStatisticsData/NotExcelStatistics.docx";
    private static final String WRONG_HEADER_DATA =
            "src/test/data/SampleStatisticsData/WrongHeaderStatistics.xlsx";
    private static final String MISSING_INPUT_DATA =
            "src/test/data/SampleStatisticsData/MissingInputStatistics.xlsx";
    private static final String INVALID_INPUT_DATA =
            "src/test/data/SampleStatisticsData/InvalidInputStatistics.xlsx";
    private static final String INVALID_STUDENTS_DATA =
            "src/test/data/SampleStatisticsData/InvalidStudentsStatistics.xlsx";
    private static final String EMPTY_SHEET =
            "src/test/data/SampleStatisticsData/EmptyExcelSheet.xlsx";
    private static final String NO_ENTRY =
            "src/test/data/SampleStatisticsData/NoEntryStatistics.xlsx";
    private static final String INVALID_FILE_PATH =
            "invalid file path";

    private DataParser parser = new ExcelParser();

    @Test
    public void parseCommand_validExcelData_success() throws Exception {
        HashMap<String, HashMap<String, Double>> data = parser.parseFile(VALID_DATA);
        assertTrue(data instanceof HashMap);
    }

    @Test
    public void parseCommand_noEntriesValidFile_success() throws Exception {
        HashMap<String, HashMap<String, Double>> data = parser.parseFile(NO_ENTRY);
        assertTrue(data instanceof HashMap);
    }

    @Test
    public void parseCommand_wrongHeader_throwsException() {
        assertThrows(ParseException.class, Messages.EXCEL_ILLEGAL_HEADER, () -> parser.parseFile(WRONG_HEADER_DATA));
    }

    @Test
    public void parseCommand_notExcelData_throwsParseException() {
        assertThrows(ParseException.class, Messages.EXCEL_FILE_TYPE_ISSUE, () -> parser.parseFile(NOT_EXCEL_DATA));
    }

    @Test
    public void parseCommand_missingData_throwsParseException() {
        assertThrows(ParseException.class, Messages.EXCEL_FILE_ILLEGAL_FORMAT, () ->
            parser.parseFile(MISSING_INPUT_DATA));
    }

    @Test
    public void parseCommand_invalidData_throwsParseException() {
        assertThrows(ParseException.class, Messages.EXCEL_FILE_ILLEGAL_INPUT, () ->
            parser.parseFile(INVALID_INPUT_DATA));
    }

    @Test
    public void parseCommand_missingStudents_throwsParseException() {
        assertThrows(ParseException.class, Messages.EXCEL_FILE_ILLEGAL_FORMAT, () ->
            parser.parseFile(INVALID_STUDENTS_DATA));
    }

    @Test
    public void parseCommand_missingFile_throwsParseException() {
        assertThrows(ParseException.class, Messages.EXCEL_FILE_NOT_FOUND, () -> parser.parseFile(INVALID_FILE_PATH));
    }

    @Test
    public void parseCommand_emptyFile_throwsParseException() {
        assertThrows(ParseException.class, Messages.EXCEL_FILE_ILLEGAL_FORMAT, () -> parser.parseFile(EMPTY_SHEET));
    }
}
