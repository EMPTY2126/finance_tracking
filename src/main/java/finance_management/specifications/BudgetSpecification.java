package finance_management.specifications;

import finance_management.enums.Category;
import finance_management.model.Budget;
import finance_management.model.User;
import org.springframework.data.jpa.domain.Specification;

public class BudgetSpecification {
    public static Specification<Budget> byUser(User user){
        return(root, query, cb) ->
                cb.equal(root.get("user"), user);
    }

    public static Specification<Budget> hasCategory(Category category){
        return(root, query, cb) ->
                cb.equal(root.get("category"), category);
    }

    public static Specification<Budget> byYear(int year){
        return(root, query, cb) ->
                cb.equal(root.get("year"), year);
    }

    public static Specification<Budget> byMonth(int month){
        return(root, query, cb) ->
                cb.equal(root.get("month"), month);
    }



}
