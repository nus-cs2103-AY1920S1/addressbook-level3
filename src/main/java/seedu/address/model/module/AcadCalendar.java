package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Contains start dates of each semester of each academic year.
 */
public class AcadCalendar {
    private Map<String, String> startDates;

    public AcadCalendar(Map<String, String> startDates) {
        this.startDates = startDates;
    }

    /**
     * Parse JSONObject to AcadCalendar
     * @param obj JSONObject
     * @return parsed AcadCalendar
     */
    public static AcadCalendar parseFromJson(JSONObject obj) {
        requireNonNull(obj);
        Map<String, String> startDates = new LinkedHashMap<>();

        for (Object acadYearObj : obj.keySet()) {
            AcadYear acadYear = new AcadYear(acadYearObj.toString());
            JSONObject semesterNoStartDateObj = (JSONObject) obj.get(acadYearObj.toString());

            for (Object semesterNoObj : semesterNoStartDateObj.keySet()) {
                JSONObject startDateObj = (JSONObject) semesterNoStartDateObj.get(semesterNoObj.toString());
                String acadYearSemNoString = acadYearObj.toString() + " Sem " + semesterNoObj.toString();

                JSONArray arr = (JSONArray) startDateObj.get("start");
                String startDate = arr.get(0) + "-" + arr.get(1) + "-" + arr.get(2);
                startDates.put(acadYearSemNoString, startDate);
            }
        }

        return new AcadCalendar(startDates);
    }

    public String getAcadSemesterStartDateString(AcadYear acadYear, SemesterNo semesterNo) {
        String acadYearSemNoString = acadYear.toString() + " Sem " + semesterNo.toString();
        return startDates.get(acadYearSemNoString);
    }

    public Map<String, String> getStartDates() {
        return startDates;
    }

    public String toString() {
        return startDates.toString();
    }
}

