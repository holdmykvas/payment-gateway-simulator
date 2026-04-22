package pj.bankingproject.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.BDDMockito.given;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;
import pj.bankingproject.entities.User;
import pj.bankingproject.entities.Wallet;
import pj.bankingproject.repositories.TransferRepository;
import pj.bankingproject.repositories.WalletRepository;


import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
public class TransferServiceTest {
    @Mock
    WalletRepository walletRepository;
    @Mock
    TransferRepository transferRepository;
    @InjectMocks
    TransferService transferService;

    @Test
    void transferMoneySuccessfull(){
        Wallet sourceWallet = Wallet.builder()
                .id(1L)
                .user(new User())
                .balance(BigDecimal.valueOf(1000))
                .currency("Zloty")
                .build();
        Wallet destinationWallet = Wallet.builder()
                .id(2L)
                .user(new User())
                .balance(BigDecimal.valueOf(500))
                .currency("Zloty")
                .build();
        BigDecimal amount = BigDecimal.valueOf(200);
        given(walletRepository.findById(sourceWallet.getId())).willReturn(Optional.of(sourceWallet));
        given(walletRepository.findById(destinationWallet.getId())).willReturn(Optional.of(destinationWallet));
        transferService.transferMoney(sourceWallet.getId(),destinationWallet.getId(),amount);
    }
}
