package finance_management.controller;

import finance_management.dto.transaction.TransactionRequest;
import finance_management.dto.transaction.TransactionResponse;
import finance_management.enums.Category;
import finance_management.enums.TransactionType;
import finance_management.model.Transaction;
import finance_management.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public TransactionResponse addTransaction(@RequestBody TransactionRequest transaction){
        return transactionService.addTransaction(transaction);
    }

    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(@PathVariable Long id,@RequestBody TransactionRequest transaction){
        return transactionService.updateTransaction(id, transaction);
    }

//    @GetMapping
//    public Page<TransactionResponse> getAllTransactionOfUser(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size
//    ){
//        return transactionService.getAllTransactions(page, size);
//    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id){
        return transactionService.deleteTransaction(id);
    }

    @PostMapping("/bulk")
    public String bulkAddTransactions(@RequestBody List<TransactionRequest> transactionRequests){
        return transactionService.bulkAddTransactions(transactionRequests);
    }

    @GetMapping
    public Page<TransactionResponse> getTransactions(

            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month,

            @RequestParam(required = false) Category category,

            @RequestParam(required = false) TransactionType type,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate,

            @RequestParam(required = false) BigDecimal minAmount,

            @RequestParam(required = false) BigDecimal maxAmount,

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size

    ) {

        return transactionService.getTransactions(
                year,
                month,
                category,
                type,
                startDate,
                endDate,
                minAmount,
                maxAmount,
                page,
                size
        );
    }


}
