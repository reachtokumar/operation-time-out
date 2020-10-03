package optimeout.consume.example;

import java.util.Comparator;

public class UserBeanIdComparator implements Comparator<UserBean> {

    @Override
    public int compare(UserBean o1, UserBean o2) {
        return o1.getId().compareTo(o2.getId());
    }
}
