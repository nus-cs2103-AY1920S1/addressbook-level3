package com.typee.ui.report;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

import com.typee.commons.core.LogsCenter;
import com.typee.commons.util.PdfUtil;
import com.typee.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Region;

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
        Path filePath = Paths.get(System.getProperty("user.dir"));
        logger.info(filePath.toString());
        treeViewReports.setRoot(getNodesForDirectory(filePath.toFile()));
    }
    private TreeItem<String> getNodesForDirectory(File directory) {
        TreeItem<String> root = new TreeItem<String>(directory.getName());
        for(File f : directory.listFiles()) {
            logger.info( "Loading " + f.getName());
            if(f.isDirectory()) {
                root.getChildren().add(getNodesForDirectory(f));
            } else {
                root.getChildren().add(new TreeItem<String>(f.getName()));
            }
        }
        return root;
    }
}
