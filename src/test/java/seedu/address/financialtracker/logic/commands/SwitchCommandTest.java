package seedu.address.financialtracker.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.financialtracker.model.Model;
import seedu.address.financialtracker.ui.CountriesDropdown;
import seedu.address.logic.commands.exceptions.CommandException;

class SwitchCommandTest {

    private ModelStub model = new ModelStub();

    @Test
    public void switch_failure() {
        // non-exist country
        assertThrows(CommandException.class, () -> new SwitchCommand("random_text").execute(model));
        // non formatted country
        assertThrows(CommandException.class, () -> new SwitchCommand("singapore").execute(model));
        assertThrows(CommandException.class, () -> new SwitchCommand("jaPaN").execute(model));
    }

    @Test
    public void switch_success() throws CommandException {
        // set success
        new SwitchCommand("Singapore").execute(model);

        // set success
        new SwitchCommand("Japan").execute(model);
    }

    @Test
    public void nonNull() {
        assertThrows(NullPointerException.class, () -> new SwitchCommand(null));
    }

    @Test
    public void equals() {
        SwitchCommand switch1 = new SwitchCommand("Singapore");
        SwitchCommand switch2 = new SwitchCommand("Japan");

        // same object -> returns true
        assertTrue(switch1.equals(switch1));

        // same values -> returns true
        SwitchCommand switch1Copy = new SwitchCommand("Singapore");
        assertTrue(switch1.equals(switch1Copy));

        // different values -> returns false
        assertFalse(switch1.equals(switch2));
    }

    /**
     * Stub for unit testing.
     */
    private class ModelStub extends Model {

        String country;

        ModelStub() {
            super();
            this.country = null;
        }

        @Override
        public void updateDropDownMenu(String args) throws CommandException {
            requireNonNull(args);
            if (!CountriesDropdown.isValidDropdownCountry(args)) {
                throw new CommandException("invalid country");
            } else {
                this.country = args;
            }
        }
    }
}
