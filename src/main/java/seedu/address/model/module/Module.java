package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;

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
    private final List<Semester> semesterData = new ArrayList<>();


    public Module(JSONObject obj) {
        if (obj.containsKey("moduleCode")) {
            this.moduleCode = new ModuleCode(obj.get("moduleCode").toString());
        } else {
            this.moduleCode = new ModuleCode("");
        }

        if (obj.containsKey("title")) {
            this.title = new Title(obj.get("title").toString());
        } else {
            this.title = new Title("");
        }

        if (obj.containsKey("description")) {
            this.description = new Description(obj.get("description").toString());
        } else {
            this.description = new Description("");
        }

        if (obj.containsKey("acadYear")) {
            this.acadYear = new AcadYear(obj.get("acadYear").toString());
        } else {
            this.acadYear = new AcadYear("");
        }

        if (obj.containsKey("semesterData")) {
            JSONArray jsonSemesterData = (JSONArray) obj.get("semesterData");
            for (int i = 0; i < jsonSemesterData.size(); i++) {
                semesterData.add(new Semester((JSONObject) jsonSemesterData.get(i)));
            }
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
}
