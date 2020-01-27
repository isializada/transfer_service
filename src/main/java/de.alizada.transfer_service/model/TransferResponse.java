package de.alizada.transfer_service.model;

public class TransferResponse {
    private int code;
    private String message;

    public TransferResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public TransferResponse setCode(int code) {
        this.code = code;
        return this;
    }

    public TransferResponse setMessage(String message) {
        this.message = message;
        return this;
    }
}
