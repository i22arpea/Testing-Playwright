package matricula.e2e.test.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.test.context.TestPropertySource;


/**
 *
 * @author aaperez
 */
@Component
@TestPropertySource(locations = "classpath:playwright.properties")
@ConfigurationProperties(prefix = "playwright")
@Data
public class TestProperties {

    private String urlLogin;
    private String urlHome;
    private String urlLoyolatpv;
    private String urlProcesosNo;
    private String urlProcesos;
    private String patternUrlHostedPayment;
    private String urlTfPago;
    private int numero_alumnos;
    private int usuarios_en_paralelo;

    private String navegador;
    private boolean headless;
    private int slowmo;
    private boolean video;
    private boolean depuracion;
    private int timeout;
    private boolean resetearUsuario;

    private String dispositivo;

    private String restUsuariosOutgoing;
    private String restUsuariosOutgoingProximoCurso;
    private String restUsuariosNuevoIngreso;
    private String restUsuariosNuevoIngresoMat;
    private String restUsuariosNuevoIngresoTitulacion;
    private String restUsuariosDECA;
    private String restUsuariosTFG;
    private String restUsuariosTFGMod;
    private String restUsuariosTFM;
    private String restUsuariosTFMMod;
    private String restUsuariosPiia;
    private String restUsuariosNuevoIngresoMaster;
    private String restUsuariosRenovacion;
    private String restUsuariosRenovacionMA;
    private String restUsuariosRenovacionAPA;
    private String restUsuariosRenovacionAPE;

    private String eliminarFuturosEstudiantes;
    private String resetFuturosEstudiantes;
    private String resetAdmisiones;
    private String resetTF;


}
