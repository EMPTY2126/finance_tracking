package finance_management.dto.dashboard;

import finance_management.dto.transaction.TransactionResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public class DashBoardResponse {
    private List<CategoryExpense> categoryExpenses;
    private List<CategoryBudget> categoryBudgets;
    private List<MonthlyTrend> monthlyTrends;
    private DashboardSummary dashboardSummary;
    private Page<TransactionResponse> transactions;

    public DashBoardResponse() {
    }

    public DashBoardResponse(List<CategoryExpense> categoryExpenses, List<CategoryBudget> categoryBudgets, List<MonthlyTrend> monthlyTrends, DashboardSummary dashboardSummary, Page<TransactionResponse> transactions) {
        this.categoryExpenses = categoryExpenses;
        this.categoryBudgets = categoryBudgets;
        this.monthlyTrends = monthlyTrends;
        this.dashboardSummary = dashboardSummary;
        this.transactions = transactions;
    }

    public List<CategoryExpense> getCategoryExpenses() {
        return categoryExpenses;
    }

    public void setCategoryExpenses(List<CategoryExpense> categoryExpenses) {
        this.categoryExpenses = categoryExpenses;
    }

    public List<CategoryBudget> getCategoryBudgets() {
        return categoryBudgets;
    }

    public void setCategoryBudgets(List<CategoryBudget> categoryBudgets) {
        this.categoryBudgets = categoryBudgets;
    }

    public List<MonthlyTrend> getMonthlyTrends() {
        return monthlyTrends;
    }

    public void setMonthlyTrends(List<MonthlyTrend> monthlyTrends) {
        this.monthlyTrends = monthlyTrends;
    }

    public DashboardSummary getDashboardSummary() {
        return dashboardSummary;
    }

    public void setDashboardSummary(DashboardSummary dashboardSummary) {
        this.dashboardSummary = dashboardSummary;
    }

    public Page<TransactionResponse> getTransactions() {
        return transactions;
    }

    public void setTransactions(Page<TransactionResponse> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "DashBoardResponce{" +
                "categoryExpenses=" + categoryExpenses +
                ", categoryBudgets=" + categoryBudgets +
                ", monthlyTrends=" + monthlyTrends +
                ", dashboardSummary=" + dashboardSummary +
                ", transactions=" + transactions +
                '}';
    }
}
