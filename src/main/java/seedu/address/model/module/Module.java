package seedu.address.model.module;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * The module details
 */
public class Module {
    private final ModuleCode moduleCode;
    private final Title title;
    private final Description description;
    private final AcadYear acadYear;
    private final SemesterData semesterData;


    public Module(JSONObject obj) {
        this.moduleCode = new ModuleCode(obj.get("moduleCode").toString());
        this.title = new Title(obj.get("title").toString());
        this.description = new Description(obj.get("description").toString());
        this.acadYear = new AcadYear(obj.get("acadYear").toString());
        this.semesterData = new SemesterData((JSONArray) obj.get("semesterData"));
    }

    public Description getDescription() {
        return description;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public Timetable getTimeTable(String semesterNo, String classNo) {
        Timetable timetable = semesterData.getSemester(semesterNo).getTimetable(classNo);
        return timetable;
    }

    public Semester getSemester(String semesterNo) {
        Semester semester = semesterData.getSemester(semesterNo);
        return semester;
    }
}
