package finance_management.mapper;

import finance_management.dto.transaction.TransactionRequest;
import finance_management.dto.transaction.TransactionResponse;
import finance_management.model.Transaction;

public class TransactionMapper {
    public static TransactionResponse toResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.getId(),
                transaction.getTitle(),
                transaction.getAmount(),
                transaction.getType(),
                transaction.getCategory(),
                transaction.getDescription(),
                transaction.getTransactionDate(),
                transaction.getCreatedAt()
        );
    }

    public static Transaction convertToEntity(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTitle(request.getTitle());
        transaction.setAmount(request.getAmount());
        transaction.setType(request.getType());
        transaction.setCategory(request.getCategory());
        transaction.setDescription(request.getDescription());
        return transaction;
    }


}
