package seedu.address.util;

import java.io.IOException;

import seedu.address.person.logic.commands.exceptions.CommandException;

import seedu.address.transaction.commands.CommandResult;
import seedu.address.transaction.model.Model;
import seedu.address.transaction.model.exception.NoSuchIndexException;
import seedu.address.transaction.model.exception.NoSuchPersonException;

/**
 * Interface for all commands.
 */
public interface OverallCommand {

    /**
     * Executes the command for transaction.
     * @param model
     * @param personModel
     * @return
     * @throws NoSuchIndexException
     * @throws CommandException
     * @throws NoSuchPersonException
     */
    CommandResult execute(Model model, seedu.address.person.model.Model personModel)
            throws NoSuchIndexException, CommandException, NoSuchPersonException;

    /**
     * Executes the command for reimbursement.
     * @param model
     * @param personModel
     * @return
     * @throws NoSuchIndexException
     * @throws CommandException
     * @throws NoSuchPersonException
     */
    seedu.address.reimbursement.commands.CommandResult execute(seedu.address.reimbursement.model.Model model,
                                                               seedu.address.person.model.Model personModel)
            throws Exception;
    //for person
    seedu.address.person.logic.commands.CommandResult execute(seedu.address.person.model.Model model,
                                                              seedu.address.transaction.logic.Logic transactionLogic,
                                                              seedu.address.reimbursement.logic.Logic
                                                                      reimbursementLogic,
                                                              seedu.address.cashier.logic.Logic cashierLogic)
            throws CommandException, IOException;
}
