package finance_management.service;

import finance_management.dto.dashboard.MonthlyTrend;
import finance_management.dto.transaction.TransactionRequest;
import finance_management.dto.transaction.TransactionResponse;
import finance_management.enums.Category;
import finance_management.enums.TransactionType;
import finance_management.exceptions.IncorrectUserException;
import finance_management.exceptions.TransactionNotFoundException;
import finance_management.exceptions.UserNotFoundException;
import finance_management.mapper.TransactionMapper;
import finance_management.model.Transaction;
import finance_management.model.User;
import finance_management.repo.TransactionRepo;
import finance_management.repo.UserRepo;
import finance_management.specifications.TransactionSpecification;
import finance_management.validations.AmountAndFieldValidation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final UserRepo userRepo;
    private final TransactionRepo transactionRepo;
    private final AmountAndFieldValidation amountAndFieldValidation;

    public TransactionService(
            UserRepo userRepo,
            AmountAndFieldValidation amountAndFieldValidation,
            TransactionRepo transactionRepo){
        this.userRepo = userRepo;
        this.transactionRepo = transactionRepo;
        this.amountAndFieldValidation = amountAndFieldValidation;
    }

    public TransactionResponse addTransaction(TransactionRequest transactionRequest){
        amountAndFieldValidation.validate(transactionRequest);
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Invaild user", HttpStatus.NOT_FOUND));
        Transaction transaction = TransactionMapper.convertToEntity(transactionRequest);
        transaction.setUser(user);
        transaction.setTransactionDate(LocalDate.now());
        transaction =  transactionRepo.save(transaction);
        return TransactionMapper.toResponse(transaction);
    }

    public Page<TransactionResponse> getAllTransactions(int page, int size){
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new UserNotFoundException("Invaild user", HttpStatus.NOT_FOUND));
        Pageable pageable = PageRequest.of(page,size, Sort.by("transactionDate").descending());
        Page<Transaction> transactions =
                transactionRepo.findByUserId(user.getId(), pageable);

        return transactions.map(TransactionMapper::toResponse);
    }

    @Transactional
    public TransactionResponse updateTransaction(Long id, TransactionRequest transactionRequest){
        Transaction tx = transactionRepo.findById(id).
                orElseThrow(()-> new TransactionNotFoundException("Transaction not found", HttpStatus.NOT_FOUND));
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        if(!tx.getUser().getEmail().equals(userEmail)) throw new IncorrectUserException("Unauthorized resource");
        amountAndFieldValidation.validate(transactionRequest);
        tx.updateFrom(transactionRequest);
        return TransactionMapper.toResponse(tx);
    }

    public String deleteTransaction(Long id){
        Transaction tx = transactionRepo.findById(id).
                orElseThrow(()-> new TransactionNotFoundException("Transaction not found", HttpStatus.NOT_FOUND));
        String userEmail = Objects.requireNonNull(SecurityContextHolder.getContext().getAuthentication()).getName();
        if(!tx.getUser().getEmail().equals(userEmail)) throw new IncorrectUserException("Unauthorized resource");
        transactionRepo.delete(tx);
        return "Successfully deleted";
    }
@Transactional
    public String bulkAddTransactions(List<TransactionRequest> transactionRequests){
        for(TransactionRequest transactionRequest : transactionRequests){
            addTransaction(transactionRequest);
        }

        return "Successfully added";
    }

    public Page<TransactionResponse> getTransactions(

            Integer year,
            Integer month,
            Category category,
            TransactionType type,
            LocalDate startDate,
            LocalDate endDate,
            BigDecimal minAmount,
            BigDecimal maxAmount,
            int page,
            int size

    ) {

        String email = Objects.requireNonNull(SecurityContextHolder.getContext()
                        .getAuthentication())
                .getName();

        User user = userRepo.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "Invalid User",
                                HttpStatus.NOT_FOUND));

        Specification<Transaction> spec =
                Specification.where(
                        TransactionSpecification.byUser(user)
                );

        if (category != null) {
            spec = spec.and(
                    TransactionSpecification.hasCategory(category)
            );
        }

        if (type != null) {
            spec = spec.and(
                    TransactionSpecification.hasType(type)
            );
        }

        if (year != null && month != null) {

            LocalDate start = LocalDate.of(year, month, 1);

            LocalDate end = start.withDayOfMonth(
                    start.lengthOfMonth()
            );

            spec = spec.and(
                    TransactionSpecification.betweenDates(
                            start,
                            end
                    )
            );
        }

        if (startDate != null && endDate != null) {

            spec = spec.and(
                    TransactionSpecification.betweenDates(
                            startDate,
                            endDate
                    )
            );

        }

        if (minAmount != null) {

            spec = spec.and(
                    TransactionSpecification.minAmount(
                            minAmount
                    )
            );

        }

        if (maxAmount != null) {

            spec = spec.and(
                    TransactionSpecification.maxAmount(
                            maxAmount
                    )
            );

        }

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by("transactionDate").descending()
                );

        Page<Transaction> transactions =
                transactionRepo.findAll(spec, pageable);

        return transactions.map(TransactionMapper::toResponse);

    }

//    public List<MonthlyTrend> getMonthlyTrend(int year) {
//
//        User user = userService.getCurrentUser();
//
//        List<Object[]> result =
//                transactionRepo.getMonthlyTrend(user.getId(), year);
//
//        return result.stream()
//                .map(row -> new MonthlyTrend(
//                        Month.of(((Number) row[0]).intValue()),
//                        (BigDecimal) row[1],
//                        (BigDecimal) row[2]
//                ))
//                .toList();
//    }

}
