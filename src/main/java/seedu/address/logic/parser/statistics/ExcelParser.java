package seedu.address.logic.parser.statistics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * A data parser that parses formatted excel(.xlsx) files containing students score data.
 */
public class ExcelParser implements DataParser {

    private static final String EXCEL_FILE_NOT_FOUND = "Excel file was not found. Please ensure file path is valid.";
    private static final String EXCEL_FILE_NOT_PARSED = "Error occurred retrieving file. Please try with another file";
    private static final String EXCEL_FILE_TYPE_ISSUE = "File path must be of type /'.xlsx/'. Please try again.";
    private static final String EXCEL_FILE_ILLEGAL_INPUT = "File has illegal input. Please refer to user guide.";
    private static final String EXCEL_FILE_ILLEGAL_FORMAT = "File has illegal format. PLease refer to user guide.";

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
    private void populateData(Iterator<Row> rowIterator, ArrayList<String> students)
            throws ParseException {

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
            } catch (NullPointerException ex) {
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
    private ArrayList<String> getStudents(Iterator<Row> rowIterator) {
        ArrayList<String> students = new ArrayList<>();
        if (rowIterator.hasNext()) {
            Row studentsRow = rowIterator.next();
            Iterator<Cell> studentsIterator = studentsRow.cellIterator();
            studentsIterator.next();
            while (studentsIterator.hasNext()) {
                Cell studentCell = studentsIterator.next();
                if (studentCell.getCellType() == CellType.STRING) {
                    students.add(studentCell.getStringCellValue());
                } else if (studentCell.getCellType() == CellType.NUMERIC) {
                    students.add(String.valueOf(studentCell.getNumericCellValue()));
                }
            }
        }
        return students;
    }

    /**
     * Gets the excel sheet from filePath specified.
     * @param filePath the relative or full filePath of file in concern.
     * @return the excel sheet read from the filePath.
     * @throws ParseException when error occurs retrieving the file.
     */
    private XSSFSheet getSheet(String filePath) throws ParseException {
        try {
            file = new FileInputStream(new File(filePath));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            return sheet;
        } catch (FileNotFoundException ex) {
            throw new ParseException(EXCEL_FILE_NOT_FOUND);
        } catch (IOException ex) {
            throw new ParseException(EXCEL_FILE_NOT_PARSED);
        } catch (NotOfficeXmlFileException ex) {
            throw new ParseException(EXCEL_FILE_TYPE_ISSUE);
        }
    }

}
