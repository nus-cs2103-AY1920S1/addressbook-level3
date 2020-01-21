package seedu.algobase.ui;

import java.util.logging.Logger;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.text.TextAlignment;
import seedu.algobase.commons.core.LogsCenter;
import seedu.algobase.logic.parser.ParserUtil;
import seedu.algobase.model.ModelType;
import seedu.algobase.model.plan.Plan;
import seedu.algobase.model.plan.PlanDescription;
import seedu.algobase.ui.action.UiActionDetails;
import seedu.algobase.ui.action.UiActionExecutor;
import seedu.algobase.ui.action.UiActionType;

/**
 * An UI component that displays information of a {@code Plan}.
 */
public class PlanCard extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(PlanCard.class);
    private static final String FXML = "PlanListCard.fxml";

    public final Plan plan;
    private final int planIndex;

    @FXML
    private HBox cardPane;
    @FXML
    private Label id;
    @FXML
    private Label planName;
    @FXML
    private Label planDescription;
    @FXML
    private Label startDate;
    @FXML
    private Label endDate;

    public PlanCard(Plan plan, int displayedIndex, UiActionExecutor uiActionExecutor) {
        super(FXML);
        this.planIndex = displayedIndex - 1;
        this.plan = plan;

        // Index
        id.setText(displayedIndex + ". ");
        id.setWrapText(true);
        id.setTextAlignment(TextAlignment.JUSTIFY);

        // Plan Name
        planName.setText(plan.getPlanName().fullName + " (" + plan.getTasks().size() + " tasks)");
        planName.setWrapText(true);
        planName.setTextAlignment(TextAlignment.JUSTIFY);

        // Plan Description
        if (!PlanDescription.isDefaultDescription(plan.getPlanDescription().value)) {
            planDescription.setText(plan.getPlanDescription().value);
        } else {
            planDescription.setText("Not specified");
            planDescription.setStyle("-fx-text-fill: grey;");
        }
        planDescription.setWrapText(true);
        planDescription.setTextAlignment(TextAlignment.JUSTIFY);

        // Start Date
        startDate.setText(plan.getStartDate().format(ParserUtil.FORMATTER));
        startDate.setWrapText(true);
        startDate.setTextAlignment(TextAlignment.JUSTIFY);

        // End Date
        endDate.setText(plan.getEndDate().format(ParserUtil.FORMATTER));
        endDate.setWrapText(true);
        endDate.setTextAlignment(TextAlignment.JUSTIFY);

        addMouseClickListener(uiActionExecutor);
    }

    @Override
    public boolean equals(Object other) {
        // checks if same object
        if (other == this) {
            return true;
        }

        // checks if object of same class
        // handles null
        if (!(other instanceof PlanCard)) {
            return false;
        }

        // check fields equality
        PlanCard card = (PlanCard) other;
        return id.getText().equals(card.id.getText())
                && plan.equals(card.plan);
    }

    /**
     * Spawns a new Tab when the cardPane registers a double click event.
     *
     * @param uiActionExecutor The executor for the given UI action
     */
    public void addMouseClickListener(UiActionExecutor uiActionExecutor) {
        cardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                    if (mouseEvent.getClickCount() == 2) {
                        logger.info("Double Clicked on Problem card with name " + plan.getPlanName());
                        logger.info(
                            "Creating new UiActionDetails with type " + UiActionType.OPEN_DETAILS_TAB
                                + " with a ModelType of " + ModelType.PLAN
                                + " with ID of " + plan.getId()
                        );

                        uiActionExecutor.execute(new UiActionDetails(
                            UiActionType.OPEN_DETAILS_TAB,
                            ModelType.PLAN,
                            plan.getId()
                        ));

                        logger.info(
                            "Creating new UiActionDetails with type " + UiActionType.SET_PLAN
                                + " with ID of " + plan.getId()
                        );
                        uiActionExecutor.execute(new UiActionDetails(
                            UiActionType.SET_PLAN,
                            plan.getId()
                        ));
                    }
                }
            }
        });
    }
}
