package finance_management.controller;

import finance_management.dto.transaction.TransactionRequest;
import finance_management.dto.transaction.TransactionResponse;
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
    public TransactionResponse addTransaction(@RequestBody TransactionRequest transaction){
        return transactionService.addTransaction(transaction);
    }

    @PutMapping("/{id}")
    public TransactionResponse updateTransaction(@PathVariable Long id,@RequestBody TransactionRequest transaction){
        return transactionService.updateTransaction(id, transaction);
    }

    @GetMapping
    public List<TransactionResponse> getAllTransactionOfUser(){
        return transactionService.getAllTransactions();
    }

    @DeleteMapping("/{id}")
    public String deleteTransaction(@PathVariable Long id){
        return transactionService.deleteTransaction(id);
    }

}
