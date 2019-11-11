package seedu.address.reimbursement.logic;

import java.io.IOException;

import seedu.address.person.model.person.Person;
import seedu.address.reimbursement.logic.commands.Command;
import seedu.address.reimbursement.logic.parser.ReimbursementTabParser;
import seedu.address.reimbursement.logic.parser.exception.ParseException;
import seedu.address.reimbursement.model.Model;
import seedu.address.reimbursement.model.ReimbursementList;
import seedu.address.reimbursement.model.exception.InvalidDeadlineException;
import seedu.address.reimbursement.model.exception.NoSuchPersonReimbursementException;
import seedu.address.transaction.model.TransactionList;
import seedu.address.transaction.storage.exception.FileReadException;
import seedu.address.util.CommandResult;

/**
 * Implements the logic for Reimbursements.
 */
public class LogicManager implements Logic {

    private final seedu.address.reimbursement.model.Model reimbursementModel;
    private final seedu.address.reimbursement.storage.Storage reimbursementStorage;
    private final seedu.address.person.model.Model personModel;
    private final ReimbursementTabParser parser;


    public LogicManager(Model reimbursementModel,
                        seedu.address.reimbursement.storage.Storage reimbursementStorage,
                        seedu.address.person.model.Model personModel) {

        this.reimbursementModel = reimbursementModel;
        this.reimbursementStorage = reimbursementStorage;
        this.parser = new ReimbursementTabParser();
        this.personModel = personModel;
    }

    @Override
    public CommandResult execute(String commandText)
            throws ParseException, NoSuchPersonReimbursementException, InvalidDeadlineException, IOException {
        Command command = parser.parseCommand(commandText, personModel);
        CommandResult commandResult = command.execute(reimbursementModel, personModel);
        reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
        return commandResult;
    }

    @Override
    public ReimbursementList getFilteredList() {
        return reimbursementModel.getFilteredReimbursementList();
    }

    @Override
    public void updateReimbursementModelAndStorage(TransactionList transactionList) throws FileReadException,
            IOException {
        reimbursementModel.updateReimbursementList(
                reimbursementStorage.getReimbursementFromFile(transactionList));
        reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
    }

    @Override
    public void updateReimbursementFromPerson(Person editedPerson, Person personToEdit) throws IOException {
        reimbursementModel.updateReimbursementList(editedPerson, personToEdit);
        reimbursementStorage.writeFile(reimbursementModel.getReimbursementList());
    }
}
