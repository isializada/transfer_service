package de.alizada.transfer_service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import de.alizada.transfer_service.handler.AccountHandler;
import de.alizada.transfer_service.handler.TransferHandler;
import de.alizada.transfer_service.model.Account;
import de.alizada.transfer_service.service.AccountService;
import de.alizada.transfer_service.service.TransferService;
import io.javalin.Javalin;

import java.sql.SQLException;

public class Application {
    //processes (read and write to table) are locked by default on H2Db (LOCK_MODE: 3)
    private static final String DATABASE_URL = "jdbc:h2:./transferDb";

    public static void main(String[] args){
        initialize();
    }

    private static void initialize(){
        /** initialize database */
        ConnectionSource connectionSource = null;
        Dao<Account, String> accountDao = null;
        try {
            connectionSource = new JdbcConnectionSource(DATABASE_URL,"sa","sa");
            TableUtils.createTableIfNotExists(connectionSource, Account.class);
            accountDao = DaoManager.createDao(connectionSource, Account.class);
        } catch (SQLException e) {
            e.printStackTrace();
            System.exit(500);
        }

        /** initialize beans */
        final TransferService transferService = new TransferService(accountDao);
        final AccountService accountService = new AccountService(accountDao);

        /** initialize controllers */
        Javalin app = Javalin.create()
                .port(7000)
                .start();
        app.post("/transfer", new TransferHandler(transferService));
        app.get("/accounts", new AccountHandler(accountService));
    }

}
