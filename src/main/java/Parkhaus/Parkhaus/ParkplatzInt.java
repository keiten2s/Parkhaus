package Parkhaus.Parkhaus;

public abstract interface ParkplatzInt {
    public void setNr(int nr);
    public int getNr();
    public boolean belegen(Auto a);
    public boolean istBelegt();
    public Auto getFahrzeug();
}