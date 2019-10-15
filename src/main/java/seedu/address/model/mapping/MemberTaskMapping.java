package seedu.address.model.mapping;

import seedu.address.model.task.Task;
import seedu.address.model.member.Member;

import java.util.HashMap;
import java.util.HashSet;

public class MemberTaskMapping {
    public HashMap<Task, HashSet<Member>> taskMemberMapping = new HashMap<Task, HashSet<Member>>();
    public HashMap<Member, HashSet<Task>> memberTaskMapping = new HashMap<Member, HashSet<Task>>();

    public void mapMemberTask(Member member, Task task) {
        if (memberTaskMapping.get(member) == null) {
            memberTaskMapping.put(member, new HashSet<Task>());
        }
        memberTaskMapping.get(member).add(task);

        if (taskMemberMapping.get(task) == null) {
            taskMemberMapping.put(task, new HashSet<Member>());
        }
        taskMemberMapping.get(task).add(member);
    }

    public HashMap<Member, HashSet<Task>> getMemberTaskMapping() {
        return memberTaskMapping;
    }

    public HashMap<Task, HashSet<Member>> getTaskMemberMapping() {
        return taskMemberMapping;
    }
}