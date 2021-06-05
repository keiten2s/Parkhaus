package Parkhaus.Parkhaus;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Arrays;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@WebListener
public class BackgroundJobManager implements ServletContextListener {
    HttpServletRequest request;
    HttpServletResponse response;

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduler.scheduleAtFixedRate(new YourParsingJob(), 0, 5, TimeUnit.HOUR);
        //System.out.println("Start");

        Scanner sc = null;
        try {
            sc = new Scanner(new File("H:/Neuer Ordner/Parkhaus/src/main/files/source.csv"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.useDelimiter(",");
        while (sc.hasNext())
        {
            System.out.print(sc.next()+"\n");
        }
        sc.close();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        //System.out.println("Stop");
        //csv_out(parts);
        scheduler.shutdownNow();
    }

/**
    public void downloadFile(HttpServletResponse response) {

        String sourceFile = "H:/Neuer Ordner/Parkhaus/src/main/files/source.csv";
        try {
            FileInputStream inputStream = null;
            try {
                inputStream = new FileInputStream(sourceFile);
            } catch (FileNotFoundException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
            String disposition = "attachment; fileName=source.csv";
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", disposition);
            response.setHeader("content-Length", String.valueOf(stream(inputStream, response.getOutputStream())));

        } catch (IOException e) {
            System.out.println (e.toString());
        }
    }*/

    public static void csv_out(String[] parts){
        try (PrintWriter writer = new PrintWriter(new File("test.csv"))) {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < parts.length; i++) {
                sb.append(parts[i]);
            }
            sb.append("\n");
            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private long stream(InputStream input, OutputStream output) throws IOException {

        try (ReadableByteChannel inputChannel = Channels.newChannel(input); WritableByteChannel outputChannel = Channels.newChannel(output)) {
            ByteBuffer buffer = ByteBuffer.allocate(10240);
            long size = 0;

            while (inputChannel.read(buffer) != -1) {
                buffer.flip();
                size += outputChannel.write(buffer);
                buffer.clear();
            }
            return size;
        }
    }
}