package finance_management.repo;

import finance_management.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
    public List<Transaction> findByUserId(Long userId);
}
