package sit707_task6_2d;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;

import sit707_task6_2d.OnTrackService.InboxItem;
import sit707_task6_2d.OnTrackService.Message;
import sit707_task6_2d.OnTrackService.Submission;
import sit707_task6_2d.OnTrackService.Task;

public class OnTrackServiceTest {
    private final LocalDateTime due = LocalDateTime.of(2026, 5, 20, 17, 0);

    @Test
    public void rightResultStudentCanSubmitTaskAndSeeFeedbackInInbox() {
        OnTrackService service = serviceWithStudentAndTask(2);

        Submission submission = service.submitTask("s123", "T6.2D", "Implemented OnTrack functions", due.minusHours(3));
        service.markSubmission(submission.getSubmissionId(), "tutor1", SubmissionStatus.ACCEPTED, "Well tested.");

        List<InboxItem> inbox = service.getTaskInbox("s123");

        assertEquals(1, inbox.size());
        assertEquals("T6.2D", inbox.get(0).getTaskId());
        assertEquals(SubmissionStatus.ACCEPTED, inbox.get(0).getStatus());
        assertEquals("Well tested.", inbox.get(0).getLatestFeedback());
    }

    @Test
    public void boundaryAllowsSubmissionExactlyAtDueTime() {
        OnTrackService service = serviceWithStudentAndTask(1);

        Submission submission = service.submitTask("s123", "T6.2D", "Submitted on the boundary", due);

        assertEquals(due, submission.getSubmittedAt());
        assertEquals(1, submission.getAttemptNumber());
    }

    @Test(expected = IllegalStateException.class)
    public void boundaryRejectsSubmissionAfterDueTime() {
        OnTrackService service = serviceWithStudentAndTask(1);

        service.submitTask("s123", "T6.2D", "Late work", due.plusNanos(1));
    }

    @Test(expected = IllegalStateException.class)
    public void boundaryRejectsAttemptAfterMaximumAttempts() {
        OnTrackService service = serviceWithStudentAndTask(1);
        service.submitTask("s123", "T6.2D", "First attempt", due.minusDays(1));

        service.submitTask("s123", "T6.2D", "Second attempt", due.minusHours(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void inverseRejectsInvalidEmail() {
        OnTrackService service = new OnTrackService();

        service.registerStudent("s123", "Gaurav", "invalid-email");
    }

    @Test(expected = IllegalArgumentException.class)
    public void inverseRejectsBlankSubmissionContent() {
        OnTrackService service = serviceWithStudentAndTask(1);

        service.submitTask("s123", "T6.2D", "   ", due.minusDays(1));
    }

    @Test
    public void crossCheckAvailableTasksAreSortedByDueDateThenId() {
        OnTrackService service = new OnTrackService();
        service.registerStudent("s123", "Gaurav", "gaurav@example.com");
        service.publishTask("T7.1P", "Security Testing", due.plusDays(2), 1);
        service.publishTask("T6.2D", "Right BICEP Testing", due, 2);
        service.publishTask("T6.1P", "Coverage Testing", due, 1);

        List<Task> tasks = service.listAvailableTasks("s123", due.minusDays(1));

        assertEquals("T6.1P", tasks.get(0).getTaskId());
        assertEquals("T6.2D", tasks.get(1).getTaskId());
        assertEquals("T7.1P", tasks.get(2).getTaskId());
    }

    @Test
    public void crossCheckMessagesCreateUnreadInboxCountUntilRead() {
        OnTrackService service = serviceWithStudentAndTask(2);
        Submission submission = service.submitTask("s123", "T6.2D", "Needs discussion", due.minusDays(1));
        service.markSubmission(submission.getSubmissionId(), "tutor1", SubmissionStatus.NEEDS_RESUBMISSION, "Please add boundary tests.");

        service.addMessage(submission.getSubmissionId(), "tutor1", "Can you add due-date boundary coverage?", due.minusHours(4));
        assertEquals(1, service.getTaskInbox("s123").get(0).getUnreadMessages());

        List<Message> messages = service.viewConversation(submission.getSubmissionId(), "s123");
        assertEquals("Can you add due-date boundary coverage?", messages.get(0).getText());

        service.markMessagesRead(submission.getSubmissionId(), "s123");
        assertEquals(0, service.getTaskInbox("s123").get(0).getUnreadMessages());
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorConditionRejectsUnknownTaskSubmission() {
        OnTrackService service = new OnTrackService();
        service.registerStudent("s123", "Gaurav", "gaurav@example.com");

        service.submitTask("s123", "missing", "Work", due.minusDays(1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorConditionRejectsMessageFromNonParticipant() {
        OnTrackService service = serviceWithStudentAndTask(2);
        Submission submission = service.submitTask("s123", "T6.2D", "Work", due.minusDays(1));
        service.markSubmission(submission.getSubmissionId(), "tutor1", SubmissionStatus.NEEDS_RESUBMISSION, "Please revise.");

        service.addMessage(submission.getSubmissionId(), "stranger", "I should not be here.", due.minusHours(1));
    }

    @Test
    public void existenceEmptyInboxAndClosedTasksReturnEmptyLists() {
        OnTrackService service = new OnTrackService();
        service.registerStudent("s123", "Gaurav", "gaurav@example.com");
        service.publishTask("T6.2D", "Right BICEP Testing", due, 1);

        assertTrue(service.getTaskInbox("s123").isEmpty());
        assertTrue(service.listAvailableTasks("s123", due.plusSeconds(1)).isEmpty());
    }

    @Test
    public void performanceListsLargeNumberOfTasksQuickly() {
        OnTrackService service = new OnTrackService();
        service.registerStudent("s123", "Gaurav", "gaurav@example.com");
        for (int i = 0; i < 1000; i++) {
            service.publishTask(String.format("T%04d", i), "Task " + i, due.plusDays(i % 7), 1);
        }

        long start = System.nanoTime();
        List<Task> tasks = service.listAvailableTasks("s123", due.minusDays(1));
        long elapsedMillis = (System.nanoTime() - start) / 1000000;

        assertEquals(1000, tasks.size());
        assertFalse("Listing tasks should stay comfortably under 200ms", elapsedMillis >= 200);
    }

    private OnTrackService serviceWithStudentAndTask(int maxAttempts) {
        OnTrackService service = new OnTrackService();
        service.registerStudent("s123", "Gaurav", "gaurav@example.com");
        service.publishTask("T6.2D", "Right BICEP Testing", due, maxAttempts);
        return service;
    }
}
