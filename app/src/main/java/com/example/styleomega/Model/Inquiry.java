package com.example.styleomega.Model;

public class Inquiry
{
    private String  inquiryEmail, inquirySubject, inquiryMessage;

    public Inquiry(String inquiryEmail, String inquirySubject, String inquiryMessage) {
        this.inquiryEmail = inquiryEmail;
        this.inquirySubject = inquirySubject;
        this.inquiryMessage = inquiryMessage;
    }
    public Inquiry() {

    }


    public String getInquiryEmail() {
        return inquiryEmail;
    }

    public void setInquiryEmail(String inquiryEmail) {
        this.inquiryEmail = inquiryEmail;
    }

    public String getInquirySubject() {
        return inquirySubject;
    }

    public void setInquirySubject(String inquirySubject) {
        this.inquirySubject = inquirySubject;
    }

    public String getInquiryMessage() {
        return inquiryMessage;
    }

    public void setInquiryMessage(String inquiryMessage) {
        this.inquiryMessage = inquiryMessage;
    }
}
