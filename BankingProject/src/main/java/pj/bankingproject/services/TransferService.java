package pj.bankingproject.services;

import jakarta.persistence.GeneratedValue;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import pj.bankingproject.STATUS;
import pj.bankingproject.entities.Transfer;
import pj.bankingproject.entities.Wallet;
import pj.bankingproject.repositories.TransferRepository;
import pj.bankingproject.repositories.WalletRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
//
@Service
public class TransferService {
    private final WalletRepository walletRepository;
    private final TransferRepository transferRepository;

    public TransferService(WalletRepository walletRepository, TransferRepository transferRepository) {
        this.walletRepository = walletRepository;
        this.transferRepository = transferRepository;
    }

    @Transactional
    public void transferMoney(Long sourceWalletId, Long destinationWalletId, BigDecimal amount) {
        Wallet sourceWallet = walletRepository.findById(sourceWalletId).orElseThrow(() -> new IllegalArgumentException("Source wallet not found."));
        Wallet destinationWallet = walletRepository.findById(destinationWalletId).orElseThrow(() -> new IllegalArgumentException("Destination wallet not found."));;

        if(Objects.equals(sourceWalletId, destinationWalletId)) {
            throw new IllegalArgumentException("Error: Transfer between 2 wallets is impossible.");
        }

        if(sourceWallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        sourceWallet.setBalance(sourceWallet.getBalance().subtract(amount));
        destinationWallet.setBalance(destinationWallet.getBalance().add(amount));

        Transfer transfer = Transfer.builder()
                .sourceWallet(sourceWallet)
                .destinationWallet(destinationWallet)
                .amount(amount)
                .status(STATUS.SUCCESS)
                .build();

        walletRepository.save(sourceWallet);
        walletRepository.save(destinationWallet);
        transferRepository.save(transfer);
    }
}
