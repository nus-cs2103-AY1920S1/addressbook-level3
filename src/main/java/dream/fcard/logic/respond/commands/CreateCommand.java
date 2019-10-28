package dream.fcard.logic.respond.commands;

import java.util.ArrayList;

import dream.fcard.core.commons.core.LogsCenter;
import dream.fcard.gui.Gui;
import dream.fcard.logic.respond.ResponseFunc;
import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.DuplicateInChoicesException;
import dream.fcard.model.exceptions.IndexNotFoundException;

/**
 * Represents a command that creates a new deck or card (?).
 */
public class CreateCommand implements ResponseFunc {
    private String deckName;
    private String front;
    private String back;
    private ArrayList<String> choices;

    private boolean hasFront;
    private boolean hasBack;
    private boolean hasChoice;

    /**
     *
     * @param commandInput string
     * @param programState state object
     * @return
     * @throws DeckNotFoundException
     */
    @Override
    public boolean funcCall(String commandInput, State programState) {

        System.out.println(commandInput);

        hasFront = hasFront(commandInput);
        hasBack = hasBack(commandInput);
        hasChoice = hasChoice(commandInput);

        boolean success;
        if (!hasChoice) {
            success = parseInputOneShot(commandInput);
        } else {
            success = parseInputWithChoice(commandInput);
        }

        if (!success) {
            Gui.showError("MCQ card creation failed.");
            return true;
        }

        try {
            Deck deck = programState.getDeck(deckName);

            if (!hasChoice) {
                deck.addNewCard(new FrontBackCard(front, back));
            } else {
                deck.addNewCard(new MultipleChoiceCard(front, back, choices));
            }

            LogsCenter.getLogger(CreateCommand.class).info("DECK_CREATE_REG_CARD: Card added to " + deckName);

        } catch (DeckNotFoundException d) {
            LogsCenter.getLogger(CreateCommand.class).warning(
                    "DECK_CREATE_REG_CARD: Deck not found - " + deckName);
            Gui.showError(d.getMessage());
        } catch (DuplicateInChoicesException e) {
            Gui.showError("Duplicate found in choices.");
        } catch (IndexNotFoundException e) {
            Gui.showError("Answer not valid.");
        } catch (NumberFormatException n) {
            Gui.showError("Answer not valid.");
        }
        return true;
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
            deckName = userInputFields[0];
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
            Gui.showError("Too few choices provided");
            return false;
        }

        return true;
    }

    /**
     *
     * @param commandInput
     * @return
     */
    private boolean parseInputOneShot(String commandInput) {
        String userInput = commandInput.replaceFirst("create deck/", "");

        if (hasBack && hasFront) {
            String[] userInputFields = userInput.trim().split(" front/");

            deckName = userInputFields[0];

            String[] userCardFields = userInputFields[1].trim().split(" back/");

            front = userCardFields[0];
            back = userCardFields[1];
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

