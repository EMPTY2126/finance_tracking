package finance_management.service;

import finance_management.dto.dashboard.*;
import finance_management.dto.transaction.TransactionResponse;
import finance_management.exceptions.UserNotFoundException;
import finance_management.model.User;
import finance_management.repo.BudgetRepo;
import finance_management.repo.TransactionRepo;
import finance_management.repo.UserRepo;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;
import java.util.Objects;

@Service
public class DashboardService {

    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final BudgetRepo budgetRepo;
    private final TransactionService transactionService;

    public DashboardService(
            UserRepo userRepo,
            TransactionRepo transactionRepo,
            BudgetRepo budgetRepo,
            TransactionService transactionService
    ){
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.budgetRepo = budgetRepo;
        this.transactionService = transactionService;
    }

    public DashBoardResponse getDashboardDetails(Integer month, Integer year){
        if(month == null) month = LocalDate.now().getMonth().getValue();
        if(year == null) year = Year.now().getValue();
        DashboardSummary dashboardSummary = getDashboardSummary(month,year);
        List<CategoryBudget> categoryBudgets = getCategoryBudget(month,year);
        List<CategoryExpense> categoryExpenses = getCategoryExpences(month, year);
        List<MonthlyTrend> monthlyTrends = getMonthlyTrend(year);
        Page<TransactionResponse> transactions = transactionService.getAllTransactions(0,5);
        return new DashBoardResponse(categoryExpenses, categoryBudgets, monthlyTrends, dashboardSummary, transactions);
    }

    public DashboardSummary getDashboardSummary(Integer year, Integer month) {
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user  = userRepo.findByEmail(userEmail).orElseThrow(()-> new UserNotFoundException("user not found", HttpStatus.NOT_FOUND));
        if(month == null) month = LocalDate.now().getMonth().getValue();
        if(year == null) year = Year.now().getValue();
        BigDecimal income = transactionRepo.getTotalIncome(user, year, month);
        BigDecimal expense = transactionRepo.getTotalExpense(user, year, month);
        BigDecimal budget = budgetRepo.getTotalBudget(user);

        return new DashboardSummary(
                income,
                expense,
                budget
        );
    }

    public List<CategoryBudget> getCategoryBudget(Integer month, Integer year){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user  = userRepo.findByEmail(userEmail).orElseThrow(()-> new UserNotFoundException("user not found", HttpStatus.NOT_FOUND));
        if(month == null) month = LocalDate.now().getMonth().getValue();
        if(year == null) year = Year.now().getValue();
        return budgetRepo.getBudgetsByCategory(user,month,year);
    }


    public List<CategoryExpense> getCategoryExpences(Integer month, Integer year){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user  = userRepo.findByEmail(userEmail).orElseThrow(()-> new UserNotFoundException("user not found", HttpStatus.NOT_FOUND));
        if(month == null) month = LocalDate.now().getMonth().getValue();
        if(year == null) year = Year.now().getValue();
        return transactionRepo.getExpensesByCategory(user,month,year);
    }

    public List<MonthlyTrend> getMonthlyTrend(int year) {
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user  = userRepo.findByEmail(userEmail).orElseThrow(()-> new UserNotFoundException("user not found", HttpStatus.NOT_FOUND));
        List<Object[]> result = transactionRepo.getMonthlyTrend(user.getId(), year);

        return result.stream()
                .map(row -> new MonthlyTrend(
                        Month.of(((Number) row[0]).intValue()),
                        (BigDecimal) row[1],
                        (BigDecimal) row[2]
                ))
                .toList();
    }

}
