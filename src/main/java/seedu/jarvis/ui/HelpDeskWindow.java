package seedu.jarvis.ui;

import java.io.FileInputStream;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

/**
 * A view representing
 */
public class HelpDeskWindow extends UiPart<Region> {

    private static final String FXML = "HelpDeskWindow.fxml";

    @FXML
    private ImageView helpDeskJarvisImage;

    @FXML
    private TextFlow helpDeskText;

    private Text text1;
    private Text text2;

    public HelpDeskWindow() {
        super(FXML);
        setImage();
        text1 = new Text("Welcome to Jarvis!");
        text1.setFill(Color.WHITE);
        text2 = new Text();
        text2.setFill(Color.WHITE);

        helpDeskText.setTextAlignment(TextAlignment.CENTER);
        helpDeskText.getChildren().addAll(text1, text2);
    }

    private void setImage() {
        FileInputStream input = null;
        try {
            helpDeskJarvisImage.setImage(getImages("/images/jarvis_round.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Image getImages(String input) {
        return new Image(this.getClass().getResourceAsStream(input));
    }

    public void setCcaText() {
        text1.setText("Cca Tracker\n\n"
                + "Try the following commands:\n\n");

        text2.setText("add-cca\n"
                 + "delete-cca\n"
                 + "edit-cca\n"
                 + "list-cca\n"
                 + "find-cca\n"
                 + "add-progress\n"
                + "increment-progress");

    }

    public void setFinanceText() {
        text1.setText("Finance Tracker\n\n"
                + "Try the following commands:\n\n");
        text2.setText("add-paid\n"
                + "delete-paid\n"
                + "find-paid\n"
                + "add-install\n"
                + "delete-install\n"
                + "edit-install\n"
                + "set-limit\n"
                + "list-finances");
    }

    public void setCourseText() {
        text1.setText("Course Planner\n\n"
                + "Try the following commands:\n\n");
        text2.setText("add-course\n"
                + "delete-course\n"
                + "check\n"
                + "lookup\n"
                + "list-course\n"
                + "help-course\n"
                + "clear-course");

    }

    public void setPlannerText() {
        text1.setText("Planner\n\n"
                + "Try the following commands:\n\n");
        text2.setText("add-task\n"
                + "delete-task\n"
                + "done-task\n"
                + "find-task\n"
                + "list-task\n"
                + "list-schedule\n"
                + "pull-task\n");

    }
}
