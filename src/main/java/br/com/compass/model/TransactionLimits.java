package br.com.compass.model;

import br.com.compass.model.enumeration.AccountType;
import br.com.compass.model.enumeration.TransactionType;
import br.com.compass.repository.dao.TransactionDAO;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Map;

public class TransactionLimits {

    private static final Map<AccountType, BigDecimal> DAILY_WITHDRAW_LIMITS = Map.of(
            AccountType.SALARY, new BigDecimal("1000.00"),
            AccountType.SAVINGS, new BigDecimal("5000.00"),
            AccountType.CHECKING, new BigDecimal("5000.00")
    );

    private static final Map<AccountType, BigDecimal> DAILY_TRANSFER_LIMITS = Map.of(
            AccountType.SALARY, new BigDecimal("3000.00"),
            AccountType.SAVINGS, new BigDecimal("10000.00"),
            AccountType.CHECKING, new BigDecimal("50000.00")
    );

    private static final Map<AccountType, BigDecimal> SINGLE_TRANSACTION_LIMITS = Map.of(
            AccountType.SALARY, new BigDecimal("500.00"),
            AccountType.SAVINGS, new BigDecimal("2000.00"),
            AccountType.CHECKING, new BigDecimal("50000.00")
    );

    private final TransactionDAO transactionDAO;

    public TransactionLimits(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public boolean isWithinDailyWithdrawLimit(Account account, BigDecimal amount) {
        BigDecimal dailyLimit = DAILY_WITHDRAW_LIMITS.get(account.getType());
        BigDecimal todayTotal = getTodayTotalWithdraws(account);

        return todayTotal.add(amount).compareTo(dailyLimit) <= 0;
    }

    public boolean isWithinDailyTransferLimit(Account account, BigDecimal amount) {
        BigDecimal dailyLimit = DAILY_TRANSFER_LIMITS.get(account.getType());
        BigDecimal todayTotal = getTodayTotalTransfers(account);

        return todayTotal.add(amount).compareTo(dailyLimit) <= 0;
    }

    public boolean isWithinSingleTransactionLimit(Account account, BigDecimal amount) {
        BigDecimal limit = SINGLE_TRANSACTION_LIMITS.get(account.getType());
        return amount.compareTo(limit) <= 0;
    }

    private BigDecimal getTodayTotalWithdraws(Account account) {
        return transactionDAO.getTodayTotalByTypeAndAccount(
                account,
                TransactionType.WITHDRAW,
                LocalDateTime.now().with(LocalTime.MIN).toInstant(ZoneId.systemDefault()
                        .getRules().getOffset(LocalDateTime.now())),
                LocalDateTime.now().with(LocalTime.MAX).toInstant(ZoneId.systemDefault()
                        .getRules().getOffset(LocalDateTime.now()))
        );
    }

    private BigDecimal getTodayTotalTransfers(Account account) {
        return transactionDAO.getTodayTotalByTypeAndAccount(
                account,
                TransactionType.TRANSFER,
                LocalDateTime.now().with(LocalTime.MIN).toInstant(ZoneId.systemDefault()
                        .getRules().getOffset(LocalDateTime.now())),
                LocalDateTime.now().with(LocalTime.MAX).toInstant(ZoneId.systemDefault()
                        .getRules().getOffset(LocalDateTime.now()))
        );
    }
}