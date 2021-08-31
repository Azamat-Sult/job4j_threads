package ru.job4j.concurrent.executorservice;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool;

    public EmailNotification() {
        this.pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }

    private void send(String subject, String body, String email) {
        System.out.println(Thread.currentThread().getName()
                + " - E-mail: " + email + "; Subject: " + subject + "; Body: " + body);
    }

    public void emailTo(User user) {
        String email = user.getEmail();
        String subject = "Notification " + user.getUserName() + " to email " + email;
        String body = "Add a new event to " + user.getUserName();
        send(subject, body, email);
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        EmailNotification mailer = new EmailNotification();

        mailer.pool.submit(() -> mailer.emailTo(new User("Jake", "jake@gmail.com")));

        mailer.pool.submit(() -> mailer.emailTo(new User("Bob", "bob@mail.ru")));

        mailer.pool.submit(() -> mailer.emailTo(new User("John", "matrix@mail.ru")));

        mailer.close();
    }
}
