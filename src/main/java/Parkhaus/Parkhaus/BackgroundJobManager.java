package Parkhaus.Parkhaus;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@WebListener
public class BackgroundJobManager implements ServletContextListener {

    private static String path = System.getProperty("user.dir")+"\\"+"source_.csv";
    static StringBuilder sb = new StringBuilder();
    public static ArrayList<String> str2;

    private ScheduledExecutorService scheduler;
    ArrayList<String> str = new ArrayList<>();
    //ArrayList<String> str2 = new ArrayList<>();

    @Override
    public void contextInitialized(ServletContextEvent event)  {
        System.out.println(path+"source.csv");
        File yourFile = new File(path);
        try {
            yourFile.createNewFile(); // if file already exists will do nothing
        } catch (IOException e) {
            e.printStackTrace();
        }

        scheduler = Executors.newSingleThreadScheduledExecutor();
        Scanner sc = null;
        String test = "";
        try {
            sc = new Scanner(new File(path));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.useDelimiter("");
        while(sc.hasNextLine()) {

            str.add(sc.nextLine().replace("leave", "enter"));
        }
        sc.close();
        str2 = str;
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }

    public static void csv_out(String[] parts) {
        try (PrintWriter writer = new PrintWriter(new File(path))) {

            for(int i = 0; i < parts.length; i++) {
                if(i > 0) {
                    sb.append(",");
                }
                    sb.append(parts[i]);
            }
            sb.append("\n");
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}