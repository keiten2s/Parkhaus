package Parkhaus.Parkhaus;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.logging.ErrorManager;

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
            sc = new Scanner(new File("h:/Neuer Ordner/Parkhaus/src/main/files/source.csv"));
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
        downloadFile(response);
        scheduler.shutdownNow();
    }


    public void downloadFile(HttpServletResponse response) {

        String sourceFile = "h:/Neuer Ordner/Parkhaus/src/main/files/source.csv";
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