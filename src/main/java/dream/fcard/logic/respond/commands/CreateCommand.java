package dream.fcard.logic.respond.commands;

import java.util.ArrayList;

import dream.fcard.model.Deck;
import dream.fcard.model.State;
import dream.fcard.model.cards.FlashCard;
import dream.fcard.model.cards.FrontBackCard;
import dream.fcard.model.cards.MultipleChoiceCard;
import dream.fcard.model.cards.Priority;
import dream.fcard.model.exceptions.DeckAlreadyExistsException;
import dream.fcard.model.exceptions.DuplicateInChoicesException;

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

    public static boolean createMcqFrontBack(ArrayList<ArrayList<String>> command, State s) throws DuplicateInChoicesException {
        // Checks if deckName matches any deck in the State.
        boolean deckExistsInState = false;
        Deck currDeck = null;
        for (Deck curr : s.getAllDecks()) {
            if (curr.getName().equals(command.get(0))) {
                deckExistsInState = true;
                currDeck = curr;
                break;
            }
        }

        if (!deckExistsInState) {
            return false;
        }

        // Checks if priority level matches high or low
        if (!command.get(1).get(0).equalsIgnoreCase("high")
                && !command.get(1).get(0).equalsIgnoreCase("low")) {
            return false;
        }

        // Checks if the card is an MCQ or FrontBack card
        boolean isMcq = (command.get(4).size() > 0);

        // Checks if the MCQ has more than two choices
        if (isMcq && command.get(4).size() < 2) {
            return false;
        }

        // Creates the card and adds to the State
        if(isMcq) {
            currDeck.addNewCard(CreateCommand.createMcq(command));
            return true;
        } else {
            currDeck.addNewCard(CreateCommand.createFrontBack(command));
            return true;
        }
    }

    private static FlashCard createMcq(ArrayList<ArrayList<String>> command) throws DuplicateInChoicesException {
        // Order of inputs in command: "deck/", "priority/", "front/", "back/", "choice/"
        String front = command.get(2).get(0);
        String correctIndex = command.get(3).get(0);
        int priority = getPriorityLevel(command.get(1).get(0));

        return new MultipleChoiceCard(front, correctIndex, command.get(4), priority);
    }

    private static FlashCard createFrontBack(ArrayList<ArrayList<String>> command) {
        // Order of inputs in command: "deck/", "priority/", "front/", "back/", "choice/"
        String front = command.get(2).get(0);
        String back = command.get(3).get(0);
        int priority = getPriorityLevel(command.get(1).get(0));

        return new FrontBackCard(front, back, priority);
    }

    private static int getPriorityLevel(String input) {
        if (input.equalsIgnoreCase("high")) {
            return Priority.HIGH_PRIORITY;
        }
        return Priority.LOW_PRIORITY;
    }
}

