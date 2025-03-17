package org.jers.generateapirest;

import org.jers.generateapirest.dependency.Dependency;
import org.jers.generateapirest.dependency.DependencyData;
import org.jers.generateapirest.dependency.ScopeGradle;
import org.jers.generateapirest.dependency.ScopeMaven;
import org.jers.generateapirest.dependency.gradle.DependencyGradle;
import org.jers.generateapirest.dependency.maven.DependencyMaven;
import org.jers.generateapirest.ui.UserInterface;

public class GenerateApiRest {

    public static void main(String[] args) {
        UserInterface window = new UserInterface();
        DependencyData data = new DependencyData("org.springframework.boot", "spring-boot-starter-data-jpa", "3.4.2", ScopeMaven.COMPILE, ScopeGradle.IMPLEMENTATION);
        Dependency dependency = new DependencyMaven(data);
        print(dependency);
        dependency = new DependencyGradle(data);
        print(dependency);
    }
    
    private static void print(Dependency dependency) {
        System.out.println(dependency.getFullDependency());
        System.out.println();
        System.out.println(dependency.getDependencyWithoutScope());
        System.out.println();
        System.out.println(dependency.getDependencyWithoutVersion());
        System.out.println();
        System.out.println(dependency.getSpringDependency());
        System.out.println("============================================================================================");
    }
}