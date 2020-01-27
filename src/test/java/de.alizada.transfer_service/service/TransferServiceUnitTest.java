package de.alizada.transfer_service.service;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import de.alizada.transfer_service.model.Account;
import de.alizada.transfer_service.model.TransferRequest;
import de.alizada.transfer_service.model.TransferResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferServiceUnitTest extends TransferService {
    private Dao dao;
    private Account account1;
    private Account account2;

    public TransferServiceUnitTest(@Mock Dao<Account, String> accountDao) {
        super(accountDao);
        dao = accountDao;
    }

    @BeforeEach
    void setUp() throws SQLException {
        account1 = new Account();
        account2 = new Account();
        account1.setName("name1").setAmount(100l).setId("id1");
        account2.setName("name2").setAmount(100l).setId("id2");

        lenient().when(dao.queryForId("id1")).thenReturn(account1);
        lenient().when(dao.queryForId("id2")).thenReturn(account2);
    }

    @Test
    public void shouldReturn200ForRightData() throws SQLException {
        when(dao.getConnectionSource()).thenReturn(mock(ConnectionSource.class));

        TransferRequest transferRequest = new TransferRequest().setAmount(50l).setFrom("id1").setTo("id2");

        assertEquals(transfer(transferRequest).getCode(), 200);
    }

    @Test
    public void shouldReturn404ForWrongId() throws SQLException {
        TransferRequest transferRequest = new TransferRequest().setAmount(50l).setFrom("id1").setTo("wrongId");

        assertEquals(transfer(transferRequest).getCode(), 404);
    }

    @Test
    public void shouldReturn400ForWrongAmount() throws SQLException {
        lenient().when(dao.update(any(Account.class))).thenReturn(1);

        TransferRequest transferRequest = new TransferRequest().setAmount(150l).setFrom("id1").setTo("id2");

        assertEquals(transfer(transferRequest).getCode(), 400);
    }

    @Override
    protected TransferResponse updateAccounts(Account from, Account to, Long amount) throws SQLException {
        return new TransferResponse(200,"");
    }
}
