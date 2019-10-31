package seedu.planner.logic.events.edit;

import java.util.List;

import seedu.planner.commons.core.index.Index;
import seedu.planner.logic.commands.EditContactCommand;
import seedu.planner.logic.commands.EditContactCommand.EditContactDescriptor;
import seedu.planner.logic.commands.UndoableCommand;
import seedu.planner.logic.events.Event;
import seedu.planner.model.Model;
import seedu.planner.model.contact.Contact;

/**
 * An event representing a 'edit contact' command.
 */
public class EditContactEvent implements Event {
    private final Index index;
    private final EditContactDescriptor editInfo;
    private final EditContactDescriptor reverseEditInfo;

    public EditContactEvent(Index index, EditContactDescriptor editInfo, Model model) {
        this.index = index;
        this.editInfo = editInfo;
        this.reverseEditInfo = generateReverseEditInfo(model);
    }

    public UndoableCommand undo() {
        return new EditContactCommand(index, reverseEditInfo);
    }

    public UndoableCommand redo() {
        return new EditContactCommand(index, editInfo);
    }

    /**
     * A method to construct an EditContactDescriptor based on the current Contact to edit in the model.
     * @param model Current model in the application.
     * @return the EditContactDescriptor containing information of the original Contact to be edited.
     */
    private EditContactDescriptor generateReverseEditInfo(Model model) {
        EditContactDescriptor result = new EditContactDescriptor();

        List<Contact> lastShownList = model.getFilteredContactList();
        assert(index.getZeroBased() < lastShownList.size());

        Contact originalContact = lastShownList.get(index.getZeroBased());

        result.setName(originalContact.getName());
        result.setPhone(originalContact.getPhone());

        if (originalContact.getEmail().isPresent()) {
            result.setEmail(originalContact.getEmail().get());
        } else {
            result.setEmail(null);
        }

        if (originalContact.getAddress().isPresent()) {
            result.setAddress(originalContact.getAddress().get());
        } else {
            result.setAddress(null);
        }
        result.setTags(originalContact.getTags());

        return result;
    }
}
