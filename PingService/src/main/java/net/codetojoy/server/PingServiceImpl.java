
package net.codetojoy.server; 

import net.codetojoy.server.config.PingConfiguration;

import net.codetojoy.common.*;
import net.codetojoy.common.rmi.*;

public class PingServiceImpl implements PingService {
    private PingConfiguration pingConfiguration = new PingConfiguration();

    @Override
    public long healthCheck() {
        return System.currentTimeMillis();
    } 

    @Override
    public Ball ping(Ball ball) {
        Ball result = ball;

        if (! ball.isMaxedOut()) {
            String message = "PING #: " + (ball.getNumHits() + 1);
            System.out.println("TRACER " + message);

            Ball newBall = ball.hit(message);
            
            try {
                PongService pongService = pingConfiguration.getPongService();
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

    public static void main(String[] args) throws Exception {
        System.out.println("\n\nTRACER: PingService starting up...");

        PingServiceImpl pingServiceImpl = new PingServiceImpl();

        // this is the server which will listen ... so `main` will not exit
        pingServiceImpl.pingConfiguration.getPingServiceExporter();

        System.out.println("\n\nTRACER: PingService ready !");
    }
}
