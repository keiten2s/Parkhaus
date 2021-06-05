package Parkhaus.Parkhaus;


import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@WebListener
public class BackgroundJobManager implements ServletContextListener {
    //hUNd

    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        //scheduler.scheduleAtFixedRate(new YourParsingJob(), 0, 5, TimeUnit.HOUR);
        System.out.println("Start");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        System.out.println("Stop");
        scheduler.shutdownNow();
    }

}