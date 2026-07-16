package finance_management.dto.dashboard;

import finance_management.enums.Category;

import java.math.BigDecimal;

public record CategoryBudget(
        Category category,
        BigDecimal monthlyLimit
) {
}
