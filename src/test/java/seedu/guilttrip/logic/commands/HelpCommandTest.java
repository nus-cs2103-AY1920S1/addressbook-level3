//package seedu.guilttrip.logic.commands;
//
//import static seedu.guilttrip.logic.commands.CommandTestUtil.assertCommandSuccess;
//import static seedu.guilttrip.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;
//
//import org.junit.jupiter.api.Test;
//
//import seedu.guilttrip.model.Model;
//import seedu.guilttrip.model.ModelManager;
//
//public class HelpCommandTest {
//    private Model model = new ModelManager();
//    private Model expectedModel = new ModelManager();
//
//    @Test
//    public void execute_help_success() {
//        CommandResult expectedCommandResult = new CommandResult(SHOWING_HELP_MESSAGE, true, false);
//        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
//    }
//}
