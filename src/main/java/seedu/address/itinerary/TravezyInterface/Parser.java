package seedu.address.itinerary.TravezyInterface;

import seedu.address.itinerary.EventItinerary.Event;
import seedu.address.itinerary.Exceptions.TravezyException;

import java.util.StringJoiner;

public class Parser {
    private static String errorMessage = "OPPS! We know you love travelling but "
            + "the command is out of this world! 🌚";

    public static void processCommand(String command, Ui ui, EventList eventList) throws TravezyException {
        String[] commandArr = command.split(" ");

        try {
            checkCommand(commandArr);
            switch (commandArr[0]) {
            case "addEvent":
                Event event = new Event(getTitle(command), eventList.size() + 1);
                eventList.add(event);
                eventList.addEventPrompt();
                break;

            case "deleteEvent":
                eventList.remove(Integer.parseInt(commandArr[1]));
                break;

            case "listEvent":
                eventList.listEvent();
                break;

            case "showEvent":
                eventList.showEvent(Integer.parseInt(commandArr[1]));
                break;

            case "doneEvent":
                eventList.markAsDone(Integer.parseInt(commandArr[1]));
                break;

            case "updateEvent":
                //Needs to be done
                // Only can use level 2 commands
                ui.setActivityLevel(2);
                break;

            case "exit":
                ui.setActivityLevel(0);
                ui.exit();
                break;

            default:
                commandError();
                break;
            }
        } catch (TravezyException ex) {
            System.out.println(ex.getMessage());
        } catch (IndexOutOfBoundsException ex) {
            System.out.println(listError());
        } catch (NumberFormatException ex) {
            System.out.println(listError());
        }
    }

    private static String getTitle(String command) {
        String[] commandArr = command.split(" ");
        StringJoiner sj = new StringJoiner(" ");

        for (int i = 1; i < commandArr.length; i++) {
            sj.add(commandArr[i]);
        }

        return sj.toString();
    }

    private static void checkCommand(String[] commandArr) throws TravezyException {
        String command = commandArr[0];
        if (command.matches("exit|listEvent")) {
            if (commandArr.length > 1) {
                commandError();
            }
        } else if (command.matches("addEvent|deleteEvent|showEvent|doneEvent|updateEvent")) {
                if (commandArr.length < 2) {
                    throw new TravezyException(printLine() + "\n" + 
                        "     Please provide the index for the command! 🙃" + "\n" + printLine());
                } else if (commandArr.length > 2) {
                    throw new TravezyException(printLine() + "\n" + 
                        "     OOF! To many index are provided! Please give only one index. 🙃"
                            + "\n" + printLine());
                }
        }
    }

    private static void commandError() throws TravezyException {
        throw new TravezyException(printLine() + "\n" + errorMessage + "\n" + printLine());
    }

    private static String listError() {
        return printLine() + "\n" + 
            "     Sorry, but this is an invalid index to the list! 😶" 
                + "\n" + printLine();
    }

    private static String printLine() {
        return "\n★ ・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・・★\n";
    }
}
