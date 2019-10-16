package seedu.address.ui.modules;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;
import seedu.address.ui.UiPart;


/**
 * Panel containing the list of cards.
 * The difference with {@code CardListPanel} is that this is not scrollable.
 */
public class CardBoxPanel extends UiPart<Region> {
    private static final String FXML = "CardBoxPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(CardListPanel.class);

    @FXML
    private VBox cardBoxView;

    public CardBoxPanel(ObservableList<Card> cardList) {
        super(FXML);
        List<Node> nodes = IntStream
                .range(0, cardList.size())
                .mapToObj(x -> {
                    Node node =  new CardCard(cardList.get(x), x + 1).getRoot();
                    if (x % 2 == 0) {
                        node.setId("cardPaneEven"); // to set the colors of the boxes
                    } else {
                        node.setId("cardPaneOdd");
                    }
                    return node;
                })
                .collect(Collectors.toList());
        cardBoxView.getChildren().addAll(nodes);
    }
}
