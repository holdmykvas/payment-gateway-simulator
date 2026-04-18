package pj.bankingproject.controllers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pj.bankingproject.entities.Wallet;
import pj.bankingproject.models.TransferRequestDTO;
import pj.bankingproject.services.TransferService;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {
    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    //Request body - translator for JSOn
    @PostMapping
    public ResponseEntity<String> transferControl(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
        transferService.transferMoney(
                transferRequestDTO.getSourceWalletId(),
                transferRequestDTO.getDestinationWalletId(),
                transferRequestDTO.getAmount());

        return new ResponseEntity<String>("Transfer successfull!", HttpStatus.OK);
    }
}
