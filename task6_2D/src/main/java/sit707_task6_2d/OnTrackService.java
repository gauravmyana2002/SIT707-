package sit707_task6_2d;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Small in-memory model of common OnTrack behaviours: student registration,
 * task publishing, submissions, feedback, task inbox, and submission messages.
 */
public class OnTrackService {
    private final Map<String, Student> students = new HashMap<String, Student>();
    private final Map<String, Task> tasks = new HashMap<String, Task>();
    private final Map<String, Submission> submissions = new HashMap<String, Submission>();
    private int nextSubmissionNumber = 1;

    public Student registerStudent(String studentId, String name, String email) {
        requireText(studentId, "studentId");
        requireText(name, "name");
        requireText(email, "email");
        if (!email.contains("@") || email.startsWith("@") || email.endsWith("@")) {
            throw new IllegalArgumentException("email must contain a valid @ separator");
        }
        if (students.containsKey(studentId)) {
            throw new IllegalArgumentException("student already exists");
        }

        Student student = new Student(studentId, name, email);
        students.put(studentId, student);
        return student;
    }

    public Task publishTask(String taskId, String title, LocalDateTime dueAt, int maxAttempts) {
        requireText(taskId, "taskId");
        requireText(title, "title");
        requireObject(dueAt, "dueAt");
        if (maxAttempts < 1) {
            throw new IllegalArgumentException("maxAttempts must be at least 1");
        }
        if (tasks.containsKey(taskId)) {
            throw new IllegalArgumentException("task already exists");
        }

        Task task = new Task(taskId, title, dueAt, maxAttempts);
        tasks.put(taskId, task);
        return task;
    }

    public List<Task> listAvailableTasks(String studentId, LocalDateTime now) {
        requireStudent(studentId);
        requireObject(now, "now");

        List<Task> available = new ArrayList<Task>();
        for (Task task : tasks.values()) {
            if (!now.isAfter(task.getDueAt())) {
                available.add(task);
            }
        }
        Collections.sort(available, new Comparator<Task>() {
            public int compare(Task left, Task right) {
                int dueCompare = left.getDueAt().compareTo(right.getDueAt());
                return dueCompare != 0 ? dueCompare : left.getTaskId().compareTo(right.getTaskId());
            }
        });
        return available;
    }

    public Submission submitTask(String studentId, String taskId, String content, LocalDateTime submittedAt) {
        requireStudent(studentId);
        Task task = requireTask(taskId);
        requireText(content, "content");
        requireObject(submittedAt, "submittedAt");

        if (submittedAt.isAfter(task.getDueAt())) {
            throw new IllegalStateException("task is closed");
        }
        int previousAttempts = countAttempts(studentId, taskId);
        if (previousAttempts >= task.getMaxAttempts()) {
            throw new IllegalStateException("maximum attempts reached");
        }

        String submissionId = "SUB-" + nextSubmissionNumber++;
        Submission submission = new Submission(submissionId, studentId, taskId, content, submittedAt, previousAttempts + 1);
        submissions.put(submissionId, submission);
        return submission;
    }

    public Submission markSubmission(String submissionId, String tutorId, SubmissionStatus status, String feedback) {
        Submission submission = requireSubmission(submissionId);
        requireText(tutorId, "tutorId");
        requireObject(status, "status");
        requireText(feedback, "feedback");

        submission.setStatus(status);
        submission.setTutorId(tutorId);
        submission.setFeedback(feedback);
        return submission;
    }

    public List<InboxItem> getTaskInbox(String studentId) {
        requireStudent(studentId);

        List<InboxItem> inbox = new ArrayList<InboxItem>();
        for (Submission submission : submissions.values()) {
            if (submission.getStudentId().equals(studentId)) {
                Task task = tasks.get(submission.getTaskId());
                inbox.add(new InboxItem(
                    task.getTaskId(),
                    task.getTitle(),
                    submission.getSubmissionId(),
                    submission.getStatus(),
                    submission.getAttemptNumber(),
                    submission.getFeedback(),
                    countUnreadMessages(submission, studentId)
                ));
            }
        }
        Collections.sort(inbox, new Comparator<InboxItem>() {
            public int compare(InboxItem left, InboxItem right) {
                return left.getSubmissionId().compareTo(right.getSubmissionId());
            }
        });
        return inbox;
    }

    public Message addMessage(String submissionId, String senderId, String text, LocalDateTime sentAt) {
        Submission submission = requireSubmission(submissionId);
        requireText(senderId, "senderId");
        requireText(text, "text");
        requireObject(sentAt, "sentAt");
        if (!senderId.equals(submission.getStudentId()) && !senderId.equals(submission.getTutorId())) {
            throw new IllegalArgumentException("sender is not part of this submission discussion");
        }

        Message message = new Message(senderId, text, sentAt);
        submission.addMessage(message);
        return message;
    }

    public List<Message> viewConversation(String submissionId, String viewerId) {
        Submission submission = requireSubmission(submissionId);
        requireDiscussionParticipant(submission, viewerId);
        return submission.getMessages();
    }

    public void markMessagesRead(String submissionId, String viewerId) {
        Submission submission = requireSubmission(submissionId);
        requireDiscussionParticipant(submission, viewerId);
        submission.markReadBy(viewerId);
    }

    private int countAttempts(String studentId, String taskId) {
        int attempts = 0;
        for (Submission submission : submissions.values()) {
            if (submission.getStudentId().equals(studentId) && submission.getTaskId().equals(taskId)) {
                attempts++;
            }
        }
        return attempts;
    }

    private int countUnreadMessages(Submission submission, String viewerId) {
        int unread = 0;
        for (int index = submission.getLastReadIndex(viewerId); index < submission.getMessages().size(); index++) {
            Message message = submission.getMessages().get(index);
            if (!message.getSenderId().equals(viewerId)) {
                unread++;
            }
        }
        return unread;
    }

    private void requireDiscussionParticipant(Submission submission, String viewerId) {
        requireText(viewerId, "viewerId");
        if (!viewerId.equals(submission.getStudentId()) && !viewerId.equals(submission.getTutorId())) {
            throw new IllegalArgumentException("viewer is not part of this submission discussion");
        }
    }

    private Student requireStudent(String studentId) {
        requireText(studentId, "studentId");
        Student student = students.get(studentId);
        if (student == null) {
            throw new IllegalArgumentException("student does not exist");
        }
        return student;
    }

    private Task requireTask(String taskId) {
        requireText(taskId, "taskId");
        Task task = tasks.get(taskId);
        if (task == null) {
            throw new IllegalArgumentException("task does not exist");
        }
        return task;
    }

    private Submission requireSubmission(String submissionId) {
        requireText(submissionId, "submissionId");
        Submission submission = submissions.get(submissionId);
        if (submission == null) {
            throw new IllegalArgumentException("submission does not exist");
        }
        return submission;
    }

    private void requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    private void requireObject(Object value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " is required");
        }
    }

    public static final class Student {
        private final String studentId;
        private final String name;
        private final String email;

        private Student(String studentId, String name, String email) {
            this.studentId = studentId;
            this.name = name;
            this.email = email;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }
    }

    public static final class Task {
        private final String taskId;
        private final String title;
        private final LocalDateTime dueAt;
        private final int maxAttempts;

        private Task(String taskId, String title, LocalDateTime dueAt, int maxAttempts) {
            this.taskId = taskId;
            this.title = title;
            this.dueAt = dueAt;
            this.maxAttempts = maxAttempts;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getTitle() {
            return title;
        }

        public LocalDateTime getDueAt() {
            return dueAt;
        }

        public int getMaxAttempts() {
            return maxAttempts;
        }
    }

    public static final class Submission {
        private final String submissionId;
        private final String studentId;
        private final String taskId;
        private final String content;
        private final LocalDateTime submittedAt;
        private final int attemptNumber;
        private final List<Message> messages = new ArrayList<Message>();
        private final Set<String> readers = new HashSet<String>();
        private final Map<String, Integer> readIndexes = new HashMap<String, Integer>();
        private SubmissionStatus status = SubmissionStatus.SUBMITTED;
        private String tutorId;
        private String feedback = "Awaiting tutor feedback";

        private Submission(String submissionId, String studentId, String taskId, String content,
                LocalDateTime submittedAt, int attemptNumber) {
            this.submissionId = submissionId;
            this.studentId = studentId;
            this.taskId = taskId;
            this.content = content;
            this.submittedAt = submittedAt;
            this.attemptNumber = attemptNumber;
        }

        public String getSubmissionId() {
            return submissionId;
        }

        public String getStudentId() {
            return studentId;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getContent() {
            return content;
        }

        public LocalDateTime getSubmittedAt() {
            return submittedAt;
        }

        public int getAttemptNumber() {
            return attemptNumber;
        }

        public SubmissionStatus getStatus() {
            return status;
        }

        public String getTutorId() {
            return tutorId;
        }

        public String getFeedback() {
            return feedback;
        }

        public List<Message> getMessages() {
            return Collections.unmodifiableList(messages);
        }

        private void setStatus(SubmissionStatus status) {
            this.status = status;
        }

        private void setTutorId(String tutorId) {
            this.tutorId = tutorId;
            this.readers.add(tutorId);
            this.readIndexes.put(tutorId, messages.size());
        }

        private void setFeedback(String feedback) {
            this.feedback = feedback;
        }

        private void addMessage(Message message) {
            messages.add(message);
            readers.add(message.getSenderId());
            readIndexes.put(message.getSenderId(), messages.size());
        }

        private void markReadBy(String viewerId) {
            readers.add(viewerId);
            readIndexes.put(viewerId, messages.size());
        }

        private int getLastReadIndex(String viewerId) {
            Integer index = readIndexes.get(viewerId);
            return index == null ? 0 : index.intValue();
        }
    }

    public static final class Message {
        private final String senderId;
        private final String text;
        private final LocalDateTime sentAt;

        private Message(String senderId, String text, LocalDateTime sentAt) {
            this.senderId = senderId;
            this.text = text;
            this.sentAt = sentAt;
        }

        public String getSenderId() {
            return senderId;
        }

        public String getText() {
            return text;
        }

        public LocalDateTime getSentAt() {
            return sentAt;
        }
    }

    public static final class InboxItem {
        private final String taskId;
        private final String taskTitle;
        private final String submissionId;
        private final SubmissionStatus status;
        private final int attemptNumber;
        private final String latestFeedback;
        private final int unreadMessages;

        private InboxItem(String taskId, String taskTitle, String submissionId, SubmissionStatus status,
                int attemptNumber, String latestFeedback, int unreadMessages) {
            this.taskId = taskId;
            this.taskTitle = taskTitle;
            this.submissionId = submissionId;
            this.status = status;
            this.attemptNumber = attemptNumber;
            this.latestFeedback = latestFeedback;
            this.unreadMessages = unreadMessages;
        }

        public String getTaskId() {
            return taskId;
        }

        public String getTaskTitle() {
            return taskTitle;
        }

        public String getSubmissionId() {
            return submissionId;
        }

        public SubmissionStatus getStatus() {
            return status;
        }

        public int getAttemptNumber() {
            return attemptNumber;
        }

        public String getLatestFeedback() {
            return latestFeedback;
        }

        public int getUnreadMessages() {
            return unreadMessages;
        }
    }
}
