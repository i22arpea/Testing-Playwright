package matricula.unit.mother.model.mat;

import java.math.BigDecimal;

import matricula.unit.mother.model.acad.estudios.TitulacionMother;
import model.acad.estudios.Titulacion;
import model.adm.GruposTipoTitulacion;
import model.crm.tiempo.MesEntity;
import model.dbo.CursoAcademico;
import model.dbo.Sistema;
import model.dbo.usuarios.Usuario;
import model.eco.liquidaciones.Liquidacion;
import model.expe.Expediente;
import model.mat.PmatAlumnoDatosEconomicos;
import model.mat.PmatAlumnoInscripcion;
import model.mat.ProcesoMatricula;
import model.mat.precios.MatriculaPreciosAbstract;
import model.mat.precios.MatriculaPreciosMatricula;
import model.mat.precios.MatriculaPreciosPlantilla;

/** @author amuro */
public class PmatAlumnoInscripcionMother {

  public static PmatAlumnoInscripcion createDeca(
      Expediente expediente, PmatAlumnoDatosEconomicos datosEconomicos, ProcesoMatricula procesoMatricula,
      GruposTipoTitulacion colectivo, Liquidacion liquidacion) {

    Usuario usuario = new Usuario(Usuario.USUARIO_SISTEMA);

    Sistema sistema = new Sistema();
    sistema.setCursoAcademico("2022/23");
    CursoAcademico curso = new CursoAcademico(sistema.getCursoAcademico());
    Titulacion titulacion = TitulacionMother.random();

    BigDecimal importeMatricula = BigDecimal.valueOf(274.95);
    BigDecimal importeReservaAdmision = BigDecimal.valueOf(27.5);
    BigDecimal credito1 = BigDecimal.valueOf(27.5);
    BigDecimal importeReconocimientoExterno = BigDecimal.valueOf(27.5);
    int mensualidades = 2;
    MesEntity mesPrimeraMensualidad = new MesEntity("10");

    MatriculaPreciosAbstract matriculaPreciosAbstract = MatriculaPreciosAbstract.preparePreciosPlantillaDeca(
        importeMatricula, importeReservaAdmision,
        credito1, importeReconocimientoExterno, mensualidades, mesPrimeraMensualidad, usuario);

    MatriculaPreciosMatricula preciosMatricula = MatriculaPreciosMatricula.preparePreciosMatricula(curso, titulacion,
        MatriculaPreciosPlantilla.MODO_PRECIOS_CREDITOS, matriculaPreciosAbstract);

    PmatAlumnoInscripcion inscripcion = PmatAlumnoInscripcion.prepareInscripcionDECA(procesoMatricula, colectivo,
        expediente, preciosMatricula);
    inscripcion.setPmatAlumnoDatosEconomicos(datosEconomicos);
    inscripcion.setLiquidacionFK(liquidacion);
    return inscripcion;
  }

}
