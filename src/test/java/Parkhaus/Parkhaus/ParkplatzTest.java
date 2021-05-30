package Parkhaus.Parkhaus;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ParkplatzTest {

    @Test
    void testNr() {
        ParkplatzInt p = new Parkplatz();
        int nr = 1;
        p.setNr(1);
        Assertions.assertTrue(p.getNr() == nr);
    }

    @Test
    void testBelegen() {
        ParkplatzInt p = new Parkplatz(1);
        Assertions.assertTrue(p.belegen(new Auto()));
        Assertions.assertTrue(p.istBelegt());
    }

    @Test
    void testBelegenUndAusgeben() {
        ParkplatzInt p = new Parkplatz(1);
        Auto a = new Auto();
        Assertions.assertTrue(p.belegen(a));
        Assertions.assertTrue(p.istBelegt());
        Assertions.assertTrue(p.getFahrzeug() == a);
    }

}