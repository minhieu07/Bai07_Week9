package bank_system;

import org.junit.jupiter.api.Test;
import java.io.File;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PathTest {

    @Test
    public void testFilePathCompatibility_Refactored() {
        // Refactor: Dùng File.separator để tự động nhận diện dấu gạch chéo
        // chuẩn của từng hệ điều hành (\ cho Windows, / cho Linux/Mac)
        String folder = "logs";
        String fileName = "transaction.log";

        String expectedPath = folder + File.separator + fileName;
        String actualPath = new File(folder, fileName).getPath();

        assertEquals(expectedPath, actualPath,
                "Đường dẫn file đã tương thích đa nền tảng!");
    }
}