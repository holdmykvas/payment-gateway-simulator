package pj.bankingproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pj.bankingproject.entities.Transfer;
import pj.bankingproject.entities.Wallet;

import java.util.List;

public interface TransferRepository extends JpaRepository<Transfer, Long> {

    @Query("SELECT t FROM Transfer t WHERE t.sourceWallet = :wallet OR t.destinationWallet = :wallet")
    List<Transfer> findAllByAnyWallet(@Param("wallet") Wallet wallet);
}
