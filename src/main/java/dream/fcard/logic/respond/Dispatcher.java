package dream.fcard.logic.respond;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Dispatches global consumers for CLI.
 */
public class Dispatcher {
    private static HashMap<String, Consumer> consumers = new HashMap<>();

    public static void addConsumer(String identifier, Consumer c) {
        consumers.put(identifier, c);
    }

    public static void removeConsumer(String identifier) {
        consumers.remove(identifier);
    }

    /**
     * @param input
     */
    @SuppressWarnings("unchecked")
    public static void parseAndDispatch(String input) {
        // Will need a parser that takes user input and stringifies it into a standard input, e.g.
        //see 1 | show deck 1 -> view 1
        if (input.startsWith(ConsumerSchema.CREATE_NEW_DECK)) {
            consumers.get(ConsumerSchema.CREATE_NEW_DECK).accept(true);
        } else if (input.startsWith(ConsumerSchema.SEE_SPECIFIC_DECK)) {
            //assume command is of the form "see_deck 1"
            Scanner sc = new Scanner(input);
            sc.next(); //get rid of the see_deck
            int num = sc.nextInt(); //might throw exception
            consumers.get(ConsumerSchema.SEE_SPECIFIC_DECK).accept(num);
        } else if (input.startsWith(ConsumerSchema.ADD_NEW_ROW_MCQ)) {
            //assume command is of the form "add row"

            // add schematics for modes - eg card creating mode, test mode, similar to state enum. Then in state,
            // create a hashset for each mode to remember what components are active in that mode -
            // eg in edit mode hashset, remember whether mcq or js or front back is activated. Everytime you enter a
            // mode, clear the hashsets for the other modes. To check whether adding a new row is relevant,
            //check for the mode and the component in the respective hashset.
        }
    }


}
