package budgetbuddy.ui.card;

import budgetbuddy.model.rule.Rule;
import budgetbuddy.model.rule.RuleAction;
import budgetbuddy.model.rule.RulePredicate;
import budgetbuddy.model.rule.expression.ActionExpression;
import budgetbuddy.model.rule.expression.PredicateExpression;
import budgetbuddy.model.rule.script.ActionScript;
import budgetbuddy.model.rule.script.PredicateScript;
import budgetbuddy.ui.UiPart;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Rule}.
 */
public class RuleCard extends UiPart<Region> {

    private static final String FXML = "RuleCard.fxml";

    public final Rule rule;

    @FXML
    private Label id;
    @FXML
    private Label predicatePrefix;
    @FXML
    private Label predicateDelimiter;
    @FXML
    private Label actionPrefix;
    @FXML
    private Label actionDelimiter;
    @FXML
    private FlowPane predicateFlow;
    @FXML
    private FlowPane actionFlow;

    public RuleCard(Rule rule, int displayedIndex) {
        super(FXML);
        this.rule = rule;
        id.setText("Rule #" + displayedIndex + ": ");

        RulePredicate predicate = rule.getPredicate();
        predicatePrefix.setText("IF");
        predicateDelimiter.setText(":");
        if (predicate.getType().equals(Rule.TYPE_EXPRESSION)) {
            PredicateExpression predExpr = (PredicateExpression) predicate;
            Label attr = new Label(predExpr.getAttribute().toString());
            Label op = new Label(predExpr.getOperator().toString());
            Label val = new Label(predExpr.getValue().toString());

            attr.setId("predAttr");
            op.setId("predOp");
            val.setId("predVal");
            predicateFlow.getChildren().addAll(attr, op, val);
        } else {
            PredicateScript predicateScript = (PredicateScript) predicate;
            Label name = new Label(predicateScript.getScriptName().toString());
            Label suffix = new Label("(script)");

            name.setId("predScript");
            suffix.setId("predSuffix");
            predicateFlow.getChildren().addAll(name, suffix);
        }

        RuleAction action = rule.getAction();
        actionPrefix.setText("THEN");
        actionDelimiter.setText(":");
        if (action.getType().equals(Rule.TYPE_EXPRESSION)) {
            ActionExpression actExpr = (ActionExpression) action;
            Label op = new Label(actExpr.getOperator().toString());
            op.setId("actOp");
            actionFlow.getChildren().add(op);

            String valStr = actExpr.getValue().toString();
            if (!valStr.isEmpty()) {
                Label val = new Label(valStr);
                val.setId("actVal");
                actionFlow.getChildren().add(val);
            }
        } else {
            ActionScript actionScript = (ActionScript) action;
            Label name = new Label(actionScript.getScriptName().toString());
            Label suffix = new Label("(script)");


            name.setId("actScript");
            suffix.setId("actSuffix");
            actionFlow.getChildren().addAll(name, suffix);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RuleCard)) {
            return false;
        }

        // state check
        RuleCard card = (RuleCard) other;
        return id.getText().equals(card.id.getText())
                && rule.equals(card.rule);
    }
}
