package pj.bankingproject.models;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TransferRequestDTO {
    private Long sourceWalletId;
    private Long destinationWalletId;

    @Positive
    @NotNull
    BigDecimal amount;

}
