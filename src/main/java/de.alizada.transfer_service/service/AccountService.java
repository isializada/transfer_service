package de.alizada.transfer_service.service;

import com.j256.ormlite.dao.Dao;
import de.alizada.transfer_service.model.Account;

import java.sql.SQLException;
import java.util.List;

public class AccountService {
    private Dao<Account, String> accountDao;

    public AccountService(final Dao<Account, String> accountDao) {
        this.accountDao = accountDao;
    }

    public List<Account> getAccounts() throws SQLException {
        return accountDao.queryForAll();
    }
}
