package sit707.ontrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TaskSubmission {
    private final String studentId;
    private final String taskId;
    private final SubmissionStatus status;
    private final List<ChatMessage> messages;

    public TaskSubmission(String studentId, String taskId, SubmissionStatus status, List<ChatMessage> messages) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("studentId is required");
        }
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("taskId is required");
        }
        if (status == null) {
            throw new IllegalArgumentException("status is required");
        }
        this.studentId = studentId;
        this.taskId = taskId;
        this.status = status;
        this.messages = new ArrayList<>(messages == null ? Collections.emptyList() : messages);
    }

    public String getStudentId() {
        return studentId;
    }

    public String getTaskId() {
        return taskId;
    }

    public SubmissionStatus getStatus() {
        return status;
    }

    public List<ChatMessage> getMessages() {
        return Collections.unmodifiableList(messages);
    }
}
