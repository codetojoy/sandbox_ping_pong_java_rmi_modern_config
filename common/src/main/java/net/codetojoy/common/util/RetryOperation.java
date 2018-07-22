package net.codetojoy.common.util; 

public interface RetryOperation<T> {
    T execute() throws Exception; 
}
