package finance_management.service;

import finance_management.exceptions.BudgetNotFoundException;
import finance_management.exceptions.UserNotFoundException;
import finance_management.model.Budget;
import finance_management.model.User;
import finance_management.repo.BudgetRepo;
import finance_management.repo.UserRepo;
import finance_management.validations.AmountValidation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BudgetService {
    private final BudgetRepo budgetRepo;
    private final UserRepo userRepo;
    private final AmountValidation amountValidation;

    public BudgetService(BudgetRepo budgetRepo,
                         UserRepo userRepo,
                         AmountValidation amountValidation){
        this.budgetRepo = budgetRepo;
        this.userRepo = userRepo;
        this.amountValidation = amountValidation;
    }

    public Budget addBudget(Budget budget){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(()->new UserNotFoundException("In valid user ID", HttpStatus.NOT_FOUND));
        amountValidation.validate(budget);
        budget.setUser(user);
        return budgetRepo.save(budget);
    }

    public List<Budget> getAllBudgets(){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(()->new UserNotFoundException("In valid user ID", HttpStatus.NOT_FOUND));
        return budgetRepo.findByUserId(user.getId());
    }

    @Transactional
    public Budget updateBudget(Budget budget){
        Budget bd = budgetRepo.findById(budget.getId()).orElseThrow(()->new BudgetNotFoundException("Budget Id not valid", HttpStatus.NOT_FOUND));
        bd.updateBudget(budget);
        return bd;
    }

    public String deleteBudget(Long id){
        Budget budget = budgetRepo.findById(id).orElseThrow(()->new BudgetNotFoundException("Budget Id not valid", HttpStatus.NOT_FOUND));
        budgetRepo.delete(budget);
        return "deleted successfullt";
    }

}
