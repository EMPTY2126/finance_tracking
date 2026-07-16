package finance_management.service;

import finance_management.dto.budget.BudgetRequest;
import finance_management.dto.budget.BudgetResponse;
import finance_management.exceptions.BudgetNotFoundException;
import finance_management.exceptions.IncorrectUserException;
import finance_management.exceptions.UserNotFoundException;
import finance_management.mapper.BudgetMapper;
import finance_management.model.Budget;
import finance_management.model.User;
import finance_management.repo.BudgetRepo;
import finance_management.repo.UserRepo;
import finance_management.validations.AmountAndFieldValidation;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BudgetService {
    private final BudgetRepo budgetRepo;
    private final UserRepo userRepo;
    private final AmountAndFieldValidation amountAndFieldValidation;

    public BudgetService(BudgetRepo budgetRepo,
                         UserRepo userRepo,
                         AmountAndFieldValidation amountAndFieldValidation){
        this.budgetRepo = budgetRepo;
        this.userRepo = userRepo;
        this.amountAndFieldValidation = amountAndFieldValidation;
    }

    public BudgetResponse addBudget(BudgetRequest budgetRequest){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(()->new UserNotFoundException("In valid user ID", HttpStatus.NOT_FOUND));
        amountAndFieldValidation.validate(budgetRequest);
        Budget budget = BudgetMapper.toEntity(budgetRequest);
        budget.setUser(user);
        budget =  budgetRepo.save(budget);
        return BudgetMapper.toResponse(budget);
    }

    public List<BudgetResponse> getAllBudgets(){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(()->new UserNotFoundException("In valid user ID", HttpStatus.NOT_FOUND));
        List<Budget> budgets =  budgetRepo.findByUserId(user.getId());
        return budgets.stream().
                map(BudgetMapper::toResponse)
                .toList();
    }

    @Transactional
    public BudgetResponse updateBudget(Long id, BudgetRequest budgetRequest){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Budget budget = budgetRepo.findById(id).orElseThrow(()->new BudgetNotFoundException("Budget Id not valid", HttpStatus.NOT_FOUND));
        if(!budget.getUser().getEmail().equals(userEmail)) throw new IncorrectUserException("Unauthorized resource");
        budget.updateBudget(budgetRequest);
        return BudgetMapper.toResponse(budget);
    }

    public String deleteBudget(Long id){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        Budget budget = budgetRepo.findById(id).orElseThrow(()->new BudgetNotFoundException("Budget Id not valid", HttpStatus.NOT_FOUND));
        if(!budget.getUser().getEmail().equals(userEmail)) throw new IncorrectUserException("Unauthorized resource");
        budgetRepo.delete(budget);
        return "deleted successfullt";
    }

    @Transactional
    public String bulkAddBudget(List<BudgetRequest> budgetRequests){
        for(BudgetRequest budgetRequest : budgetRequests){
            addBudget(budgetRequest);
        }
        return "Budgets added successfully";
    }

}
