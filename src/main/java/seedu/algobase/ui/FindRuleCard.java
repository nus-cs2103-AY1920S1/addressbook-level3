package seedu.algobase.ui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import seedu.algobase.model.searchrule.problemsearchrule.DifficultyIsInRangePredicate;
import seedu.algobase.model.searchrule.problemsearchrule.ProblemSearchRule;

/**
 * An UI component that displays information of a {@code FindRule}.
 */
public class FindRuleCard extends UiPart<Region> {

    private static final String FXML = "FindRuleListCard.fxml";
    private static final String DEFAULT_PREDICATE = "No restriction.";

    public final ProblemSearchRule findRule;

    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label ruleName;
    @FXML
    private Label problemName;
    @FXML
    private Label author;
    @FXML
    private Label description;
    @FXML
    private Label difficulty;
    @FXML
    private Label source;
    @FXML
    private Label tag;

    public FindRuleCard(ProblemSearchRule findRule, int displayedIndex) {
        super(FXML);
        this.findRule = findRule;
        id.setText(displayedIndex + ". ");
        id.setWrapText(true);
        id.setTextAlignment(TextAlignment.JUSTIFY);
        ruleName.setText(findRule.getName().name);
        ruleName.setWrapText(true);
        ruleName.setTextAlignment(TextAlignment.JUSTIFY);

        if (findRule.getNamePredicate().isEmpty()) {
            problemName.setText(DEFAULT_PREDICATE);
        } else {
            problemName.setText(findRule.getNamePredicate().get().getKeywords().toString());
        }
        problemName.setWrapText(true);
        problemName.setTextAlignment(TextAlignment.JUSTIFY);

        if (findRule.getAuthorPredicate().isEmpty()) {
            author.setText(DEFAULT_PREDICATE);
        } else {
            author.setText(findRule.getAuthorPredicate().get().getKeyword().keyword);
        }
        author.setWrapText(true);
        author.setTextAlignment(TextAlignment.JUSTIFY);

        if (findRule.getDescriptionPredicate().isEmpty()) {
            description.setText(DEFAULT_PREDICATE);
        } else {
            description.setText(findRule.getDescriptionPredicate().get().getKeywords().toString());
        }
        description.setWrapText(true);
        description.setTextAlignment(TextAlignment.JUSTIFY);

        if (findRule.getDifficultyPredicate().isEmpty()) {
            difficulty.setText(DEFAULT_PREDICATE);
        } else {
            DifficultyIsInRangePredicate difficultyPredicate = findRule.getDifficultyPredicate().get();
            double lowerBound = difficultyPredicate.getLowerBound();
            double upperBound = difficultyPredicate.getUpperBound();
            String difficultyString = String.format("%.2f - %.2f", lowerBound, upperBound);
            difficulty.setText(difficultyString);
        }
        difficulty.setWrapText(true);
        difficulty.setTextAlignment(TextAlignment.JUSTIFY);

        if (findRule.getSourcePredicate().isEmpty()) {
            source.setText(DEFAULT_PREDICATE);
        } else {
            source.setText(findRule.getSourcePredicate().get().getKeyword().keyword);
        }
        source.setWrapText(true);
        source.setTextAlignment(TextAlignment.JUSTIFY);

        if (findRule.getTagPredicate().isEmpty()) {
            tag.setText(DEFAULT_PREDICATE);
        } else {
            tag.setText(findRule.getTagPredicate().get().getKeywords().toString());
        }
        tag.setWrapText(true);
        tag.setTextAlignment(TextAlignment.JUSTIFY);

    }

    /**
     * Initialize the GUI and make sure that all expected components are present.
     */
    @FXML
    void initialize() {
        assert cardPane != null
                : "fx:id=\"cardPane\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert id != null
                : "fx:id=\"id\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert ruleName != null
                : "fx:id=\"ruleName\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert problemName != null
                : "fx:id=\"problemName\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert author != null
                : "fx:id=\"author\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert description != null
                : "fx:id=\"description\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert difficulty != null
                : "fx:id=\"difficulty\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert source != null
                : "fx:id=\"source\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
        assert tag != null : "fx:id=\"tag\" was not injected: check your FXML file 'FindRuleListCard.fxml'.";
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindRuleCard)) {
            return false;
        }

        // state check
        FindRuleCard card = (FindRuleCard) other;
        return id.getText().equals(card.id.getText())
                && findRule.equals(card.findRule);
    }
}
