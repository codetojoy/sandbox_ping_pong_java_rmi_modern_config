
package net.codetojoy.monitor; 

import net.codetojoy.common.*;
import net.codetojoy.monitor.config.MonitorConfiguration;

import java.util.*;
import java.util.concurrent.*;

import java.rmi.registry.*;

class Task implements Runnable {
    private MonitorConfiguration monitorConfiguration;
 
    private static final String PING_SERVICE = "pingService";
    private static final String PONG_SERVICE = "pongService";

    private final Set ALL_ENTRIES = new HashSet<>();

    Task(MonitorConfiguration monitorConfiguration) {
        this.monitorConfiguration = monitorConfiguration; 

        ALL_ENTRIES.add(PING_SERVICE);
        ALL_ENTRIES.add(PONG_SERVICE);
    }

    private void healthCheck(String serviceName) {
        try {
            String result = "N/A";

            if (serviceName.equals(PING_SERVICE)) {
                PingService pingService = monitorConfiguration.getPingService();
                long healthCheckResult = pingService.healthCheck();
                result = new Date(healthCheckResult).toString();
            } else if (serviceName.equals(PONG_SERVICE)) {
                PongService pongService = monitorConfiguration.getPongService();
                long healthCheckResult = pongService.healthCheck();
                result = new Date(healthCheckResult).toString();
            } 

            System.out.println("TRACER from " + serviceName + " : " + result);
        } catch(Exception ex) {
            System.err.println("TRACER caught ex: " + ex.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("--------------------------");
            System.out.println("\n\nTRACER " + new Date() + " checking...");
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2020);
            String[] entries = registry.list();

            if (entries != null) {
                Set<String> refSet = new HashSet(ALL_ENTRIES); 
                Set<String> entrySet = new HashSet<String>(Arrays.asList(entries));
                refSet.removeAll(entrySet);

                if (! refSet.isEmpty()) {
                    for (String entry : refSet) {
                        System.out.println("\nTRACER found entry: " + entry); 
                        healthCheck(entry);
                    }
                }
            } else {
                System.out.println("TRACER no entries found");
            } 
        } catch (Exception ex) {
            System.err.println("TRACER caught exception: " + ex.getMessage());
        }
    }
}

public class Monitor {
    private MonitorConfiguration monitorConfiguration;
    private ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);

    Monitor(MonitorConfiguration monitorConfiguration) {
        this.monitorConfiguration = monitorConfiguration;
    }

    public void start() {
        final int delayInSeconds = 4;
        scheduledPool.scheduleAtFixedRate(new Task(monitorConfiguration), 
                                            delayInSeconds, delayInSeconds, TimeUnit.SECONDS);
    }
    
    public static void main(String[] args) {
        MonitorConfiguration monitorConfiguration = new MonitorConfiguration();
        Monitor monitor = new Monitor(monitorConfiguration);
        monitor.start();

        while (true) {
            try {  Thread.sleep(30*1000); } catch (Exception ex) {}
        }
    }
}
