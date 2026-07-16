package finance_management.validations;

import finance_management.dto.budget.BudgetRequest;
import finance_management.dto.transaction.TransactionRequest;
import finance_management.exceptions.InvalidField;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Year;

@Component
public class AmountAndFieldValidation {


    public void validate(TransactionRequest transaction){
        if(transaction.getAmount()==null || transaction.getAmount().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidField("Amount Must be grater than Zero", HttpStatus.BAD_REQUEST);
        }
        if(transaction.getTitle() == null){
            throw new InvalidField("Please enter the title", HttpStatus.BAD_REQUEST);
        }
    }

    public void validate(BudgetRequest budget){
        if(budget.getMonthlyLimit() == null || budget.getMonthlyLimit().compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidField("Enter a valid amount", HttpStatus.BAD_REQUEST);
        }
        if(budget.getMonth()>12 || budget.getMonth()<=0){
            throw new InvalidField("Enter a proper month", HttpStatus.BAD_REQUEST);
        }
//
//        if(budget.getYear() < Year.now().getValue()){
//            throw new InvalidField("Enter a proper year", HttpStatus.BAD_REQUEST);
//        }
    }

}
