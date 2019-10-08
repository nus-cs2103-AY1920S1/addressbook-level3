package tagline.ui;

import static java.util.Objects.requireNonNull;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import tagline.ui.CommandBox.CommandExecutor;

/**
 * The UI component that stores the chat window.
 */
public class ChatPane extends UiPart<GridPane> {

    private static final String FXML = "ChatPane.fxml";
    private static final String TAGLINE_RESPONSE_IMAGE = "/images/tagline_icon.png";

    private CommandBox commandBox;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private ScrollPane dialogScrollPane;

    @FXML
    private VBox dialogContainer;

    private Image image = new Image(getClass().getResourceAsStream(TAGLINE_RESPONSE_IMAGE));

    public ChatPane(CommandExecutor commandExecutor) {
        super(FXML);

        commandBox = new CommandBox(commandExecutor);
        commandBox.getRoot().prefWidthProperty().bind(getRoot().widthProperty());
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        //setting vvalue to 1.0 doesn't work
        dialogScrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /**
     * Displays a new command from the user in the chat window.
     * @param commandFromUser Command entered by user
     */
    public void setCommandFromUser(String commandFromUser) {
        requireNonNull(commandFromUser);

        //Don't display empty messages
        if (commandFromUser.isEmpty()) {
            return;
        }

        dialogContainer.getChildren().add(DialogBox.getCommandDialog(commandFromUser).getRoot());
    }

    /**
     * Displays the feedback returned by TagLine in the chat window.
     * @param feedbackToUser Feedback to user
     */
    public void setFeedbackToUser(String feedbackToUser) {
        requireNonNull(feedbackToUser);
        dialogContainer.getChildren().add(DialogBox.getResponseDialog(feedbackToUser, image).getRoot());
    }
}
