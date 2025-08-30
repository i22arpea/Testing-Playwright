package matricula;

import com.ibm.icu.impl.Assert;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;



@Log
@ActiveProfiles("dev")
@SpringBootTest
class TestSistema {




  @Test
  void test() {
    Assert.assrt(true);
  }



}