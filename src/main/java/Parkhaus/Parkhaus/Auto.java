package Parkhaus.Parkhaus;
//Test
//Test2222
public class Auto implements AutoInt {
    private int nr;
    private String farbe;
    private String vehicletype;
    private String license;
    private boolean isParked = false;

    /**
     * Blank Constructor
     */
    public Auto() {

    }

    /**
     * Constructor Method
     * @param nr Database ID
     * @param //timerbegin When the car starts parking
     * @param //duration How long the car parks
     * @param //price Price
     * @param //ticket TicketId @see Ticket
     * @param farbe Color of the car (color code)
     * @param //space Index position in Parkhaus
     * @param //clientcategory Familycar, etc.
     * @param vehicletype Type of vehicle (any)
     * @param license Licenseplate
     */
    public Auto(int nr, String farbe, String vehicletype, String license) {
        if (pruefeKennzeichen(this.license) == false) {
            throw new RuntimeException();
        }

        this.nr = nr;
        this.farbe = farbe;
        this.vehicletype = vehicletype;
        this.license = license;
    }

    /**
     * @param nr Database ID
     * @param farbe Color of the car (color code)
     */
    public Auto(int nr, String farbe) {
        this.farbe = farbe;
        this.nr = nr;
    }

    /**
     * Getter
     * @return Color of the car (color code)
     */
    public String farbe() {
        return farbe;
    }

    /**
     * Getter
     * @return Database ID
     */
    public int nr() {
        return nr;
    }

    /**
     * Getter
     * @return Licenseplate
     */
    public String license() {
        return this.license;
    }

    /**
     * Getter
     * @return Vehicle type any
     */
    public String vehicletype() {
        return this.vehicletype;
    }

    /**
     * Getter
     * @return If the car is parked or not
     */
    public boolean isParked() {
        return this.isParked;
    }

    /**
     * Checks if the license plate format is correct
     * @param license License plate
     * @return Yes or no
     */
    public boolean pruefeKennzeichen(String license){
        //prüfen ob Kein Nummerschild
        if (license.isEmpty() || license.length() < 5) {
            return false;
        }

        //prüfen ob überhaupt Bindestriche
        if (license.indexOf("-") == -1 ) {
            return false;
        }

        int bindestrichPosi1 = license.indexOf("-");
        int bindestrichPosi2 = license.indexOf("-", bindestrichPosi1+1);

        //Überprüfung nach mehr als 2 Bindestriche
        if (license.indexOf("-", bindestrichPosi2+1) != -1) {
            return false;
        }

        String first = license.substring(0,bindestrichPosi1);
        String second = license.substring(bindestrichPosi1+1 ,bindestrichPosi2);

        //Überprüfung nach Buschstaben
        if ((!(first.matches("[a-zA-Z]+"))) || (!(second.matches("[a-zA-Z]+")))) {
            return false;
        }

        String numbers = license.substring(bindestrichPosi2 + 1, license.length());

        //Überprüfung nach Zahlen
        for (int i = 0; i < numbers.length(); i++) {
            if (!Character.isDigit(numbers.charAt(i))) {
                return false;
            }
        }

        return true;
    }
}