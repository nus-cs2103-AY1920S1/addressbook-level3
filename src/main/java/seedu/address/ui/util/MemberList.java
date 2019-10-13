package seedu.address.ui.util;

import java.util.List;

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

/**
 * A class to show the members of a group.
 */
public class MemberList extends UiPart<Region> {

    private static final String FXML = "MemberList.fxml";

    @FXML
    private GridPane listHeaders;

    @FXML
    private VBox listMembers;

    @FXML
    private ScrollPane listHeaderContainer;

    @FXML
    private ScrollPane listMemberContainer;

    public MemberList(List<String> members, List<String> colors) {
        super(FXML);
        for (int i = 0; i < members.size(); i++) {
            MemberCard memberCard = new MemberCard(members.get(i), colors.get(i));
            listMembers.getChildren().add(memberCard.getRoot());
        }
        listHeaderContainer.hvalueProperty().bindBidirectional(listMemberContainer.hvalueProperty());
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
