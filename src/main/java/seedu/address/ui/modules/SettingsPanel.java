//package seedu.address.ui.modules;
//
//import javafx.fxml.FXML;
//import javafx.scene.Node;
//import javafx.scene.control.RadioButton;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.StackPane;
//import seedu.address.ui.UiPart;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class SettingsPanel extends UiPart<Region> {
//    private static final String FXML = "SettingsPanel.fxml";
//
//    @FXML
//    private HBox difficultyOptions;
//
//    @FXML
//    private StackPane themeOptions;
//
//    @FXML
//    private StackPane hintsOptions;
//
//    @FXML
//    private StackPane profilePicture;
//
//    public SettingsPanel() {
//        super(FXML);
//        addOptions(difficultyOptions, "radioButton", "Easy", "Medium", "Hard");
//
//    }
//
//    private void addOptions(Node field, String optionType, String... names) {
//        if (optionType.equals("radioButton")) {
//            SettingsRadioButton radioButtons = new SettingsRadioButton();
//            for (String labelTitle: names) {
//                radioButtons.addRadioButton(labelTitle);
//            }
//            ((HBox)field).getChildren().add(radioButtons.getRoot());
//        }
//    }
//
//}
