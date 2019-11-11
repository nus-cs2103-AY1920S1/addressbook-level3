package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.projection.ProjectionGraph;

/**
 * Panel depicting the {@code ProjectionGraph}
 */
public class ProjectionGraphPanel extends UiPart<StackPane> {

    private static final String FXML = "ProjectionGraph.fxml";
    private final Logger logger = LogsCenter.getLogger(ProjectionGraphPanel.class);

    @FXML
    private StackPane projectionGraph;

    public ProjectionGraphPanel(ProjectionGraph projectionGraph) {
        super(FXML);
        this.projectionGraph = projectionGraph;
    }
}
