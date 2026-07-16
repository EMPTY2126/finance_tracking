package finance_management.dto.dashboard;

import java.math.BigDecimal;
import java.time.Month;

public record MonthlyTrend(
        Month month,
        BigDecimal income,
        BigDecimal expense
) {}
