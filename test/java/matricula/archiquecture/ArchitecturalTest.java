package matricula.archiquecture;

import com.enofex.taikai.Taikai;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class ArchitecturalTest {

    private static Taikai.Builder taikaiBuilder = null;

    @BeforeAll
    static void setup() {
//        taikaiBuilder = Taikai.builder()
//                .namespace(ROOT_PACKAGE);
    }

    /*//@Test
    void shouldFulfillConstraints() {
        Taikai.builder()
                .namespace("loyolaMoveOn4")
                .java(java -> java
                        .noUsageOfDeprecatedAPIs()
                        .methodsShouldNotDeclareGenericExceptions()
                        .utilityClassesShouldBeFinalAndHavePrivateConstructor()
                        .imports(imports -> imports
                                .shouldHaveNoCycles()
                                .shouldNotImport("..shaded..")
                                .shouldNotImport("org.junit.."))
                        .naming(naming -> naming
                                .classesShouldNotMatch(".*Impl")
                                .methodsShouldNotMatch("^(foo$|bar$).*")
                                .fieldsShouldNotMatch(".*(List|Set|Map)$")
                                .fieldsShouldMatch("com.enofex.taikai.Matcher", "matcher")
                                .constantsShouldFollowConventions()
                                .interfacesShouldNotHavePrefixI()))
//                .logging(logging -> logging
//                        .loggersShouldFollowConventions(Logger.class, "logger", List.of(PRIVATE, FINAL)))
                .test(test -> test
                        .junit5(junit5 -> junit5
                                .classesShouldNotBeAnnotatedWithDisabled()
                                .methodsShouldNotBeAnnotatedWithDisabled()))
                .spring(spring -> spring
                        .noAutowiredFields()
                        .boot(boot -> boot
                                .springBootApplicationShouldBeIn("loyolaMoveOn4"))
                        .configurations(configuration -> configuration
                                .namesShouldEndWithConfiguration())
                        .controllers(controllers -> controllers
                                .shouldBeAnnotatedWithRestController()
                                .namesShouldEndWithController()
                                .shouldNotDependOnOtherControllers()
                                .shouldBePackagePrivate())
                        .services(services -> services
                                .shouldBeAnnotatedWithService()
                                .shouldNotDependOnControllers()
                                .namesShouldEndWithService())
                        .repositories(repositories -> repositories
                                .shouldBeAnnotatedWithRepository()
                                .shouldNotDependOnServices()
                                .namesShouldEndWithRepository()))
                //.addRule(TaikaiRule.of(...)) // Add custom ArchUnit rule here
      .build()
                .checkAll();
    }*/
    private static final String ROOT_PACKAGE = "matricula";

    @Nested
    class Java {

  //      @Test
        void should_not_use_deprecated_apis() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .java(java -> java
                            .noUsageOfDeprecatedAPIs())
                    .build()
                    .checkAll();
        }

       /*  @Test
        void should_classes_implement_hash_code_and_equals() {
            taikaiBuilder
                    .namespace(ROOT_PACKAGE)
                    .java(java -> java.classesShouldImplementHashCodeAndEquals())
                    .build()
                    .checkAll();
        }

        @Test
        void should_classes_reside_in_package() {
            taikaiBuilder
                    .java(java -> java.classesShouldResideInPackage(ROOT_PACKAGE))
                    .build()
                    .checkAll();
        }

       @Test
        void should_classes_be_annotated_with() {
            Taikai.builder()
                    .namespace("com.company.project")
                    .java(java -> java.classesShouldBeAnnotatedWith(MyAnnotation.class))
                    .build()
                    .check();
        }

        @Test
        void should_classes_have_modifiers() {
            Taikai.builder()
                    .namespace("com.company.project")
                    .java(java -> java.classesShouldHaveModifiers(Modifier.PUBLIC))
                    .build()
                    .check();
        }*/

       /* @Test
        void should_not_imports_have_cycles()   {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .java(java -> java
                            .imports(imports -> imports
                                    .shouldHaveNoCycles()))
                    .build()
                    .checkAll();
        }*/

    //    @Test
        void should_fields_not_be_public() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .java(java -> java.fieldsShouldNotBePublic())
                    .build()
                    .checkAll();
        }

        @Test
        void should_not_use_system_out_print() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .java(java -> java
                            .noUsageOfSystemOutOrErr())
                    .build()
                    .checkAll();
        }

       /* @Test
        void ensure_clasess_use_specified_logger() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .logging(logging -> logging
                            .classesShouldUseLogger(org.slf4j.Logger.class, ".*Service")
                            .classesShouldUseLogger("org.slf4j.Logger", ".*Service"))

                    .build()
                    .check();
        }*/
    }

    @Nested
    class Spring {

        @Test
        void should_applitaction_in_the_defaut_package() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .spring(spring -> spring
                            .boot(boot -> boot
                                    .springBootApplicationShouldBeIn(ROOT_PACKAGE)))
                    .build()
                    .checkAll();

        }

        @Test
        void should_configuration_naming() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .spring(spring -> spring
                            .configurations(configuration -> configuration
                                    .namesShouldMatch(".+(Config|Configuration)")))
                    .build()
                    .checkAll();

        }

        @Test
        void should_controllers_fulfill() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .spring(spring -> spring
                            .controllers(controllers -> controllers
                                    .shouldBeAnnotatedWithRestController()
                                    //.namesShouldEndWithController()
                                    //.shouldBeAnnotatedWithValidated()
                                    .namesShouldMatch(".+(Controller|EndPoint)")
                                    .shouldNotDependOnOtherControllers()))
                    //.shouldBePackagePrivate()))
                    .build()
                    .checkAll();

        }
        @Test
        void should_services_fulfill() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .spring(spring -> spring
                            .services(services -> services
                                    .shouldBeAnnotatedWithService()
                                    .shouldNotDependOnControllers()
                                    //.namesShouldEndWithService()
                            )).excludeClasses("loyolaMoveOn4.api.ws.service.OutogingService","loyolaMoveOn4.api.ws.service.PersonalService")
                    .build()
                    .checkAll();
        }

        @Test
        void should_repositories_fulfill() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .spring(spring -> spring
                            .repositories(repositories -> repositories
                                   // .shouldBeAnnotatedWithRepository()
                                    .shouldNotDependOnServices()
                                    .namesShouldEndWithRepository()))
                    .build()
                    .checkAll();
        }



       // @Test
        void should_not_there_be_autowired_in_fields() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .spring(spring -> spring
                            .noAutowiredFields()).excludeClasses("loyolaMoveOn4.api.ws.config.CXFConfig")
                    .build()
                    .checkAll();
        }

      /*  @Test
        void should_spring_service_annotation_present() {
            Taikai.builder()
                    .namespace("com.company.project")
                    .spring(spring -> spring.classesShouldBeAnnotatedWith(Service.class))
                    .build()
                    .check();
        }

        @Test
        void should_spring_component_annotation_present() {
            Taikai.builder()
                    .namespace("com.company.project")
                    .spring(spring -> spring.classesShouldBeAnnotatedWith(Component.class))
                    .build()
                    .check();
        }

        @Test
        void should_spring_repository_annotation_present() {
            Taikai.builder()
                    .namespace("com.company.project")
                    .spring(spring -> spring.classesShouldBeAnnotatedWith(Repository.class))
                    .build()
                    .check();
        }

        @Test
        void should_spring_controller_annotation_present() {
            Taikai.builder()
                    .namespace("com.company.project")
                    .spring(spring -> spring.classesShouldBeAnnotatedWith(Controller.class))
                    .build()
                    .check();
        }*/
    }

    @Nested
    class Testing {

        @Test
        void should_not_be_disabled_tests() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .test(test -> test
                            .junit5(junit5 -> junit5
                                    .classesShouldNotBeAnnotatedWithDisabled()
                                    .methodsShouldNotBeAnnotatedWithDisabled()))
                    .build()
                    .checkAll();
        }

        @Test
        void should_not_be_public_tests() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .test(test -> test
                            .junit5(junit5 -> junit5
                                    .methodsShouldBePackagePrivate()))
                    .build()
                    .checkAll();
        }

        //@Test
        void should_have_verification() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .test(test -> test
                            .junit5(junit5 -> junit5
                                    .methodsShouldContainAssertionsOrVerifications()))
                    .build()
                    .checkAll();
        }

        @Test
        void should_there_not_be_javax_transaction_imports() {
            Taikai.builder()
                    .namespace(ROOT_PACKAGE)
                    .java(java -> java
                            .imports(imports -> imports
                                  .shouldNotImport("javax.transaction.Transactional")))
                    .build()
                    .checkAll();
        }




    }

}
