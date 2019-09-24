package seedu.address.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class ScheduleView extends Application {
    private ArrayList<String> dayNames = new ArrayList<String>(List.of("Sunday", "Monday", "Tuesday",
            "Wednesday", "Thursday", "Friday", "Saturday"));
    private int oneHourLength = 50;
    private int preferredWidth = 100;
    private int blockWidth = 20;
    private int startTime = 8;
    private int endTime = 20;
    @Override
    public void start(Stage stage) throws Exception {
        GridPane scheduleView = new GridPane();
        //initialise headers
        Rectangle placeHolder = new Rectangle(preferredWidth, 50);
        placeHolder.setStyle("-fx-fill: lightgrey");
        scheduleView.add(placeHolder, 0, 0);
        //day headers
        for (int i = 0; i < dayNames.size(); i++) {
            StackPane sp = new StackPane();
            Text dayText = new Text(dayNames.get(i));
            Region dayLabelRegion = new Region();
            dayLabelRegion.setPrefSize(preferredWidth, 50);
            dayLabelRegion.setStyle("-fx-background-color: white; -fx-border-width: 2");
            sp.getChildren().addAll(dayLabelRegion, dayText);
            scheduleView.add(sp, i + 1, 0);
        }
        //timeslot headers
        for (int j = startTime; j < endTime; j++) {
            //Region is used for border and background color control.
            Region timeslotLabelContainer = new Region();
            timeslotLabelContainer.setPrefSize(preferredWidth, oneHourLength);
            timeslotLabelContainer.setStyle("-fx-background-color: grey; -fx-border-color: white;");
            Text timeslotText = new Text(j * 100 + "");
            timeslotText.setTranslateX(preferredWidth / 2);
            StackPane stack = new StackPane();
            stack.getChildren().addAll(timeslotLabelContainer, timeslotText);
            scheduleView.add(stack, 0, j - startTime + 1);
        }
        //timeslot data
        for (int l = 0; l < dayNames.size(); l++) {
            StackPane stackPane = new StackPane();
            VBox timeslotContainer = new VBox();
            Region firstRegionOffset = new Region();
            firstRegionOffset.setPrefSize(preferredWidth, oneHourLength / 2);
            firstRegionOffset.setStyle("-fx-border-color: lightgrey; -fx-border-style: solid none none solid");
            timeslotContainer.getChildren().add(firstRegionOffset);
            for (int k = startTime; k < endTime; k++) {
                StackPane timeslotRegion = new StackPane();
                Region timeslotMajorRegion = new Region();
                timeslotMajorRegion.setStyle("-fx-border-color: lightgrey; -fx-border-style: solid none none solid;");
                Line timeslotMinorRegion = new Line(0.0, oneHourLength/2, preferredWidth, oneHourLength/2);
                timeslotMinorRegion.getStrokeDashArray().addAll(2d);
                timeslotMinorRegion.setStyle("-fx-stroke: silver;");
                if (k == endTime - 1) {
                    timeslotMajorRegion.setPrefSize(preferredWidth, oneHourLength / 2);
                } else {
                    timeslotMajorRegion.setPrefSize(preferredWidth, oneHourLength);
                }
                timeslotRegion.getChildren().addAll(timeslotMajorRegion, timeslotMinorRegion);
                timeslotContainer.getChildren().add(timeslotRegion);
            }
            stackPane.getChildren().add(timeslotContainer);
            scheduleView.add(stackPane, l + 1, 1, 1, endTime - startTime);
        }
        ScrollPane mainLayout = new ScrollPane();
        mainLayout.setContent(scheduleView);
        Scene scene = new Scene(mainLayout);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(ScheduleView.class, args);
    }
}
