package finance_management.dto.dashboard;

import java.math.BigDecimal;

public record DashboardSummary(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal totalBudget
) {}