package dream.fcard.logic.respond.commands;

import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.IndexNotFoundException;
import dream.fcard.model.exceptions.InvalidInputException;

/**
 * A command to edit cards using inputs from the user.
 */
public class EditCommand extends Command {
    private String input;
    private State progState;

    public EditCommand(String i, State s) {
        this.input = i;
        this.progState = s;
    }

    /**
     * Performs execution of edit command.
     */
    public boolean funcCall() throws DeckNotFoundException, InvalidInputException, IndexNotFoundException {
        String deckName = input.split("deck/")[1].split(" ")[0];
        String action = input.split("action/")[1].split(" ")[0].toLowerCase();
        Deck currDeck = progState.getDeck(deckName);

        if (!progState.isEditMode()) {
            progState.toggleEditMode();
        }

        switch(action) {
        case "add":
            String front = input.split("front/")[1].split(" ")[0];
            String back = input.split("back/")[1].split(" ")[0];

            currDeck.addNewCard(new FrontBackCard(front, back));
            break;

        case "change":
            int indexC = Integer.parseInt(input.split("index/")[1].split(" ")[0]);

            if (indexC < 1) {
                throw new InvalidInputException("Index is not a positive integer!");
            } else {
                boolean hasFront = hasOnlyOneFront(input);
                boolean hasBack = hasOnlyOneBack(input);

                if (hasFront) {
                    currDeck.editFrontCardInDeck(input.split("front/")[1].split(" ")[0], indexC);
                }

                if (hasBack) {
                    currDeck.editFrontCardInDeck(input.split("back/")[1].split(" ")[0], indexC);
                }
            }
            break;

        case "remove":
            int indexR = Integer.parseInt(input.split("index/")[1].split(" ")[0]);

            currDeck.removeCard(indexR);
            break;

        default:
            throw new InvalidInputException("The input given does not match any possible command!");
        }

        progState.toggleEditMode();
        return true;
    }

    /**
     * A boolean representing that there is only one front instruction, no more and no less.
     *
     * @param input The String input from the user.
     * @return A boolean representing that there is only one front instruction.
     */
    private boolean hasOnlyOneFront(String input) {
        if (input.split("front/").length == 2) {
            return true;
        }
        return false;
    }

    /**
     * A boolean representing that there is only one back instruction, no more and no less.
     *
     * @param input The String input from the user.
     * @return A boolean representing that there is only one back instruction.
     */
    private boolean hasOnlyOneBack(String input) {
        if (input.split("back/").length == 2) {
            return true;
        }
        return false;
    }
}
