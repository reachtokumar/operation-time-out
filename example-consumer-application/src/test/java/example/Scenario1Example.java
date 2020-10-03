package example;

import com.operation.timeout.observer.OperationTimeOutObserver;
import com.operation.timeout.util.OperationTimeOut;
import optimeout.consume.example.UserBean;
import optimeout.consume.example.UserBeanIdComparator;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Scenario1Example {
    static List<UserBean> userBeanList = null;

    @BeforeAll
    public static void initialize() {
        userBeanList = new ArrayList<>();

        IntStream.range(1, 1000).forEach(i -> {
            userBeanList.add(new UserBean(i, "KK", "India", "Karnataka", "CV", "Bengaluru"));
        });
    }

    @DisplayName("Timeout occurred check with null data returned")
    @Test
    public void testTimeoutSuccessWithNullReturn(){
        OperationTimeOut<List<UserBean>> timeOut = new OperationTimeOut<>();
        var output = timeOut.performOperation(() -> {
                    Collections.sort(userBeanList, new UserBeanIdComparator());
                    return userBeanList;
                },
                (long) 1, TimeUnit.MICROSECONDS);
        Assertions.assertNull(output);
    }

    @DisplayName("Timeout occurred check with observer")
    @Test
    public void testTimeoutWithObserver(){
        var operationTimeOutObserver = new OperationTimeOutObserver();
        OperationTimeOut<List<UserBean>> timeOut = new OperationTimeOut<>();
        var output = timeOut.performOperation(() -> {
                    return userBeanList.stream().filter(userBean -> userBean.getName().equals("*k*")).collect(Collectors.toUnmodifiableList());
                },
                (long) 1, TimeUnit.MICROSECONDS, operationTimeOutObserver);
        Assertions.assertNull(output);
        Assertions.assertTrue(operationTimeOutObserver.getTimeOut());
    }

    @AfterAll
    public static void destroy() {
        userBeanList.clear();
        userBeanList = null;
    }
}
