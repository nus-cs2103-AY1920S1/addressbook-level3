package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_AUTO_ONLY_Y_N;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_INDEX_OF_V;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLE_ASSIGNMENT_PROMPT;
import static seedu.address.commons.core.Messages.MESSAGE_VEHICLE_OOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.vehicle.District;

public class NewCommandParserTest {

    @Test
    public void parse_validAutoY_newCommandCreated() {
        try {
            NewCommand nc = new NewCommandParser().parse(" dist/1 auto/y");
            NewCommand ec = new NewCommand(new District(1), true, 0);
            assertEquals(nc, ec);
        } catch (ParseException e) {
            assert(false);
        }
    }

    @Test
    public void parse_validAutoN_newCommandCreated() {
        try {
            NewCommand nc = new NewCommandParser().parse(" dist/1 auto/n v/1");
            NewCommand ec = new NewCommand(new District(1), false, 1);
            assertEquals(nc, ec);
        } catch (ParseException e) {
            assert(false);
        }
    }

    @Test
    public void parse_invalidAuto_exceptionThrown() {
        try {
            NewCommand nc = new NewCommandParser().parse(" dist/1 auto/a");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), MESSAGE_AUTO_ONLY_Y_N);
        }
    }

    @Test
    public void parse_invalidDAutoY_exceptionThrown() {
        try {
            NewCommand nc = new NewCommandParser().parse(" dist/100 auto/y");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), District.MESSAGE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_indexOobAutoN_exceptionThrown() {
        try {
            NewCommand nc = new NewCommandParser().parse(" dist/1 auto/n v/100");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), MESSAGE_VEHICLE_OOB);
        }
    }

    @Test
    public void parse_noIndexAutoN_exceptionThrown() {
        try {
            NewCommand nc = new NewCommandParser().parse(" dist/1 auto/n");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), MESSAGE_VEHICLE_ASSIGNMENT_PROMPT);
        }
    }

    @Test
    public void parse_invalidIndexAutoN_exceptionThrown() {
        try {
            NewCommand nc = new NewCommandParser().parse(" dist/1 auto/n v/1.3940");
        } catch (ParseException e) {
            assertEquals(e.getErrorMessage(), MESSAGE_INVALID_INDEX_OF_V);
        }
    }

}
