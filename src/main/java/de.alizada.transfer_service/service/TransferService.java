package de.alizada.transfer_service.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import de.alizada.transfer_service.model.Account;
import de.alizada.transfer_service.model.TransferRequest;
import de.alizada.transfer_service.model.TransferResponse;

import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * service for transfer process
 */
public class TransferService {
    private Dao<Account, String> accountDao;

    public TransferService(final Dao<Account, String> accountDao) {
        this.accountDao = accountDao;
    }

    /**
     * @param transferData - request body
     * @return TransferResponse with status and message
     * @throws SQLException
     */
    public TransferResponse transfer(final TransferRequest transferData) throws SQLException {
        final Long moneyAmount = transferData.getAmount();
        final String accountFromId = transferData.getFrom();
        final String accountToId = transferData.getTo();

        // find 1st account and validate
        Account accountFrom = accountDao.queryForId(accountFromId);
        if(isNull(accountFrom)){
            return new TransferResponse(404, "Account not found with id " + accountFromId);
        }

        if(isNotEnoughAmount(accountFrom.getAmount(), moneyAmount)){
            return new TransferResponse(400, "There is not enough amount of money");
        }

        //find 2nd account and validate
        Account accountTo = accountDao.queryForId(accountToId);
        if(isNull(accountTo)){
            return new TransferResponse(404, "Account not found with id " + accountToId);
        }


        return updateAccounts(accountFrom, accountTo, moneyAmount);
    }

    /**
     * @param from current account
     * @param to target account
     * @param amount money
     * @return TransferResponse with code and message
     * @throws SQLException and rollback when it will face db error
     */
    protected TransferResponse updateAccounts(Account from, Account to, Long amount) throws SQLException {
        return TransactionManager.callInTransaction(accountDao.getConnectionSource(), new Callable<TransferResponse>() {
            public TransferResponse call() {
                try {
                    accountDao.update(from.setAmount(from.getAmount() - amount));
                    accountDao.update(to.setAmount(to.getAmount() + amount));
                    return new TransferResponse(200, amount + " money transferred to " + to.getName());
                } catch (Exception ex){
                    return new TransferResponse(500, ex.getMessage());
                }
            }
        });
    }

    private boolean isNotEnoughAmount(Long amount, Long minimum) {
        return null == amount || amount < minimum;
    }

    private boolean isNull(Object obj){
        return null == obj;
    }
}
