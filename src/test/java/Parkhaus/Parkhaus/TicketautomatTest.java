package Parkhaus.Parkhaus;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

public class TicketautomatTest {
    @Test
    public void testNegativeMoney() {
        Ticketautomat automat = new Ticketautomat(-5);
        assertTrue(automat.getPrice() > 0);
    }
}