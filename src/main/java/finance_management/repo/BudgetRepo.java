package finance_management.repo;

import finance_management.dto.dashboard.CategoryBudget;
import finance_management.model.Budget;
import finance_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BudgetRepo extends JpaRepository<Budget, Long>, JpaSpecificationExecutor<Budget> {
    List<Budget> findByUserId(Long userId);

    @Query("""
    SELECT new finance_management.dto.dashboard.CategoryBudget(
        b.category,
        b.monthlyLimit
    )
    FROM Budget b
    WHERE b.user = :user
      AND b.year = :year
      AND b.month = :month
""")
    List<CategoryBudget> getBudgetsByCategory(
            User user,
            int month,
            int year
    );

    @Query("""
    SELECT COALESCE(SUM(b.monthlyLimit), 0)
    FROM Budget b
    WHERE b.user = :user
""")
    BigDecimal getTotalBudget(User user);
}
