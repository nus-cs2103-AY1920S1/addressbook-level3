package seedu.address.model.timetable;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

public class LessonTypeMapping {
    private static LessonTypeMapping mapping = null;
    private BiMap<String, String> lessonTypeMap;

    // private constructor restricted to this class itself
    private LessonTypeMapping() {
        lessonTypeMap = HashBiMap.create();
        lessonTypeMap.put("LEC", "Lecture");
        lessonTypeMap.put("TUT", "Tutorial");
        lessonTypeMap.put("LAB", "Laboratory");
        lessonTypeMap.put("REC", "Recitation");
        lessonTypeMap.put("SEC", "Sectional Teaching");
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
