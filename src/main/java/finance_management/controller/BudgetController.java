package finance_management.controller;

import finance_management.dto.budget.BudgetRequest;
import finance_management.dto.budget.BudgetResponse;
import finance_management.model.Budget;
import finance_management.service.BudgetService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/budgets")
public class BudgetController {

    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @GetMapping
    public List<BudgetResponse> getAllBudget(){
        return budgetService.getAllBudgets();
    }

    @PostMapping
    public BudgetResponse addBidget(@RequestBody BudgetRequest budgetRequest){
        return budgetService.addBudget(budgetRequest);
    }

    @PutMapping("/{id}")
    public BudgetResponse updateBudget(@PathVariable Long id, @RequestBody BudgetRequest budgetRequest){
        return budgetService.updateBudget(id, budgetRequest);
    }

    @DeleteMapping("/{id}")
    public String deleteBudget(@PathVariable Long id){
        return budgetService.deleteBudget(id);
    }



}
