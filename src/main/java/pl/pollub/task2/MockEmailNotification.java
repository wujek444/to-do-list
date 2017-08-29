package pl.pollub.task2;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@AllArgsConstructor
@Data
class MockEmailNotification{
   private int taskId;
   private Collection<String> emails;
}