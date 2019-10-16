package seedu.address.logic.commands.loan;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a command that can handle multiple targeted loans.
 */
public abstract class MultiLoanCommand extends Command {

    protected List<PersonLoanIndexPair> personLoanIndexPairs;

    public MultiLoanCommand(List<Index> targetPersonsIndices, List<Index> targetLoansIndices) throws CommandException {
        requireAllNonNull(targetPersonsIndices, targetLoansIndices);
        constructPersonLoanIndexPairList(targetPersonsIndices, targetLoansIndices);
    }

    /**
     * Constructs a list of person-loan index pairs using two given lists.
     * @param personIndexList The list of person indices to be used.
     * @param loanIndexList The list of loan indices to be used.
     * @throws CommandException If the list sizes do not match, or if either list is empty.
     */
    private void constructPersonLoanIndexPairList(
            List<Index> personIndexList, List<Index> loanIndexList) throws CommandException {
        requireAllNonNull(personIndexList, loanIndexList);

        if (personIndexList.size() != loanIndexList.size()) {
            throw new CommandException("Person index list size does not match loan index list size.");
        }

        if (personIndexList.isEmpty()) {
            throw new CommandException("Person/loan index lists empty.");
        }

        personLoanIndexPairs = new ArrayList<PersonLoanIndexPair>();
        for (int i = 0; i < personIndexList.size(); i++) {
            personLoanIndexPairs.add(
                    new PersonLoanIndexPair(
                            personIndexList.get(i),
                            loanIndexList.get(i)));
        }
    }

    /**
     * Assembles a result message that takes into account targeted loans not found in the loans manager.
     * @param pairsNotFound The targeted person-loan indices that could not be found in the loans manager.
     * @param successMessage Message for when at least one target was found.
     * @param failureMessage Message for when no targets were found at all.
     * @return The result message.
     * @throws CommandException If no targets were found at all.
     */
    protected String constructMultiLoanResult(List<PersonLoanIndexPair> pairsNotFound,
            String successMessage, String failureMessage) throws CommandException {
        requireAllNonNull(pairsNotFound, successMessage, failureMessage);

        if (pairsNotFound.equals(personLoanIndexPairs)) {
            throw new CommandException(failureMessage);
        }

        StringBuilder resultMessage = new StringBuilder(successMessage);
        if (!pairsNotFound.isEmpty()) {
            resultMessage.append("The following loans were not found:\n");
            for (PersonLoanIndexPair missingPair : pairsNotFound) {
                resultMessage.append("  Loan ")
                        .append(missingPair.personIndex.getOneBased())
                        .append(".")
                        .append(missingPair.loanIndex.getOneBased())
                        .append("\n");
            }
        }

        return resultMessage.toString();
    }

    /**
     * A pair of two indices, one indicating a person and the other indicating a loan.
     */
    protected static class PersonLoanIndexPair {

        private final Index personIndex;
        private final Index loanIndex;

        public PersonLoanIndexPair(Index personIndex, Index loanIndex) {
            this.personIndex = personIndex;
            this.loanIndex = loanIndex;
        }

        public Index getPersonIndex() {
            return personIndex;
        }

        public Index getLoanIndex() {
            return loanIndex;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof PersonLoanIndexPair)) {
                return false;
            }

            PersonLoanIndexPair otherCommand = (PersonLoanIndexPair) other;
            return personIndex.equals(otherCommand.personIndex)
                    && loanIndex.equals(otherCommand.loanIndex);
        }
    }
}
