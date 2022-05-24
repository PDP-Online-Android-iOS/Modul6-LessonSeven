package dev.ogabek.java.model;

import java.util.ArrayList;

public class Respond {

    private final String status;
    private final ArrayList<Employee> data;
    private final String message;

    public Respond(String status, ArrayList<Employee> data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public ArrayList<Employee> getEmployees() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Respond{" +
                "status='" + status + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
