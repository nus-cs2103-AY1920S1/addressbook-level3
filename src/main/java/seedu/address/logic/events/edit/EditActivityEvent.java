package seedu.address.logic.events.edit;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditActivityCommand;
import seedu.address.logic.commands.EditActivityCommand.EditActivityDescriptor;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.events.Event;
import seedu.address.model.Model;
import seedu.address.model.itineraryitem.activity.Activity;

/**
 * An event representing a 'edit activity' command.
 */
public class EditActivityEvent implements Event {
    private final Index index;
    private final EditActivityDescriptor editInfo;
    private final EditActivityDescriptor reverseEditInfo;

    public EditActivityEvent(Index index, EditActivityDescriptor editInfo, Model model) {
        this.index = index;
        this.editInfo = editInfo;
        this.reverseEditInfo = generateReverseEditInfo(model);
    }

    public UndoableCommand undo() {
        return new EditActivityCommand(index, reverseEditInfo);
    }

    public UndoableCommand redo() {
        return new EditActivityCommand(index, editInfo);
    }

    /**
     * A method to construct an EditActivityDescriptor based on the current Activity to edit in the model.
     * @param model Current model in the application.
     * @return the EditActivityDescriptor containing information of the original Activity to be edited.
     */
    private EditActivityDescriptor generateReverseEditInfo(Model model) {
        EditActivityDescriptor result = new EditActivityDescriptor();

        List<Activity> lastShownList = model.getFilteredActivityList();
        assert(index.getZeroBased() < lastShownList.size());

        Activity originalActivity = lastShownList.get(index.getZeroBased());

        result.setName(originalActivity.getName());
        result.setAddress(originalActivity.getAddress());
        if (originalActivity.getContact().isPresent()) {
            result.setPhone(originalActivity.getContact().get().getPhone());
        } else {
            result.setPhone(null);
        }
        result.setTags(originalActivity.getTags());

        return result;
    }
}
