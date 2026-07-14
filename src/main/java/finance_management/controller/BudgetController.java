package finance_management.controller;

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
    public List<Budget> getAllBudget(){
        return budgetService.getAllBudgets();
    }

    @PostMapping
    public Budget addBidget(@RequestBody Budget budget){
        return budgetService.addBudget(budget);
    }

    @PutMapping
    public Budget updateBudget(@RequestBody Budget budget){
        return budgetService.updateBudget(budget);
    }

    @DeleteMapping("/{id}")
    public String deleteBudget(@PathVariable Long id){
        return budgetService.deleteBudget(id);
    }



}
