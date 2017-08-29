package pl.pollub.task2;

public interface UserService {

    User getUserById(int id);

    void addUsers(User... usersToAdd);

}
