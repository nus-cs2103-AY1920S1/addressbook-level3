package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedOrder.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalOrders.ORDER_JSON_TEST;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.customer.Customer;
import seedu.address.model.order.Price;
import seedu.address.model.order.Status;
import seedu.address.model.phone.Phone;
import seedu.address.model.schedule.Schedule;

public class JsonAdaptedOrderTest {

    private static final String INVALID_ID = " ";
    private static final String INVALID_PRICE = " ";
    private static final String INVALID_STATUS = "ANyhow";
    private static final String INVALID_TAG = "#friend";


    private static final String VALID_ID = ORDER_JSON_TEST.getId().toString();
    private static final Customer VALID_CUSTOMER = ORDER_JSON_TEST.getCustomer();
    private static final Phone VALID_PHONE = ORDER_JSON_TEST.getPhone();
    private static final String VALID_PRICE = ORDER_JSON_TEST.getPrice().value;
    private static final String VALID_STATUS = ORDER_JSON_TEST.getStatus().toString();
    private static final Schedule VALID_SCHEDULE = ORDER_JSON_TEST.getSchedule().get();
    private static final List<JsonAdaptedTag> VALID_TAGS = ORDER_JSON_TEST.getTags().stream()
            .map(JsonAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validOrderDetails_returnOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(ORDER_JSON_TEST);
        assertEquals(ORDER_JSON_TEST, order.toModelType());
    }

    @Test
    public void toModelType_validInvalidID_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(INVALID_ID, new JsonAdaptedCustomer(VALID_CUSTOMER),
                new JsonAdaptedPhone(VALID_PHONE), VALID_PRICE, VALID_STATUS, new JsonAdaptedSchedule(VALID_SCHEDULE),
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT,
                UUID.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_validInvalidPrice_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ID, new JsonAdaptedCustomer(VALID_CUSTOMER),
                new JsonAdaptedPhone(VALID_PHONE), INVALID_PRICE, VALID_STATUS, new JsonAdaptedSchedule(VALID_SCHEDULE),
                VALID_TAGS);
        String expectedMessage = Price.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, order::toModelType);
    }

    @Test
    public void toModelType_validInvalidStatus_returnsOrder() throws Exception {
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ID, new JsonAdaptedCustomer(VALID_CUSTOMER),
                new JsonAdaptedPhone(VALID_PHONE), VALID_PRICE, INVALID_STATUS, new JsonAdaptedSchedule(VALID_SCHEDULE),
                VALID_TAGS);

        assertEquals(Status.CANCELLED, order.toModelType().getStatus());
    }

    @Test
    public void toModelType_invalidTag_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedOrder order = new JsonAdaptedOrder(VALID_ID, new JsonAdaptedCustomer(VALID_CUSTOMER),
                new JsonAdaptedPhone(VALID_PHONE), VALID_PRICE, INVALID_STATUS, new JsonAdaptedSchedule(VALID_SCHEDULE),
                invalidTags);
        assertThrows(IllegalValueException.class, order::toModelType);
    }



}
