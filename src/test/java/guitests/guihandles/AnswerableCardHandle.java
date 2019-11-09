package guitests.guihandles;

import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.revision.model.answerable.Answerable;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class AnswerableCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String QUESTION_TYPE_FIELD_ID = "#questionType";
    private static final String QUESTION_FIELD_ID = "#question";
    private static final String DIFFICULTY_FIELD_ID = "#difficulty";
    private static final String ANSWERS_FIELD_ID = "#answerPane";
    private static final String CATEGORIES_FIELD_ID = "#categories";

    private final Label idLabel;
    private final Label questionTypeLabel;
    private final Label questionLabel;
    private final Label difficultyLabel;
    private final List<Label> answerLabels;
    private final List<Label> categoryLabels;

    public AnswerableCardHandle(Node cardNode) {
        super(cardNode);

        idLabel = getChildNode(ID_FIELD_ID);
        questionTypeLabel = getChildNode(QUESTION_TYPE_FIELD_ID);
        questionLabel = getChildNode(QUESTION_FIELD_ID);
        difficultyLabel = getChildNode(DIFFICULTY_FIELD_ID);
        Region answersContainer = getChildNode(ANSWERS_FIELD_ID);
        answerLabels = answersContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());

        Region categoriesContainer = getChildNode(CATEGORIES_FIELD_ID);
        categoryLabels = categoriesContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getQuestionType() {
        return questionTypeLabel.getText();
    }

    public String getQuestion() {
        return questionLabel.getText();
    }

    public String getDifficulty() {
        return difficultyLabel.getText();
    }

    public List<String> getCombinedAnswerList() {
        return answerLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
    public List<String> getCategories() {
        return categoryLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Returns true if this handle contains {@code person}.
     */
    public boolean equals(Answerable answerable) {
        return getQuestion().equals(answerable.getQuestion().question)
                && getDifficulty().equals(answerable.getDifficulty().difficulty)
                && getCombinedAnswerList().equals(answerable.getCombinedAnswerList().stream()
                        .map(a -> a.answer)
                        .sorted()
                        .collect(Collectors.toList()))
                && getCategories().equals(answerable.getCategories().stream()
                        .map(cat -> cat.category)
                        .sorted()
                        .collect(Collectors.toList()));
    }
}
