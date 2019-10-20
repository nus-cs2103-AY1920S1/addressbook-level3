package com.typee.logic.commands;

import static com.typee.logic.commands.CommandTestUtil.assertCommandFailure;
import static com.typee.logic.commands.CommandTestUtil.assertCommandSuccess;
import static com.typee.testutil.TypicalEngagements.getTypicalEngagementList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.typee.model.Model;
import com.typee.model.ModelManager;
import com.typee.model.UserPrefs;
import com.typee.model.engagement.Engagement;
import com.typee.testutil.EngagementBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalEngagementList(), new UserPrefs());
    }

    @Test
    public void execute_newAppointment_success() {
        Engagement validAppointment = new EngagementBuilder().buildAsAppointment();

        Model expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
        expectedModel.addEngagement(validAppointment);
        expectedModel.saveEngagementList();

        assertCommandSuccess(new AddCommand(validAppointment), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validAppointment), expectedModel);
    }

    @Test
    public void execute_newInterview_success() {
        Engagement validInterview = new EngagementBuilder().buildAsInterview();

        Model expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
        expectedModel.addEngagement(validInterview);
        expectedModel.saveEngagementList();

        assertCommandSuccess(new AddCommand(validInterview), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validInterview), expectedModel);
    }

    @Test
    public void execute_newMeeting_success() {
        Engagement validMeeting = new EngagementBuilder().buildAsMeeting();

        Model expectedModel = new ModelManager(model.getEngagementList(), new UserPrefs());
        expectedModel.addEngagement(validMeeting);
        expectedModel.saveEngagementList();

        assertCommandSuccess(new AddCommand(validMeeting), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validMeeting), expectedModel);
    }

    @Test
    public void execute_duplicateAppointment_throwsCommandException() {
        Engagement appointmentInList = model.getEngagementList().getEngagementList().get(0);
        assertCommandFailure(new AddCommand(appointmentInList), model, AddCommand.MESSAGE_DUPLICATE_ENGAGEMENT);
    }

    @Test
    public void execute_duplicateInterview_throwsCommandException() {
        Engagement interviewInList = model.getEngagementList().getEngagementList().get(1);
        assertCommandFailure(new AddCommand(interviewInList), model, AddCommand.MESSAGE_DUPLICATE_ENGAGEMENT);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() {
        Engagement meetingInList = model.getEngagementList().getEngagementList().get(2);
        assertCommandFailure(new AddCommand(meetingInList), model, AddCommand.MESSAGE_DUPLICATE_ENGAGEMENT);
    }

}
