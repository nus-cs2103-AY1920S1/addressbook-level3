package seedu.algobase.ui;

import java.util.Comparator;
import java.util.function.Consumer;
import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.model.Id;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.gui.WriteOnlyTabManager;
import seedu.algobase.model.problem.Problem;

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

    public ProblemCard(Problem problem, int displayedIndex, WriteOnlyTabManager writeOnlyTabManager) {
        super(FXML);
        this.problem = problem;
        id.setText(displayedIndex + ". ");
        name.setText(problem.getName().fullName);
        author.setText(problem.getAuthor().value);
        description.setText(problem.getDescription().value);
        weblink.setText(problem.getWebLink().value);
        difficulty.setText(problem.getDifficulty().toString());
        remark.setText(problem.getRemark().value);
        source.setText(problem.getSource().value);
        problem.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        this.addMouseClickListener(writeOnlyTabManager.addDetailsTabConsumer(ModelType.PROBLEM));
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
     *
     * @param addDetailsTabConsumer
     */
    public void addMouseClickListener(Consumer<Id> addDetailsTabConsumer) {
        cardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        logger.fine("Double Clicked");
                        addDetailsTabConsumer.accept(problem.getId());
                    }
                }
            }
        });
    }
}
