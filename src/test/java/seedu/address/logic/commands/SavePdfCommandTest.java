package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_NO_INCOMPLETE_OR_ASSIGNED_TASKS_FOR_THE_DATE;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.SavePdfCommand.MESSAGE_SUCCESS_SUMMARY;
import static seedu.address.logic.commands.SavePdfCommand.PDF_DELIVERY_ORDER;
import static seedu.address.logic.commands.SavePdfCommand.PDF_SUMMARY;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_CUSTOMER;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_DESCRIPTION;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_EVENT_TIME;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_LOCAL_DATE;
import static seedu.address.testutil.SampleEntity.FOURTH_VALID_TASK_ID;
import static seedu.address.testutil.SampleEntity.getCompleteTask;
import static seedu.address.testutil.SampleEntity.getFourthSampleCompletedTask;
import static seedu.address.testutil.SampleEntity.getFourthSampleDriver;
import static seedu.address.testutil.SampleEntity.getSampleEmptyModel;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.GlobalClock;
import seedu.address.model.Model;
import seedu.address.model.task.Task;
import seedu.address.testutil.SampleEntity;

class SavePdfCommandTest {

    private static final String tempFilePath = "tempForSavePdf.pdf";

    //builds a temporary directory which will be deleted after the test case regardless if it fails or pass
    @TempDir
    public Path testFolder;

    private Model model;
    private Model expectedModel;
    private LocalDate fixedToday = GlobalClock.getStaticDate();

    private String getTempFilePath() {
        return testFolder.toString();
    }

    @BeforeEach
    void setUp() {
        //get a sample data with no assigned tasks
        model = SampleEntity.getSampleFreshModel();
        expectedModel = SampleEntity.getSampleFreshModel();
        testFolder = testFolder.resolve(tempFilePath);
    }

    @Test
    void execute_noAssignedTask_noAssignedTaskForTheDate() {
        //test savepdf today date
        SavePdfCommand savePdfCommand = new SavePdfCommand(PDF_SUMMARY, Optional.of(fixedToday),
                getTempFilePath());
        String expectedMessage = String.format(MESSAGE_NO_ASSIGNED_TASK_FOR_THE_DATE, fixedToday);

        assertCommandFailure(savePdfCommand, model, expectedMessage);
    }

    @Test
    void execute_hasAssignedTaskToday_savePdfSummary() {
        //add a complete task today into the manager
        Task completedTask = getCompleteTask(FOURTH_VALID_TASK_ID, FOURTH_VALID_DESCRIPTION, fixedToday,
                FOURTH_VALID_CUSTOMER, getFourthSampleDriver(), FOURTH_VALID_EVENT_TIME);
        model.addTask(completedTask);

        SavePdfCommand savePdfCommand = new SavePdfCommand(PDF_SUMMARY, Optional.of(fixedToday), getTempFilePath());
        String expectedMessage = String.format(MESSAGE_SUCCESS_SUMMARY, fixedToday);

        //expectedModel
        expectedModel.addTask(completedTask);

        //no change to model.
        assertCommandSuccess(savePdfCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_hasAssignedTaskOtherDate_savePdfSummary() {
        //add a complete task on other date into the manager
        LocalDate otherDate = FOURTH_VALID_LOCAL_DATE;
        Task completedTask = getFourthSampleCompletedTask();
        model.addTask(completedTask);

        SavePdfCommand savePdfCommand = new SavePdfCommand(PDF_SUMMARY, Optional.of(otherDate), getTempFilePath());
        String expectedMessage = String.format(MESSAGE_SUCCESS_SUMMARY, otherDate);

        //expectedModel
        expectedModel.addTask(completedTask);

        //no change to model.
        assertCommandSuccess(savePdfCommand, model, expectedMessage, expectedModel);
    }

    @Test
    void execute_hasOnlyCompletedTask_noInCompleteOrAssignedTasksExist() {
        Model sampleModel = getSampleEmptyModel();
        Task completedTask = getFourthSampleCompletedTask();
        model.addTask(completedTask);

        SavePdfCommand savePdfCommand = new SavePdfCommand(PDF_DELIVERY_ORDER, Optional.of(FOURTH_VALID_LOCAL_DATE),
                getTempFilePath());
        String expectedMessage = String.format(MESSAGE_NO_INCOMPLETE_OR_ASSIGNED_TASKS_FOR_THE_DATE,
                FOURTH_VALID_LOCAL_DATE);

        assertCommandFailure(savePdfCommand, sampleModel, expectedMessage);
    }
}
