package seedu.address.model.performanceoverview;

import seedu.address.model.person.Performance;
import seedu.address.model.person.Person;
import seedu.address.model.project.Meeting;
import seedu.address.model.project.Project;
import seedu.address.model.project.Task;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class PerformanceOverview {
    private final List<Person> memberList = new ArrayList<>();
    private final List<Performance> memberPerformanceList = new ArrayList<>();
    private final Project project;
    private final int numOfMeetings;
    private final int numOfTasks;
    private final HashMap<String, RateOfAttendance> attendanceRateMap = new HashMap<>();
    private final HashMap<String, Integer> attendanceMap = new HashMap<>();
    private final HashMap<String, RateOfTaskCompletion> completionRateMap = new HashMap<>();
    private final HashMap<String, Long> taskDoneMap = new HashMap<>();

    public PerformanceOverview(Project project, List<Person> memberList) {
        this.project = project;
        this.memberList.addAll(memberList);
        this.numOfMeetings = project.getListOfMeeting().size();
        this.numOfTasks = project.getTasks().size();
        attendanceRateMap.putAll(makeAttendanceRateMap());
        attendanceMap.putAll(makeAttendanceMap());
        completionRateMap.putAll(makeCompletionRateMap());
        taskDoneMap.putAll(makeTaskDoneMap());
    }

    public List<Person> getMemberList() {
        return memberList;
    }

    public Project getProject() {
        return project;
    }


    public RateOfAttendance getRateOfAttendanceOf(Person person) {
        String name = person.getName().fullName;
        return attendanceRateMap.get(name);
    }

    public int getAttendanceOf(Person person) {
        String name = person.getName().fullName;
        return attendanceMap.get(name);
    }

    public RateOfTaskCompletion getTaskCompletionRateOf(Person person) {
        String name = person.getName().fullName;
        return completionRateMap.get(name);
    }

    public long getNumOfTaskDoneOf(Person person) {
        String name = person.getName().fullName;
        return taskDoneMap.get(name);
    }

    public List<Person> getSortedMemberList() {
        memberList.sort(Comparator.comparing(person -> person.getName().fullName));
        return memberList;
    }

    private HashMap<String, RateOfAttendance> makeAttendanceRateMap() {
        HashMap<String, RateOfAttendance> attendanceRateMap = new HashMap<>();
        String projectTitle = project.getTitle().title;
        for (Person member : memberList) {
            HashMap<String, List<Meeting>> meetingsAttendedMap = member.getPerformance().getMeetingsAttended();
            if (!meetingsAttendedMap.containsKey(projectTitle)) {
                attendanceRateMap.put(member.getName().fullName, new RateOfAttendance(numOfMeetings, 0));
            } else {
                int numOfMeetingsAttended = meetingsAttendedMap.get(projectTitle).size();
                attendanceRateMap.put(member.getName().fullName, new RateOfAttendance(numOfMeetings, numOfMeetingsAttended));
            }
        }

        return attendanceRateMap;
    }

    private HashMap<String, Integer> makeAttendanceMap() {
        HashMap<String, Integer> attendanceMap = new HashMap<>();
        String projectTitle = project.getTitle().title;
        for (Person member : memberList) {
            HashMap<String, List<Meeting>> meetingsAttendedMap = member.getPerformance().getMeetingsAttended();

            if (!meetingsAttendedMap.containsKey(projectTitle)) {
                attendanceMap.put(member.getName().fullName, 0);
            } else {
                int numOfMeetingsAttended = meetingsAttendedMap.get(projectTitle).size();
                attendanceMap.put(member.getName().fullName, numOfMeetingsAttended);
            }
        }

        return attendanceMap;
    }

    private HashMap<String, RateOfTaskCompletion> makeCompletionRateMap() {
        HashMap<String, RateOfTaskCompletion> completionRateMap = new HashMap<>();
        String projectTitle = project.getTitle().title;
        for (Person member : memberList) {
            HashMap<String, List<Task>> taskAssignment = member.getPerformance().getTaskAssignment();

            if (!taskAssignment.containsKey(projectTitle)) {
                completionRateMap.put(member.getName().fullName, new RateOfTaskCompletion(new ArrayList<>()));
            } else {
                completionRateMap.put(member.getName().fullName, new RateOfTaskCompletion(taskAssignment.get(projectTitle)));
            }
        }

        return completionRateMap;
    }

    private HashMap<String, Long> makeTaskDoneMap() {
        HashMap<String, Long> taskDoneMap = new HashMap<>();
        String projectTitle = project.getTitle().title;
        for (Person member : memberList) {
            HashMap<String, List<Task>> taskAssignment = member.getPerformance().getTaskAssignment();

            if (!taskAssignment.containsKey(projectTitle)) {
                taskDoneMap.put(member.getName().fullName, (long) 0);
            } else {
                long numOfTaskDone = taskAssignment.get(projectTitle).stream().filter(task -> task.isDone).count();
                taskDoneMap.put(member.getName().fullName, numOfTaskDone);
            }
        }

        return taskDoneMap;
    }
}
