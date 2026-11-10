package bank_system;

import java.util.Locale;

/**
 * Ngoại lệ khi số dư tài khoản không đủ.
 */
public class InsufficientFundsException extends BankException {
    public InsufficientFundsException(double amount) {
        super(String.format(Locale.US,
                "Số dư tài khoản không đủ $%.2f để thực hiện giao dịch", amount));
    }
}
