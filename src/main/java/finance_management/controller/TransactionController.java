package finance_management.controller;

import finance_management.model.Transaction;
import finance_management.service.TransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping
    public Transaction addTransaction(@RequestBody Transaction transaction){
        return transactionService.addTransaction(transaction);
    }

    @PutMapping
    public Transaction updateTransaction(@RequestBody Transaction transaction){
        return transactionService.updateTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> getAllTransactionOfUser(){
        return transactionService.getAllTransactions();
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id){
        return transactionService.deleteTransaction(id);
    }

}
