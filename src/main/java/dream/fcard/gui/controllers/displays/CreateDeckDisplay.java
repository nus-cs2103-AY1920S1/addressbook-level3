package dream.fcard.gui.controllers.displays;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.gui.controllers.windows.CardCreatingWindow;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.DuplicateInChoicesException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * This class is used for editing an existing deck as well as creating a new deck.
 */
public class CreateDeckDisplay extends AnchorPane {
    @FXML
    private TextField deckNameInput;
    @FXML
    private Button onSaveDeck;
    @FXML
    private Button cancelButton;
    @FXML
    private Label deckName;
    @FXML
    private Label deckSize;
    @FXML
    private ScrollPane cardCreatingPane;

    private int numCards = 0;
    private CardCreatingWindow editingWindow;

    private String deckNameString;
    private String front;
    private String back;
    private ArrayList<String> choices;

    private boolean hasFront;
    private boolean hasBack;
    private boolean hasChoice;

    private final String frontBack = "Front-back";
    private final String mcq = "MCQ";
    //private final String java = "Java";
    private final String js = "JavaScript";

    private Consumer<Integer> incrementNumCards = x -> {
        ++numCards;
        deckSize.setText(numCards + (numCards == 1 ? " card" : " cards"));
    };
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> exitEditingMode = State.getState().getConsumer(ConsumerSchema.DISPLAY_DECKS);
    @SuppressWarnings("unchecked")
    private Consumer<String> displayMessage = State.getState().getConsumer(ConsumerSchema.DISPLAY_MESSAGE);
    @SuppressWarnings("unchecked")
    private Consumer<Boolean> clearMessage = State.getState().getConsumer(ConsumerSchema.CLEAR_MESSAGE);
    //private Consumer<Boolean> exitCreate = State.getState().getConsumer(ConsumerSchema.EXIT_CREATE);

    /**
     * Creates the form required to add questions to a deck.
     *
     */
    public CreateDeckDisplay() {
        try {
            clearMessage.accept(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/"
                    + "CreateDeckDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            editingWindow = new CardCreatingWindow(incrementNumCards);
            cardCreatingPane.setContent(editingWindow);
            onSaveDeck.setOnAction(e -> onSaveDeck());
            cancelButton.setOnAction(e -> exitEditingMode.accept(true));
        } catch (IOException e) {
            //TODO: replace or augment with a logger
            e.printStackTrace();
        }
    }

    /**
     * Note that the temporary deck is inside CardCreatingWindow. This method pulls that Deck object out and saves it
     * to the State.
     */
    public void onSaveDeck() {
        if (editingWindow != null) {
            Deck deck = editingWindow.getTempDeck();
            if (deck.getCards().size() == 0) {
                displayMessage.accept("No cards made. Exiting deck creation mode.");
            } else {
                String deckName = deckNameInput.getText();
                if (deckName.isBlank()) {
                    displayMessage.accept("You need to give your deck a name!");
                    return;
                }
                deck.setDeckName(deckName);
                State.getState().addDeck(deck);
                displayMessage.accept("Your new deck has been created!");
            }
            exitEditingMode.accept(true);
        }
    }

    public void processInput(String input) {
        hasFront = hasFront(input);
        hasBack = hasBack(input);
        hasChoice = hasChoice(input);

        boolean success;
        if (!hasChoice) {
            success = parseInputOneShot(input);
        } else {
            success = parseInputWithChoice(input);
        }

        if (!success) {
            displayMessage.accept("MCQ card creation failed.");
        }

        try {
            Deck deck = editingWindow.getTempDeck();

            if (!hasChoice) {
                editingWindow.setCardType(frontBack);
                editingWindow.setQuestionFieldText(front);
                editingWindow.setAnswerFieldText(back);
                editingWindow.publicAddCard();
            } else {
                deck.addNewCard(new MultipleChoiceCard(front, back, choices));
            }

            //LogsCenter.getLogger(CreateCommand.class).info("DECK_CREATE_REG_CARD: Card added to " + deckName);

        } catch (DuplicateInChoicesException e) {
            displayMessage.accept("Duplicate found in choices.");
        } catch (IndexNotFoundException e) {
            displayMessage.accept("Answer not valid.");
        } catch (NumberFormatException n) {
            displayMessage.accept("Answer not valid.");
        }
    }

    /**
     *
     *
     * @param commandInput
     * @return
     */
    private boolean parseInputWithChoice(String commandInput) {
        String userInput = commandInput.replaceFirst("create deck/", "");

        String[] userCardFields;
        if (hasBack && hasFront) {
            String[] userInputFields = userInput.trim().split(" front/");
            //String newDeckName = userInputFields[0];
            userCardFields = userInputFields[1].trim().split(" back/");
            front = userCardFields[0];

            userCardFields = userCardFields[1].trim().split(" choice/");
            back = userCardFields[0];
        } else {
            return false;
        }

        choices = new ArrayList<>();
        for (int i = 1; i < userCardFields.length; i++) {
            choices.add(userCardFields[i]);
        }

        if (choices.size() <= 1) {
            displayMessage.accept("Too few choices provided");
            return false;
        }

        return true;
    }

    private boolean parseInputOneShot(String commandInput) {
        String userInput = commandInput.replaceFirst("create deck/", "");

        if (hasBack && hasFront) {
            //String[] userInputFields = userInput.trim().split("front/");

            //deckName = userInputFields[0];

            //String[] userCardFields = userInputFields[1].trim().split("back/");

            front = commandInput.split("front/")[1].split("back/")[0].strip();
            back = commandInput.split("back/")[1].split("front/")[0].strip();
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param input
     * @return
     */
    private boolean hasFront(String input) {
        return input.contains("front/");
    }

    /**
     *
     * @param input
     * @return
     */
    private boolean hasBack(String input) {
        return input.contains("back/");
    }

    /**
     *
     * @param input
     * @return
     */
    private boolean hasChoice(String input) {
        return input.contains("choice/");
    }

}
