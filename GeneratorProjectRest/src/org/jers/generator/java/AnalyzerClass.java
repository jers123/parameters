package org.jers.generator.java;

import org.jers.models.ascenture.Branch;
import org.jers.models.ascenture.Franchise;
import org.jers.models.castor.employee.Cargo;
import org.jers.models.castor.employee.Empleado;
import org.jers.models.nexos.Position;
import org.jers.models.nexos.Product;
import org.jers.models.nexos.User;
import org.jers.models.parameters.*;
import org.jers.models.stephaninitest.PruebaEstudiante;

import java.util.ArrayList;
import java.util.List;

public class AnalyzerClass {

    public static void loadClasses() {
        //List<Class<?>> classes = getParametersPackage();
        //List<Class<?>> classes = getCastorEmployee();
        //List<Class<?>> classes = getStephaniniTest();
        //List<Class<?>> classes = getNexosPackage();
        List<Class<?>> classes = getAccenturePackage();

        List<GenerateEntity> entities = new ArrayList<>();
        List<GenerateRepository> repositories = new ArrayList<>();
        List<GenerateSuperDto> superDtos = new ArrayList<>();
        List<GenerateMapper> mappers = new ArrayList<>();
        List<GenerateService> services = new ArrayList<>();
        List<GenerateController> controllers = new ArrayList<>();

        GenerateEntity entity;
        for(Class<?> clas: classes) {
            entity = new GenerateEntity(clas, classes, entities);
            entities = entity.getEntities();
            entities.add(entity);
            repositories.add(new GenerateRepository(clas, classes));
            superDtos.add(new GenerateSuperDto(clas, classes));
            mappers.add(new GenerateMapper(clas, classes));
            services.add(new GenerateService(clas, classes));
            controllers.add(new GenerateController(clas, classes));
        }
        for (GenerateEntity entityImp : entities) {
            entityImp.generateFile();
        }
        GenerateSystemConstants.generateClass();
        GenerateConstants.generateClass();

        /*String text = "";
        text = text.replace("    ", "\\t");
        System.out.println(text);*/
    }

    private static List<Class<?>> getParametersPackage() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(BaseEntity.class);
        classes.add(Gender.class);
        classes.add(TelephoneType.class);
        classes.add(CivilStatus.class);
        classes.add(Sector.class);
        classes.add(DocumentType.class);
        classes.add(Country.class);
        classes.add(State.class);
        classes.add(CityType.class);
        classes.add(City.class);
        classes.add(CityToCity.class);

        return classes;
    }

    private static List<Class<?>> getCastorEmployee() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(org.jers.models.castor.employee.BaseEntity.class);
        classes.add(Cargo.class);
        classes.add(Empleado.class);

        return classes;
    }

    private static List<Class<?>> getCastorS() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(BaseEntity.class);
        classes.add(Gender.class);

        return classes;
    }

    private static List<Class<?>> getStephaniniTest() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(PruebaEstudiante.class);

        return classes;
    }

    private static List<Class<?>> getNexosPackage() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(org.jers.models.nexos.BaseEntity.class);
        classes.add(Position.class);
        classes.add(User.class);
        classes.add(Product.class);

        return classes;
    }

    private static List<Class<?>> getAccenturePackage() {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(org.jers.models.ascenture.BaseEntity.class);
        classes.add(Franchise.class);
        classes.add(Branch.class);
        classes.add(org.jers.models.ascenture.Product.class);
        return classes;
    }
}