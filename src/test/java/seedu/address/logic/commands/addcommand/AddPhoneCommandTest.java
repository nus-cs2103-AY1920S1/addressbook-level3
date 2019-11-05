package seedu.address.logic.commands.addcommand;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.DataBook;
import seedu.address.model.ReadOnlyDataBook;
import seedu.address.model.phone.Phone;
import seedu.address.testutil.ModelStub;
import seedu.address.testutil.PhoneBuilder;

public class AddPhoneCommandTest {

    @Test
    public void constructor_nullPhone_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddPhoneCommand(null));
    }

    @Test
    public void execute_phoneAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPhoneAdded modelStub = new ModelStubAcceptingPhoneAdded();
        Phone validPhone = new PhoneBuilder().build();

        CommandResult commandResult = new AddPhoneCommand(validPhone)
                .executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack());

        assertEquals(String.format(AddPhoneCommand.MESSAGE_SUCCESS, validPhone), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPhone), modelStub.phonesAdded);
    }

    @Test
    public void execute_duplicatePhone_throwsCommandException() {
        Phone validPhone = new PhoneBuilder().build();
        AddPhoneCommand addPhoneCommand = new AddPhoneCommand(validPhone);
        ModelStub modelStub = new ModelStubWithPhone(validPhone);

        assertThrows(CommandException.class, AddPhoneCommand.MESSAGE_DUPLICATE_PHONE, ()
            -> addPhoneCommand.executeUndoableCommand(modelStub, new CommandHistory(), new UndoRedoStack()));
    }

    @Test
    public void equals() {

        Phone iphone11 = new PhoneBuilder().withName("iPhone 11").build();
        Phone iphone11pro = new PhoneBuilder().withName("iPhone 11 Pro").build();
        AddPhoneCommand addiPhone11Command = new AddPhoneCommand(iphone11);
        AddPhoneCommand addiPhone11ProCommand = new AddPhoneCommand(iphone11pro);

        // same object -> returns true
        assertTrue(addiPhone11Command.equals(addiPhone11Command));

        // same values -> returns true
        AddPhoneCommand addiPhone11CommandCopy = new AddPhoneCommand(iphone11);
        assertTrue(addiPhone11Command.equals(addiPhone11CommandCopy));

        // different types -> returns false
        assertFalse(addiPhone11Command.equals(1));

        // null -> returns false
        assertFalse(addiPhone11Command.equals(null));

        // different person -> returns false
        assertFalse(addiPhone11Command.equals(addiPhone11ProCommand));
    }

    /**
     * A Model stub that contains a single phone.
     */
    private class ModelStubWithPhone extends ModelStub {
        private final Phone phone;

        ModelStubWithPhone(Phone phone) {
            requireNonNull(phone);
            this.phone = phone;
        }

        @Override
        public boolean hasPhone(Phone phone) {
            requireNonNull(phone);
            return this.phone.isSamePhone(phone);
        }
    }

    /**
     * A Model stub that always accept the phone being added.
     */
    private class ModelStubAcceptingPhoneAdded extends ModelStub {
        final ArrayList<Phone> phonesAdded = new ArrayList<>();

        @Override
        public boolean hasPhone(Phone phone) {
            requireNonNull(phone);
            return phonesAdded.stream().anyMatch(phone::isSamePhone);
        }

        @Override
        public void addPhone(Phone phone) {
            requireNonNull(phone);
            phonesAdded.add(phone);
        }

        @Override
        public ReadOnlyDataBook<Phone> getPhoneBook() {
            return new DataBook<Phone>();
        }
    }

}
