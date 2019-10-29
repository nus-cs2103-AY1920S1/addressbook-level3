package seedu.address.model.calendar;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.IOException;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import seedu.address.model.member.MemberName;

public class DataAccess {
    public static String calendarString = "BEGIN:VCALENDAR" +
            "PRODID:-//Ben Fortuna//iCal4j 1.0//EN" +
            "VERSION:2.0" +
            "CALSCALE:GREGORIAN" +
            "END:VCALENDAR";

    public static Calendar getCalendarFile(FilePath filePath) {
        net.fortuna.ical4j.model.Calendar calendar = null;
        try {
            FileInputStream fin = new FileInputStream(filePath.getFilePath());
            CalendarBuilder builder = new CalendarBuilder();
            calendar = builder.build(fin);
        } catch (FileNotFoundException e) {
            //Display Error for illegal filePath
            System.out.println(e.getClass().getSimpleName());
        } catch (IOException e) {
            //Display Error for attempting to build Calendar with incorrect inputstream
            System.out.println(e.getClass().getSimpleName());
            e.printStackTrace();
        } catch (ParserException e) {
            //Display Error for parsing error when building Calendar
            System.out.println(e.getClass().getSimpleName());
        }
        return calendar;
    }

    public static CalendarWrapper getCalendarFile(MemberName memberName, FilePath filePath) {
        return new CalendarWrapper(memberName, getCalendarFile(filePath));
    }
}
