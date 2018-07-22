
package net.codetojoy.common.util; 

public class RetryLogic<T> {

    private static final long DEFAULT_DELAY_IN_MILLIS = 1000;
    private static final int DEFAULT_MAX_ATTEMPTS = 400;

    public T attemptWithRetries(RetryOperation<T> operation) {
        return attemptWithRetries(operation, DEFAULT_DELAY_IN_MILLIS, DEFAULT_MAX_ATTEMPTS);
    }

    public T attemptWithRetries(RetryOperation<T> operation, long delayInMillis, int maxAttempts) {
        T result = null;
   
        boolean isDone = false;
        int attemptCount = 0;
 
        while (! isDone) {
            try {
                result = operation.execute();
            } catch (Exception ex) {
                System.err.println("TRACER caught exception while attempting operation. attempt # " + attemptCount + " of " + maxAttempts);
                try { Thread.sleep(delayInMillis); } catch (Exception e) {} 
            }

            if ((result != null) || (attemptCount >= maxAttempts)) { 
                isDone = true; 
            }

            attemptCount++;
        }
        
        return result;
    }
}
