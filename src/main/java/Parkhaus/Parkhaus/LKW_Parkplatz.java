package Parkhaus.Parkhaus;

public class LKW_Parkplatz extends Parkplatz{

    double height;
    double lenght;
    boolean anhänger;


    double getHeight(){
        return this.height;
    }

    double getlenght(){
        return this.lenght;
    }

    boolean isAnhänger(){
        return this.anhänger;
    }
}
