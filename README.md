# 📘 Navegación Matricula - Testing Automático con Playwright Java

![Logo Playwright](https://playwright.dev/img/playwright-logo.svg)

****

## ⚙️ Archivo de Propiedades

El proyecto utiliza un archivo de propiedades `playwright.properties` para configurar los parámetros del entorno de prueba:

````properties
# Configuración del servidor
playwright.servidor.ip=10.20.44.18
server.port = 8070
server.tpv.port = 8080

# URLs de entorno
playwright.urlLogin=http://${playwright.servidor.ip}:${server.port}/Matricula/
playwright.urlHome=http://${playwright.servidor.ip}:${server.port}/Matricula/secure/home.jsf
playwright.patternUrlHostedPayment=https://hpp.sandbox.addonpayments.com/hosted-payments/blue/card.html.*$
playwright.urlProcesosNo=http://${playwright.servidor.ip}:${server.port}/Matricula/secure/admision/listadoprocesos.jsf#no-back-button
playwright.urlProcesos=http://${playwright.servidor.ip}:${server.port}/Matricula/secure/admision/listadoprocesos.jsf
playwright.urlLoyolatpv =http://${playwright.servidor.ip}:${server.tpv.port}/LoyolaTPV/recepcionInfoPago.jsp#no-back-button
playwright.urlTfPago=http://${playwright.servidor.ip}:${server.port}/Matricula/secure/matricula/tfg/matricula_tf.xhtml

# Numero de tests a ejecutar
playwright.numero_alumnos=1
playwright.usuarios_en_paralelo=1

# Configuración de navegador
playwright.navegador=chromium
playwright.headless=false
playwright.slowmo=0
playwright.video=false
playwright.depuracion=false
playwright.timeout=60000
playwright.resetearUsuario=true
playwright.dispositivo=escritorio
# (tablet, movil_pequeno, movil_moderno, escritorio)


# Datos de matriculación
playwright.cursoAcad=2025/26
playwright.proceso_matricula=5622
playwright.proceso_admision=2743
playwright.titulacion=1179

# Servicios REST

# ===============================
# = Outgoing

playwright.restUsuariosOutgoing =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesrenovacion/colectivo?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=G&soloTF=0&colectivo=14
playwright.restUsuariosOutgoingProximoCurso=http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesoutgoing/proximocurso?cursoAcad=${playwright.cursoAcad}

# ===============================
# = DECA

playwright.restUsuariosDECA =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesrenovacion?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=3&soloTF=0

# ===============================
# = TFG

playwright.restUsuariosTFG =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesTF/tfg?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=G&soloTF=1
playwright.restUsuariosTFGMod =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesTF/tfgMod?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=G&soloTF=1

# ===============================
# = Nuevo Ingreso

playwright.restUsuariosNuevoIngreso =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantescandidato?idProceso=${playwright.proceso_admision}
playwright.restUsuariosNuevoIngresoMat =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantescandidato/Mat?idProceso=${playwright.proceso_matricula}
playwright.restUsuariosNuevoIngresoTitulacion =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantescandidato/Titulacion?idProceso=${playwright.proceso_matricula}&titulacion=${playwright.titulacion}

# ===============================
# = TFM

playwright.restUsuariosTFM =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesTF/tfm?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=M&soloTF=1
playwright.restUsuariosTFMMod =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesTF/tfmMod?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=M&soloTF=1

# ===============================
# = Piia

playwright.restUsuariosPiia =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantespiia/colectivo?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=F&soloTF=0&colectivo=40

# ===============================
# = Renovacion

playwright.restUsuariosRenovacion =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesrenovacion/colectivo?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=G&soloTF=0&colectivo=13
playwright.restUsuariosRenovacionMA =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesrenovacion/masasig?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=G&soloTF=0&colectivo=13
playwright.restUsuariosRenovacionAPA =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesrenovacion/asigrestacept?asigSuperada=14778&cursoAcad=${playwright.cursoAcad}&asigMat=14787
playwright.restUsuariosRenovacionAPE =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesrenovacion/asigresterror?asigSuperada=8937&cursoAcad=${playwright.cursoAcad}&asigMat=8945


# ===============================
# = NI Master

playwright.restUsuariosNuevoIngresoMaster =http://${playwright.servidor.ip}:${server.port}/Matricula/futurosestudiantesrenovacion/colectivo?cursoAcad=${playwright.cursoAcad}&tipoTitulacion=M&soloTF=0&colectivo=4

# ===============================
# = Reset

playwright.eliminarFuturosEstudiantes =http://${playwright.servidor.ip}:${server.port}/Matricula/eliminarinsfuturosestudiantes?alumnoId=%d&idins=%d&cursoAcad=%s
playwright.resetFuturosEstudiantes =http://${playwright.servidor.ip}:${server.port}/Matricula/resetfuturosestudiantes?alumnoId=%d&idins=%d&cursoAcad=%s
playwright.resetAdmisiones =http://${playwright.servidor.ip}:${server.port}/Matricula/resetfuturosestudiantescandidato?candidatoId=%d&procesoAdmisionId=%d&titulacionCursoId=%d
playwright.resetTF =http://${playwright.servidor.ip}:${server.port}/Matricula/resetfuturosestudiantestf?alumnoId=%d&idins=%d&cursoAcad=%s&tipoTitulacion=%c
````

## 📂 Pages (Páginas del flujo de matrícula)

### 🔐 Login

- [IndexPage.java](test/java/matricula/e2e/page/IndexPage.java)

### 🏠 Home y Procesos

- [ProcesosPage.java](test/java/matricula/e2e/page/secure/ProcesosPage.java)
- [HomePage.java]test/java/matricula/e2e/(page/secure/HomePage.java)


### 📝 Flujo de Matrícula

#### 🧍 Datos Personales
- [DatosPersonalesPage.java](test/java/matricula/e2e/page/secure/matricula/proceso/pestana/DatosPersonalesPage.java)
  - `rellenarPrimeraSeccion()` – Rellena la primera sección de datos personales.
  - `rellenarSegundaSeccion()` – Rellena la segunda sección de datos personales.
  - `rellearSegundaSeccionPiia()` – Rellena la segunda sección de datos personales para PIIA.
  - `rellenarTerceraSeccion()` – Rellena la tercera sección de datos personales.
  - `rellenarCuartaSeccion()` – Rellena la cuarta sección de datos personales.

#### 📊 Datos Estadísticos
- [DatosEstadisticosPage.java](test/java/matricula/e2e/page/secure/matricula/proceso/pestana/DatosEstadisticosPage.java)
  - `rellenarDatosEstadisticos()` - Rellenar los datos estadísticos del usuario.


#### 🔐 RGPD y Académicos
- [DatosRGPDPage.java](test/java/matricula/e2e/page/secure/matricula/proceso/pestana/DatosRGPDPage.java)
- [DatosAcademicosPage.java](test/java/matricula/e2e/page/secure/matricula/proceso/pestana/DatosAcademicosPage.java)
  - `rellenarAsignaturas()` – Rellena las asignaturas del usuario.
  - `rellenarMasAsignaturas()` – Rellena mas asignaturas de las que puede el usuario.
  - `rellenarAsigyGrupos()` – Rellena menos asignaturas y grupos del usuario.
  - `rellenarAsignaturasMarcadas()` – Rellena los grupos de las asignaturas del usuario.
  - `rellenarAsignaturaRestriccion(String asignatura)` – Rellena una asignatura con restricciones.
  - `aceptacionSolapes()` – Verifica la aceptación de solapes de asignaturas.

#### 💰 Económicos y Confirmación
- [DatosEconomicosPage.java](test/java/matricula/e2e/page/secure/matricula/proceso/pestana/DatosEconomicosPage.java)
  - `rellenarPrimeraSeccion()` – Rellena la primera sección de datos económicos.
  - `rellenarSegundaSeccion()` – Rellena la segunda sección de datos económicos.
- [DatosConfirmacionPage.java](page/secure/matricula/proceso/pestana/DatosConfirmacionPage.java)
  - `rellenarClausulas()` – Rellena las cláusulas de confirmación.

#### 💳 Pago y Finalización
- [RecepcionInfoPagoPage.java](test/java/matricula/e2e/page/ext/tpv/RecepcionInfoPagoPage.java)
- [FormulatioTPVPage.java](test/java/matricula/e2e/page/secure/matricula/proceso/pago/FormularioTPVPage.java)
  - `rellenarDatosInfoPago()` – Rellena los datos de pago en el formulario TPV.
- [CardPage.java](test/java/matricula/e2e/page/ext/hosted_payment/CardPage.java)
  - `rellenarDatosCard()` – Rellena los datos de la tarjeta en la página de pago.
  - `rellenarDatosCardFalsa()` – Rellena los datos de la tarjeta con un error intencionado.
- [DatosMatriculaFinalizadaPage.java](page/secure/matricula/proceso/pestana/DatosMatriculaFinalizadaPage.java)
- [DatosTFEPage.java](page/secure/matricula/proceso/pestana/DatosTFEPage.java)

****

## ♿ Accesibilidad

- [axe.min.js](test/java/matricula/e2e/resources/axe.min.js)
- [AccessibilityExcelExporter.java](test/java/matricula/e2e/test/utils/accesibilidad/AccessibilityExcelExporter.java) – Extractor de problemas de accesibilidad a Excel.
- [HighlightAccessibilityIssues.java](test/java/matricula/e2e/test/utils/accesibilidad/HighlightAccessibilityIssues.java) - Resalta los problemas de accesibilidad en la página.

## 📈 Reportes y Resultados

- [ExtraccionExcel.java](test/java/matricula/e2e/test/utils/reporteExcel/ExtraccionExcel.java) - Extracción de datos de pruebas a un archivo Excel.
- [TestResult.java](test/java/matricula/e2e/test/utils/reporteExcel/TestResult.java) - Clase que representa un resultado de prueba.

📁 Carpetas:
- [`reports/accesibilidad`](test/java/matricula/e2e/reports/accesibilidad)
- [`reports/results`](test/java/matricula/e2e/reports/results)
- [`reports/videos`](test/java/matricula/e2e/reports/videos)

****

## 🌐 Servicios REST (Tests)

- [ObtenerUsuarios.java](test/java/matricula/e2e/test/utils/servicioRest/ObtenerUsuarios.java)
  - `obtenerFuturosEstudiantes()` – Obtiene usuarios de futuros estudiantes.
  - `obtenerFuturosEstudiantesAsignatura()` – Obtiene usuarios con asignatura restricción.
  - `obtenerUsuariosAdmisiones()` – Obtiene usuarios de admisiones.
- [ResetearUsuarios.java](test/java/matricula/e2e/test/utils/servicioRest/ResetearUsuarios.java)
  - `eliminarUsuariosRenovacion()` – Elimina los usuarios de renovación.
  - `resetearUsuariosFE()` – Resetea los usuarios de futuros estudiantes.
  - `resetearUsuariosAdmisiones()` – Resetea los usuarios de admisiones.
  - `resetearUsuariosTF()` – Resetea los usuarios de TFG y TFM.
- [TestProperties.java](test/java/matricula/e2e/test/utils/servicioRest/TestProperties.java)

****

## 🌐 Procedures

- [ResetFEAdmisiones.sql](procedures/ResetFEAdmisiones.sql)
- [ResetFEDECA.sql](procedures/ResetFEDECA.sql)
- [ResetFERenovacion.sql](procedures/ResetFERenovacion.sql)
- [ResetFETFe.sql](procedures/ResetFETFe.sql)

****

## ✅ Tests Implementados

### 🔐 Login
- [LoginTest.java](test/java/matricula/e2e/test/LoginTest.java)
  - `loginExito()` – Test de inicio de sesión con usuario y contraseña.
  - `loginFalloIdentificador()` – Test de inicio de sesión con error de usuario o contraseña.
  - `loginFalloPassword()` – Test de inicio de sesión con error de contraseña.
  - `loginIdentificadorVacio()` – Test de inicio de sesión con error de captcha.

### 🎓 Inscripción por tipo de usuario
- [ProcesoInscripcionNuevoIngresoTest.java](test/java/matricula/e2e/test/ProcesoInscripcionNuevoIngresoTest.java)
  - `procesoInscripcionCompletoTest()` – Proceso de inscripción completo para nuevo ingreso.
- [ProcesoInscripcionNuevoIngresoMasterTest.java](test/java/matricula/e2e/test/ProcesoInscripcionNuevoIngresoMasterTest.java)
  - `procesoInscripcionCompletoTest()` – Proceso de inscripción completo para nuevo ingreso con máster.
- [ProcesoInscripcionDECATest.java](test/java/matricula/e2e/test/ProcesoInscripcionDECATest.java)
  - `procesoInscripcionCompletoTestPagoUnico()` – Proceso de inscripción completo para usuarios DECA.
  - `procesoInscripcionCompletoTestPagoDoble()` – Proceso de inscripción completo para usuarios DECA con pago doble.
- [ProcesoInscripcionOutgoingTest.java](test/java/matricula/e2e/test/ProcesoInscripcionOutgoingTest.java)
  - `procesoInscripcionCompletoTest()` – Proceso de inscripción completo para usuarios Outgoing.
- [ProcesoInscripcionPiiaTest.java](test/java/matricula/e2e/test/ProcesoInscripcionPiiaTest.java)
  - `procesoInscripcionCompletoTest()` – Proceso de inscripción completo para usuarios PIIA.
- [ProcesoInscripcionRenovacionTest.java](test/java/matricula/e2e/test/ProcesoInscripcionRenovacionTest.java)
  - `procesoInscripcionCompletoTest()` – Proceso de inscripción completo para usuarios en renovación.
  - `procesoMasAsignaturasTest()` – Proceso de verificación de inscripción con más asignaturas para usuarios en renovación.
  - `procesoMenosAsignaturasTest()` – Proceso de verificación de inscripción con menos asignaturas para usuarios en renovación.
  - `procesoSolapesAsignaturasTest()` – Proceso de verificación de solapes de asignaturas para usuarios en renovación.
  - `procesoPagoErroneoTest()` – Proceso de inscripción completo para usuarios en renovación con error de pago.
  - `procesoRequisitoAsignaturaTestAceptacion()` – Proceso de verificación de requisitos de asignaturas para usuarios en renovación con aceptación.
  - `procesoRequisitoAsignaturaTestError()` – Proceso de verificación de requisitos de asignaturas para usuarios en renovación con error.
  - `procesoModificacionMatriculaTest()` – Proceso de modificación de matrícula para usuarios en renovación.
  - `procesoCoincidenciaExamenTest()` – Test para verificar el error a la hora de coincidir 2 asignaturas en la fecha de examen.
- [ProcesoInscripcionSoloTFGTest.java](test/java/matricula/e2e/test/ProcesoInscripcionSoloTFGTest.java)
  - `procesoInscripcionCompletoTestSinMatricula()` – Proceso de inscripción completo para usuarios que solo matriculan TFG.
  - `procesoInscripcionCompletoTestConMatricula()` – Proceso de inscripción completo para usuarios que matriculan TFG teniendo una matricula anterior.
- [ProcesoInscripcionSoloTFMTest.java](test/java/matricula/e2e/test/ProcesoInscripcionSoloTFMTest.java)
  - `procesoInscripcionCompletoTestSinMatricula()` – Proceso de inscripción completo para usuarios que solo matriculan TFM.
  - `procesoInscripcionCompletoTestConMatricula()` – Proceso de inscripción completo para usuarios que matriculan TFM teniendo una matricula anterior.

### 📱 Usabilidad
- [ProcesoInscripcionUsabilidadTest.java](test/ProcesoInscripcionUsabilidadTest.java)
  - `procesoInscripcionMovilModernoTest()` – Proceso de inscripción completo para usuarios con pruebas de usabilidad.
  - `procesoInscripcionMovilPequenoTest()` – Proceso de inscripción completo para usuarios con pruebas de usabilidad en móvil antiguo.
  - `procesoInscripcionTabletTest()` – Proceso de inscripción completo para usuarios con pruebas de usabilidad en tablet.
  - `procesoInscripcionEscritorioGrandeTest()` – Proceso de inscripción completo para usuarios con pruebas de usabilidad en pantalla 2560x1440.

****

## 🛰️ Servicios REST (Back-end)

- [EliminarInscripcionFuturosEstudiantes.java](main/java/matricula/rest/futurosestudiantes/EliminarInscripcionFuturosEstudiantes.java)
  - Extensión: `/eliminarinsfuturosestudiantes`
- [ObtenerFuturosEstudiantesCandidatoController.java](main/java/matricula/rest/futurosestudiantes/ObtenerFuturosEstudiantesCandidatoController.java) 
  - Extensión: `/futurosestudiantescandidato`
- [ObtenerFuturosEstudiantesOutgoing.java](main/java/matricula/rest/futurosestudiantes/ObtenerFuturosEstudiantesOutgoing.java)
  - Extensión: `/futurosestudiantesoutgoing/proximocurso`
- [ObtenerFuturosEstudiantesPiia.java](main/java/matricula/rest/futurosestudiantes/ObtenerFuturosEstudiantesPiia.java)
  - Extensión: `/futurosestudiantespiia` --> `/futurosestudiantespiia/colectivo`
- [ObtenerFuturosEstudiantesRenovacionController.java](main/java/matricula/rest/futurosestudiantes/ObtenerFuturosEstudiantesRenovacionController.java)
  - Extensión: `/futurosestudiantesrenovacion`
  - Extensión colectivo: `/futurosestudiantesrenovacion/colectivo`
  - Extensión más asignaturas: `/futurosestudiantesrenovacion/masasig`
  - Extensión restriccion asignatura : `/futurosestudiantesrenovacion/asigrestacept`
  - Extensión restricción no asignatura: `/futurosestudiantesrenovacion/asigresterror`
- [ObtenerFuturosEstudiantesTF.java](main/java/matricula/rest/futurosestudiantes/ObtenerFuturosEstudiantesTF.java)
  - Extensión TFG sin matricula: `/futurosestudiantesTF/tfg`
  - Extensión TFG con matricula: `/futurosestudiantesTF/tfgMod`
  - Extensión TFM sin matricula: `/futurosestudiantesTF/tfm`
  - Extensión TFM con matricula: `/futurosestudiantesTF/tfmMod`
- [ResetearFuturosEstudiantes.java](main/java/matricula/rest/futurosestudiantes/ResetearFuturosEstudiantes.java)
  - Extensión: `/resetfuturosestudiantes`
- [ResetearFuturosEstudiantesCandidatoController.java](main/java/matricula/rest/futurosestudiantes/ResetearFuturosEstudiantesCandidatoController.java)
  - Extensión: `/resetfuturosestudiantescandidato`
- [ResetearFuturosEstudiantesTF.java](main/java/matricula/rest/futurosestudiantes/ResetearFuturosEstudiantesTF.java)
  - Extensión: `/resetfuturosestudiantestf`

****

## ☕ Beans y DTOs

- [FuturosEstudiantesBean.java](main/java/matricula/ejb/matricula/FuturosEstudiantesBean.java) - Este EJB contiene la lógica de negocio relacionada con los futuros estudiantes.
- [FuturosEstudiantesOutgoing.java](main/java/matricula/ejb/matricula/dto/FuturosEstudiantesOutgoing.java) - Este DTO representa a un futuro estudiante de tipo Outgoing para proximo curso.
- [FuturosEstudiantesAdmisiones.java](main/java/matricula/ejb/matricula/dto/FuturosEstudiantesAdmisiones.java) - Este DTO representa a un futuro estudiante de admisiones.
- [FuturosEstudiantesAsignatura.java](main/java/matricula/ejb/matricula/dto/FuturosEstudiantesAsignatura.java) - Este DTO representa a un futuro estudiante con una asignatura de restricción.
- [FuturosEstudiantes.java](main/java/matricula/ejb/matricula/dto/FuturosEstudiantes.java) - Este DTO representa a un futuro estudiante.

****

## 🧩 Facades

- [FuturosEstudiantesAdmisionesFacade.java](main/java/matricula/dao/mat/FuturosEstudiantesAdmisionesFacade.java)
- [FuturosEstudiantesOutgoingFacade.java](main/java/matricula/dao/mat/FuturosEstudiantesOutgoingFacade.java)
- [FuturosEstudiantesPiiaFacade.java](main/java/matricula/dao/mat/FuturosEstudiantesPiiaFacade.java)
- [FuturosEstudiantesRenovacionFacade.java](main/java/matricula/dao/mat/FuturosEstudiantesRenovacionFacade.java)
- [FuturosEstudiantesTFFacade.java](main/java/matricula/dao/mat/FuturosEstudiantesTFFacade.java)



