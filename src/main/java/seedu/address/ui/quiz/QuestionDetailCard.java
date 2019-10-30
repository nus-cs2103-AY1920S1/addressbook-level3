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
import javafx.scene.text.Text;
import seedu.address.model.quiz.person.Question;

/**
 * An UI component that displays information of a {@code Question}.
 */
public class QuestionDetailCard extends UiPart<Region> {

    private static final String FXML = "QuestionDetailCard.fxml";

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
    private Text name;
    @FXML
    private Label id;
    @FXML
    private Label answer;
    @FXML
    private Label type;
    @FXML
    private Label category;
    @FXML
    private Text comment;
    @FXML
    private Circle circleType;
    @FXML
    private FlowPane tags;

    public QuestionDetailCard(Question question, int displayedIndex) {
        super(FXML);
        this.question = question;
        id.setText("Filtered Question  #" + displayedIndex + "  ");
        name.setText(question.getName().fullName);
        name.wrappingWidthProperty().set(800);

        if (question.getComment() == null) {
            comment.setText("No explanation yet");
        } else {
            comment.setText(question.getComment().value);
        }

        comment.wrappingWidthProperty().set(500);
        answer.setText(question.getAnswer().value);

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
        if (!(other instanceof QuestionDetailCard)) {
            return false;
        }

        // state check
        QuestionDetailCard card = (QuestionDetailCard) other;
        return id.getText().equals(card.id.getText())
                && question.equals(card.question);
    }
}
