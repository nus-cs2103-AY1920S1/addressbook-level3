package seedu.address.transaction.ui;

import java.util.Scanner;
import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.logic.Logic;

public class MainWindow {

    private Logic logic;
    private String input;

    public MainWindow(Logic logic) {
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
