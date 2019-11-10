//@@author PhireHandy

package dream.fcard.logic.respond.commands;

import java.util.ArrayList;

import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.cards.Priority;
import dream.fcard.model.exceptions.DeckAlreadyExistsException;
import dream.fcard.model.exceptions.DeckNotFoundException;
import dream.fcard.model.exceptions.DuplicateInChoicesException;
import dream.fcard.model.exceptions.InvalidInputException;

/**
 * Represents a command that creates a new deck or card (?).
 */
public class CreateCommand extends Command {

    private String input;
    private State progState;

    public CreateCommand(String i, State s) {
        this.input = i;
        this.progState = s;
    }

    /**
     * Performs execution of create command.
     */
    public boolean funcCall() throws DeckAlreadyExistsException {
        String deckName = input.split("deck/")[1].strip();
        //if (deckAlreadyExists(deckName)) {
        //    throw new DeckAlreadyExistsException(deckName);
        //} else {
        //    Deck newDeck = new Deck(deckName);
        //    Scanner sc = new Scanner(System.in);
        //
        //
        //    if (!progState.isCreateMode()) {
        //        progState.toggleCreateMode();
        //    }

        //    //GUI.printCreateMode("Let’s begin creating the deck ‘" + deckName
        //    // + "’. When done, simply type ‘quit’!");
        //    System.out.println("Let’s begin creating the deck ‘" + deckName
        //            + "’. When done, simply type ‘quit’!");
        //    while (sc.hasNextLine()) {
        //        // GUI.printCreateMode("Type the front of card #" + (newDeck.getNumCards() + 1));
        //        System.out.println("Type the front of card #" + (newDeck.getNumCards() + 1));
        //        String frontOfCard;
        //        String backOfCard;
        //        if (sc.nextLine().matches("(?i)^(quit)?.")) {
        //            //GUI.printCreateMode("You have created the deck ‘" + deckName + "’ with a total of "
        //            //        + newDeck.getNumCards() + " cards!\n");
        //            System.out.println("You have created the deck ‘" + deckName + "’ with a total of "
        //                    + newDeck.getNumCards() + " cards!\n");
        //            break;
        //        } else {
        //            frontOfCard = sc.nextLine();
        //        }
        //
        //        // GUI.printCreateMode("Type the back of card #" + (newDeck.getNumCards() + 1));
        //        System.out.println("Type the back of card #" + (newDeck.getNumCards() + 1));
        //        if (sc.nextLine().matches("(?i)^(quit)?.")) {
        //            //GUI.printCreateMode("You have created the deck ‘" + deckName + "’ with a total of "
        //            //        + newDeck.getNumCards() + " cards!\n");
        //            System.out.println("You have created the deck ‘" + deckName + "’ with a total of "
        //                    + newDeck.getNumCards() + " cards!\n");
        //            break;
        //        } else {
        //            backOfCard = sc.nextLine();
        //            FlashCard newCard = new FrontBackCard(frontOfCard, backOfCard);
        //            newDeck.addNewCard(newCard);
        //        }
        //    }
        //    progState.toggleCreateMode();
        //    return true;
        //}
        return false;
    }

    /**
     * Is used to create a FrontBack or MCQ card and adds it to the State in a specific deck
     *
     * @param command An ArrayList of the parsed user input.
     * @param state The State
     * @throws DuplicateInChoicesException
     * @throws DeckNotFoundException
     * @throws InvalidInputException
     */
    public static void createMcqFrontBack(ArrayList<ArrayList<String>> command, State state)
        throws DuplicateInChoicesException, DeckNotFoundException, InvalidInputException {
        // Checks if deckName matches any deck in the State.
        boolean deckExistsInState = false;
        Deck currDeck = null;
        for (Deck curr : state.getDecks()) {

            if (curr.getDeckName().equals(command.get(0).get(0))) {
                deckExistsInState = true;
                currDeck = curr;
                break;
            }
        }

        if (!deckExistsInState) {
            throw new DeckNotFoundException("The specified deck does not exist! Please create it first.");
        }

        boolean hasPriority = command.get(1).size() > 0;

        // Checks if priority level matches high or low
        if (hasPriority && (!command.get(1).get(0).equalsIgnoreCase("high")
            && !command.get(1).get(0).equalsIgnoreCase("low"))) {
            throw new InvalidInputException("Can only supply one Priority!");
        }

        // Checks if the card is an MCQ or FrontBack card
        boolean isMcq = (command.get(4).size() > 0);

        // Checks if the MCQ has more than two choices
        if (isMcq && command.get(4).size() < 2) {
            throw new InvalidInputException("MCQ has more than 2 choices!");
        }

        // Creates the card and adds to the State
        if (isMcq) {
            currDeck.addNewCard(CreateCommand.createMcq(command));
        } else {
            currDeck.addNewCard(CreateCommand.createFrontBack(command));
        }
    }

    /**
     * Used to create an MCQ card. Only used internally.
     *
     * @param command An ArrayList of the parsed user input
     * @return The MCQ Card represented as a FlashCard
     * @throws DuplicateInChoicesException
     */
    private static FlashCard createMcq(ArrayList<ArrayList<String>> command) throws DuplicateInChoicesException {
        // Order of inputs in command: "deck/", "priority/", "front/", "back/", "choice/"
        String front = command.get(2).get(0);
        String correctIndex = command.get(3).get(0);
        int priority = getPriorityLevel(command.get(1));

        return new MultipleChoiceCard(front, correctIndex, command.get(4), priority);
    }

    /**
     * Used to create a Front Back card. Only used internally.
     *
     * @param command An ArrayList of the parsed user input
     * @return The FrontBack Card represented as a FlashCard
     */
    private static FlashCard createFrontBack(ArrayList<ArrayList<String>> command) {
        // Order of inputs in command: "deck/", "priority/", "front/", "back/", "choice/"
        String front = command.get(2).get(0);
        String back = command.get(3).get(0);
        int priority = getPriorityLevel(command.get(1));

        return new FrontBackCard(front, back, priority);
    }

    private static int getPriorityLevel(ArrayList<String> input) {
        if (input.size() > 0) {
            if (input.get(0).equalsIgnoreCase("high")) {
                return Priority.HIGH_PRIORITY;
            }
        }
        return Priority.LOW_PRIORITY;
    }
}
