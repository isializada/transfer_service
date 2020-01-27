package de.alizada.transfer_service.handler;

import de.alizada.transfer_service.model.TransferRequest;
import de.alizada.transfer_service.model.TransferResponse;
import de.alizada.transfer_service.service.TransferService;
import io.javalin.Context;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransferHandlerUnitTest {
    @Mock
    private TransferService transferService;
    private TransferHandler transferHandler;

    @BeforeEach
    void setUp(){
        transferHandler = new TransferHandler(transferService);
    }

    @Test
    public void shouldReturn200ForCorrectData() throws Exception {
        TransferRequest transferRequest = new TransferRequest().setTo("id1").setFrom("id2").setAmount(100l);
        Context ctx = mock(Context.class);
        when(ctx.body()).thenReturn(transferRequest.toString());
        when(ctx.status(anyInt())).thenAnswer(invocation -> {
            Integer code = (Integer)invocation.getArguments()[0];
            if(code == 200){
                return ctx;
            }

            throw new RuntimeException();
        });
        when(ctx.result(anyString())).thenReturn(ctx);
        when(transferService.transfer(any(TransferRequest.class))).thenReturn(new TransferResponse(200,""));

        assertDoesNotThrow(() -> {
            transferHandler.handle(ctx);
        });
    }

    @Test
    public void shouldReturn400ForValidationError(){
        TransferRequest transferRequest = new TransferRequest().setTo("id1").setFrom("id2").setAmount(0l);
        Context ctx = mock(Context.class);
        when(ctx.body()).thenReturn(transferRequest.toString());
        when(ctx.status(anyInt())).thenAnswer(invocation -> {
            Integer code = (Integer)invocation.getArguments()[0];
            if(code == 400){
                return ctx;
            }

            throw new RuntimeException();
        });
        when(ctx.result(anyString())).thenReturn(ctx);

        assertDoesNotThrow(() -> {
            transferHandler.handle(ctx);
        });
    }
}
