package seedu.jarvis.model.finance;

import org.junit.jupiter.api.Test;
import seedu.jarvis.model.financetracker.Instalment;
import seedu.jarvis.model.financetracker.InstalmentList;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class InstalmentListTest {

    @Test
    public static void main(String[] args) throws Exception {
        ArrayList<Instalment> listInstalments = new ArrayList<>();
        listInstalments.add(new InstalmentStub());
        InstalmentList ip = new InstalmentList(listInstalments);
        ip.addInstalment(new InstalmentStub());
        ip.addInstalment(new InstalmentStub());
        ip.addInstalment(new InstalmentStub());
        ip.addInstalment(new InstalmentStub());
        assertEquals(65.0, ip.getTotalMoneySpentOnInstalments());
    }
}

class InstalmentStub extends Instalment {
    public InstalmentStub() {
        super("Netflix subscription", 13.0);
    }
}
