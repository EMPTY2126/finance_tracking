package finance_management.dto.budget;

import finance_management.enums.Category;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.math.BigDecimal;

public class BudgetRequest {

    @Enumerated(EnumType.STRING)
    private Category category;

    private BigDecimal monthlyLimit;

    private int month;

    private int year;



    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(BigDecimal monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public BudgetRequest(Category category, BigDecimal monthlyLimit, int month, int year) {
        this.category = category;
        this.monthlyLimit = monthlyLimit;
        this.month = month;
        this.year = year;
    }

    @Override
    public String toString() {
        return "BudgetRequest{" +
                "category=" + category +
                ", monthlyLimit=" + monthlyLimit +
                ", month=" + month +
                ", year=" + year +
                '}';
    }

    public BudgetRequest() {
    }
}
