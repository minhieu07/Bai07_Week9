package bank_system;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Lớp đại diện cho hệ thống ngân hàng quản lý khách hàng.
 */
public class Bank {
    private static final Logger logger = LoggerFactory.getLogger(Bank.class);
    private static final String ID_REGEX = "\\d{9}";

    private List<Customer> customerList;

    public Bank() {
        this.customerList = new ArrayList<>();
    }

    public List<Customer> getCustomerList() {
        return customerList;
    }

    /**
     * Cập nhật danh sách khách hàng.
     *
     * @param customerList Danh sách khách hàng mới.
     */
    public void setCustomerList(List<Customer> customerList) {
        if (customerList == null) {
            this.customerList = new ArrayList<>();
        } else {
            this.customerList = customerList;
        }
    }

    /**
     * Đọc danh sách khách hàng từ luồng đầu vào (InputStream).
     *
     * @param inputStream Luồng dữ liệu đọc khách hàng.
     */
    public void readCustomerList(InputStream inputStream) {
        logger.info("Bắt đầu đọc dữ liệu khách hàng từ InputStream.");
        if (inputStream == null) {
            logger.error("InputStream bị null, dừng đọc dữ liệu.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            Customer current = null;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                int lastSpaceIndex = line.lastIndexOf(' ');
                if (lastSpaceIndex <= 0) {
                    continue;
                }

                String token = line.substring(lastSpaceIndex + 1).trim();

                // Nếu token là mã số khách hàng (9 chữ số)
                if (token.matches(ID_REGEX)) {
                    String name = line.substring(0, lastSpaceIndex).trim();
                    current = new Customer(Long.parseLong(token), name);
                    customerList.add(current);
                    logger.debug("Đã thêm khách hàng: {}", name);
                } else if (current != null) {
                    // Xử lý tạo tài khoản cho khách hàng hiện tại
                    String[] parts = line.split("\\s+");
                    if (parts.length >= 3) {
                        long accNumber = Long.parseLong(parts[0]);
                        double balance = Double.parseDouble(parts[2]);

                        if (Account.CHECKING_TYPE.equals(parts[1])) {
                            current.addAccount(new CheckingAccount(accNumber, balance));
                        } else if (Account.SAVINGS_TYPE.equals(parts[1])) {
                            current.addAccount(new SavingsAccount(accNumber, balance));
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Đã xảy ra lỗi trong quá trình đọc dữ liệu khách hàng.", e);
        }
    }

    /**
     * Lấy thông tin khách hàng sắp xếp theo mã ID.
     *
     * @return Chuỗi thông tin danh sách khách hàng.
     */
    public String getCustomersInfoByIdOrder() {
        customerList.sort((c1, c2) -> Long.compare(c1.getIdNumber(), c2.getIdNumber()));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < customerList.size(); i++) {
            sb.append(customerList.get(i).getCustomerInfo());
            if (i < customerList.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * Lấy thông tin khách hàng sắp xếp theo Tên.
     *
     * @return Chuỗi thông tin danh sách khách hàng.
     */
    public String getCustomersInfoByNameOrder() {
        List<Customer> copyList = new ArrayList<>(customerList);
        copyList.sort((c1, c2) -> {
            int nameCompare = c1.getFullName().compareTo(c2.getFullName());
            return nameCompare != 0 ? nameCompare : Long.compare(c1.getIdNumber(), c2.getIdNumber());
        });

        StringBuilder sb = new StringBuilder();
        for (Customer customer : copyList) {
            sb.append(customer.getCustomerInfo()).append("\n");
        }
        return sb.toString().trim()
    }
}