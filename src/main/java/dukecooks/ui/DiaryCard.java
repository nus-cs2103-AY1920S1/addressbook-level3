package dukecooks.ui;

import dukecooks.commons.util.AppUtil;
import dukecooks.model.diary.components.Diary;
import dukecooks.model.diary.components.Page;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * An UI component that displays information of a {@code Diary}.
 */
public class DiaryCard extends UiPart<Region> {

    private static final String FXML = "DiaryListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Diary diary;

    @FXML
    private HBox cardPane;

    @FXML
    private Label diaryName;

    @FXML
    private Label id;

    @FXML
    private FlowPane pages;

    @FXML
    private ImageView icon;

    public DiaryCard(Diary diary, int displayedIndex) {
        super(FXML);
        this.diary = diary;
        id.setText(displayedIndex + ". ");
        diaryName.setText(diary.getDiaryName().fullName);

        ObservableList<Page> pageList = diary.getPages();
        for (int i = 0; i < pageList.size(); i++) {
            Page currentPage = pageList.get(i);
            Label pageLabel = new Label(currentPage.getTitle().toString());

            if (currentPage.getPageType().toString().equals("food")) {
                // Set label background to yellow & food image
                pageLabel.setStyle("-fx-background-color: #fff176;");
                icon = new ImageView(AppUtil.getImage("/images/food.png"));
            } else if (currentPage.getPageType().toString().equals("health")) {
                // Set label background to pink & health image
                pageLabel.setStyle("-fx-background-color: #f8bbd0;");
                icon = new ImageView(AppUtil.getImage("/images/hearticon.png"));
            } else {
                // Set label background to green & exercise image
                pageLabel.setStyle("-fx-background-color: #aed581;");
                icon = new ImageView(AppUtil.getImage("/images/strong.png"));
            }

            icon.setFitWidth(20);
            icon.setFitHeight(20);
            pageLabel.setGraphic(icon);
            pages.getChildren().add(pageLabel);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DiaryCard)) {
            return false;
        }

        // state check
        DiaryCard card = (DiaryCard) other;
        return id.getText().equals(card.id.getText())
                && diary.equals(card.diary);
    }
}
