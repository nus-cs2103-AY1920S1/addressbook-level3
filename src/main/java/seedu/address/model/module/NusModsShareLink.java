package seedu.address.model.module;

import static java.util.Map.entry;
import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.websocket.util.UrlUtil;

/**
 * Represents an NUSMods share link.
 */
public class NusModsShareLink {

    public static final String EXAMPLE = "https://nusmods.com/timetable/sem-1/share?CS2101=&CS2103T=LEC:G05&CS3230"
            + "=LEC:1,TUT:08&CS3243=TUT:07,LEC:1&GEQ1000=TUT:D17";

    public static final String MESSAGE_CONSTRAINTS = "An NUSMods share link should be of the following format:"
            + "https://nusmods.com/timetable/SEMESTER/share?MODULE_LESSON_PAIR\n"
            + "A MODULE_LESSON_PAIR should look like MODULE_CODE=LESSON_TYPE:LESSON_NUMBER.\n"
            + "An example link: " + EXAMPLE + "\n";

    public static final Map<String, String> SEMESTER_NUMBER_MAPPING = Map.ofEntries(
            entry("sem-1", "1"),
            entry("sem-2", "2"),
            entry("st-i", "3"),
            entry("st-ii", "4")
    );

    public static final String SEMESTER_REGEX = "(" + String.join("|", SEMESTER_NUMBER_MAPPING.keySet()) + ")";
    public static final String VALIDATION_REGEX = "^https://nusmods.com/timetable/"
            + SEMESTER_REGEX + "/share?(.+)$";

    public final String value;
    public final SemesterNo semesterNo;
    public final Map<ModuleCode, List<LessonNo>> moduleLessonsMap;

    public NusModsShareLink(String link) throws ParseException {
        requireNonNull(link);
        checkArgument(isValidUrl(link), MESSAGE_CONSTRAINTS);

        try {
            // parse semester number from link
            Pattern p = Pattern.compile(VALIDATION_REGEX);
            Matcher m = p.matcher(link);
            String semString = "";
            if (m.matches()) {
                semString = m.group(1);
            } else {
                throw new ParseException(NusModsShareLink.MESSAGE_CONSTRAINTS);
            }

            if (!SEMESTER_NUMBER_MAPPING.containsKey(semString)) {
                throw new ParseException(NusModsShareLink.MESSAGE_CONSTRAINTS);
            }
            SemesterNo semesterNo = new SemesterNo(SEMESTER_NUMBER_MAPPING.get(semString));

            // parse pairs of module code & lessons from query string
            URL url = new URL(link);
            Map<String, String> queryMap = UrlUtil.splitQuery(url);
            Map<ModuleCode, List<LessonNo>> moduleLessonsMap = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                ModuleCode moduleCode = ParserUtil.parseModuleCode(entry.getKey());
                String[] lessons = entry.getValue().split(",");
                List<LessonNo> lessonsNos = Arrays.stream(lessons)
                        .map(l -> l.split(":")[1])
                        .map(LessonNo::new)
                        .collect(Collectors.toList());

                moduleLessonsMap.put(moduleCode, lessonsNos);
            }

            value = link;
            this.semesterNo = semesterNo;
            this.moduleLessonsMap = moduleLessonsMap;
        } catch (UnsupportedEncodingException | MalformedURLException e) {
            throw new ParseException(e.getMessage(), e);
        }
    }

    /**
     * Returns if a given string is a valid NUSMods URL.
     */
    public static boolean isValidUrl(String link) {
        return link.matches(VALIDATION_REGEX);
    }
}
