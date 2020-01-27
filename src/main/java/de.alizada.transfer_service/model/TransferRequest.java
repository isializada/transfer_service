package de.alizada.transfer_service.model;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class TransferRequest {
    @NotNull
    private String from;
    @NotNull
    private String to;
    @NotNull
    @Min(value = 1, message = "Amount can not be 0 or negative")
    private Long amount;

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public Long getAmount() {
        return amount;
    }

    public TransferRequest setFrom(String from) {
        this.from = from;
        return this;
    }

    public TransferRequest setTo(String to) {
        this.to = to;
        return this;
    }

    public TransferRequest setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    @Override
    public String toString() {
        return "{" +
                "\"from\":\"" + from + "\"," +
                "\"to\":\"" + to + "\"," +
                "\"amount\":" + amount +
                "}";
    }
}
