package de.alizada.transfer_service.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "accounts")
public class Account {
    @DatabaseField(index = true, unique = true, id = true)
    private String id;
    @DatabaseField
    private String name;
    @DatabaseField
    private Long amount;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getAmount() {
        return amount;
    }

    public Account setAmount(Long amount) {
        this.amount = amount;
        return this;
    }

    public Account setName(String name) {
        this.name = name;
        return this;
    }

    public Account setId(String id) {
        this.id = id;
        return this;
    }
}
