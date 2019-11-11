package seedu.address.logic.export;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.ui.schedule.GroupInformationDisplay;
import seedu.address.ui.schedule.ScheduleView;

/**
 * Class to handle exporting visual representation of a group.
 * Details of the group and the schedules of the members are exported to a png file.
 */
public class GroupScheduleExporter implements Exporter {
    private ScheduleView scheduleView;
    private GroupInformationDisplay groupInformationDisplay;
    private String fileFormat;
    private String filepath;

    public GroupScheduleExporter(ScheduleView scheduleView, GroupInformationDisplay groupInformationDisplay,
                                 String fileFormat, String filepath) {
        this.scheduleView = scheduleView;
        this.fileFormat = fileFormat;
        this.filepath = filepath;
        this.groupInformationDisplay = groupInformationDisplay;
    }

    /**
     * Method to export this group's schedule to a png file.
     * @throws IOException to be handled elsewhere.
     */
    public void export() throws IOException {
        HBox exportContainer = new HBox();
        Region scheduleTable = scheduleView.getRoot();
        scheduleTable.setMaxWidth(1000);
        exportContainer.getChildren().addAll(groupInformationDisplay.getRoot(), scheduleTable);
        exportContainer.setId("exportContainer");
        Scene scene = new Scene(exportContainer);
        scene.getStylesheets().add("/view/DarkTheme.css");
        VisualExporter.exportTo(scene.getRoot(), fileFormat, filepath);
    }
}
