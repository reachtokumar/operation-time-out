# operation-time-out
**OpeartionTimeOut utility** A java utility API to bind any kind of operation in a timeout restriction. Timeout can be in all java supported TimeUnit

## Access and use
You can see the below approach to get the jar and use
1. Checkout the repository. 
2. Move to operation-timeout-api application location
3. Build the application using command - gradle clean build
4. You will get the operation-time-out*.jar in the 'build' folder/directory.
5. Include the jar in your application's classpath and use it.

## API Introduction
```javascript
public T performOperation(Supplier<T> operation, Long timout, TimeUnit timeUnit)
```
### Details
Perform supplied operation in the provided time bound.
### Parameters
Parameter | Description
------------ | -------------
operation | Operation to be performed
timout | Timeout of the operation
timeUnit | Standard TimeUnit of the timeout

### Return
Supplied operation output or null if exception occurred or timeout happened<br/>

-------------------
```javascript
public T performOperation(Supplier<T> operation, Long timeout, TimeUnit timeUnit, OperationTimeOutObserver observer)
```
### Details
Perform supplied operation in the provided time bound. And observe success or failure through provided observer instance
### Parameters
All the parameters available in the 1st method. One extra parameter is required for opration's observations.

Parameter | Description
------------ | -------------
observer | To observ if exception occurred or timeout occurred or success

### Return
Supplied operation output or null if exception occurred or timeout happened

## Example Usage
```javascript
// Creating and populating data
List<UserBean> userBeanList = new ArrayList<>();

IntStream.range(1, 1000).forEach(i -> {
    userBeanList.add(new UserBean(i, "KK", "India", "Karnataka", "CV", "Bengaluru"));
});

// Operation time out usage
OperationTimeOut<List<UserBean>> timeOut = new OperationTimeOut<>();
var output = timeOut.performOperation(() -> {
            Collections.sort(userBeanList, new UserBeanIdComparator());
            return userBeanList;
        },
        (long) 1, TimeUnit.MICROSECONDS);

if(output == null) {
  // Timeout occurred or Some exception happened
} else {
  // Operation processed within time out boundry
}
```

```javascript
// Creating and populating data
List<UserBean> userBeanList = new ArrayList<>();

IntStream.range(1, 1000).forEach(i -> {
    userBeanList.add(new UserBean(i, "KK", "India", "Karnataka", "CV", "Bengaluru"));
});

// Operation time out usage
var operationTimeOutObserver = new OperationTimeOutObserver();
OperationTimeOut<List<UserBean>> timeOut = new OperationTimeOut<>();
var output = timeOut.performOperation(() -> {
            return userBeanList.stream().filter(userBean -> userBean.getName().equals("*k*")).collect(Collectors.toUnmodifiableList());
        },
        (long) 1, TimeUnit.MICROSECONDS, operationTimeOutObserver);

// if(output == null) {
if(!operationTimeOutObserver.getSuccess()) {
  // Timeout occurred or Some exception happened
  if(operationTimeOutObserver.getTimeOut()) {
    // Time out have occurred
  } else if(operationTimeOutObserver.getExceptionOccured()) {
    // Some exception occurred while performing the operation
    
    // Get the exception type
    operationTimeOutObserver.getExceptionType();
    
    // Get the exception object
    operationTimeOutObserver.getException();
  }
} else {
  // Operation processed within time out boundry
}
```

#### Note - You can see the example codes in the repository's example-consumer-application. You need to explore the Unite test cases in example-consumer-application
