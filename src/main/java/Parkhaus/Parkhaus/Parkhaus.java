package Parkhaus.Parkhaus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Manages an array with Parkplatz objects
 */
public class Parkhaus {
    private Parkplatz[] spots;
    List<Auto> autos;

    /**
     * Constructor method
     *
     */
    public Parkhaus(int size, Auto[] autos) {
        this.spots = new Parkplatz[size];
        this.autos = (List) new ArrayList<Auto>(Arrays.asList(autos));

        for(int i = 0; i < spots.length; i++) {
            spots[i] = new Parkplatz();
        }
    }

    public boolean exitSpot(int autoNr) {
        for(int i=0;i<spots.length;i++) {
            if(spots[i].getFahrzeug() != null && spots[i].getFahrzeug().nr() == autoNr) {
                spots[i].exit();
                return true;
            }
        }

        return false;
    }

    public boolean enterSpot(Auto auto, int spotNr) {
        //ist ein parkplatz gefragt der auserhalb des Arrays liegt ?
        if(spotNr >= spots.length) {
            Parkplatz[] s = new Parkplatz[spotNr];
            for(int i=0;i<s.length;i++) {
                s[i] = spots[i];
            }
            spots = s;
        }

        if(spots[spotNr-1].istBelegt()) {
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