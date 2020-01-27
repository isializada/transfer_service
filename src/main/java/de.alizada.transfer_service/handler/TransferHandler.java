package de.alizada.transfer_service.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.alizada.transfer_service.model.TransferRequest;
import de.alizada.transfer_service.model.TransferResponse;
import de.alizada.transfer_service.service.TransferService;
import io.javalin.Context;
import io.javalin.Handler;

import java.util.Objects;

/**
 * handle transfer request
 */
public class TransferHandler implements Handler {
    private TransferService transferService;
    private ObjectMapper mapper = new ObjectMapper();

    public TransferHandler(TransferService transferService) {
        this.transferService = transferService;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        //map body to object
        final TransferRequest transferData = mapper.readValue(Objects.requireNonNull(ctx.body()), TransferRequest.class);
        //validate the mapped data
        if(notValidTransferData(transferData)){
            ctx.status(400).result("Not valid data");
        } else {
            TransferResponse transferResponse = transferService.transfer(transferData);
            ctx.status(transferResponse.getCode()).result(transferResponse.getMessage());
        }
    }

    private boolean notValidTransferData(TransferRequest transferData) {
        if(isNegativeAmount(transferData.getAmount())){
            return true;
        }

        if(isEmptyId(transferData.getTo())){
            return true;
        }

        if(isEmptyId(transferData.getFrom())){
            return true;
        }

        return false;
    }

    private boolean isNegativeAmount(Long amount) {
        return null == amount || amount < 1;
    }

    private boolean isEmptyId(String id) {
        return null == id || id.isEmpty();
    }
}
