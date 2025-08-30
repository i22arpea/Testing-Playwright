package matricula.integration.addonpayment;

import matricula.ejb.economico.loyolatpv.PagoAutorizadoCheckerService;
import matricula.ejb.economico.loyolatpv.PagoFueraDeConsultaException;
import matricula.ejb.economico.loyolatpv.addonpaymentsapi.ErrorNoDefinidoPagoException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

@SpringBootTest
@TestPropertySource(locations = "classpath:test.properties")
class ShouldCheckPaymentStatusIT {

    @Autowired private PagoAutorizadoCheckerService sut;

    // TODO: habria que obtener un id de un pago autorizado en test
//    @Test
//    void should_be_pago_autorizado() throws PagoFueraDeConsultaException, ErrorNoDefinidoPagoException {
//        String orderId = "13557920250514181638";
//        boolean autorizado = sut.isAutorizado(orderId);
//        assertThat(autorizado).isTrue();
//    }

//    @Test
//    void should_throw_pago_fuera_de_consulta_exception()  {
//        String orderId = "13553920250513172434";
//        Throwable thrown = catchThrowable(() -> sut.isAutorizado(orderId));
//        assertThat(thrown)
//                .isInstanceOf(PagoFueraDeConsultaException.class);
//    }




 /*   @Test
    void should_check_payment_status() throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();

        String orderId = "13553920250513172434";
       // System.out.println("orderId: " + orderId);
        String timestamp = "20250513172434";

       // System.out.println("order timestamp: " + timestamp);
        //String timestamp = AddonUtils.generateTimestamp();
//        String timestamp = LocalDateTime.parse("2025-05-13 17:25:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
//                .format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String merachantId = "Uloyola";
       // System.out.println("merachantId: " + merachantId);
        String account = "internet";
       // System.out.println("account: " + account);

        String sha1Hash = AddonUtils.generateHash(timestamp , merachantId, orderId, "eu2V7OcU4e");
       // System.out.println("HASH Paso 3 SHA-1(hashPaso2.sharedsecret): " + sha1Hash);
        String bodyString = """
                <request type="query" timestamp="%s">
                   <merchantid>%s</merchantid>
                   <account>%s</account>
                   <orderid>%s</orderid>
                   <sha1hash>%s</sha1hash>
                </request>
                """.formatted(timestamp, merachantId, account, orderId, sha1Hash);

        HttpRequest.BodyPublisher body = HttpRequest.BodyPublishers.ofString(bodyString);
        System.out.println(bodyString);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.sandbox.realexpayments.com/epage-remote.cgi"))
                .POST(body)
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
    }*/
}
