package seedu.address.model.timetable;

import java.util.HashMap;
import java.util.Map;

public class LessonTypeMapping {
    private static LessonTypeMapping mapping = null;
    private Map<String, String> lessonTypeMap;

    // Obtained data from "https://github.com/nusmodifications/nusmods/blob/8e76af2e407f602dcecab538804009b6de280196/website/src/utils/timetables.ts"
    private LessonTypeMapping() {
        lessonTypeMap = new HashMap<>();
        lessonTypeMap.put("LEC", "Lecture");
        lessonTypeMap.put("TUT", "Tutorial");
        lessonTypeMap.put("LAB", "Laboratory");
        lessonTypeMap.put("REC", "Recitation");
        lessonTypeMap.put("SEC", "Sectional Teaching");
        lessonTypeMap.put("DLEC", "Design Lecture");
        lessonTypeMap.put("PLEC", "Packaged Lecture");
        lessonTypeMap.put("PTUT", "Packaged Tutorial");
        lessonTypeMap.put("SEM", "Seminar-Style Module Class");
        lessonTypeMap.put("TUT2", "Tutorial Type 2");
        lessonTypeMap.put("TUT3", "Tutorial Type 3");
        lessonTypeMap.put("WS", "Workshop");
    }

    public String get(String key) {
        return lessonTypeMap.get(key);
    }

    // static method to create instance of Singleton class
    public static LessonTypeMapping getInstance() {
        if (mapping == null) {
            mapping = new LessonTypeMapping();
        }
        return mapping;
    }
}
