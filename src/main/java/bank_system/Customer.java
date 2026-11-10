package bank_system;

import java.util.ArrayList;
import java.util.List;

/**
 * Lớp Customer đại diện cho một khách hàng.
 */
public class Customer {
    private long idNumber;
    private String fullName;
    private List<Account> accountList;

    /**
     * Constructor mặc định.
     */
    public Customer() {
        this(0L, "");
    }

    public Customer(long idNumber, String fullName) {
        this.idNumber = idNumber;
        this.fullName = fullName;
        this.accountList = new ArrayList<>();
    }

    public long getIdNumber() { return idNumber; }
    public void setIdNumber(long idNumber) { this.idNumber = idNumber; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        if (accountList == null) {
            this.accountList = new ArrayList<>();
        } else {
            this.accountList = accountList;
        }
    }

    /**
     * Thêm tài khoản cho khách hàng.
     *
     * @param account Tài khoản cần thêm.
     */
    public void addAccount(Account account) {
        if (account != null && !accountList.contains(account)) {
            accountList.add(account);
        }
    }

    /**
     * Xóa tài khoản khỏi khách hàng.
     *
     * @param account Tài khoản cần xóa.
     */
    public void removeAccount(Account account) {
        if (account != null) {
            accountList.remove(account);
        }
    }

    /**
     * Lấy thông tin cơ bản của khách hàng.
     *
     * @return Chuỗi thông tin khách hàng.
     */
    public String getCustomerInfo() {
        return "Số CMND: " + idNumber + ". Họ tên: " + fullName + ".";
    }
}