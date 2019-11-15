package dukecooks.storage.dashboard;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import dukecooks.commons.util.JsonUtil;
import dukecooks.model.dashboard.DashboardRecords;
import dukecooks.testutil.dashboard.TypicalDashboard;

public class JsonSerializableDashboardTest {

    private static final Path TEST_DATA_FOLDER =
            Paths.get("src", "test", "data", "JsonSerializableDashboardTest");
    private static final Path TYPICAL_DASHBOARDS_FILE = TEST_DATA_FOLDER.resolve("typicalDashboardRecords.json");

    @Test
    public void toModelType_typicalDashboardsFile_success() throws Exception {
        JsonSerializableDashboard dataFromFile = JsonUtil.readJsonFile(TYPICAL_DASHBOARDS_FILE,
                JsonSerializableDashboard.class).get();
        DashboardRecords dashboardRecordsFromFile = dataFromFile.toModelType();
        DashboardRecords typicalDashboardDashboardRecords = TypicalDashboard.getTypicalDashboardRecords();
        assertEquals(dashboardRecordsFromFile, dashboardRecordsFromFile);
    }
}
