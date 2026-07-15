package finance_management.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import finance_management.dto.transaction.TransactionRequest;
import finance_management.enums.Category;
import finance_management.enums.TransactionType;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "amount", precision = 10, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    public Transaction() {
    }

    public Transaction(String title,
                       BigDecimal amount,
                       TransactionType type,
                       Category category,
                       String description,
                       LocalDate transactionDate) {

        this.title = title;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
        this.transactionDate = transactionDate;
    }

    public Transaction(TransactionRequest transactionRequest){
        this.title = transactionRequest.getTitle();
        this.amount = transactionRequest.getAmount();
        this.type = transactionRequest.getType();
        this.category = transactionRequest.getCategory();
        this.description = transactionRequest.getDescription();
    }

    public void updateFrom(TransactionRequest transactionRequest) {
        this.title = transactionRequest.getTitle();
        this.amount = transactionRequest.getAmount();
        this.type = transactionRequest.getType();
        this.category = transactionRequest.getCategory();
        this.description = transactionRequest.getDescription();
//        this.transactionDate = transactionRequest.getTransactionDate();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", amount=" + amount +
                ", type=" + type +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", transactionDate=" + transactionDate +
                ", createdAt=" + createdAt +
                ", user=" + user +
                '}';
    }
}
