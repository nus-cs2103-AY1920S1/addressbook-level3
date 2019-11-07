package seedu.algobase.ui;

import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.tag.Tag;
import seedu.algobase.storage.SaveStorageRunnable;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionType;

/**
 * An UI component that displays information of a {@code Problem}.
 */
public class ProblemCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(ProblemCard.class);

    private static final String FXML = "ProblemListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Problem problem;
    private final UiActionExecutor uiActionExecutor;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label name;
    @FXML
    private Label author;
    @FXML
    private Label description;
    @FXML
    private Label weblink;
    @FXML
    private Label difficulty;
    @FXML
    private Label remark;
    @FXML
    private Label source;
    @FXML
    private FlowPane tags;

    public ProblemCard(Problem problem, int displayedIndex, UiActionExecutor uiActionExecutor) {
        super(FXML);
        this.problem = problem;
        this.uiActionExecutor = uiActionExecutor;

        id.setText(displayedIndex + ". ");
        id.setWrapText(true);
        id.setTextAlignment(TextAlignment.JUSTIFY);
        name.setText(problem.getName().fullName);
        name.setWrapText(true);
        name.setTextAlignment(TextAlignment.JUSTIFY);
        author.setText(problem.getAuthor().value);
        author.setWrapText(true);
        author.setTextAlignment(TextAlignment.JUSTIFY);
        description.setText(problem.getDescription().value);
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);
        weblink.setText(problem.getWebLink().value);
        weblink.setWrapText(true);
        weblink.setTextAlignment(TextAlignment.JUSTIFY);
        difficulty.setText(problem.getDifficulty().toString());
        difficulty.setWrapText(true);
        difficulty.setTextAlignment(TextAlignment.JUSTIFY);
        remark.setText(problem.getRemark().value);
        remark.setWrapText(true);
        remark.setTextAlignment(TextAlignment.JUSTIFY);
        source.setText(problem.getSource().value);

        for (Tag tag : problem.getTags()) {
            Label l = new Label(tag.getName());
            String colorStyle = "-fx-background-color: " + tag.getColor();
            l.setStyle(colorStyle);
            tags.getChildren().add(l);
        }
        source.setWrapText(true);
        source.setTextAlignment(TextAlignment.JUSTIFY);
        this.addMouseClickListener(writeOnlyTabManager, saveStorageRunnable);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ProblemCard)) {
            return false;
        }

        // state check
        ProblemCard card = (ProblemCard) other;
        return id.getText().equals(card.id.getText())
                && problem.equals(card.problem);
    }

    /**
     * Spawns a new Tab when the cardPane registers a double click event.
     */
    public void addMouseClickListener() {
        cardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        logger.info("Double Clicked on Problem card with name " + problem.getName());
                        uiActionExecutor.execute(new UiActionDetails(
                            UiActionType.OPEN_DETAILS_TAB,
                            ModelType.PROBLEM,
                            problem.getId()
                        ));
                    }
                }
            }
        });
    }
}
