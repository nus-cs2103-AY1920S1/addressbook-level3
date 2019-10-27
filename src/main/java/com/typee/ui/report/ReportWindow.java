package com.typee.ui.report;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.commons.util.PdfUtil;
import com.typee.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;

/**
 * Controller class for Report Window.
 */
public class ReportWindow extends UiPart<Region> {
    public static final String FXML = "ReportWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private PdfUtil pdfUtil;

    @FXML
    private TreeView treeViewReports;

    public ReportWindow() {
        super(FXML);
        Path filePath = Paths.get(System.getProperty("user.dir") + "/reports");
        logger.info(filePath.toString());
        treeViewReports.setRoot(getNodesForDirectory(filePath.toFile()));
        treeViewReports.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent event) -> handleMouseClicked(event));
    }

    /**
     * Handles mouse click event for treeview cell by openeing the file.
     */
    private void handleMouseClicked(MouseEvent event) {
        if (event.getButton().equals(MouseButton.PRIMARY)) {
            if (event.getClickCount() == 2) {
                Node node = event.getPickResult().getIntersectedNode();
                if (node instanceof Text || (node instanceof TreeCell && ((TreeCell) node).getText() != null)) {
                    String name = (String) ((TreeItem) treeViewReports
                            .getSelectionModel().getSelectedItem()).getValue();
                    logger.info("Node click: " + name);
                }
            }
        }
    }

    private TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for (File f : directory.listFiles()) {
            if (f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
            }
        }
        root.setExpanded(true);
        return root;
    }
}
