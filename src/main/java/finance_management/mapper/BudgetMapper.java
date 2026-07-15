package finance_management.mapper;

import finance_management.dto.budget.BudgetRequest;
import finance_management.dto.budget.BudgetResponse;
import finance_management.model.Budget;

public class BudgetMapper {
    public static Budget toEntity(BudgetRequest request) {
        Budget budget = new Budget();
        budget.setCategory(request.getCategory());
        budget.setMonthlyLimit(request.getMonthlyLimit());
        budget.setMonth(request.getMonth());
        budget.setYear(request.getYear());
        return budget;
    }

    public static BudgetResponse toResponse(Budget budget) {
        return new BudgetResponse(
                budget.getId(),
                budget.getCategory(),
                budget.getMonthlyLimit(),
                budget.getMonth(),
                budget.getYear(),
                budget.getCreatedAt()
        );
    }
}
