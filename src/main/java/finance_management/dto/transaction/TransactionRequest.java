package finance_management.dto.transaction;

import finance_management.enums.Category;
import finance_management.enums.TransactionType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.antlr.v4.runtime.misc.NotNull;

import java.math.BigDecimal;

public class TransactionRequest {
    private String title;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    public TransactionRequest(){

    }

    public TransactionRequest(String title, BigDecimal amount, TransactionType type, Category category, String description) {
        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
