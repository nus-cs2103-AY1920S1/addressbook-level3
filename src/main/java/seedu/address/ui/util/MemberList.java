package seedu.address.ui.util;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import seedu.address.ui.UiPart;

import java.util.List;

/**
 * A class to show the members of a group.
 */
public class MemberList extends UiPart<Region> {

    private static final String FXML = "MemberList.fxml";
    private static final double NAME_SPACE_PERCENT = 25;
    private static final double EMAIL_SPACE_PERCENT = 35;
    private static final double ROLE_SPACE_PERCENT = 40;

    @FXML
    private StackPane listHeaders;

    @FXML
    private VBox listMembers;

    @FXML
    private ScrollPane listHeaderContainer;

    @FXML
    private ScrollPane listMemberContainer;

    public MemberList(List<String> members, List<String> colors) {
        super(FXML);
        listHeaders.getChildren().add(generateHeaders());
        for (int i = 0; i < members.size(); i++) {
            MemberCard memberCard = new MemberCard(members.get(i), colors.get(i));
            listMembers.getChildren().add(memberCard.getRoot());
        }
        listHeaderContainer.hvalueProperty().bindBidirectional(listMemberContainer.hvalueProperty());
    }

    /**
     * Method to generate table headers.
     * @return GridPane representation of the headers.
     */
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

    class MemberCard extends UiPart<Region> {
        private static final String FXML = "MemberCard.fxml";

        @FXML
        private GridPane memberCard;
        @FXML
        private Label memberName;
        @FXML
        private Label memberEmail;
        @FXML
        private Label memberRole;
        @FXML
        private Region colorRegion;

        public MemberCard(String name, String color) {
            super(FXML);
            this.memberName.setText(name);
            this.memberEmail.setText("MY EMAIL");
            this.memberRole.setText("MY ROLE");
            setUpCard(color);
        }

        private GridPane setUpCard(String color) {
            memberCard.setStyle("-fx-background-color: white;"
                    + "-fx-border-radius: 5;"
                    + "-fx-background-radius: 5;"
                    + "-fx-pref-height: 50;");
            DropShadow shadowEffect = new DropShadow();
            shadowEffect.setOffsetY(3);
            shadowEffect.setOffsetX(3);
            memberCard.setEffect(shadowEffect);
            colorRegion.setStyle("-fx-background-color:" + color + ";"
                    + "-fx-border-radius: 2;"
                    + "-fx-background-radius: 2;"
                    + "-fx-min-height: 50;");
            return memberCard;
        }
    }
}
