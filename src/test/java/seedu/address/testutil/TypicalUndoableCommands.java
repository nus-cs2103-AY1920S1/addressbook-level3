package seedu.address.testutil;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.entity.body.Body;

//@@author ambervoong
/**
 * Contains various UndoableCommands for use in testing.
 */
public class TypicalUndoableCommands {

    public static final Body TYPICAL_BODY = new BodyBuilder().build(1);
    public static final UpdateBodyDescriptor TYPICAL_UPDATE_BODY_DESCRIPTOR = new UpdateBodyDescriptor(TYPICAL_BODY);
    public static final UpdateCommand TYPICAL_UPDATE_COMMAND = new UpdateCommand(TYPICAL_BODY.getIdNum(),
            TYPICAL_UPDATE_BODY_DESCRIPTOR);
    public static final AddCommand TYPICAL_ADD_COMMAND = new AddCommand(TYPICAL_BODY);

    public static final DeleteCommand TYPICAL_DELETE_COMMAND =
            new DeleteCommand(Index.fromZeroBased(TYPICAL_BODY.getIdNum().getIdNum()), "B");
}
