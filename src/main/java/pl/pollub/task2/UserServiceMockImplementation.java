package pl.pollub.task2;

import org.assertj.core.util.Lists;

import java.util.ArrayList;
import java.util.Collection;

public class UserServiceMockImplementation implements UserService {

    private Collection<User> users = new ArrayList<>();

    @Override
    public User getUserById(int id) {
        return (User)users.stream().filter(user -> user.getId() == id).toArray()[0];
    }

    @Override
    public void addUsers(User... usersToAdd){
        users.addAll(Lists.newArrayList(usersToAdd));
    }
}
