package com.dbxprts.ferro_msc_edi.model;

import javax.mail.internet.InternetAddress;

public class Mail {

    private String from;
    private InternetAddress[] to;
    private String subject;
    private String content;
    private String pathToAttachment;

    public Mail() {
    }

    public Mail(String from, InternetAddress[] to, String subject, String content, String pathToAttachment) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.pathToAttachment = pathToAttachment;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public InternetAddress[] getTo() {
        return to;
    }

    public void setTo(InternetAddress[] to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPathToAttachment() {
        return pathToAttachment;
    }

    public void setPathToAttachment(String pathToAttachment) {
        this.pathToAttachment = pathToAttachment;
    }

    @Override
    public String toString() {
        return "Mail{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", pathToAttachment='" + pathToAttachment + '\'' +
                '}';
    }
}
