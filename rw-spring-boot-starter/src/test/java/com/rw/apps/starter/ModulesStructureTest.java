package com.rw.apps.starter;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.library.dependencies.SlicesRuleDefinition;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class ModulesStructureTest {
    static final String BASE_PACKAGE_NAME = ModulesStructureTest.class.getPackageName();

    @TestFactory
    Stream<DynamicTest> verifyModularStructure() {
        JavaClasses classes = new ClassFileImporter().importPackages(BASE_PACKAGE_NAME);

        Set<String> packages = classes.stream()
                                      .map(JavaClass::getPackageName)
                                      .filter(pkg -> pkg.startsWith(BASE_PACKAGE_NAME))
                                      .collect(Collectors.toSet());

        Set<String> allPackages = new HashSet<>();
        for (String pkg : packages) {
            String[] parts = pkg.split("\\.");
            for (int i = BASE_PACKAGE_NAME.split("\\.").length + 1; i <= parts.length; i++) {
                allPackages.add(String.join(".", Arrays.copyOfRange(parts, 0, i)));
            }
        }

        return allPackages.stream()
                          .sorted()
                          .map(ModulesStructureTest::createTest);
    }

    private static DynamicTest createTest(String pkg) {
        if (pkg.equals(BASE_PACKAGE_NAME + ".common.validation")) {
            return DynamicTest.dynamicTest("Common Validation package", () -> {
                // it's ok
            });
        }
        return DynamicTest.dynamicTest(
                "No internal cycles in: " + pkg,
                () -> {
                    JavaClasses pkgClasses = new ClassFileImporter().importPackages(pkg);
                    SlicesRuleDefinition.slices()
                                        .matching(pkg + ".(*)..")
                                        .should().beFreeOfCycles()
                                        .allowEmptyShould(true)
                                        .check(pkgClasses);
                }
        );
    }
}
