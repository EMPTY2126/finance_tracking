package finance_management.validations;

import finance_management.exceptions.InvalidAmount;
import finance_management.model.Budget;
import finance_management.model.Transaction;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AmountValidation {


    public void validate(Transaction transaction){
        if(transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmount("Enter a valid amount", HttpStatus.BAD_REQUEST);
        }
    }

    public void validate(Budget budget){
        if(budget.getMonthlyLimit().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmount("Enter a valid amount", HttpStatus.BAD_REQUEST);
        }
    }

}
