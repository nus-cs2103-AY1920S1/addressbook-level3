package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import seedu.address.model.person.schedule.Event;

/**
 * The module details
 */
public class Module {
    private final ModuleCode moduleCode;
    private final Title title;
    private final Description description;
    private final AcadYear acadYear;
    private final List<Semester> semesterData = new ArrayList<>();


    public Module(JSONObject obj) {
        this.moduleCode = new ModuleCode(obj.get("moduleCode").toString());
        this.title = new Title(obj.get("title").toString());
        this.description = new Description(obj.get("description").toString());
        this.acadYear = new AcadYear(obj.get("acadYear").toString());

        JSONArray jsonSemesterData = (JSONArray) obj.get("semesterData");
        for (int i = 0; i < jsonSemesterData.size(); i++) {
            semesterData.add(new Semester((JSONObject) jsonSemesterData.get(i)));
        }
    }

    public Module(ModuleCode moduleCode, Title title, Description description,
                  AcadYear acadYear, List<Semester> semesterData) {
        this.moduleCode = moduleCode;
        this.title = title;
        this.description = description;
        this.acadYear = acadYear;
        this.semesterData.addAll(semesterData);
    }

    public Title getTitle() {
        return title;
    }

    public Description getDescription() {
        return description;
    }

    public ModuleCode getModuleCode() {
        return moduleCode;
    }

    public AcadYear getAcadYear() {
        return acadYear;
    }

    public List<Semester> getSemesterData() {
        return semesterData;
    }

    public Semester getSemester(SemesterNo semesterNo) {
        for (int i = 0; i < semesterData.size(); i++) {
            if (semesterData.get(i).getSemesterNo().equals(semesterNo)) {
                return semesterData.get(i);
            }
        }
        System.out.println("Error: No such semesterNo found");
        return null;
    }

    @Override
    public String toString() {
        String result = moduleCode + " " + title;
        return result;
    }

    /**
     * Converts a {@code Module} to an {@code Event}.
     * @return an Event translated from a Module.
     */
    public Event toEvent(SemesterNo semesterNo, List<String> lessonNos) {

        Event event = new Event(moduleCode.toString());

        for (String lessonNo : lessonNos) {
            ArrayList<Lesson> lessons = this.getSemester(semesterNo).findLessons(lessonNo);
        }

        // TODO: convert lessons to event timeslots
        // See https://github.com/nusmodifications/nusmods/blob/master/website/src/data/academic-calendar.json
        //     https://github.com/nusmodifications/nusmods/blob/master/website/src/data/holidays.json

        return event;
    }
}
