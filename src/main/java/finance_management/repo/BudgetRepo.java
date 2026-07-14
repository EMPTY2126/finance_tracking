package finance_management.repo;

import finance_management.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BudgetRepo extends JpaRepository<Budget, Long> {
    public List<Budget> findByUserId(Long userId);
}
