package testutil;

import com.opensource.projectu.openapi.model.Complexity;
import com.opensource.projectu.openapi.model.Project;
import com.opensource.projectu.openapi.model.State;
import com.opensource.projectu.openapi.model.Task;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MockTestingUtil {

    public static Project buildMockProject() {
        return Project.builder()
                .id(UUID.randomUUID())
                .title("title1")
                .description("description1")
                .tasks(Arrays.asList(
                        Task.builder()
                                .id(UUID.randomUUID())
                                .title("task1")
                                .description("task description1")
                                .done(false)
                                .estimatedDurationInHours(1)
                                .build(),
                        Task.builder()
                                .id(UUID.randomUUID())
                                .title("task2")
                                .description("task description2")
                                .done(false)
                                .estimatedDurationInHours(2)
                                .build(),
                        Task.builder()
                                .id(UUID.randomUUID())
                                .title("task3")
                                .description("task description3")
                                .done(false)
                                .estimatedDurationInHours(3)
                                .build()
                ))
                .state(State.INITIATED)
                .complexity(Complexity.EASY)
                .estimatedDurationInHours(10)
                .expectedResult("1 successful unit test")
                .createdAt(1508484583267L)
                .startedAt("01.01.2022")
                .build();
    }

    public static List<Project> buildMockProjects() {
        return new ArrayList<>(Arrays.asList(
                Project.builder()
                        .id(UUID.randomUUID())
                        .title("title1")
                        .description("description1")
                        .tasks(Arrays.asList(
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task1")
                                        .description("task description1")
                                        .done(false)
                                        .estimatedDurationInHours(1)
                                        .build(),
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task2")
                                        .description("task description2")
                                        .done(false)
                                        .estimatedDurationInHours(2)
                                        .build(),
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task1")
                                        .description("task description1")
                                        .done(false)
                                        .estimatedDurationInHours(3)
                                        .build()
                        ))
                        .state(State.INITIATED)
                        .complexity(Complexity.EASY)
                        .estimatedDurationInHours(10)
                        .expectedResult("1 successful unit test")
                        .createdAt(1508484583267L)
                        .startedAt("01.01.2022")
                        .build(),
                Project.builder()
                        .id(UUID.randomUUID())
                        .title("title2")
                        .description("description2")
                        .state(State.HALTED)
                        .complexity(Complexity.MEDIUM)
                        .estimatedDurationInHours(20)
                        .expectedResult("2 successful unit test")
                        .createdAt(1508484583267L)
                        .startedAt("02.01.2022")
                        .build(),
                Project.builder()
                        .id(UUID.randomUUID())
                        .title("title3")
                        .description("description3")
                        .tasks(Arrays.asList(
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task1")
                                        .description("task description1")
                                        .done(false)
                                        .estimatedDurationInHours(1)
                                        .build(),
                                Task.builder()
                                        .id(UUID.randomUUID())
                                        .title("task2")
                                        .description("task description2")
                                        .done(false)
                                        .estimatedDurationInHours(2)
                                        .build()
                        ))
                        .state(State.FINISHED)
                        .complexity(Complexity.DIFFICULT)
                        .estimatedDurationInHours(30)
                        .expectedResult("3 successful unit test")
                        .createdAt(1508484583267L)
                        .startedAt("03.01.2022")
                        .build()
        ));
    }

    public static Task buildMockTask() {
        return Task.builder()
                .id(UUID.randomUUID())
                .title("new task")
                .description("task description new")
                .done(false)
                .estimatedDurationInHours(30)
                .build();
    }

    public static int generateRandomInteger(int max) {
        return (int) (Math.random() * ((max) + 1));
    }

    private MockTestingUtil() {}
}
