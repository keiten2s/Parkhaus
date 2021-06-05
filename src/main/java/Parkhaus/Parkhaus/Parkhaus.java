package Parkhaus.Parkhaus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manages an array with Parkplatz objects
 */
public class Parkhaus {
    private Parkplatz[] spots;
    ArrayList<Auto> autos;

    public Parkhaus(int size) {
        this.spots = new Parkplatz[size];

        for(int i = 0; i < spots.length; i++) {
            spots[i] = new Parkplatz();
        }
    }

    /**
     * Constructor method
     *
     */
    public Parkhaus(int size, Auto[] autos) {
        this.spots = new Parkplatz[size];
        this.autos = new ArrayList<Auto>(Arrays.asList(autos));

        for(int i = 0; i < spots.length; i++) {
            spots[i] = new Parkplatz();
        }
    }

    public boolean exitSpot(int autoNr) {
        for (Parkplatz spot : spots) {
            if (spot.getFahrzeug() != null && spot.getFahrzeug().nr() == autoNr) {
                spot.exit();
                return true;
            }
        }

        return false;
    }

    public boolean enterSpot(Auto auto, int spotNr) {
        //ist ein parkplatz gefragt der auserhalb des Arrays liegt ?
        if (spotNr >= spots.length) {
            Parkplatz[] s = new Parkplatz[spotNr];

            System.arraycopy(spots, 0, s, 0, s.length);
            spots = s;
        }

        if (spots[spotNr-1].istBelegt()) {
            System.out.println("Parkplatz ist schon belegt!");
            return false;
        }

        return spots[spotNr-1].belegen(auto);
    }

    public int anzahlBelegt() {
        int anz = 0;

        for(int i=0;i<spots.length;i++) {
            if(spots[i].istBelegt()) {
                anz +=1;
            }
        }
        return anz;
    }


}