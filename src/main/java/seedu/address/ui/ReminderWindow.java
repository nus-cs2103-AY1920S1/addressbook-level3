package seedu.address.ui;

import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.model.reminder.Reminder;

/**
 * Controller for a help page
 */
public class ReminderWindow extends UiPart<Stage> {

    public static final String USERGUIDE_URL = "https://ay1920s1-cs2103t-f14-2.github.io/main/UserGuide.html";
    public static final String HELP_MESSAGE = "Reminders! Reminders will be placed inside this box ";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    private static final String FXML = "ReminderWindow.fxml";

    @FXML
    private Button copyButton;

    @FXML
    private Label reminder;

    @FXML
    private VBox vbox;

    @FXML
    private BorderPane border;

    private Logic logic;

    private ListView<Reminder> reminderListView;

    /**
     * Creates a new ReminderWindow.
     *
     * @param root Stage to use as the root of the HelpWindow.
     */
    public ReminderWindow(Stage root, Logic logic) {
        super(FXML, root);
        ListView<Reminder> reminderListView = new ListView<>();
        HBox hbox = addHBox();
        border.setTop(hbox);
        border.setCenter(reminderListView);
        reminderListView.setItems(logic.getFilteredReminderList());
        reminderListView.setPrefWidth(700);
        reminderListView.setCellFactory(listView -> new ReminderListViewCell());
        border.isResizable();
        border.setMargin(reminderListView, new Insets(5, 5, 5, 5));
    }
    //addStackPane(hbox);
    // Add stack to HBox in top region
    //border.setCenter(new GridPane());
    //border.setRight(new FlowPane());
    //hbox.getChildren().addAll(reminder);
    //reminder.setText(HELP_MESSAGE);

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
     */
    class ReminderListViewCell extends ListCell<Reminder> {

        @Override
        protected void updateItem(Reminder reminder, boolean empty) {
            super.updateItem(reminder, empty);

            if (empty || reminder == null) {
                setText(null);
            } else {
                setText(reminder.toWindowString());

            }
        }
    }

    /**
     * Creates a new ReminderWindow.
     */
    public ReminderWindow(Logic logic) {
        this(new Stage(), logic);
    }

    /**
     * Shows the help window.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        logger.fine("Showing help page about the application.");
        getRoot().show();
        getRoot().centerOnScreen();
    }

    /**
     * Returns true if the help window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the help window.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the help window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Copies the URL to the user guide to the clipboard.
     */
    @FXML
    private void copyUrl() {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent url = new ClipboardContent();
        url.putString(USERGUIDE_URL);
        clipboard.setContent(url);
    }

    /**
     * Creates a new HBox for the Title.
     */
    public HBox addHBox() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #3EB9E4;");

        Text title = new Text("Reminders");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        Button buttonCurrent = new Button("Current");
        buttonCurrent.setPrefSize(100, 20);

        Button buttonProjected = new Button("Projected");
        buttonProjected.setPrefSize(100, 20);

        hbox.getChildren().addAll(title);
        hbox.setAlignment(Pos.CENTER);

        return hbox;
    }

    /**
     * Creates a new StackPane for the Title.
     */
    public void addStackPane(HBox hb) {
        StackPane stack = new StackPane();
        Rectangle helpIcon = new Rectangle(30.0, 25.0);
        helpIcon.setFill(new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE,
                new Stop[]{
                    new Stop(0, Color.web("#4977A3")),
                    new Stop(0.5, Color.web("#B0C6DA")),
                    new Stop(1, Color.web("#9CB6CF"))}));
        helpIcon.setStroke(Color.web("#D0E6FA"));
        helpIcon.setArcHeight(3.5);
        helpIcon.setArcWidth(3.5);

        Text helpText = new Text("?");
        helpText.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
        helpText.setFill(Color.WHITE);
        helpText.setStroke(Color.web("#7080A0"));

        stack.getChildren().addAll(helpIcon, helpText);
        stack.setAlignment(Pos.CENTER_RIGHT); // Right-justify nodes in stack
        StackPane.setMargin(helpText, new Insets(0, 10, 0, 0)); // Center "?"

        hb.getChildren().add(stack); // Add to HBox from Example 1-2
        HBox.setHgrow(stack, Priority.ALWAYS); // Give stack any extra space
    }
}
