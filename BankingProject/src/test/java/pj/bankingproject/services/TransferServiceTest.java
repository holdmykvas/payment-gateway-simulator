package pj.bankingproject.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import java.util.Optional;
import org.mockito.junit.jupiter.MockitoExtension;
import pj.bankingproject.entities.User;
import pj.bankingproject.entities.Wallet;
import pj.bankingproject.repositories.TransferRepository;
import pj.bankingproject.repositories.WalletRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


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

        //checking if balances changed
        assertEquals(BigDecimal.valueOf(800), sourceWallet.getBalance());
        assertEquals(BigDecimal.valueOf(700), destinationWallet.getBalance());

        //checking if the repository actually called .save()
        verify(walletRepository, times(1)).save(sourceWallet);
        verify(walletRepository, times(1)).save(destinationWallet);
    }

    @Test
    void transferMoneyUnsuccessful(){
        Wallet sourceWallet = Wallet.builder()
                .id(1L)
                .user(new User())
                .balance(BigDecimal.valueOf(100))
                .currency("Zloty")
                .build();

        Wallet destinationWallet = Wallet.builder()
                .id(2L)
                .user(new User())
                .balance(BigDecimal.valueOf(500))
                .currency("Zloty")
                .build();

        BigDecimal amount = BigDecimal.valueOf(2000);
        given(walletRepository.findById(1L)).willReturn(Optional.of(sourceWallet));
        given(walletRepository.findById(2L)).willReturn(Optional.of(destinationWallet));

        //this method will crash so need to wrap it
        assertThrows(IllegalArgumentException.class, () -> {
            transferService.transferMoney(1L,2L,amount);
        });

        //verifications which are not touched by crashed method
        verify(walletRepository, never()).save(any());
        verify(transferRepository, never()).save(any());
    }
}
