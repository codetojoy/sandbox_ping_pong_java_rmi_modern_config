
package net.codetojoy.server; 

import net.codetojoy.server.config.PingConfiguration;

import net.codetojoy.common.*;
import net.codetojoy.common.rmi.*;
import net.codetojoy.common.util.*;

public class PingServiceImpl implements PingService {
    private PingConfiguration pingConfiguration = new PingConfiguration();

    @Override
    public long healthCheck() {
        return System.currentTimeMillis();
    } 

    private PongService getPongServiceWithRetries() {
        
        PongService pongService = new RetryLogic<PongService>().attemptWithRetries(
            new RetryOperation<PongService>() {
                public PongService execute() throws Exception {
                    return pingConfiguration.getPongService();
                }
            }
        ); 
   
        return pongService;
    }

    @Override
    public Ball ping(Ball ball) {
        Ball result = ball;

        if (! ball.isMaxedOut()) {
            String message = "PING #: " + (ball.getNumHits() + 1);
            System.out.println("TRACER " + message);

            Ball newBall = ball.hit(message);
            
            try {
                PongService pongService = getPongServiceWithRetries(); // pingConfiguration.getPongService();
                result = pongService.pong(newBall);
            } catch (Exception ex) {
                System.err.println("TRACER caught exception on pong service, so sequence will end here");
            }
        } else {
            System.out.println("TRACER halting sequence. numHits: " + ball.getNumHits());
            System.out.println("TRACER ball: " + ball.toString());
        }
        
        return result;
    }

    private static void exportPingServiceWithRetries(final PingServiceImpl pingServiceImpl) {

        Boolean ignored = new RetryLogic<Boolean>().attemptWithRetries(
            new RetryOperation<Boolean>() {
                public Boolean execute() throws Exception {
                    System.err.println("TRACER attemping export");

                    // this is the server which will listen ... so `main` will not exit
                    pingServiceImpl.pingConfiguration.getPingServiceExporter();
                    return true;
                }
            }
        ); 
    }

    public static void main(String[] args) throws Exception {
        System.out.println("\n\nTRACER: PingService starting up...");

        PingServiceImpl pingServiceImpl = new PingServiceImpl();

        exportPingServiceWithRetries(pingServiceImpl);
        // this is the server which will listen ... so `main` will not exit
        // pingServiceImpl.pingConfiguration.getPingServiceExporter();

        System.out.println("\n\nTRACER: PingService ready !");
    }
}
