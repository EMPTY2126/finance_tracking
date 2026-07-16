package finance_management.specifications;

import finance_management.enums.Category;
import finance_management.enums.TransactionType;
import finance_management.model.Transaction;
import finance_management.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionSpecification {
    public static Specification<Transaction> byUser(User user) {
        return (root, query, cb) ->
                cb.equal(root.get("user"), user);
    }

    public static Specification<Transaction> hasCategory(Category category) {
        return (root, query, cb) ->
                cb.equal(root.get("category"), category);
    }

    public static Specification<Transaction> hasType(TransactionType type) {
        return (root, query, cb) ->
                cb.equal(root.get("type"), type);
    }

    public static Specification<Transaction> betweenDates(
            LocalDate start,
            LocalDate end) {

        return (root, query, cb) ->
                cb.between(root.get("transactionDate"), start, end);
    }

    public static Specification<Transaction> minAmount(BigDecimal amount) {
        return (root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("amount"), amount);
    }

    public static Specification<Transaction> maxAmount(BigDecimal amount) {
        return (root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("amount"), amount);
    }
}
