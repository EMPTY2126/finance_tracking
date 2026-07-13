package finance_management.validations;

import finance_management.exceptions.InvalidTransactionAmount;
import finance_management.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionValidation {


    public void validateTransaction(Transaction transaction){
        if(transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidTransactionAmount("Enter a valid amount", HttpStatus.BAD_REQUEST);
        }
    }

}
