package seedu.address.ui.quiz;

import static javafx.scene.paint.Color.GREENYELLOW;
import static javafx.scene.paint.Color.ORANGE;
import static javafx.scene.paint.Color.RED;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import seedu.address.model.quiz.person.Question;

/**
 * An UI component that displays information of a {@code Question}.
 */
public class QuestionCard extends UiPart<Region> {

    private static final String FXML = "QuestionListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Question question;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label answer;
    @FXML
    private Label type;
    @FXML
    private Label category;
    @FXML
    private Circle circleType;
    @FXML
    private FlowPane tags;

    public QuestionCard(Question question, int displayedIndex, boolean showAnswer) {
        super(FXML);
        this.question = question;
        id.setText(displayedIndex + ". ");
        name.setText(question.getName().fullName);

        if (showAnswer) {
            answer.setText(question.getAnswer().value);
        } else {
            answer.setText(question.getMockAnswer().value);
        }

        String typeContent = question.getType().value;
        type.setText(typeContent);

        if (typeContent.equals("high")) {
            circleType.setFill(RED);
        } else if (typeContent.equals("normal")) {
            circleType.setFill(ORANGE);
        } else {
            circleType.setFill(GREENYELLOW);
        }

        category.setText(question.getCategory().value);
        question.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof QuestionCard)) {
            return false;
        }

        // state check
        QuestionCard card = (QuestionCard) other;
        return id.getText().equals(card.id.getText())
                && question.equals(card.question);
    }
}
