package seedu.address.model.module;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.module.exceptions.LessonTypeNotFoundException;

/**
 * Lesson type of the Lesson.
 * See: https://github.com/nusmodifications/nusmods/blob/master/website/src/utils/timetables.ts#L55
 */
public enum LessonType {
    DESIGN_LECTURE("Design Lecture", "DLEC"),
    LABORATORY("Laboratory", "LAB"),
    LECTURE("Lecture", "LEC"),
    PACKAGED_LECTURE("Packaged Lecture", "PLEC"),
    PACKAGED_TUTORIAL("Packaged Tutorial", "PTUT"),
    RECITATION("Recitation", "REC"),
    SECTIONAL_TEACHING("Sectional Teaching", "SEC"),
    SEMINAR_STYLE_MODULE_CLASS("Seminar-Style Module Class", "SEM"),
    TUTORIAL("Tutorial", "TUT"),
    TUTORIAL_TYPE_2("Tutorial Type 2", "TUT2"),
    TUTORIAL_TYPE_3("Tutorial Type 3", "TUT3"),
    WORKSHOP("Workshop", "WS");

    private final String lessonType;
    private final String abbreviation;

    LessonType(String lessonType, String abbreviation) {
        this.lessonType = lessonType;
        this.abbreviation = abbreviation;
    }

    public String lessonType() {
        return lessonType;
    }

    public String abbreviation() {
        return abbreviation;
    }

    /**
     * Find the LessonType whose lessonType or abbreviation matches the input string.
     * @param stringToMatch input string to match.
     * @return LessonType matched.
     */
    public static LessonType findLessonType(String stringToMatch) throws LessonTypeNotFoundException {
        for (LessonType lessonType : LessonType.values()) {
            if (lessonType.lessonType.equals(stringToMatch) || lessonType.abbreviation.equals(stringToMatch)) {
                return lessonType;
            }
        }
        throw new LessonTypeNotFoundException();
    }

    /**
     * Get all lesson type abbreviations.
     */
    public static Set<String> getAbbreviations() {
        Set<String> abbreviations = new HashSet<>();
        for (LessonType lessonType : LessonType.values()) {
            abbreviations.add(lessonType.abbreviation);
        }
        return abbreviations;
    }
}
