package dev.ogabek.java.model;

public class Respond {

    String status;
    Employee[] data;
    String message;

    public Respond(String status, Employee[] data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public Employee[] getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
