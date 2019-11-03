package dream.fcard.gui.controllers.displays.createandeditdeck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;

import dream.fcard.gui.controllers.displays.createandeditdeck.mcqcard.McqOptionsSetter;
import dream.fcard.gui.controllers.windows.CardCreatingWindow;
import dream.fcard.gui.controllers.windows.MainWindow;
import dream.fcard.logic.respond.ConsumerSchema;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 * This class is used for editing an existing deck as well as creating a new deck.
 */
public class CreateDeckDisplay extends VBox {
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
    private VBox cardCreatingPane;

    private int numCards = 0;
    private CardCreatingWindow editingWindow;

    private String deckNameString;
    private String front;
    private String back;
    private String testCases;
    private ArrayList<String> choices;

    private boolean hasFront;
    private boolean hasBack;
    private boolean hasChoice;
    private boolean hasTestCases;
    private boolean hasDeckName;
    private int correctIndex;

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
            cardCreatingPane.getChildren().add(editingWindow);
            onSaveDeck.setOnAction(e -> onSaveDeck());
            cancelButton.setOnAction(e -> exitEditingMode.accept(true));
        } catch (IOException e) {
            //TODO: replace or augment with a logger
            e.printStackTrace();
        }
    }

    public CreateDeckDisplay(String deckNameInput) {
        try {
            clearMessage.accept(true);
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/Displays/"
                    + "CreateDeckDisplay.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
            editingWindow = new CardCreatingWindow(incrementNumCards);
            cardCreatingPane.getChildren().add(editingWindow);
            onSaveDeck.setOnAction(e -> onSaveDeck());
            cancelButton.setOnAction(e -> exitEditingMode.accept(true));
            this.deckNameInput = new TextField(deckNameInput);
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

    /**
     * Takes in the CLI input and parses it. The next action depends on the type of card found.
     *
     * @param input The CLI input.
     */
    public void processInput(String input) {
        hasFront = hasFront(input);
        hasBack = hasBack(input);
        hasChoice = hasChoice(input);
        hasTestCases = hasTestCase(input);
        hasDeckName = hasDeckName(input);

        if (hasDeckName) {
            deckNameString = input.split("deck/")[1].split(" ")[0].strip();
            this.deckNameInput = new TextField(deckNameString);
        }

        boolean success;
        if (hasChoice) {
            success = parseMcq(input);
        } else if (hasTestCases) {
            success = parseJs(input);
        } else {
            success = parseFrontBack(input);
        }

        if (!success) {
            displayMessage.accept("CLI card creation failed.");
        }

        try {

            if (hasTestCases) {
                editingWindow.setCardType(js);
                editingWindow.publicChangeInputBox(js);

                editingWindow.setQuestionFieldText(front);

                editingWindow.setTestCases(testCases);

                editingWindow.publicAddCard();
            } else if (hasChoice) {
                editingWindow.setCardType(mcq);
                editingWindow.publicChangeInputBox(mcq);

                editingWindow.setQuestionFieldText(front);

                McqOptionsSetter mcqSetter = editingWindow.getMcqOptionsSetter();
                for (int i = 0; i < choices.size(); i++) {
                    System.out.println(choices.get(i));
                    if (i == correctIndex - 1) {
                        mcqSetter.addNewRow(choices.get(i), true);
                    } else {
                        mcqSetter.addNewRow(choices.get(i), false);
                    }
                }

                editingWindow.publicAddCard();
            } else {
                editingWindow.setCardType(frontBack);
                editingWindow.publicChangeInputBox(frontBack);

                editingWindow.setQuestionFieldText(front);
                editingWindow.setAnswerFieldText(back);
                editingWindow.publicAddCard();
            }

            //LogsCenter.getLogger(CreateCommand.class).info("DECK_CREATE_REG_CARD: Card added to " + deckName);
        } catch (NumberFormatException n) {
            displayMessage.accept("Answer not valid.");
        }
    }

    /**
     * Used to parse an MCQ-type card.
     *
     * @param input the CLI input.
     * @return a boolean that represents if the input matches MCQ-type input.
     */
    private boolean parseMcq(String input) {
        String userInput = input.replaceFirst("create deck/", "");

        String[] userCardFields;
        if (hasFront && hasChoice) {
            userCardFields = userInput.trim().split("front/");
            //String newDeckName = userInputFields[0];
            userCardFields = userCardFields[1].trim().split("correctIndex/");
            correctIndex = Integer.valueOf(userCardFields[1].strip()) - 1;

            userCardFields = userCardFields[0].trim().split("choice/");
            front = userCardFields[0].strip();

        } else {
            return false;
        }

        choices = new ArrayList<>();
        for (int i = 1; i < userCardFields.length; i++) {
            if (!userCardFields[i].strip().equals("")) {
                choices.add(userCardFields[i]);
            }
        }

        if (choices.size() <= 1) {
            displayMessage.accept("Too few choices provided");
            return false;
        }

        return true;
    }

    /**
     * Used to parse FrontBack-type cards.
     *
     * @param input the CLI input.
     * @return a boolean that represents if the input matches FrontBack-type input.
     */
    private boolean parseFrontBack(String input) {
        String userInput = input.replaceFirst("create deck/", "");

        if (hasBack && hasFront) {
            front = input.split("front/")[1].split("back/")[0].strip();
            back = input.split("back/")[1].split("front/")[0].strip();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Used to parse JS-type cards.
     *
     * @param input the CLI input.
     * @return a boolean that represents if the input matches JS-type input.
     */
    private boolean parseJs(String input) {
        String userInput = input.replaceFirst("create deck/", "");

        if (hasFront && hasTestCases) {
            String[] userInputFields = input.split("front/")[1].split("testCase/");
            front = userInputFields[0].strip();

            StringBuilder temp = new StringBuilder();
            for (int i = 1; i < userInputFields.length; i++) {
                temp.append(userInputFields[i].strip());
            }

            testCases = temp.toString();
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

    /**
     * @param input
     * @return
     */
    private boolean hasTestCase(String input) {
        System.out.println("hasTestCase");
        return input.contains("testCase/");
    }

    private boolean hasDeckName(String input) {
        return input.contains("deck/");
    }

}
