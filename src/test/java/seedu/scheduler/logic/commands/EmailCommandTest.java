package seedu.scheduler.logic.commands;

import static seedu.scheduler.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_SLOT_AMY;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.scheduler.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.scheduler.logic.commands.EmailCommand.MESSAGE_EMAIL_ALL_SUCCESS;
import static seedu.scheduler.logic.commands.EmailCommand.MESSAGE_EMAIL_CLIENT_ERROR;
import static seedu.scheduler.logic.commands.EmailCommand.MESSAGE_NO_SLOTS_ALLOCATED;
import static seedu.scheduler.logic.commands.EmailCommand.MESSAGE_STATUS;
import static seedu.scheduler.logic.commands.EmailCommand.MESSAGE_USAGE;
import static seedu.scheduler.testutil.TypicalPersons.ALICE_INTERVIEWEE;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalIntervieweeList;
import static seedu.scheduler.testutil.TypicalPersons.getTypicalInterviewerList;

import java.awt.Desktop;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.scheduler.commons.core.Messages;
import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.ModelStub;
import seedu.scheduler.model.ReadOnlyList;
import seedu.scheduler.model.ReadOnlyUserPrefs;
import seedu.scheduler.model.Schedule;
import seedu.scheduler.model.UserPrefs;
import seedu.scheduler.model.person.Interviewee;
import seedu.scheduler.model.person.Interviewer;
import seedu.scheduler.model.person.Name;
import seedu.scheduler.model.person.Slot;

public class EmailCommandTest {
    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());
        expectedModel = new ModelManager(model.getMutableIntervieweeList(), model.getMutableInterviewerList(),
                new UserPrefs(), new LinkedList<>());
    }

    @Test
    public void execute_emailInvalidSubcommand_commandException() {
        EmailCommand emailCommand = new EmailCommand("invalid");
        assertCommandFailure(emailCommand, model, String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void execute_emailStatusSubcommand_success() {
        EmailCommand emailCommand = new EmailCommand("status");
        String expectedResult = String.format(MESSAGE_STATUS, 0, expectedModel.getUnfilteredIntervieweeList().size());
        assertCommandSuccess(emailCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_emailInvalidName_invalidPerson() {
        EmailCommand emailCommand = new EmailCommand("timeslot", new Name("Invalid Doe John"));
        assertCommandFailure(emailCommand, model, Messages.MESSAGE_INVALID_PERSON_NAME);
    }

    @Test
    public void execute_emailTimeslotSubcommand_noSlotsAllocated() {
        EmailCommand emailCommand = new EmailCommand("timeslot", ALICE_INTERVIEWEE.getName());
        assertCommandFailure(emailCommand, model, MESSAGE_NO_SLOTS_ALLOCATED);
    }

    @Test
    public void execute_emailTimeslotSubcommand_mailClientError() {
        if (!Desktop.isDesktopSupported() || !Desktop.getDesktop().isSupported(Desktop.Action.MAIL)) {
            EmailCommand emailCommand = new EmailCommand("timeslot", ALICE_INTERVIEWEE.getName());
            ALICE_INTERVIEWEE.setAllocatedSlot(Slot.fromString(VALID_SLOT_AMY));
            assertCommandFailure(emailCommand, model, MESSAGE_EMAIL_CLIENT_ERROR);
            ALICE_INTERVIEWEE.clearAllocatedSlot();
        }
    }

    // Note: This method is disabled until we find a suitable way of testing without an annoying popup of the
    // mail client
    // @Test
    // public void execute_emailTimeslotSubcommand_success() {
    //     EmailCommand emailCommand = new EmailCommand("timeslot", ALICE_INTERVIEWEE.getName());
    //     String expectedResult = String.format(MESSAGE_EMAIL_INTERVIEWEE_SUCCESS, ALICE_INTERVIEWEE.getName());
    //     ALICE_INTERVIEWEE.setAllocatedSlot(Slot.fromString(VALID_SLOT_AMY));
    //     assertCommandSuccess(emailCommand, model, expectedResult, expectedModel);
    //     ALICE_INTERVIEWEE.clearAllocatedSlot();
    //     ALICE_INTERVIEWEE.setEmailSent(false);
    // }

    @Test
    public void execute_emailAllTimeslotSubcommand_successWithAllNoSlots() {
        EmailCommand emailCommand = new EmailCommand("alltimeslot");
        int numberOfInterviewees = model.getUnfilteredIntervieweeList().size();
        String expectedResult = String.format(MESSAGE_EMAIL_ALL_SUCCESS, 0, 0, 0, numberOfInterviewees,
                numberOfInterviewees);
        assertCommandSuccess(emailCommand, model, expectedResult, expectedModel);
    }

    @Test
    public void execute_emailAllTimeslotSubcommand_successWithAllSkipped() {
        model = new ModelStubWithAllSlotsAllocated(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());
        expectedModel = new ModelStubWithAllSlotsAllocated(getTypicalIntervieweeList(), getTypicalInterviewerList(),
                new UserPrefs(), new LinkedList<>());

        EmailCommand emailCommand = new EmailCommand("alltimeslot");

        for (Interviewee interviewee : model.getUnfilteredIntervieweeList()) {
            interviewee.setEmailSent(true);
        }

        int numberOfInterviewees = model.getUnfilteredIntervieweeList().size();
        String expectedResult = String.format(MESSAGE_EMAIL_ALL_SUCCESS, 0, numberOfInterviewees, 0, 0,
                numberOfInterviewees);

        assertCommandSuccess(emailCommand, model, expectedResult, expectedModel);

        // Reset state to avoid affecting other test cases
        for (Interviewee interviewee : model.getUnfilteredIntervieweeList()) {
            interviewee.setEmailSent(false);
        }
    }

    /**
     * A Model stub that always return Interviewees with a slot allocated.
     */
    private class ModelStubWithAllSlotsAllocated extends ModelStub {
        private final ReadOnlyList<Interviewee> typicalIntervieweeList;
        private final ReadOnlyList<Interviewer> typicalInterviewerList;
        private final ReadOnlyUserPrefs userPrefs;
        private final List<Schedule> schedulesList;

        public ModelStubWithAllSlotsAllocated(ReadOnlyList<Interviewee> intervieweeList,
                ReadOnlyList<Interviewer> interviewerList, ReadOnlyUserPrefs userPrefs, List<Schedule> schedulesList) {
            this.typicalIntervieweeList = intervieweeList;
            this.typicalInterviewerList = interviewerList;
            this.userPrefs = userPrefs;
            this.schedulesList = schedulesList;
        }

        @Override
        public Optional<Slot> getAllocatedSlot(String intervieweeName) {
            return Optional.of(Slot.fromString(VALID_SLOT_AMY));
        }

        @Override
        public ObservableList<Interviewee> getUnfilteredIntervieweeList() {
            return this.typicalIntervieweeList.getEntityList();
        }

        @Override
        public boolean equals(Object obj) {
            // short circuit if same object
            if (obj == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(obj instanceof ModelStubWithAllSlotsAllocated)) {
                return false;
            }

            // state check
            ModelStubWithAllSlotsAllocated other = (ModelStubWithAllSlotsAllocated) obj;
            return userPrefs.equals(other.userPrefs)
                    && typicalIntervieweeList.equals(other.typicalIntervieweeList)
                    && typicalInterviewerList.equals(other.typicalInterviewerList)
                    && schedulesList.equals(other.schedulesList);
        }
    }
}
