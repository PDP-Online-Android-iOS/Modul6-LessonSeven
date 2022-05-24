package dev.ogabek.java.model;

import java.util.ArrayList;

public class RespondDelete {

    private final String status;
    private final String data;
    private final String message;

    public RespondDelete(String status, String data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getEmployees() {
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
