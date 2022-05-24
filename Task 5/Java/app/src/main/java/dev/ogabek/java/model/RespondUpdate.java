package dev.ogabek.java.model;

public class RespondUpdate {

    private final String status;
    private final Employee data;
    private final String message;

    public RespondUpdate(String status, Employee data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public Employee getEmployees() {
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
