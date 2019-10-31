package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PHONE;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.addcommand.AddCustomerCommand;
import seedu.address.logic.commands.addcommand.AddPhoneCommand;
import seedu.address.logic.commands.clearcommand.ClearCustomerCommand;
import seedu.address.logic.commands.clearcommand.ClearPhoneCommand;
import seedu.address.logic.commands.deletecommand.DeleteCustomerCommand;
import seedu.address.logic.commands.deletecommand.DeletePhoneCommand;
import seedu.address.logic.commands.editcommand.EditCustomerCommand;
import seedu.address.logic.commands.editcommand.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.logic.commands.editcommand.EditPhoneCommand;
import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.logic.commands.findcommand.FindCustomerCommand;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.commands.listcommand.ListCustomerCommand;
import seedu.address.logic.commands.listcommand.ListPhoneCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.predicates.CustomerContainsKeywordsPredicate;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.CustomerUtil;
import seedu.address.testutil.EditCustomerDescriptorBuilder;
import seedu.address.testutil.EditPhoneDescriptorBuilder;
import seedu.address.testutil.PhoneBuilder;
import seedu.address.testutil.PhoneUtil;

public class SellerManagerParserTest {

    private final SellerManagerParser parser = new SellerManagerParser();

    @Test
    public void parseCommand_addCustomer() throws Exception {
        Customer customer = new CustomerBuilder().build();
        AddCustomerCommand command = (AddCustomerCommand) parser.parseCommand(CustomerUtil.getAddCommand(customer));
        assertEquals(new AddCustomerCommand(customer), command);
    }

    @Test
    public void parseCommand_addPhone() throws Exception {
        Phone phone = new PhoneBuilder().build();
        AddPhoneCommand command = (AddPhoneCommand) parser.parseCommand(PhoneUtil.getAddCommand(phone));
        assertEquals(new AddPhoneCommand(phone), command);
    }

    @Test
    public void parseCommand_clearCustomer() throws Exception {
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_WORD) instanceof ClearCustomerCommand);
        assertTrue(parser.parseCommand(ClearCustomerCommand.COMMAND_WORD + " 3") instanceof ClearCustomerCommand);
    }

    @Test
    public void parseCommand_clearPhone() throws Exception {
        assertTrue(parser.parseCommand(ClearPhoneCommand.COMMAND_WORD) instanceof ClearPhoneCommand);
        assertTrue(parser.parseCommand(ClearPhoneCommand.COMMAND_WORD + " 3") instanceof ClearPhoneCommand);
    }

    @Test
    public void parseCommand_deleteCustomer() throws Exception {
        DeleteCustomerCommand command = (DeleteCustomerCommand) parser.parseCommand(
                DeleteCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased());
        assertEquals(new DeleteCustomerCommand(INDEX_FIRST_CUSTOMER), command);
    }

    @Test
    public void parseCommand_deletePhone() throws Exception {
        DeletePhoneCommand command = (DeletePhoneCommand) parser.parseCommand(
                DeletePhoneCommand.COMMAND_WORD + " " + INDEX_FIRST_PHONE.getOneBased());
        assertEquals(new DeletePhoneCommand(INDEX_FIRST_PHONE), command);
    }

    @Test
    public void parseCommand_editCustomer() throws Exception {
        Customer customer = new CustomerBuilder().build();
        EditCustomerDescriptor descriptor = new EditCustomerDescriptorBuilder(customer).build();
        EditCustomerCommand command = (EditCustomerCommand) parser.parseCommand(EditCustomerCommand.COMMAND_WORD
                + " " + INDEX_FIRST_CUSTOMER.getOneBased() + " "
                + CustomerUtil.getEditCustomerDescriptorDetails(descriptor));
        assertEquals(new EditCustomerCommand(INDEX_FIRST_CUSTOMER, descriptor), command);
    }

    @Test
    public void parseCommand_editPhone() throws Exception {
        Phone phone = new PhoneBuilder().build();
        EditPhoneDescriptor descriptor = new EditPhoneDescriptorBuilder(phone).build();
        EditPhoneCommand command = (EditPhoneCommand) parser.parseCommand(EditPhoneCommand.COMMAND_WORD
                + " " + INDEX_FIRST_PHONE.getOneBased() + " "
                + PhoneUtil.getEditPhoneDescriptorDetails(descriptor));
        assertEquals(new EditPhoneCommand(INDEX_FIRST_PHONE, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_findCustomer() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCustomerCommand command = (FindCustomerCommand) parser.parseCommand(
                FindCustomerCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindCustomerCommand(new CustomerContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_findPhone() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindPhoneCommand command = (FindPhoneCommand) parser.parseCommand(
                FindPhoneCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindPhoneCommand(new PhoneContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_listCustomer() throws Exception {
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD) instanceof ListCustomerCommand);
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD + " 3") instanceof ListCustomerCommand);
    }

    @Test
    public void parseCommand_listPhone() throws Exception {
        assertTrue(parser.parseCommand(ListPhoneCommand.COMMAND_WORD) instanceof ListPhoneCommand);
        assertTrue(parser.parseCommand(ListPhoneCommand.COMMAND_WORD + " 3") instanceof ListPhoneCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
