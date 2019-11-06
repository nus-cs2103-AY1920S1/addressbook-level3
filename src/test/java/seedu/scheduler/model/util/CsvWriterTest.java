package seedu.scheduler.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.scheduler.logic.commands.CommandTestUtil.VALID_FILE_PATH;
import static seedu.scheduler.logic.commands.CommandTestUtil.getExpectedExportedData;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import seedu.scheduler.model.Model;
import seedu.scheduler.model.ModelManager;
import seedu.scheduler.model.Schedule;
import seedu.scheduler.testutil.SampleSchedules;

public class CsvWriterTest {
    private static Model model = new ModelManager();

    @Test
    public void writeSchedulesToFile_success() throws IOException {
        ArrayList<String> expectedResult = getExpectedExportedData();

        ArrayList<Schedule> modelData = new ArrayList<>();
        modelData.add(SampleSchedules.getSampleFilledSchedule());
        model.setSchedulesList(modelData);
        CsvWriter writer = new CsvWriter(VALID_FILE_PATH, model);

        ArrayList<String> testResult = writer.writeSchedulesToFile();
        assertEquals(expectedResult, testResult);
    }
}
