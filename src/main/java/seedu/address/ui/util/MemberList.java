package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import seedu.address.ui.UiPart;

public class MemberList extends UiPart<Region> {

    private static final String FXML = "MemberList.fxml";
    private static final double NAME_SPACE_PERCENT = 25;
    private static final double EMAIL_SPACE_PERCENT = 45;
    private static final double ROLE_SPACE_PERCENT = 30;

    @FXML
    private StackPane listHeaders;

    @FXML
    private VBox listMembers;

    @FXML
    private ScrollPane listHeaderContainer;

    @FXML
    private ScrollPane listMemberContainer;

    public MemberList() {
        super(FXML);
        listHeaders.getChildren().add(generateHeaders());
        listMembers.getChildren().addAll(new MemberCard("One").getCard(), new MemberCard("Two").getCard());
        //listHeaderContainer.setContent(listHeaders);
        //listMemberContainer.setContent(listMembers);
        listHeaderContainer.hvalueProperty().bindBidirectional(listMemberContainer.hvalueProperty());
    }

    private GridPane generateHeaders() {
        GridPane result = new GridPane();
        ColumnConstraints nameColumnConstraint = new ColumnConstraints();
        nameColumnConstraint.setPercentWidth(NAME_SPACE_PERCENT);
        ColumnConstraints emailColumnConstraint = new ColumnConstraints();
        emailColumnConstraint.setPercentWidth(EMAIL_SPACE_PERCENT);
        ColumnConstraints roleColumnConstraint = new ColumnConstraints();
        roleColumnConstraint.setPercentWidth(ROLE_SPACE_PERCENT);
        result.getColumnConstraints().addAll(nameColumnConstraint, emailColumnConstraint, roleColumnConstraint);
        Label nameHeader = new Label("Name");
        Label emailHeader = new Label("Email");
        Label roleHeader = new Label("Role");
        result.add(nameHeader, 0, 0);
        result.add(emailHeader, 1, 0);
        result.add(roleHeader, 2, 0);
        result.setStyle("-fx-border-color: lightgrey; -fx-border-width: 2; -fx-border-style: hidden hidden solid hidden;");
        return result;
    }

    class MemberCard {
        private static final double NAME_SPACE_PERCENT = 25;
        private static final double EMAIL_SPACE_PERCENT = 45;
        private static final double ROLE_SPACE_PERCENT = 30;

        private String name;
        public MemberCard(String name) {
            this.name = name;
        }

        public GridPane getCard() {
            GridPane card = new GridPane();
            ColumnConstraints nameColumnConstraint = new ColumnConstraints();
            nameColumnConstraint.setPercentWidth(NAME_SPACE_PERCENT);
            ColumnConstraints emailColumnConstraint = new ColumnConstraints();
            emailColumnConstraint.setPercentWidth(EMAIL_SPACE_PERCENT);
            ColumnConstraints roleColumnConstraint = new ColumnConstraints();
            roleColumnConstraint.setPercentWidth(ROLE_SPACE_PERCENT);
            card.getColumnConstraints().addAll(nameColumnConstraint, emailColumnConstraint, roleColumnConstraint);
            Label name = new Label(this.name);
            Label email = new Label("My email");
            Label role = new Label("My role");
            card.add(name, 0, 0);
            card.add(email, 1, 0);
            card.add(role, 2, 0);
            card.setStyle("-fx-background-color: white;");
            DropShadow shadowEffect = new DropShadow();
            shadowEffect.setOffsetY(3);
            shadowEffect.setOffsetX(3);
            card.setEffect(shadowEffect);
            return card;
        }
    }
}
