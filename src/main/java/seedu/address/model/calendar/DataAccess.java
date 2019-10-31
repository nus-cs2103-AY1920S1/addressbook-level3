package seedu.address.model.calendar;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import seedu.address.logic.parser.exceptions.ParseException;

public class DataAccess {
    public static Calendar getCalendarFile(FilePath filePath)
            throws ParseException, FileNotFoundException {
        net.fortuna.ical4j.model.Calendar calendar = null;
        try {
            FileInputStream fin = new FileInputStream(filePath.getFilePath());
            CalendarBuilder builder = new CalendarBuilder();
            calendar = builder.build(fin);
        } catch (FileNotFoundException e) {
            //Error when .ics file cannot be found in filePath
            throw e;
        } catch (IOException e) {
            //Error when building Calendar with incorrect input stream
            throw new ParseException("IOException: Error occurred when parsing .ics file");
        } catch (ParserException e) {
            //Error when there is a parsing error when building Calendar
            throw new ParseException("ParserException: Error occurred when parsing .ics file");
        }
        return calendar;
    }
}
