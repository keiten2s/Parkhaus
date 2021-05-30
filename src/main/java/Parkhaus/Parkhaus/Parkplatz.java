package Parkhaus.Parkhaus;

public class Parkplatz implements ParkplatzInt {

    private Auto auto;
    private int nr = 0;

    public Parkplatz() {}

    public Parkplatz(int n) {
        nr = n;
    }

    @Override
    public void setNr(int nr) {
        this.nr = nr;
    }

    @Override
    public int getNr() {
        return nr;
    }

    @Override
    public boolean belegen(Auto auto) {
        if(istBelegt()) {
            return false;
        }

        this.auto = auto;

        return true;
    }

    public Auto exit() {
        if (this.auto != null) {
            Auto car = auto;
            this.auto = null;

            return car;
        }

        return null;
    }

    @Override
    public boolean istBelegt() {
        return (auto != null);
    }

    @Override
    public Auto getFahrzeug() {
        return auto;
    }
}
