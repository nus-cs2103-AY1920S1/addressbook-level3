package seedu.ezwatchlist.ui;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class CommandBoxTest {
    private CommandBox.CommandExecutor commandExecutor = commandText -> null;

    @Test
    void commandBoxConstructor() {
        assertThrows(ExceptionInInitializerError.class, () -> new TestCommandBox(commandExecutor));
    }
    @Test
    void setMainWindow() {

    }

    private static class TestCommandBox extends CommandBox {

        public TestCommandBox(CommandExecutor commandExecutor) {
            super(commandExecutor);
        }
        public MainWindow getMainWindow() {
            return getMainWindow();
        }
        public CommandExecutor getCommandExecutor() {
            return getCommandExecutor();
        }
    }
}
