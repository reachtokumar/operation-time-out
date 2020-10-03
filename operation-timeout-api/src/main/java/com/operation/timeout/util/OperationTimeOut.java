package com.operation.timeout.util;

import com.operation.timeout.observer.OperationTimeOutObserver;

import java.util.Optional;
import java.util.concurrent.*;
import java.util.function.Supplier;

/***
 *
 * @param <T> Type of output to be expected, after performing the operation
 */
public class OperationTimeOut<T> {

    /***
     * Perform supplied operation in the provided time bound.
     * @param operation Operation to be performed
     * @param timout Timeout of the operation
     * @param timeUnit Standard TimeUnit
     * @return Supplied operation output or null if exception occurred or timeout happened
     */
    public T performOperation(Supplier<T> operation, Long timout, TimeUnit timeUnit){
        return performOperation(operation, timout, timeUnit, null);
    }

    /***
     * Perform supplied operation in the provided time bound. And observe success or failure through provided observer instance
     * @param operation Operation to be performed
     * @param timeout Timeout of the operation
     * @param timeUnit Standard TimeUnit
     * @param observer To observ if exception occurred or timeout occurred or success
     * @return Supplied operation output or null if exception occurred or timeout happened
     */
    public T performOperation(Supplier<T> operation, Long timeout, TimeUnit timeUnit, OperationTimeOutObserver observer){
        Optional.ofNullable(observer).ifPresent(observer1 -> observer.cleanObserver());

        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<T> opDataFuture = service.submit(() -> {
           T returnData = null;
           try {
               // TODO: Handle the operation where return is void
               returnData = operation.get();
           } catch (Exception e) {
               Optional.ofNullable(observer).ifPresent(observer1 -> {
                   observer1.setExceptionType(e.toString());
                   observer1.setException(e);
                   observer1.setExceptionOccured(true);
               });
           }

            // TODO: Handle different type of return data (handle the generics pattern ?)
           return returnData;
        });

        //Shutdown the service. Don't receive further oprations
        service.shutdown();

        try {
            boolean isTerminatedProperly = service.awaitTermination(timeout, timeUnit);

            if (isTerminatedProperly) {
                Optional.ofNullable(observer).ifPresent(observer1 -> observer1.setSuccess(true));
                return opDataFuture.get();
            } else {
                Optional.ofNullable(observer).ifPresent(observer1 -> observer1.setTimeOut(true));
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            Optional.ofNullable(observer).ifPresent(observer1 -> {
                //Checking if already exception occurred while processing the passed operation
                if (!observer1.getExceptionOccured()) {
                    observer1.setExceptionType(e.toString());
                    observer1.setException(e);
                    observer1.setExceptionOccured(true);
                }
            });

            return null;
        }
    }

    // TODO: Write similar method to return Optional

    // TODO: Write similar method to get data and if data not found throw exception
}
