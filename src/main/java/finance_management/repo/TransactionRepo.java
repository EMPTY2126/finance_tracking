package finance_management.repo;

import finance_management.dto.dashboard.CategoryExpense;
import finance_management.model.Transaction;
import finance_management.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
    public Page<Transaction> findByUserId(Long userId, Pageable page);

    @Query("""
    SELECT new finance_management.dto.dashboard.CategoryExpense(
        t.category,
        SUM(t.amount)
    )
    FROM Transaction t
    WHERE t.user = :user
      AND t.type = finance_management.enums.TransactionType.EXPENSE
      AND YEAR(t.transactionDate) = :year
      AND MONTH(t.transactionDate) = :month
    GROUP BY t.category
    """)
    List<CategoryExpense> getExpensesByCategory(
            User user,
            int month,
            int year
    );


    @Query(value = """
SELECT
    MONTH(transaction_date) AS month,
    SUM(CASE WHEN type = 'INCOME' THEN amount ELSE 0 END) AS income,
    SUM(CASE WHEN type = 'EXPENSE' THEN amount ELSE 0 END) AS expense
FROM transactions
WHERE user_id = :userId
  AND YEAR(transaction_date) = :year
GROUP BY MONTH(transaction_date)
ORDER BY MONTH(transaction_date)
""", nativeQuery = true)
    List<Object[]> getMonthlyTrend(Long userId, int year);



    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.user = :user
      AND t.type = finance_management.enums.TransactionType.INCOME
      AND YEAR(t.transactionDate) = :year
      AND MONTH(t.transactionDate) = :month
""")
    BigDecimal getTotalIncome(User user, int year, int month);

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.user = :user
      AND t.type = finance_management.enums.TransactionType.EXPENSE
      AND YEAR(t.transactionDate) = :year
      AND MONTH(t.transactionDate) = :month
""")
    BigDecimal getTotalExpense(User user, int year, int month);



}
