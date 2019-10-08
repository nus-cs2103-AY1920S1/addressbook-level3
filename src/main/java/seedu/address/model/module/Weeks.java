package seedu.address.model.module;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Weeks of the Lesson.
 */
public class Weeks {
    private List<Integer> weekNumbers = new ArrayList<>();
    private String startDateString;
    private String endDateString;
    private int weekInterval;
    private int type; //TODO: convert to enum

    //TODO: return placeholder if data incomplete/missing
    public Weeks(Object obj) {
        requireNonNull(obj);

        // weekNumbers only format
        if (obj.toString().startsWith("[")) {
            JSONArray ja = (JSONArray) obj;
            for (int i = 0; i < ja.size(); i++) {
                this.weekNumbers.add(Integer.parseInt(ja.get(i).toString()));
            }
            type = 1;
        } else {
            assert true : obj.toString().startsWith("{");
            JSONObject jo = (JSONObject) obj;
            startDateString = jo.get("start").toString();
            endDateString = jo.get("end").toString();

            // start, end and weekNumbers format
            if (jo.containsKey("weeks")) {
                JSONArray ja = (JSONArray) jo.get("weeks");
                for (int i = 0; i < ja.size(); i++) {
                    this.weekNumbers.add(Integer.parseInt(ja.get(i).toString()));
                }
                type = 2;
            } else { // start, end and weekInterval format
                assert true : jo.containsKey("weekInterval");
                weekInterval = Integer.parseInt(jo.get("weekInterval").toString());
                type = 3;
            }
        }
    }

    public List<Integer> getWeekNumbers() {
        return weekNumbers;
    }

    public void setWeekNumbers(List<Integer> weekNumbers) {
        this.weekNumbers = weekNumbers;
    }

    public String getStartDateString() {
        return startDateString;
    }

    public void setStartDateString(String startDateString) {
        this.startDateString = startDateString;
    }

    public String getEndDateString() {
        return endDateString;
    }

    public void setEndDateString(String endDateString) {
        this.endDateString = endDateString;
    }

    public int getWeekInterval() {
        return weekInterval;
    }

    public void setWeekInterval(int weekInterval) {
        this.weekInterval = weekInterval;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return weekNumbers.toString();
    }
}
