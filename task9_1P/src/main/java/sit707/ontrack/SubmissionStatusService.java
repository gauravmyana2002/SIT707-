package sit707.ontrack;

import java.util.List;
import java.util.NoSuchElementException;

public class SubmissionStatusService {
    private final List<TaskSubmission> submissions;

    public SubmissionStatusService(List<TaskSubmission> submissions) {
        this.submissions = submissions;
    }

    public SubmissionView viewSubmission(String studentId, String taskId) {
        if (studentId == null || studentId.trim().isEmpty()) {
            throw new IllegalArgumentException("studentId is required");
        }
        if (taskId == null || taskId.trim().isEmpty()) {
            throw new IllegalArgumentException("taskId is required");
        }

        for (TaskSubmission submission : submissions) {
            if (submission.getStudentId().equals(studentId) && submission.getTaskId().equals(taskId)) {
                return new SubmissionView(
                        submission.getTaskId(),
                        submission.getStatus(),
                        submission.getMessages());
            }
        }

        throw new NoSuchElementException("No submission found for the requested student and task");
    }
}
