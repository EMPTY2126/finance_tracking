package finance_management.service;

import finance_management.exceptions.TransactionNotFoundException;
import finance_management.exceptions.UserNotFoundException;
import finance_management.model.Transaction;
import finance_management.model.User;
import finance_management.repo.TransactionRepo;
import finance_management.repo.UserRepo;
import finance_management.validations.AmountValidation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final AmountValidation amountValidation;

    public TransactionService(
            UserRepo userRepo,
            AmountValidation amountValidation,
            TransactionRepo transactionRepo){
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.amountValidation = amountValidation;

    }

    public Transaction addTransaction(Transaction transaction){
        amountValidation.validate(transaction);
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Invaild user", HttpStatus.NOT_FOUND));
        transaction.setUser(user);
        transaction.setTransactionDate(LocalDate.now());
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getAllTransactions(){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Invaild user", HttpStatus.NOT_FOUND));
        return transactionRepo.findByUserId(user.getId());
    }

    @Transactional
    public Transaction updateTransaction(Transaction transaction){
        amountValidation.validate(transaction);
        Transaction tx = transactionRepo.findById(transaction.getId()).
                orElseThrow(()-> new TransactionNotFoundException("Transaction not found", HttpStatus.NOT_FOUND));
        tx.updateFrom(transaction);
        return tx;
    }

    public String deleteTransaction(Long id){
        Transaction tx = transactionRepo.findById(id).
                orElseThrow(()-> new TransactionNotFoundException("Transaction not found", HttpStatus.NOT_FOUND));
        transactionRepo.delete(tx);
        return "Successfully deleted";
    }

}
