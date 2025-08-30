package matricula.e2e.test.utils.reporteExcel;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author aaperez
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TestResult {
    private String testName;
    private String usuario;
    private String status;
    private String message;
    private long duracion;
    private String timestamp;
    private String screenshotPath;

}
