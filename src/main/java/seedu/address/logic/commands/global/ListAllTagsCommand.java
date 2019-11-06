package seedu.address.logic.commands.global;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TAGS;

import java.util.ArrayList;
import java.util.Collections;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.commandresults.GlobalCommandResult;
import seedu.address.model.Model;
import seedu.address.model.StudyBuddyCounter;
import seedu.address.model.tag.Tag;

/**
 * Lists all tags in the StudyBuddy Application to the user.
 */
public class ListAllTagsCommand extends Command {
    public static final String COMMAND_WORD = "taglist";

    public static final String MESSAGE_SUCCESS = "Here are all the tags in StudyBuddyPro."
            + "\n"
            + "Listing all tags : "
            + "\n";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredTagList(PREDICATE_SHOW_ALL_TAGS);
        StringBuilder outputString = new StringBuilder();
        ArrayList<String> sortedTagListInStringForm = model.getListOfTags();
        Collections.sort(sortedTagListInStringForm);

        ArrayList<Tag> sortedTagList = new ArrayList<>();
        for (String s : sortedTagListInStringForm) {
            sortedTagList.add(new Tag(s));
        }

        ArrayList<StudyBuddyCounter> studyBuddyCounters = model.getStatistics(sortedTagList);

        for (int i = 0; i < sortedTagListInStringForm.size(); i++) {
            if (studyBuddyCounters.get(i).isTagExisting()) {
                outputString.append("[");
                outputString.append(sortedTagListInStringForm.get(i));
                outputString.append("]");
                outputString.append(" | ");
                outputString.append(studyBuddyCounters.get(i).toString());
                outputString.append("\n");
            }
        }
        return new GlobalCommandResult(MESSAGE_SUCCESS + outputString.toString());
    }
}
