package sit707.ontrack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubmissionView {
    private final String taskId;
    private final SubmissionStatus status;
    private final List<ChatMessage> messages;

    public SubmissionView(String taskId, SubmissionStatus status, List<ChatMessage> messages) {
        this.taskId = taskId;
        this.status = status;
        this.messages = new ArrayList<>(messages);
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

    public boolean hasTutorFeedback() {
        return status == SubmissionStatus.FEEDBACK_READY || status == SubmissionStatus.COMPLETED;
    }
}
