package seedu.address.logic.parser.statistics;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import seedu.address.logic.commands.note.NoteAddCommand;
import seedu.address.logic.commands.statistics.StatisticsAddCommand;
import seedu.address.logic.commands.statistics.StatisticsCommand;
import seedu.address.logic.parser.*;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.statistics.Statistics;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class StatisticsCommandParser implements Parser<StatisticsCommand> {

    private HashMap<String, HashMap<String, Double>> data = new HashMap<>();

    /**
     * Parses the given {@code String} of arguments in the context of the AddNoteCommand
     * and returns an NoteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public StatisticsCommand parse(String args) throws ParseException {
        requireNonNull(args);

        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, CliSyntax.PREFIX_METHOD, CliSyntax.PREFIX_FILEPATH);

        if (!arePrefixesPresent(argMultimap, CliSyntax.PREFIX_METHOD, CliSyntax.PREFIX_FILEPATH)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String
                            .format(MESSAGE_INVALID_COMMAND_FORMAT, NoteAddCommand.MESSAGE_USAGE));
        }

        String method = argMultimap.getValue(CliSyntax.PREFIX_METHOD).orElse("");
        String filePath = argMultimap.getValue(CliSyntax.PREFIX_FILEPATH).orElse("");

        getExcel(method, filePath);

        return new StatisticsAddCommand(new Statistics(data));
    }

    public void getExcel(String method, String filePath) throws ParseException {
        try
        {
            FileInputStream file = new FileInputStream(new File(filePath));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();
            ArrayList<String> students = new ArrayList<>();
            if (rowIterator.hasNext()) {
                Row studentsRow = rowIterator.next();
                Iterator<Cell> studentsIterator = studentsRow.cellIterator();
                studentsIterator.next();
                while (studentsIterator.hasNext()) {
                    Cell studentCell = studentsIterator.next();
                    if (studentCell.getCellType() == CellType.STRING) {
                        students.add(studentCell.getStringCellValue());
                    }
                    else if (studentCell.getCellType() == CellType.NUMERIC) {
                        students.add(String.valueOf(studentCell.getNumericCellValue()));
                    }
                }
            }

            //Iterate through each rows one by one
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                Cell subjectCell = cellIterator.next();
                String subject;
                if (subjectCell.getCellType() == CellType.STRING) {
                    subject = subjectCell.getStringCellValue();
                } else if (subjectCell.getCellType() == CellType.NUMERIC) {
                    subject = String.valueOf(subjectCell.getNumericCellValue());
                } else {
                    throw new ParseException("Excel file format has illegal values");
                }
                for (int i = 0; i < students.size(); i++) {
                    Cell cell = cellIterator.next();
                    if (cell.getCellType() == CellType.STRING) {
                        throw new ParseException("Grade must be a numeric value, not string or text");
                    }
                    else if (cell.getCellType() == CellType.NUMERIC) {
                        if (data.containsKey(students.get(i))) {
                            data.get(students.get(i)).put(subject, cell.getNumericCellValue());
                        }
                        else {
                            HashMap<String, Double> subjectToScore = new HashMap<>();
                            subjectToScore.put(subject, cell.getNumericCellValue());
                            data.put(students.get(i), subjectToScore);
                        }
                    }
                    else {
                        throw new ParseException("Excel file has illegal values");
                    }
                }
            }
            file.close();
        }
        catch (Exception e)
        {
            throw new ParseException("Error parsing excel file. Refer to user guide on file format.");
        }
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes)
                .allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
