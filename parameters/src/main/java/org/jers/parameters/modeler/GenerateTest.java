package org.jers.parameters.modeler;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.jers.parameters.utils.Constants;
import org.jers.parameters.utils.SystemConstants;
import org.jers.parameters.utils.response.ReplyMessage;


import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GenerateTest {
    public static void main(String[] args) throws IllegalAccessException {
        List<Class<?>> classes = new ArrayList<>();
        classes.add(Constants.class);
        classes.add(SystemConstants.class);
        classes.add(ReplyMessage.class);


        int modifier;
        String test = "";
        for(Class<?> cls : classes) {
            modifier = cls.getModifiers();
            Annotation[] annotations = cls.getAnnotations();
            boolean annotationExist = false;
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().getSimpleName().equals("Entity")) {
                    annotationExist = true;
                    break;
                }
            }
            if(Modifier.isFinal(modifier) && !cls.isEnum()) {
                test = getTestConstants(cls);
            } else if(cls.isEnum()) {
                test = getTestEnum(cls);
            } else if(annotationExist) {
                //test = getTestEntity(cls, ActiveRepository.class);
            } else {
                test = getTestDTO(cls);
            }
            System.out.println(test);
        }
    }

    private static String getTestConstants(Class<?> cls) throws IllegalAccessException {
        String test = "===========================================================================================================\n" +
                "                          " + cls.getSimpleName().toUpperCase() + "\n" +
                "===========================================================================================================\n" +
                "package " + cls.getPackage() + ";\n\n" +
                "import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;\n" +
                "import org.junit.jupiter.api.Order;\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.junit.jupiter.api.TestMethodOrder;\n\n" +
                "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                "import static org.junit.jupiter.api.Assertions.assertFalse;\n" +
                "import static org.junit.jupiter.api.Assertions.assertNotNull;\n\n" +
                "@TestMethodOrder(OrderAnnotation.class)\n" +
                "class " + cls.getSimpleName() + "Test {\n";
        Field[] fields = cls.getDeclaredFields();
        String assertNotNull = "    @Test\n    @Order(1)\n    void testNotNullConstants() {\n";
        String notEmpty = "    @Test\n    @Order(2)\n    void testNotEmptyConstants() {\n";
        String assertEquals = "    @Test\n    @Order(3)\n    void testEquealsConstants() {\n";
        String variable;
        for(Field field: fields) {
            assertNotNull = assertNotNull + "        assertNotNull(" + cls.getSimpleName() + "." + field.getName() + ");\n";
            assertEquals = assertEquals + "        assertEquals(";
            field.setAccessible(true);
            if(field.getType().getSimpleName().equals("String")) {
                notEmpty = notEmpty + "        assertFalse(" + cls.getSimpleName() + "." + field.getName() + ".isEmpty());\n";
                variable = "\"" + field.get(null) + "\"";
            } else {
                variable = field.get(null).toString();
            }
            assertEquals = assertEquals + variable + ", " + cls.getSimpleName() + "." + field.getName() + ");\n";
        }
        assertNotNull = assertNotNull + "    }";
        notEmpty = notEmpty + "    }";
        assertEquals = assertEquals + "    }";
        test = test + assertNotNull + "\n\n" + notEmpty + "\n\n" + assertEquals + "\n}";
        return test;
    }

    private static String getTestDTO(Class<?> cls) {
        String[] extendsClass = getExtends(cls, cls);
        String[] annotations = getDTOAnnotations(cls);
        String test = "===========================================================================================================\n" +
                "                          " + cls.getSimpleName().toUpperCase() + "\n" +
                "===========================================================================================================\n" +
                "package " + cls.getPackage() + ";\n\n" +
                annotations[0] +
                "class "+ cls.getSimpleName() + "Test" + extendsClass[0] + " {\n\n" +
                annotations[1] +
                annotations[2] +
                "    @Test\n";
        if(!annotations[1].equals("")) {
            test = test + "    @Order(1)\n";
        }
        test = test + "    void testSettersAndGetters() {\n";
        String className = cls.getSimpleName();
        className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        if(annotations[1].equals("")) {
            test = test + "        " + cls.getSimpleName() + " " + className + " = new " + cls.getSimpleName() + "();\n";
        }
        getDTOAnnotations(cls);
        for(int i = 1; i < extendsClass.length; i++) {
            test = test + extendsClass[i] + "\n";
        }
        test = test + "    }\n";
        for(int i = 3; i < annotations.length; i++) {
            test = test + annotations[i];
        }
        test = test + "}";
        return test;
    }

    private static String[] getDTOAnnotations(Class<?> cls) {
        String[] getAnnotations = new String[6];
        Arrays.fill(getAnnotations, "");
        Field[] fields = cls.getDeclaredFields();
        String className = cls.getSimpleName();
        className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        int cantAnnotations = 0;
        String fieldName;
        int order = 3;
        for(Field field: fields) {
            Annotation[] annotations = field.getDeclaredAnnotations();
            fieldName = field.getName();
            fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            for(Annotation annotation : annotations) {
                if(annotation instanceof NotNull) {
                    cantAnnotations++;
                    getAnnotations[4] = getAnnotations[4] + "    @Test\n" +
                            "    @Order(" + order + ")\n" +
                            "    void test" + fieldName + "Null() {\n" +
                            "        " + className + ".set" + fieldName + "(null);\n" +
                            "        Set<ConstraintViolation<" + cls.getSimpleName() + ">> violations = validator.validate(" + className + ");\n" +
                            "        assertFalse(violations.isEmpty(), errorValidation);\n\n" +
                            "        String message = \"\";\n" +
                            "        for (ConstraintViolation<" + cls.getSimpleName() + "> violation : violations) {\n" +
                            "            message = violation.getMessage();\n" +
                            "            break;\n" +
                            "        }\n" +
                            "        assertEquals(ACTIVE_SERIAL_NOT_NULL, message, errorMessage);\n" +
                            "    }\n\n";
                    order++;
                }
                if(annotation instanceof Size) {
                    cantAnnotations++;
                    getAnnotations[4] = getAnnotations[4] + "    @Test\n" +
                            "    @Order(" + order + ")\n" +
                            "    void test" + fieldName + "Length() {\n" +
                            "        " + className + ".set" + fieldName + "(" + getValues(field, null) + ");\n" +
                            "        Set<ConstraintViolation<" + cls.getSimpleName() + ">> violations = validator.validate(" + className + ");\n" +
                            "        assertFalse(violations.isEmpty(), errorValidation);\n\n" +
                            "        String message = \"\";\n" +
                            "        for (ConstraintViolation<" + cls.getSimpleName() + "> violation : violations) {\n" +
                            "            message = violation.getMessage();\n" +
                            "            break;\n" +
                            "        }\n" +
                            "        assertTrue(message.contains(ACTIVE_SERIAL_SIZE), errorMessage);\n" +
                            "    }\n\n";
                    order++;
                }
            }
        }
        if (cantAnnotations > 0) {
            getAnnotations[0] = "import jakarta.validation.ConstraintViolation;\n" +
                    "import jakarta.validation.Validation;\n" +
                    "import jakarta.validation.Validator;\n" +
                    "import org.junit.jupiter.api.AfterEach;\n" +
                    "import org.junit.jupiter.api.BeforeEach;\n" +
                    "import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;\n" +
                    "import org.junit.jupiter.api.Order;\n" +
                    "import org.junit.jupiter.api.TestMethodOrder;\n" +
                    "import org.junit.jupiter.api.Test;\n\n" +
                    "import java.util.Set;\n\n" +
                    "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                    "import static org.junit.jupiter.api.Assertions.assertFalse;\n" +
                    "import static org.junit.jupiter.api.Assertions.assertNotNull;\n" +
                    "import static org.junit.jupiter.api.Assertions.assertTrue;\n\n" +
                    "@TestMethodOrder(OrderAnnotation.class)\n";
            getAnnotations[1] = "private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();\n" +
                    "    private final String errorMessage = \"Mensaje de error incorrecto\";\n" +
                    "    private final String errorValidation = \"Se esperan violaciones de validación\";\n" +
                    "    private " + cls.getSimpleName() + " " + className + ";\n\n";
            getAnnotations[2] = getAnnotations[2] + "    @BeforeEach\n" +
                    "    void setUp() {\n" +
                    "        " + className + " = new " + cls.getSimpleName() + "();\n\n";
            for(Field field: fields) {
                fieldName = field.getName();
                fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                getAnnotations[2] = getAnnotations[2] + "        "+ className + ".set" + fieldName + "(" + getValues(field, null) + ");\n";
            }
            getAnnotations[2] = getAnnotations[2] + "    }\n\n";
            getAnnotations[3] = "\n    @Test\n" +
                    "    @Order(2)\n" +
                    "    void testValid" + cls.getSimpleName() + "() {\n" +
                    "        Set<ConstraintViolation<" + cls.getSimpleName() + ">> violations = validator.validate(" + className + ");\n" +
                    "        assertTrue(violations.isEmpty(), \"No se esperaban violaciones de validación\");\n" +
                    "    }\n";
            getAnnotations[5] = "    @AfterEach\n" +
                    "    void tearDown() {\n" +
                    "        " + className + " = null;\n" +
                    "    }\n";
        } else {
            getAnnotations[0] = "import org.junit.jupiter.api.Test;\n\n" +
                    "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                    "import static org.junit.jupiter.api.Assertions.assertNotNull;\n\n";
        }
        return getAnnotations;
    }

    private static String[] getExtends(Class<?> cls, Class<?> clsOrigin) {
        String[] extendsClass = new String[6];
        Arrays.fill(extendsClass, "");
        Class<?> superClass = cls.getSuperclass();
        if (!superClass.getSimpleName().equals("Object")) {
            extendsClass = getExtends(superClass, clsOrigin);
            extendsClass[0] = " extends " + superClass.getSimpleName() + "Test";
        }
        String fieldName;
        Field[] fields = cls.getDeclaredFields();
        String className = clsOrigin.getSimpleName();
        className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        for(Field field: fields) {
            fieldName = field.getName();
            fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            extendsClass[1] = extendsClass[1] + "\n        " + field.getType().getSimpleName();
            if(field.getType().getSimpleName().equals("List")) {
                ParameterizedType listType = (ParameterizedType) field.getGenericType();
                Type[] typeArguments = listType.getActualTypeArguments();
                if (listType.getRawType() == List.class && typeArguments.length > 0) {
                    Class<?> genericType = (Class<?>) typeArguments[0];
                    extendsClass[1] = extendsClass[1] + "<" + genericType.getSimpleName() + ">";
                }
            }
            extendsClass[1] = extendsClass[1] + " " + field.getName() + " = " + getValues(field, null) + ";";
            extendsClass[2] = extendsClass[2] + "\n        " + className + ".set" + fieldName + "(" + field.getName() + ");";
            extendsClass[3] = extendsClass[3] + "\n        assertNotNull(" + className + ".get" + fieldName + "());";
            extendsClass[4] = extendsClass[4] + "\n        assertEquals(" + field.getName() + ", " + className + ".get"+ fieldName + "());";
            Annotation[] annotations = field.getAnnotations();
            boolean annotationExist = false;
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().getSimpleName().equals("Id")
                    || annotation.annotationType().getSimpleName().equals("OneToMany")) {
                    annotationExist = true;
                    break;
                }
            }
            if(!annotationExist) {
                extendsClass[5] = extendsClass[5] + "        "+ className + ".set" + fieldName + "(" + getValues(field, null) + ");\n";
            }
        }
        return extendsClass;
    }

    private static String getTestEnum(Class<?> cls) throws IllegalAccessException {
        String test = "===========================================================================================================\n" +
                "                          " + cls.getSimpleName().toUpperCase() + "\n" +
                "===========================================================================================================\n" +
                "package " + cls.getPackage() + ";\n\n";
        if(cls.isEnum()) {
            String className = cls.getSimpleName();
            className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
            test = test + "import org.junit.jupiter.api.Test;\n\n" +
                    "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                    "import static org.junit.jupiter.api.Assertions.assertNotNull;\n\n" +
                    "class "+ cls.getSimpleName() + "Test {\n" +
                    "    @Test\n" +
                    "    void testNotNull() {\n" +
                    "        for (" + cls.getSimpleName() + " " + className + " : " + cls.getSimpleName() + ".values()) {\n" +
                    "            assertNotNull(" + className + ");\n" +
                    "        }\n" +
                    "    }\n";
            Field[] fields = cls.getDeclaredFields();
            String fieldName;
            String variable;
            for(Field field: fields) {
                field.setAccessible(true);
                int modifier = field.getModifiers();
                fieldName = field.getName();
                fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
                if(Modifier.isPrivate(modifier) && Modifier.isFinal(modifier) && !fieldName.equals("$VALUES")) {
                    test = test + "    @Test\n" +
                            "    void test" + fieldName + "IsEquals() {\n";
                    Enum<?>[] values = (Enum<?>[]) cls.getEnumConstants();
                    for(Enum<?> value : values) {
                        if(field.getType().getSimpleName().equals("String")) {
                            variable = "\"" + field.get(value) + "\"";
                        } else {
                            variable = field.get(value).toString();
                        }
                        test = test + "        assertEquals(" + variable + ", " + cls.getSimpleName() + "." + value.name() + ".get" + fieldName + "());\n";
                    }
                    test = test + "    }\n";
                }
            }
            test = test + "}";
        } else {
            test = test + "ERROR al generar la clase " + cls.getSimpleName() + "Test, la clase " + cls.getSimpleName() +  " no es de tipo Enum";
        }
        return test;
    }

    private static String getTestEntity(Class<?> cls, Class<?> clsRepository) {
        String[] extendsClass = getExtends(cls, cls);
        String className = cls.getSimpleName();
        className = Character.toLowerCase(className.charAt(0)) + className.substring(1);
        String test = "===========================================================================================================\n" +
                "                          " + cls.getSimpleName().toUpperCase() + "\n" +
                "===========================================================================================================\n" +
                "package " + cls.getPackage() + ";\n\n" +
                "import org.junit.jupiter.api.AfterEach;\n" +
                "import org.junit.jupiter.api.BeforeEach;\n" +
                "import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;\n" +
                "import org.junit.jupiter.api.Order;\n" +
                "import org.junit.jupiter.api.Test;\n" +
                "import org.junit.jupiter.api.TestMethodOrder;\n" +
                "import org.springframework.beans.factory.annotation.Autowired;\n" +
                "import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;\n" +
                "import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;\n" +
                "import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;\n\n" +
                "import java.util.List;\n\n" +
                "import static org.assertj.core.api.Assertions.assertThat;\n" +
                "import static org.junit.jupiter.api.Assertions.assertEquals;\n" +
                "import static org.junit.jupiter.api.Assertions.assertFalse;\n" +
                "import static org.junit.jupiter.api.Assertions.assertNotNull;\n" +
                "import static org.junit.jupiter.api.Assertions.assertNull;\n" +
                "import static org.junit.jupiter.api.Assertions.assertTrue;\n" +
                "import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;\n\n" +
                "@DataJpaTest\n" +
                "@AutoConfigureTestDatabase(replace = NONE)\n" +
                "@TestMethodOrder(OrderAnnotation.class)\n" +
                "class "+ cls.getSimpleName() + "Test {\n\n" +
                "    @Autowired\n" +
                "    private " + clsRepository.getSimpleName() + " repository;\n\n" +
                "    private " + clsRepository.getSimpleName() + " " + className + ";\n\n" +
                "    @BeforeEach\n" +
                "    void setUp() {\n" +
                "        " + className + " = new " + cls.getSimpleName() + "();\n\n" +
                extendsClass[5] + "    }\n\n" +
                "    @Test\n" +
                "    @Order(1)\n" +
                "    void testSettersAndGetters() {\n" +
                "        active = new Active();" +
                extendsClass[0] + "\n" +
                extendsClass[1] + "\n" +
                extendsClass[2] + "\n" +
                extendsClass[3] + "\n" +
                extendsClass[4] + "\n    }\n\n" +
                "    @Test\n" +
                "    @Order(2)\n" +
                "    void testSave() {\n" +
                "        " + cls.getSimpleName() + " " + className + "Save = repository.save(" + className + ");\n" +
                extendsClass[3] + "\n\n";
        Field[] fields = cls.getDeclaredFields();
        String fieldName;
        for(Field field : fields) {
            fieldName = field.getName();
            fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            test = test + "        assertEquals(" + className + ".get" + fieldName + "(), " + className + "Save.get" + fieldName + "());\n";
        }
        test = test + "    }\n\n";
        test = test + "    @Test\n" +
                "    @Order(4)\n" +
                "    void testFindAll() {\n" +
                "        " + className + " = repository.save(" + className + ");\n\n" +
                "        List<" + cls.getSimpleName() + "> " + className + "s = repository.findAll();\n\n" +
                "        for (" + cls.getSimpleName() + " " + className + "1: " + className + "s) {\n" +
                "            System.out.println(" + className + "1.toString());\n" +
                "        }\n" +
                "        assertThat(" + className + "s.size()).isGreaterThan(0);\n" +
                "    }\n\n" +
                "    @Test\n" +
                "    @Order(7)\n" +
                "    void testUpdate() {\n" +
                "        " + className + " = repository.save(" + className + ");\n" +
                extendsClass[1] + "\n" +
                extendsClass[2] + "\n" +
                "        repository.save(" + className + ");\n\n" +
                "        " + cls.getSimpleName() + " " + className + "Save = repository.searchById(" + className + ".getId" + cls.getSimpleName() + "());\n\n";
        for(Field field : fields) {
            fieldName = field.getName();
            fieldName = Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
            test = test + "        assertEquals(" + className + ".get" + fieldName + "(), " + className + "Save.get" + fieldName + "());\n";
        }
        test = test + "    }\n\n" +
                "    @Test\n" +
                "    @Order(8)\n" +
                "    void testDelete() {\n" +
                "        " + className + " = repository.save(" + className + ");\n" +
                "\n" +
                "        boolean exist = repository.findById(" + className + ".getId" + cls.getSimpleName() + "()).isPresent();\n" +
                "        assertTrue(exist);\n" +
                "        repository.deleteById(" + className + ".getId" + cls.getSimpleName() + "());\n" +
                "        exist = repository.findById(" + className + ".getId" + cls.getSimpleName() + "()).isPresent();\n" +
                "        assertFalse(exist);\n" +
                "    }\n\n" +
                "    @AfterEach\n" +
                "    void tearDown() {\n" +
                "        " + className + " = null;\n" +
                "    }\n}";
        return test;
    }

    private static String getValues(Field field, String param) {
        String cls;
        if(param == null) {
            cls = field.getType().getSimpleName();
        } else {
            cls = param;
        }
        String test = "";
        if(cls.equals("String")) {
            test = test + "\"ABC\"";
        } else if(cls.equals("Integer") || cls.equals("int")) {
            test = test + "1";
        } else if(cls.equals("Double") || cls.equals("double")) {
            test = test + "1.0";
        } else if(cls.equals("Boolean") || cls.equals("boolean")) {
            test = test + "true";
        } else if(cls.equals("Long") || cls.equals("long")) {
            test = test + "1L";
        } else if(cls.equals("Float") || cls.equals("float")) {
            test = test + "1F";
        } else if(cls.equals("HttpStatus")) {
            test = test + "HttpStatus.OK";
        } else if(cls.equals("List")) {
            String aux;//getValues();
            ParameterizedType listType = (ParameterizedType) field.getGenericType();
            Type[] typeArguments = listType.getActualTypeArguments();
            if (listType.getRawType() == List.class && typeArguments.length > 0) {
                Class<?> genericType = (Class<?>) typeArguments[0];
                aux = getValues(field, genericType.getSimpleName());
            } else {
                aux = "\"List\"";
            }
            test = test + "List.of(" + aux + ", " + aux + ")";
        } else if(cls.equals("LocalDate")) {
            test = test + "LocalDate.now()";
        } else if(cls.equals("LocalTime")) {
            test = test + "LocalTime.now()";
        } else if(cls.equals("LocalDateTime")) {
            test = test + "LocalDateTime.now()";
        } else if(field.getType().isEnum()) {
            Enum<?>[] values = (Enum<?>[]) field.getType().getEnumConstants();
            test = test + cls + "." + values[0].name();
        } else if(cls.equals("Object")) {
            test = test + "new Object()";
        } else  {
            test = test + "new " + cls + "()";
        }
        return test;
    }
}