package seedu.pluswork.model.calendar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;

import seedu.pluswork.logic.parser.exceptions.ParseException;

public class DataAccess {
    public static String getCalendarStorageFormat(FilePath filePath) throws FileNotFoundException, ParseException {
        try {
            FileInputStream fin = new FileInputStream(filePath.getFilePath());
            StringWriter writer = new StringWriter();
            IOUtils.copy(fin, writer, Charset.defaultCharset());
            String calendarStorageFormat = writer.toString();
            return calendarStorageFormat;
        } catch (IOException e) {
            throw new ParseException("Error occurred when converting FileInputStream to " +
                    "String using StringWriter and IOUtils");
        }
    }
}
