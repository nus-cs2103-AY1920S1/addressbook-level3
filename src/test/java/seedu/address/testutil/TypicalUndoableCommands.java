package seedu.address.testutil;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.utility.UpdateBodyDescriptor;
import seedu.address.model.entity.body.Body;

//@@author ambervoong
/**
 * Contains various UndoableCommands for use in testing.
 */
public class TypicalUndoableCommands {

    public static final Body TYPICAL_BODY = new BodyBuilder().build();
    public static final UpdateBodyDescriptor TYPICAL_UPDATE_BODY_DESCRIPTOR = new UpdateBodyDescriptor(TYPICAL_BODY);
    public static final UpdateCommand TYPICAL_UPDATE_COMMAND = new UpdateCommand(TYPICAL_BODY.getBodyIdNum(),
            TYPICAL_UPDATE_BODY_DESCRIPTOR);
}
