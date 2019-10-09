package seedu.address.cashier.commands;

public class AddCashierCommand extends Command {
    private Transaction transaction;
    public static final String COMMAND_WORD = "add";
    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Creates an AddCommand to add the specified {@code Transaction}
     */
    public AddCashierCommand(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public CommandResult execute(Model model, seedu.address.person.model.Model personModel) {
        TransactionMessages transactionMessages = new TransactionMessages();
        model.addTransaction(transaction);
        logger.info(transaction.toString());
        return new CommandResult(transactionMessages.addedTransaction(transaction));
    }
}
