package Parkhaus.Parkhaus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
//test
public class CommandHandler {
    HttpServletRequest request;
    HttpServletResponse response;

    public CommandHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        this.request = request;
        this.response = response;
    }

    public void sum(float sum) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        out.println("Gesamteinnahmen: &euro; " + sum);

        System.out.println("sum = " + sum);
    }

    public void avg(float avg) throws IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        out.println("durschnittliche Kosten: &euro; " + avg + " durchschnittliche Dauer: " + avg + " Minuten");
        System.out.println("avg = " + avg);
    }

    public void besucher(int besucher) throws IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        out.println("Gesamt Besucherzahl " + besucher);
        System.out.println("Besucherzahlen = " + besucher);
    }

    public void preis(float preis) throws IOException {
        PrintWriter out = response.getWriter();

        response.setContentType("text/html");

        out.println("Preis pro Stunde: &euro;" + preis);
        System.out.println("Preis pro Stunde = " + preis);
    }

    public void graphAnzahl(Parkhaus parkhaus,int langzeitparken,int kurzzeitparken) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        String anzahlString = "{\n" +
                "  \"data\": [\n" +
                "    {\n" +
                "      \"x\": [\n"+
                "        \"Anzahl\",\n" +
                "        \"AnzahlGesamt\"\n" +
                "      ],\n" +
                "      \"y\": [" ;
        int anz = parkhaus.anzahlBelegt();
        anzahlString  +=  ""+anz+",\n";
        anzahlString  +=  ""+(langzeitparken+kurzzeitparken+anz)+"\n";
        anzahlString  +=  "      ],\n" +
                "      \"type\": \"bar\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";
        out.println(anzahlString);

    }

    public void graphParkverhalten(int langzeitparken, int kurzzeitparken) throws IOException {
        PrintWriter out = response.getWriter();
        response.setContentType("text/plain");

        String chartString = "{\"data\": [{\"values\": [\""+langzeitparken+"\",\""+kurzzeitparken+"\"],\"labels\": [\"Langzeitparker (ab 20 Min)\",\"Kurzzeitparker\"],\"type\": \"pie\"}]}";

        out.println(chartString);
    }

}
