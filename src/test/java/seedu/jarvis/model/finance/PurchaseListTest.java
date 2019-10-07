package seedu.jarvis.model.finance;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.financetracker.Purchase;
import seedu.jarvis.model.financetracker.PurchaseList;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests totalSpending() method //todo edit documentation
 */
public class PurchaseListTest {

    @Test
    public static void main(String[] args) throws Exception {
        ArrayList<Purchase> listPurchases = new ArrayList<>();
        listPurchases.add(new PurchaseStub());
        PurchaseList p = new PurchaseList(listPurchases);
        p.addSinglePurchase(new PurchaseStub());
        p.addSinglePurchase(new PurchaseStub());
        p.addSinglePurchase(new PurchaseStub());
        p.addSinglePurchase(new PurchaseStub());
        p.addSinglePurchase(new PurchaseStub());
        assertEquals(30.0, p.totalSpending());
    }
}

class PurchaseStub extends Purchase {
    public PurchaseStub() {
        super("test purchase", 5.00);
    }
}
