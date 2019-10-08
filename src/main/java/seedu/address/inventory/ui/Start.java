package seedu.address.inventory.ui;

import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.commands.exception.NoSuchPersonException;
import seedu.address.transaction.logic.Logic;

import java.util.Scanner;

//NO LONGER USED (USED FOR CLI, NO UI)
public class Start {

    private Logic logic;
    private String input;

    public Start(Logic logic) {
        this.input = input;
        this.logic = logic;
    }

    public void show() {
        String result;
        try {
            System.out.println("Enter your command:");
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            CommandResult commandResult = this.executeCommand(input);
            result = commandResult.toString();
        } catch (NoSuchPersonException e) {
            result = "Please input the person using person";
        } catch (Exception e) {
            result = e.toString();
        }
        System.out.println(result);
    }

    private CommandResult executeCommand(String input) throws Exception {
        CommandResult commandResult = logic.execute(input);
        return commandResult;
    }

}
