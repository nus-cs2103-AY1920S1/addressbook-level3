package seedu.address.logic.export;

import java.io.IOException;

import javafx.scene.Scene;
import javafx.scene.layout.Region;
import seedu.address.ui.schedule.ScheduleView;

/**
 * Class to handle exporting visual representation of an individual.
 * Handles the exportation of an individual's schedule.
 */
public class IndividualScheduleExporter implements Exporter {
    private ScheduleView scheduleView;
    private String fileFormat;
    private String filepath;

    public IndividualScheduleExporter(ScheduleView scheduleView, String fileFormat, String filepath) {
        this.scheduleView = scheduleView;
        this.fileFormat = fileFormat;
        this.filepath = filepath;
    }

    /**
     * Method to export this individual's schedule to a png file.
     * @throws IOException to be handled elsewhere.
     */
    public void export() throws IOException {
        Region export = scheduleView.getRoot();
        export.setMaxWidth(1000);
        Scene scene = new Scene(export);
        scene.getStylesheets().add("/view/DarkTheme.css");
        VisualExporter.exportTo(scene.getRoot(), fileFormat, filepath);
    }
}
