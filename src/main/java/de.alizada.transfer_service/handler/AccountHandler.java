package de.alizada.transfer_service.handler;

import de.alizada.transfer_service.service.AccountService;
import io.javalin.Context;
import io.javalin.Handler;

/**
 * handle getRequest for accounts
 */
public class AccountHandler implements Handler {
    private AccountService accountService;

    public AccountHandler(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        ctx.status(200).json(accountService.getAccounts());
    }
}
