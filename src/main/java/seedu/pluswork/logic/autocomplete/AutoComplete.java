package seedu.pluswork.logic.autocomplete;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Set;

import javafx.collections.ObservableList;

import seedu.pluswork.commons.Keywords;
import seedu.pluswork.model.Model;
import seedu.pluswork.model.member.Member;
import seedu.pluswork.model.tag.Tag;
import seedu.pluswork.model.task.Task;

public class AutoComplete {
    /**
     * AutoComplete logic to return suggestions based on command/prefix that the user is typing
     * uses the model to get relevant data for existing tasks, members
     * @param input
     * @param model
     */
    private Model model;
    private LinkedList<String> members;
    private LinkedList<String> tags;

    public AutoComplete(Model model) {
        this.model = model;
    }
    private LinkedList<String> suggestions = new LinkedList<>();

    public LinkedList<String> completeText(String input) {
        suggestions.clear();
        int firstSpace = input.indexOf(" ");
        if (firstSpace == -1) { // still entering commandWord
            SortedSet <String> commandList = new TreeSet<>(Keywords.commandList);
            suggestions.addAll(commandList.subSet(input, input + Character.MAX_VALUE));
        }
        return suggestions;
    }
    /**
     * method to return prefix suggestions - coming in v2.0
     * @param input
     * @return
     */
    private LinkedList<String> getPrefixSuggestion(String input) {
        members = getMemberId(model.getFilteredMembersList());
        tags = getTaskTags(model.getFilteredTasksList());
        int lastSpace = input.lastIndexOf(" ");
        String prefix = input.substring(lastSpace + 1);
        switch (prefix) {

        case "mi/":
            return members;

        case "s/":
            List<String> taskStatus = Arrays.asList("done", "unbegun", "doing");
            return new LinkedList<>(taskStatus);

        case "ty/":
            List<String> inventoryType = Arrays.asList("members", "tasks");
            return new LinkedList<>(inventoryType);

        case "tt/":
            return tags;

        default:
            return new LinkedList<>();
        }
    }

    /**
     * gets memberId suggestion from created {@code Member}
     * for prefix suggestions in v2.0
     * @param memberList
     * @return
     */
    private LinkedList<String> getMemberId (ObservableList<Member> memberList) {
        LinkedList<String> memberIdList = new LinkedList<>();
        for (Member member : memberList) {
            memberIdList.add(member.getId().getDisplayId());
        }
        return memberIdList;
    }

    /**
     * gets all the existing tags created by {@Code Task}
     * for prefix suggestions in v2.0
     * @param taskList
     * @return
     */
    private LinkedList<String> getTaskTags(ObservableList<Task> taskList) {
        TreeSet<String> tagList = new TreeSet<>();
        for (Task task : taskList) {
            Set<Tag> temp = task.getTags();
            //tasks can have multiple tags
            for (Tag tag : temp) {
                tagList.add(tag.getTag());
            }
        }

        return new LinkedList<String>(tagList);
    }
}
