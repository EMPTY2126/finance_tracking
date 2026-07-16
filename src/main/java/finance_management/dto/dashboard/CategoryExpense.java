package finance_management.dto.dashboard;

import finance_management.enums.Category;

import java.math.BigDecimal;

public record CategoryExpense(
        Category category,
        BigDecimal amount
) {
}
