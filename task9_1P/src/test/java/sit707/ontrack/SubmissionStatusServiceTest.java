package sit707.ontrack;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.NoSuchElementException;

import org.junit.Test;

public class SubmissionStatusServiceTest {
    @Test
    public void viewSubmissionReturnsStatusAndChatMessagesForStudentTask() {
        SubmissionStatusService service = new SubmissionStatusService(Arrays.asList(
                new TaskSubmission("s123", "9.1P", SubmissionStatus.FEEDBACK_READY, Arrays.asList(
                        new ChatMessage("student", "I have submitted the task."),
                        new ChatMessage("tutor", "Feedback is ready for review.")))));

        SubmissionView view = service.viewSubmission("s123", "9.1P");

        assertEquals("9.1P", view.getTaskId());
        assertEquals(SubmissionStatus.FEEDBACK_READY, view.getStatus());
        assertEquals(2, view.getMessages().size());
        assertTrue(view.hasTutorFeedback());
    }

    @Test
    public void viewSubmissionDoesNotShowFeedbackFlagForSubmittedOnlyTask() {
        SubmissionStatusService service = new SubmissionStatusService(Collections.singletonList(
                new TaskSubmission("s123", "8.1P", SubmissionStatus.SUBMITTED, Collections.emptyList())));

        SubmissionView view = service.viewSubmission("s123", "8.1P");

        assertFalse(view.hasTutorFeedback());
    }

    @Test
    public void viewSubmissionRejectsBlankStudentId() {
        SubmissionStatusService service = new SubmissionStatusService(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> service.viewSubmission(" ", "9.1P"));
    }

    @Test
    public void viewSubmissionRejectsBlankTaskId() {
        SubmissionStatusService service = new SubmissionStatusService(Collections.emptyList());

        assertThrows(IllegalArgumentException.class, () -> service.viewSubmission("s123", ""));
    }

    @Test
    public void viewSubmissionFailsWhenStudentDoesNotOwnRequestedSubmission() {
        SubmissionStatusService service = new SubmissionStatusService(Collections.singletonList(
                new TaskSubmission("s999", "9.1P", SubmissionStatus.COMPLETED, Collections.emptyList())));

        assertThrows(NoSuchElementException.class, () -> service.viewSubmission("s123", "9.1P"));
    }
}
