package seedu.address.ics;

import seedu.address.model.events.DateTime;
import seedu.address.model.events.EventSource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.TimeZone;

/***
 * Parses a .ics file to allow importing into Horo.
 */
public class IcsParser {

    private static final String FILE_DOES_NOT_EXIST = "Sorry, the file path you've specified is invalid!";
    private static final String FILE_CANNOT_BE_READ = "Sorry, the file specified cannot be read!";
    private static final String FILE_CANNOT_BE_FOUND = "Sorry, the file specified cannot be found!";
    private static final String INVALID_FILE_EXTENSION = "The file specified is not a .ics file!";
    private static final String FILE_IS_CORRUPTED = "The ICS file is corrupted!";

    private File icsFile;

    private IcsParser(String path) throws IcsException {
        this.icsFile = getIcsFile(path);
    }

    private File getIcsFile(String path) throws IcsException {
        File file = new File(path);
        if (!file.exists()) {
            throw new IcsException(FILE_DOES_NOT_EXIST);
        } else if (!file.canRead()) {
            throw new IcsException(FILE_CANNOT_BE_READ);
        } else if (!file.toString().endsWith(".ics")) {
            throw new IcsException(INVALID_FILE_EXTENSION);
        }
        return file;
    }

    public static IcsParser parse(String path) throws IcsException {
        return new IcsParser(path);
    }

    public String getEvents() throws IcsException {
        try {
            boolean isCreatingEvent = false;
            BufferedReader br = new BufferedReader(new FileReader(icsFile));
            StringBuilder stringBuilder = new StringBuilder("");
            ArrayList<EventSource> eventSources = new ArrayList<>();
            while (br.ready()) {
                String line = br.readLine();
                if (isCreatingEvent) {
                    if (line.startsWith("END:VEVENT")) {
                        isCreatingEvent = false;
                        EventSource eventSource = createEvent(stringBuilder.toString());
                        eventSources.add(eventSource);
                    } else {
                        stringBuilder.append(line).append("\n");
                    }
                } else {
                    switch (line) {
                        case "BEGIN:VEVENT":
                            if (isCreatingEvent) {
                                throw new IcsException(FILE_IS_CORRUPTED);
                            } else {
                                isCreatingEvent = true;
                            }
                            stringBuilder = new StringBuilder("");
                            break;
                    }
                }
            }
            for (EventSource e : eventSources) {
                System.out.println(e);
            }
            return stringBuilder.toString();
        } catch (FileNotFoundException e) {
            throw new IcsException(FILE_CANNOT_BE_FOUND);
        } catch (IOException e) {
            throw new IcsException(FILE_CANNOT_BE_READ);
        }
    }

    private DateTime parseTimeStamp(String timestamp) throws IcsException {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return new DateTime(sdf.parse(timestamp).toInstant());
        } catch (ParseException e) {
            throw new IcsException("The timestamp provided is invalid!");
        }
    }

    public EventSource createEvent(String segment) throws IOException, IcsException {
        String[] lines = segment.split("\\r?\\n");
        String description = "";
        DateTime dateTime = null;
        for (String line : lines) {
            if (line.startsWith("DESCRIPTION:")) {
                description = line.replaceFirst("DESCRIPTION:", "");
            } else if (line.startsWith("DTSTART:")) {
                String timestamp = line.replaceFirst("DTSTART:", "");
                dateTime = parseTimeStamp(timestamp);
            } else if (line.equals("END:VCALENDAR")) {
                if (description.equals("") || dateTime == null) {
                    throw new IcsException(FILE_IS_CORRUPTED);
                }
            }
        }
        if (description.equals("")) {
            throw new IcsException("The description of an event cannot be empty!");
        }
        return new EventSource(description, dateTime);
    }
}
