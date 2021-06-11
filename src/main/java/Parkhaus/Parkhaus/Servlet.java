package Parkhaus.Parkhaus;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DemoServlet
 */
@WebServlet("/Parkhaus")
    //@WebServlet(name = "Parkhaus", value = "/")
public class Servlet extends HttpServlet {
    static final long serialVersionUID = 1L;

    Float summe = 0.0f;
    Integer ausfahren = 0;
    Float average = 0.0f;
    Float price_help = 0.0f;
    int langzeitparken = 0;
    int kurzzeitparken = 0;
    int einfahren = 0;
    final Parkhaus parkhaus = new Parkhaus(10);

    /**
     * Constructor method
     * @see HttpServlet#HttpServlet()
     */
    public Servlet() {
        super();
    }

    /**
     * HttpGet
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //System.out.println(BackgroundJobManager.str2.size());
        for (int i = 0; i < BackgroundJobManager.str2.size(); i++) {
            //System.out.println("kekw");
            //System.out.println(BackgroundJobManager.str2.get(i));
            String param = BackgroundJobManager.str2.get(i);
            System.out.println(param);
            String req = "http://localhost:8080/";
            URL url = new URL(req);
            HttpURLConnection connection = (HttpURLConnection)
                    url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects(false);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" +  Integer.toString(param.getBytes().length));
            connection.setUseCaches (false);

            DataOutputStream out = new DataOutputStream(connection.getOutputStream ());
            out.writeBytes(param);
            out.flush();
            out.close();
            connection.disconnect();
        }
        String queryString = request.getQueryString();
        if (queryString == null)
            return;

        String[] requestParamString = request.getQueryString().split("=");
        String command = requestParamString[0];
        String param = requestParamString[1];

        if (command.equals("cmd")) {
            PrintWriter out = response.getWriter();
            CommandHandler commandhandler = new CommandHandler(request,response);

            switch (param) {
                case "sum":
                    commandhandler.sum(summe);
                    break;
                case "avg":
                    commandhandler.avg(average);
                    break;
                case "Besucher":
                    commandhandler.besucher(einfahren);
                    break;
                case "Preis":
                    commandhandler.preis(price_help);
                    break;
                case "Anzahl":
                    commandhandler.graphAnzahl(parkhaus,langzeitparken,kurzzeitparken);
                    break;
                case "Parkverhalten":
                    commandhandler.graphParkverhalten(langzeitparken,kurzzeitparken);
                    break;
                default:
                    System.out.println("Invalid Command: " + request.getQueryString());
                    break;
            }
        }
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String messsage = request.getParameter("str");
        System.out.println(messsage);


        String body = getBody(request);
        //0 = status, 1 = int nummer, 2 = ?, 3 = String sum, 4 = ?, 5 = ?, 6 = String farbe
        String[] parts = body.split(",");
        String status = parts[0];
        String sum = "";

        if (parts.length >= 3) {
            sum = parts[3];
        }


        if (sum.length() > 3) {
            String res = sum.substring(0, sum.length() - 3) + "." + sum.substring(sum.length() - 3, sum.length());
            summe += Float.parseFloat(res);
        } else if (sum.length() == 3) {
            String res = "0." + sum;
            summe += Float.parseFloat(res);
        } else if (sum.length() == 2) {
            String res = "0.0" + sum;
            summe += Float.parseFloat(res);
        }
            // System.out.println(summe);

        if (status.equals("leave")) {
            BackgroundJobManager.csv_out(parts);
            ausfahren++;
            price_help = Float.parseFloat(parts[4]);
            price_help = (Float.parseFloat(sum) / price_help) * 10;

            Arrays.stream(parts)
                    .filter(data -> Integer.parseInt(parts[3]) > 20000)
                    .forEach(data -> langzeitparken++);

            Arrays.stream(parts)
                    .filter(data -> Integer.parseInt(parts[3]) <= 20000)
                    .forEach(data -> kurzzeitparken++);

            int nr = Integer.parseInt(parts[1]);
            parkhaus.exitSpot(nr);

        } else if (status.equals("enter")) {
            BackgroundJobManager.csv_out(parts);
            einfahren++;
            int parkplatzNr = Integer.parseInt(parts[7]);
            Auto car = new Auto(Integer.parseInt(parts[1]), parts[6]);

            parkhaus.enterSpot(car, parkplatzNr);
        }

        average = summe / ausfahren;

        System.out.println(body);
    }

    private ServletContext getApplication() {
        return getServletConfig().getServletContext();
    }



    private Float getPersistentSum() {
        Float sum = (Float) getApplication().getAttribute("sum");

        return sum;
    }

    private static String getBody(HttpServletRequest request) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {
            InputStream inputStream = request.getInputStream();

            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;

                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
                stringBuilder.append("");
            }
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException ignored) {}
            }
        }
        
        return stringBuilder.toString();
    }
}