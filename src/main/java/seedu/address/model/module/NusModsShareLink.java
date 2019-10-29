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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.exceptions.SemesterNoNotFoundException;
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

    public static final Set<String> SHORT_SEMESTER_NAMES = SemesterNo.getShortSemesterNames();

    public static final String SEMESTER_REGEX = "(" + String.join("|", SHORT_SEMESTER_NAMES) + ")";
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
            } else { //no semester in URL
                throw new ParseException(NusModsShareLink.MESSAGE_CONSTRAINTS);
            }

            SemesterNo semesterNo;
            try {
                semesterNo = SemesterNo.findSemesterNo(semString);
            } catch (SemesterNoNotFoundException e) { // semester in URL is invalid.
                throw new ParseException(NusModsShareLink.MESSAGE_CONSTRAINTS);
            }
            // parse pairs of module code & lessons from query string.
            URL url = new URL(link);
            Map<String, String> queryMap = UrlUtil.splitQuery(url);
            if (queryMap.isEmpty()) { // no key-value pairs found in query string.
                throw new ParseException(NusModsShareLink.MESSAGE_CONSTRAINTS);
            }
            Map<ModuleCode, List<LessonNo>> moduleLessonsMap = new LinkedHashMap<>();
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                ModuleCode moduleCode = ParserUtil.parseModuleCode(entry.getKey());
                if (StringUtil.isNullOrEmpty(entry.getValue())) {
                    // Skip if no value in pair, happens for combinations like CS2101/CS2103T, where CS2101
                    // has no lessons.
                    continue;
                }
                String[] lessons = entry.getValue().split(",");
                List<LessonNo> lessonsNos = Arrays.stream(lessons)
                        .map(l -> l.split(":")[1])
                        .map(LessonNo::new)
                        .collect(Collectors.toList());
                if (lessonsNos.isEmpty()) { // query value improperly formatted -> no potential lessonNos found.
                    throw new ParseException(NusModsShareLink.MESSAGE_CONSTRAINTS);
                }

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
