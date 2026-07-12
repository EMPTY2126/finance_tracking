package finance_management.repo;

import finance_management.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepo extends JpaRepository<Budget, Long> {
}
