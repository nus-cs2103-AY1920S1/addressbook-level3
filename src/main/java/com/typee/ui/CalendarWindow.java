package com.typee.ui;

import java.net.URL;
import java.util.ResourceBundle;

import com.typee.logic.Logic;

import javafx.fxml.Initializable;
import javafx.scene.layout.Region;

/**
 * Controller class which handles Calendar Window.
 */
public class CalendarWindow extends UiPart<Region> implements Initializable {
    public static final String FXML = "CalendarWindow.fxml";

    private Logic logic;

    public CalendarWindow() {
        super(FXML);
    }

    public CalendarWindow(Logic logic) {
        super(FXML);
        this.logic = logic;
    }

    public Logic getLogic() {
        return logic;
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
