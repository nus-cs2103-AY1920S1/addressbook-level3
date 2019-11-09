package seedu.address.model.timetable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TimetableInput {
    public Timetable getTimetableFromFilepath(String absoluteFilepath) throws IOException, ParseException {
        String content = new Scanner(new File(absoluteFilepath)).useDelimiter("\\Z").next();
        return ParserUtil.parseTimetable(content);
    }

    /**
     * Return retreived timetable from NUSMods
     * @param url URL of NUSMods shared timetable
     * @return Retrieved {@code Timetable}
     * @throws IOException URL parsing error
     * @throws IllegalValueException Cannot find lesson grouping on NUSMods
     */
    public Timetable getTimetableFromUrl(URL url) throws IOException, IllegalValueException {
        String urlString = url.toString();
        int semMatch = urlString.indexOf("sem-");
        int sem = Integer.parseInt(urlString.substring(semMatch + 4, semMatch + 5));

        List<TimeRange> timeRanges = new ArrayList<>();

        int start = urlString.indexOf("share?");
        String relevantInfo = urlString.substring(start + 6);

        String[] splitted = relevantInfo.split("&");
        for (String moduleString : splitted) {
            String[] moduleCodePartition = moduleString.split("=");
            if (moduleCodePartition.length < 2) {
                continue;
            }

            String moduleCode = moduleCodePartition[0].toUpperCase();
            String rest = moduleCodePartition[1];

            List<String> lessonTypes = new ArrayList<>();
            List<String> groups = new ArrayList<>();

            String[] timings = rest.split(",");
            for (String timing : timings) {
                String[] typeAndGroup = timing.split(":");
                lessonTypes.add(LessonTypeMapping.getInstance().get(typeAndGroup[0]));
                groups.add(typeAndGroup[1]);
            }

            timeRanges.addAll(getTimeRangesForModule(moduleCode, groups, lessonTypes, sem));
        }

        return new Timetable(timeRanges);
    }
    /**
     * Send HTTP GET request here, return list of timeRange for that particular module.
     * @param moduleCode e.g. CS1101S
     * @param groups List of groups. Each index of list must correspond with that of lessonTypes.
     * @param lessonTypes List of lessonTypes. Can be found on left side of mapping in {@code LessonTypeMapping.java}. Each index of list must correspond with that of groups.
     * @param sem e.g. 1 or 2
     */
    public List<TimeRange> getTimeRangesForModule(String moduleCode, List<String> groups, List<String> lessonTypes, int sem) throws IOException, IllegalValueException {
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = new URL("https://api.nusmods.com/v2/2019-2020/modules/" + moduleCode.toUpperCase() + ".json");
        JsonNode root = objectMapper.readTree(url);
        List<TimeRange> timeRanges = new ArrayList<>();
        for (int i = 0; i < groups.size(); i++) {
            timeRanges.addAll(getTimeRangeFromEntry(root, groups.get(i), lessonTypes.get(i), sem, moduleCode.toUpperCase()));
        }
        return timeRanges;
    }

    /**
     * Given JSON data, get time ranges for a certain class group. Possible to have more than 1 time range for a single combination of class group and lesson type of a module.
     * @param moduleNode Slice of JsonNode for that particular module.
     * @param group Class group. e.g. 8
     * @param lessonType Type of lession. Can be found on left side of mapping in {@code LessonTypeMapping.java}
     * @param sem either 1 or 2
     */
    public List<TimeRange> getTimeRangeFromEntry(JsonNode moduleNode, String group, String lessonType, int sem, String moduleCode) throws IllegalValueException, JsonProcessingException {
        List<JsonNode> targets = new ArrayList<>();
        moduleNode.path("semesterData")
                .path(sem - 1) // Sem 1
                .path("timetable")
                .forEach(node -> {
                    if (node.path("classNo").asText().equals(group) && node.path("lessonType").asText().equals(lessonType)) {
                        targets.add(node);
                    }
                });
        if (targets.size() < 1) {
            // System.out.println(String.format("Group:%s,LessonType:%s,sem:%d", group, lessonType, sem));
            throw new IllegalValueException("No such lesson exists: " + String.format("Module: %s, Group: %s, LessonType: %s, Sem: %d", moduleCode, group, lessonType, sem));
        }

        // Possible to have more than
        List<TimeRange> timeRanges = new ArrayList<>();
        for (JsonNode lesson : targets) {
            String day = lesson.path("day").asText().toUpperCase();
            String startTime = lesson.path("startTime").asText();
            String endTime = lesson.path("endTime").asText();
            timeRanges.add(ParserUtil.parseTimeRange(day + " " + startTime + " " + day + " " + endTime));
        }
        return timeRanges;
    }
}
