package pl.pollub.task2;

import java.util.Collection;

public class EmailNotifierMockImplementation implements EmailNotifier{

    private MockEmailNotification mockNotification;

    @Override
    public void notify(int taskId, Collection<String> emails) {
        mockNotification = new MockEmailNotification(taskId, emails);
    }

    @Override
    public MockEmailNotification getResultNotification() {
        return mockNotification;
    }


}
