package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SCHEDULE_MONDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CUSTOMER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_ORDER;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PHONE;
import static seedu.address.testutil.TypicalSchedules.MONDAY_SCHEDULE;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.CancelOrderCommand;
import seedu.address.logic.commands.CompleteOrderCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.addcommand.AddCustomerCommand;
import seedu.address.logic.commands.addcommand.AddOrderCommand;
import seedu.address.logic.commands.addcommand.AddPhoneCommand;
import seedu.address.logic.commands.addcommand.AddScheduleCommand;
import seedu.address.logic.commands.clearcommand.ClearArchivedOrderCommand;
import seedu.address.logic.commands.clearcommand.ClearCustomerCommand;
import seedu.address.logic.commands.clearcommand.ClearOrderCommand;
import seedu.address.logic.commands.clearcommand.ClearPhoneCommand;
import seedu.address.logic.commands.clearcommand.ClearScheduleCommand;
import seedu.address.logic.commands.copycommand.CopyCustomerCommand;
import seedu.address.logic.commands.copycommand.CopyOrderCommand;
import seedu.address.logic.commands.copycommand.CopyPhoneCommand;
import seedu.address.logic.commands.deletecommand.DeleteCustomerCommand;
import seedu.address.logic.commands.deletecommand.DeletePhoneCommand;
import seedu.address.logic.commands.deletecommand.DeleteScheduleCommand;
import seedu.address.logic.commands.editcommand.EditCustomerCommand;
import seedu.address.logic.commands.editcommand.EditCustomerCommand.EditCustomerDescriptor;
import seedu.address.logic.commands.editcommand.EditOrderCommand;
import seedu.address.logic.commands.editcommand.EditOrderCommand.EditOrderDescriptor;
import seedu.address.logic.commands.editcommand.EditPhoneCommand;
import seedu.address.logic.commands.editcommand.EditPhoneCommand.EditPhoneDescriptor;
import seedu.address.logic.commands.editcommand.EditScheduleCommand;
import seedu.address.logic.commands.editcommand.EditScheduleCommand.EditScheduleDescriptor;
import seedu.address.logic.commands.findcommand.FindCustomerCommand;
import seedu.address.logic.commands.findcommand.FindOrderCommand;
import seedu.address.logic.commands.findcommand.FindPhoneCommand;
import seedu.address.logic.commands.listcommand.ListCustomerCommand;
import seedu.address.logic.commands.listcommand.ListOrderCommand;
import seedu.address.logic.commands.listcommand.ListPhoneCommand;
import seedu.address.logic.commands.switchcommand.SwitchArchivedOrderPanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchCustomerPanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchOrderPanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchPhonePanelCommand;
import seedu.address.logic.commands.switchcommand.SwitchSchedulePanelCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.customer.Customer;
import seedu.address.model.customer.predicates.CustomerContainsKeywordsPredicate;
import seedu.address.model.order.Order;
import seedu.address.model.order.Price;
import seedu.address.model.order.predicates.OrderContainsKeywordsPredicate;
import seedu.address.model.phone.Phone;
import seedu.address.model.phone.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CustomerBuilder;
import seedu.address.testutil.CustomerUtil;
import seedu.address.testutil.EditCustomerDescriptorBuilder;
import seedu.address.testutil.EditOrderDescriptorBuilder;
import seedu.address.testutil.EditPhoneDescriptorBuilder;
import seedu.address.testutil.EditScheduleDescriptorBuilder;
import seedu.address.testutil.OrderBuilder;
import seedu.address.testutil.OrderUtil;
import seedu.address.testutil.PhoneBuilder;
import seedu.address.testutil.PhoneUtil;
import seedu.address.testutil.ScheduleBuilder;
import seedu.address.testutil.ScheduleUtil;

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
    public void parseCommand_addOrder() throws Exception {
        Order order = new OrderBuilder().build();
        Index customerIndex = INDEX_FIRST_CUSTOMER;
        Index phoneIndex = INDEX_FIRST_PHONE;
        Price orderPrice = order.getPrice();
        Set<Tag> orderTags = order.getTags();
        AddOrderCommand command = (AddOrderCommand) parser.parseCommand(OrderUtil.getAddCommand(order));
        assertEquals(new AddOrderCommand(customerIndex, phoneIndex, orderPrice, orderTags), command);
    }

    @Test
    public void parseCommand_addSchedule() throws Exception {
        Schedule schedule = new ScheduleBuilder().build();
        Index orderIndex = INDEX_FIRST_ORDER;
        boolean canClash = false;
        AddScheduleCommand command = (AddScheduleCommand) parser.parseCommand(ScheduleUtil.getAddCommand(schedule));
        assertEquals(new AddScheduleCommand(schedule, orderIndex, canClash), command);
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
    public void parseCommand_clearOrder() throws Exception {
        assertTrue(parser.parseCommand(ClearOrderCommand.COMMAND_WORD) instanceof ClearOrderCommand);
        assertTrue(parser.parseCommand(ClearOrderCommand.COMMAND_WORD + " 3") instanceof ClearOrderCommand);
    }

    @Test
    public void parseCommand_clearSchedule() throws Exception {
        assertTrue(parser.parseCommand(ClearScheduleCommand.COMMAND_WORD) instanceof ClearScheduleCommand);
        assertTrue(parser.parseCommand(ClearScheduleCommand.COMMAND_WORD + " 3") instanceof ClearScheduleCommand);
    }

    @Test
    public void parseCommand_clearArchivedOrder() throws Exception {
        assertTrue(parser.parseCommand(ClearArchivedOrderCommand.COMMAND_WORD) instanceof ClearArchivedOrderCommand);
        assertTrue(parser.parseCommand(ClearArchivedOrderCommand.COMMAND_WORD + " 3")
                instanceof ClearArchivedOrderCommand);
    }

    @Test
    public void parseCommand_copyCustomer() throws Exception {
        CopyCustomerCommand command = (CopyCustomerCommand) parser.parseCommand(
                CopyCustomerCommand.COMMAND_WORD + " " + INDEX_FIRST_CUSTOMER.getOneBased());
        assertEquals(new CopyCustomerCommand(INDEX_FIRST_CUSTOMER), command);
    }

    @Test
    public void parseCommand_copyPhone() throws Exception {
        CopyPhoneCommand command = (CopyPhoneCommand) parser.parseCommand(
                CopyPhoneCommand.COMMAND_WORD + " " + INDEX_FIRST_PHONE.getOneBased());
        assertEquals(new CopyPhoneCommand(INDEX_FIRST_PHONE), command);
    }

    @Test
    public void parseCommand_copyOrder() throws Exception {
        CopyOrderCommand command = (CopyOrderCommand) parser.parseCommand(
                CopyOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new CopyOrderCommand(INDEX_FIRST_ORDER), command);
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
    public void parseCommand_deleteSchedule() throws Exception {
        DeleteScheduleCommand command = (DeleteScheduleCommand) parser.parseCommand(
                DeleteScheduleCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new DeleteScheduleCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_cancelOrder() throws Exception {
        CancelOrderCommand command = (CancelOrderCommand) parser.parseCommand(
                CancelOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new CancelOrderCommand(INDEX_FIRST_ORDER), command);
    }

    @Test
    public void parseCommand_completeOrder() throws Exception {
        CompleteOrderCommand command = (CompleteOrderCommand) parser.parseCommand(
                CompleteOrderCommand.COMMAND_WORD + " " + INDEX_FIRST_ORDER.getOneBased());
        assertEquals(new CompleteOrderCommand(INDEX_FIRST_ORDER), command);
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
    public void parseCommand_editOrder() throws Exception {
        Order order = new OrderBuilder().build();
        EditOrderDescriptor descriptor = new EditOrderDescriptorBuilder(order).build();
        descriptor.setCustomer(null);
        descriptor.setPhone(null);
        EditOrderCommand command = (EditOrderCommand) parser.parseCommand(EditOrderCommand.COMMAND_WORD
                + " " + INDEX_FIRST_ORDER.getOneBased() + " "
                + OrderUtil.getEditOrderDescriptorDetails(descriptor));
        assertEquals(new EditOrderCommand(INDEX_FIRST_ORDER, Optional.empty(), Optional.empty(), descriptor), command);
    }

    @Test
    public void parseCommand_editSchedule() throws Exception {
        Schedule schedule = new ScheduleBuilder(MONDAY_SCHEDULE).build();
        EditScheduleDescriptor descriptor = new EditScheduleDescriptorBuilder(schedule).build();
        EditScheduleCommand command = (EditScheduleCommand) parser.parseCommand(EditScheduleCommand.COMMAND_WORD
                + " " + INDEX_FIRST_ORDER.getOneBased() + " "
                + ScheduleUtil.getEditScheduleDescriptorDetails(descriptor));
        assertEquals(new EditScheduleCommand(INDEX_FIRST_ORDER, descriptor, false), command);
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
    public void parseCommand_findOrder() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindOrderCommand command = (FindOrderCommand) parser.parseCommand(
                FindOrderCommand.COMMAND_WORD + " " + keywords.stream()
                        .collect(Collectors.joining(" ")));
        assertEquals(new FindOrderCommand(new OrderContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_schedule() throws Exception {
        Calendar calendar = VALID_SCHEDULE_MONDAY;
        ScheduleCommand command = (ScheduleCommand) parser.parseCommand(ScheduleCommand.COMMAND_WORD
                + " " + PREFIX_DATE + VALID_DATE_MONDAY);
        assertEquals(new ScheduleCommand(calendar), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_history() throws Exception {
        assertTrue(parser.parseCommand(HistoryCommand.COMMAND_WORD) instanceof HistoryCommand);
    }

    @Test
    public void parseCommand_undo() throws Exception {
        assertTrue(parser.parseCommand(UndoCommand.COMMAND_WORD) instanceof UndoCommand);
    }

    @Test
    public void parseCommand_redo() throws Exception {
        assertTrue(parser.parseCommand(RedoCommand.COMMAND_WORD) instanceof RedoCommand);
    }

    @Test
    public void parseCommand_listCustomer() throws Exception {
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD) instanceof ListCustomerCommand);
        assertTrue(parser.parseCommand(ListCustomerCommand.COMMAND_WORD + " 3")
                instanceof ListCustomerCommand);
    }

    @Test
    public void parseCommand_listPhone() throws Exception {
        assertTrue(parser.parseCommand(ListPhoneCommand.COMMAND_WORD) instanceof ListPhoneCommand);
        assertTrue(parser.parseCommand(ListPhoneCommand.COMMAND_WORD + " 3") instanceof ListPhoneCommand);
    }

    @Test
    public void parseCommand_listOrder() throws Exception {
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_WORD) instanceof ListOrderCommand);
        assertTrue(parser.parseCommand(ListOrderCommand.COMMAND_WORD + " 3") instanceof ListOrderCommand);
    }

    @Test
    public void parseCommand_switchCustomer() throws Exception {
        assertTrue(parser.parseCommand(SwitchCustomerPanelCommand.COMMAND_WORD) instanceof SwitchCustomerPanelCommand);
        assertTrue(parser.parseCommand(SwitchCustomerPanelCommand.COMMAND_WORD + " 3")
                instanceof SwitchCustomerPanelCommand);
    }

    @Test
    public void parseCommand_switchPhone() throws Exception {
        assertTrue(parser.parseCommand(SwitchPhonePanelCommand.COMMAND_WORD) instanceof SwitchPhonePanelCommand);
        assertTrue(parser.parseCommand(SwitchPhonePanelCommand.COMMAND_WORD + " 3")
                instanceof SwitchPhonePanelCommand);
    }

    @Test
    public void parseCommand_switchOrder() throws Exception {
        assertTrue(parser.parseCommand(SwitchOrderPanelCommand.COMMAND_WORD) instanceof SwitchOrderPanelCommand);
        assertTrue(parser.parseCommand(SwitchOrderPanelCommand.COMMAND_WORD + " 3")
                instanceof SwitchOrderPanelCommand);
    }

    @Test
    public void parseCommand_switchSchedule() throws Exception {
        assertTrue(parser.parseCommand(SwitchSchedulePanelCommand.COMMAND_WORD)
                instanceof SwitchSchedulePanelCommand);
        assertTrue(parser.parseCommand(SwitchSchedulePanelCommand.COMMAND_WORD + " 3")
                instanceof SwitchSchedulePanelCommand);
    }

    @Test
    public void parseCommand_switchArchived() throws Exception {
        assertTrue(parser.parseCommand(SwitchArchivedOrderPanelCommand.COMMAND_WORD)
                instanceof SwitchArchivedOrderPanelCommand);
        assertTrue(parser.parseCommand(SwitchArchivedOrderPanelCommand.COMMAND_WORD + " 3")
                instanceof SwitchArchivedOrderPanelCommand);
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
