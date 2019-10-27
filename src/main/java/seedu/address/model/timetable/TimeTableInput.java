package seedu.address.model.timetable;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class TimeTableInput {
    public TimeTable getTabletableFromFilepath(String absoluteFilepath) throws IOException, ParseException {
        String content = new Scanner(new File(absoluteFilepath)).useDelimiter("\\Z").next();
        return ParserUtil.parseTimeTable(content);
    }
}
