package com.typee.logic.interactive.parser.state.pdfmachine;

import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_FROM;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_LIST_INDEX;
import static com.typee.logic.interactive.parser.CliSyntax.PREFIX_TO;

import com.typee.commons.core.index.Index;
import com.typee.logic.commands.Command;
import com.typee.logic.commands.PdfCommand;
import com.typee.logic.commands.exceptions.CommandException;
import com.typee.logic.interactive.parser.ArgumentMultimap;
import com.typee.logic.interactive.parser.InteractiveParserUtil;
import com.typee.logic.interactive.parser.Prefix;
import com.typee.logic.interactive.parser.state.EndState;
import com.typee.logic.interactive.parser.state.State;
import com.typee.logic.interactive.parser.state.exceptions.StateTransitionException;
import com.typee.logic.parser.exceptions.ParseException;
import com.typee.model.person.Name;
import com.typee.model.person.Person;

/**
 * Represents the end state of the state machine that builds the {@code PdfCommand}.
 */
public class PdfEndState extends EndState {

    private static final String MESSAGE_CONSTRAINTS = "Generating PDF.";

    protected PdfEndState(ArgumentMultimap soFar) {
        super(soFar);
    }

    @Override
    public Command buildCommand() throws CommandException {
        Index index = fetchIndex();
        Person sender = fetchSender();
        Person receiver = fetchReceiver();
        return new PdfCommand(index.getOneBased(), sender, receiver);
    }

    /**
     * Returns an index from its {@code String} representation.
     *
     * @return Index.
     * @throws CommandException If the index is invalid.
     */
    private Index fetchIndex() throws CommandException {
        String oneBasedIndex = soFar.getValue(PREFIX_LIST_INDEX).get();
        try {
            Index index = InteractiveParserUtil.parseIndex(oneBasedIndex);
            return index;
        } catch (ParseException e) {
            throw new CommandException(e.getMessage());
        }
    }

    private Person fetchSender() {
        Person person = makePerson(PREFIX_FROM);
        return person;
    }

    private Person fetchReceiver() {
        Person person = makePerson(PREFIX_TO);
        return person;
    }

    private Person makePerson(Prefix prefix) {
        String nameString = soFar.getValue(prefix).get();
        Name name = new Name(nameString);
        return new Person(name);
    }

    @Override
    public State transition(ArgumentMultimap newArgs) throws StateTransitionException {
        throw new StateTransitionException(MESSAGE_END_STATE);
    }

    @Override
    public String getStateConstraints() {
        return MESSAGE_CONSTRAINTS;
    }

    @Override
    public boolean isEndState() {
        return true;
    }

    @Override
    public Prefix getPrefix() {
        return DUMMY_PREFIX;
    }
}
