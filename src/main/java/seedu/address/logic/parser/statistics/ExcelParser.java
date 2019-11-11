package seedu.address.logic.parser.statistics;

import static seedu.address.commons.core.Messages.EXCEL_FILE_ILLEGAL_FORMAT;
import static seedu.address.commons.core.Messages.EXCEL_FILE_ILLEGAL_INPUT;
import static seedu.address.commons.core.Messages.EXCEL_FILE_NOT_FOUND;
import static seedu.address.commons.core.Messages.EXCEL_FILE_NOT_PARSED;
import static seedu.address.commons.core.Messages.EXCEL_FILE_TYPE_ISSUE;
import static seedu.address.commons.core.Messages.EXCEL_ILLEGAL_HEADER;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

import org.apache.poi.ooxml.POIXMLException;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.parser.quiz.QuizCommandParser;

/**
 * A data parser that parses formatted excel(.xlsx) files containing students score data.
 */
public class ExcelParser implements DataParser {

    private static final Logger logger = LogsCenter.getLogger(ExcelParser.class);

    private FileInputStream file;
    private HashMap<String, HashMap<String, Double>> data;

    public ExcelParser() {
        data = new HashMap<>();
    }

    /**
     * Returns a processed data from file specified by input path and throws ParseException when error occurs.
     * @param filePath file path of relevant data file.
     * @return a HashMap object mapping the students' name to a the key-value pairs of subject-scores.
     * @throws ParseException when error occurs while loading or parsing the file.
     */
    @Override
    public HashMap<String, HashMap<String, Double>> parseFile(String filePath) throws ParseException {
        try {
            XSSFSheet sheet = getSheet(filePath);
            Iterator<Row> rowIterator = sheet.iterator();
            ArrayList<String> students = getStudents(rowIterator);
            populateData(rowIterator, students);
            file.close();
        } catch (IOException ex) {
            logger.info("Error parsing excel input file");
            throw new ParseException(EXCEL_FILE_NOT_PARSED);
        }
        return data;
    }

    /**
     * Populates the data inside ExcelParser object with data using iterators.
     * @param rowIterator iterator for excel rows.
     * @param students the arraylist containing strings representing students inside the file in concern.
     * @throws ParseException when error occurs parsing the file.
     */
    private void populateData(Iterator<Row> rowIterator, ArrayList<String> students) throws ParseException {
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            String subject = readNextCell(cellIterator);
            storeSubjectScores(cellIterator, subject, students);
        }
    }

    /**
     * For each subject specified in the file, store the mappings of students and subject-scores inside ExcelParser's
     * internal data structure.
     */
    private void storeSubjectScores(Iterator<Cell> cellIterator, String subject, ArrayList<String> students)
            throws ParseException {

        for (int i = 0; i < students.size(); i++) {
            try {
                Cell cell = cellIterator.next();
                if (cell.getCellType() == CellType.NUMERIC) {
                    allocateEntryToStudent(cell.getNumericCellValue(), students.get(i), subject);
                } else {
                    throw new ParseException(EXCEL_FILE_ILLEGAL_INPUT);
                }
            } catch (NullPointerException | NoSuchElementException ex) {
                logger.info("Excel input file has wrong format");
                throw new ParseException(EXCEL_FILE_ILLEGAL_FORMAT);
            }
        }
    }

    /**
     * Allocates a particular score, student and subject to the internal data structure of ExcelParser.
     */
    private void allocateEntryToStudent(double cellValue, String student, String subject) {
        if (data.containsKey(student)) {
            data.get(student).put(subject, cellValue);
        } else {
            HashMap<String, Double> subjectToScore = new HashMap<>();
            subjectToScore.put(subject, cellValue);
            data.put(student, subjectToScore);
        }
    }

    /**
     * Returns the content of an excel cell in string representation.
     * @param cellIterator iterates through cells of a row.
     * @return cell content that cellIterator has iterated over.
     * @throws ParseException when the cell value type is illegal.
     */
    private String readNextCell(Iterator<Cell> cellIterator) throws ParseException {
        Cell subjectCell = cellIterator.next();
        if (subjectCell.getCellType() == CellType.STRING) {
            return subjectCell.getStringCellValue().trim();
        } else if (subjectCell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(subjectCell.getNumericCellValue());
        } else {
            throw new ParseException(EXCEL_FILE_ILLEGAL_INPUT);
        }
    }

    /**
     * Gets the students in the data file.
     * @param rowIterator iterates through a row of an excel sheet.
     * @return the list of student names
     */
    private ArrayList<String> getStudents(Iterator<Row> rowIterator) throws ParseException {
        ArrayList<String> students = new ArrayList<>();
        if (rowIterator.hasNext()) {
            Row studentsRow = rowIterator.next();
            Iterator<Cell> studentsIterator = studentsRow.cellIterator();
            validateDataStart(studentsIterator);
            while (studentsIterator.hasNext()) {
                Cell studentCell = studentsIterator.next();
                if (studentCell.getCellType() == CellType.STRING
                        && !studentCell.getStringCellValue().trim().isEmpty()) {
                    students.add(studentCell.getStringCellValue());
                } else if (studentCell.getCellType() == CellType.NUMERIC) {
                    students.add(String.valueOf(studentCell.getNumericCellValue()));
                } else {
                    logger.info("Excel input file has wrong format");
                    throw new ParseException(EXCEL_FILE_ILLEGAL_FORMAT);
                }
            }
        } else {
            logger.info("Excel input file has wrong format");
            throw new ParseException(EXCEL_FILE_ILLEGAL_FORMAT);
        }
        return students;
    }

    /**
     * Ensures that the first Data Cell starts with "Students" in the input file.
     * @param studentsIterator the cell iterator for the data file.
     * @throws ParseException if the iterator does not find any cell or content of cell is not as specified.
     */
    private void validateDataStart(Iterator<Cell> studentsIterator) throws ParseException {
        if (studentsIterator.hasNext()) {
            Cell dataStart = studentsIterator.next();
            if (dataStart.getCellType() != CellType.STRING
                    || !dataStart.getStringCellValue().trim().equals("Students")) {
                logger.info("Excel input file has illegal headers");
                throw new ParseException(EXCEL_ILLEGAL_HEADER);
            }
        } else {
            logger.info("Excel input file has wrong format");
            throw new ParseException(EXCEL_FILE_ILLEGAL_FORMAT);
        }
    }

    /**
     * Gets the excel sheet from filePath specified.
     * @param filePath the relative or full filePath of file in concern.
     * @return the excel sheet read from the filePath.
     * @throws ParseException when error occurs retrieving the file.
     */
    private XSSFSheet getSheet(String filePath) throws ParseException {
        try {
            if (!filePath.trim().endsWith(".xlsx")) {
                throw new NotOfficeXmlFileException(filePath);
            }
            file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            return sheet;
        } catch (FileNotFoundException ex) {
            logger.info("Input file is not found");
            throw new ParseException(EXCEL_FILE_NOT_FOUND);
        } catch (IOException ex) {
            logger.info("Input file is not parsed, system error");
            throw new ParseException(EXCEL_FILE_NOT_PARSED);
        } catch (NotOfficeXmlFileException | POIXMLException ex) {
            logger.info("Input file is not supported. Try excel files with .xlsx extension");
            throw new ParseException(EXCEL_FILE_TYPE_ISSUE);
        }
    }

}
