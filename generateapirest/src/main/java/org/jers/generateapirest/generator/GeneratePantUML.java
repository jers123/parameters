package org.jers.generateapirest.generator;

/*import org.jers.parameters.ParametersApplication;
import org.jers.parameters.annotation.Dto;
import org.jers.parameters.annotation.RestApi;
import org.jers.parameters.config.SwaggerConfig;
import org.jers.parameters.controller.*;
import org.jers.parameters.exception.ApiExceptionHandler;
import org.jers.parameters.model.dto.BaseEntityInputDTO;
import org.jers.parameters.model.dto.BaseEntityOutputDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusCreateDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusOutputDTO;
import org.jers.parameters.model.dto.civilstatus.CivilStatusUpdateDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeCreateDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeOutputDTO;
import org.jers.parameters.model.dto.documenttype.DocumentTypeUpdateDTO;
import org.jers.parameters.model.dto.gender.GenderCreateDTO;
import org.jers.parameters.model.dto.gender.GenderOutputDTO;
import org.jers.parameters.model.dto.gender.GenderUpdateDTO;
import org.jers.parameters.model.dto.sector.SectorCreateDTO;
import org.jers.parameters.model.dto.sector.SectorOutputDTO;
import org.jers.parameters.model.dto.sector.SectorUpdateDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeCreateDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeOutputDTO;
import org.jers.parameters.model.dto.telephonetype.TelephoneTypeUpdateDTO;
import org.jers.parameters.model.entity.*;
import org.jers.parameters.model.repository.*;
import org.jers.parameters.service.*;
import org.jers.parameters.utils.Constants;
import org.jers.parameters.utils.SystemConstants;
import org.jers.parameters.utils.mappers.*;
import org.jers.parameters.utils.response.ReplyMessage;
import org.jers.parameters.utils.response.ReplyMessageList;
import org.jers.parameters.utils.response.ReplyMessageSimple;
import org.springframework.http.ResponseEntity;
*/

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class GeneratePantUML {

    public static void main(String[] args) {
        List<Class<?>> clasess = new ArrayList<>();
        /*clasess.add(ParametersApplication.class);

        clasess.add(SystemConstants.class);
        clasess.add(Constants.class);

        clasess.add(SwaggerConfig.class);

        clasess.add(ApiExceptionHandler.class);

        clasess.add(ReplyMessage.class);
        clasess.add(ReplyMessageSimple.class);

        clasess.add(Dto.class);
        clasess.add(RestApi.class);

        clasess.add(BaseEntityInputDTO.class);
        clasess.add(BaseEntityOutputDTO.class);
        clasess.add(CivilStatusCreateDTO.class);
        clasess.add(CivilStatusUpdateDTO.class);
        clasess.add(CivilStatusOutputDTO.class);
        clasess.add(DocumentTypeCreateDTO.class);
        clasess.add(DocumentTypeUpdateDTO.class);
        clasess.add(DocumentTypeOutputDTO.class);
        clasess.add(GenderCreateDTO.class);
        clasess.add(GenderUpdateDTO.class);
        clasess.add(GenderOutputDTO.class);
        clasess.add(SectorCreateDTO.class);
        clasess.add(SectorUpdateDTO.class);
        clasess.add(SectorOutputDTO.class);
        clasess.add(TelephoneTypeCreateDTO.class);
        clasess.add(TelephoneTypeUpdateDTO.class);
        clasess.add(TelephoneTypeOutputDTO.class);

        clasess.add(BaseEntity.class);
        clasess.add(CivilStatus.class);
        clasess.add(DocumentType.class);
        clasess.add(Gender.class);
        clasess.add(Sector.class);
        clasess.add(TelephoneType.class);

        clasess.add(IBaseRepository.class);
        clasess.add(ICivilStatusRepository.class);
        clasess.add(IDocumentTypeRepository.class);
        clasess.add(IGenderRepository.class);
        clasess.add(ISectorRepository.class);
        clasess.add(ITelephoneTypeRepository.class);

        clasess.add(IMapper.class);
        clasess.add(CivilStatusMapper.class);
        clasess.add(DocumentTypeMapper.class);
        clasess.add(GenderMapper.class);
        clasess.add(SectorMapper.class);
        clasess.add(TelephoneTypeMapper.class);

        clasess.add(IBaseService.class);
        clasess.add(IDocumentTypeService.class);
        clasess.add(CivilStatusService.class);
        clasess.add(DocumentTypeService.class);
        clasess.add(GenderService.class);
        clasess.add(SectorService.class);
        clasess.add(TelephoneTypeService.class);

        clasess.add(IBaseController.class);
        clasess.add(IDocumentTypeController.class);
        clasess.add(CivilStatusController.class);
        clasess.add(DocumentTypeController.class);
        clasess.add(GenderController.class);
        clasess.add(SectorController.class);
        clasess.add(TelephoneTypeController.class);

        clasess.add(ReplyMessageList.class);
        */

        System.out.println("@startuml");
        for(int i = 0; i < clasess.size(); i++) {
            System.out.println(getPlantClass(clasess.get(i), 1));
        }
        System.out.println("@enduml");
    }

    private static String getPlantClass(Class<?> cls, int type) {
        String plantUML = "";
        if(cls.isEnum()) {
            plantUML = "enum ";
        } else if(cls.isAnnotation()) {
            plantUML = "annotation ";
        } else if(cls.isInterface()) {
            plantUML = "interface ";
        } else if(Modifier.isAbstract(cls.getModifiers())) {
            plantUML = "abstract class ";
        } else {
            Annotation[] annotations = cls.getAnnotations();
            boolean isEntity = false;
            boolean isDto = false;
            for (Annotation annotation : annotations) {
                if(annotation.annotationType().getSimpleName().equals("Entity")) {
                    isEntity = true;
                    break;
                } else if(annotation.annotationType().getSimpleName().equals("Dto")) {
                    isDto = true;
                    break;
                }
            }
            if(isEntity) {
                plantUML = "entity ";
            } else if(isDto) {
                plantUML = "struct ";
            } else {
                plantUML = "class ";
            }
        }
        if(type == 1) {
            plantUML = plantUML + cls.getName();
        } else  {
            plantUML = plantUML + cls.getSimpleName();
        }
        plantUML = plantUML + " {\n" + getFields(cls) + getMethods(cls) + "}\n";
        return plantUML;
    }

    private static String getFields(Class<?> cls) {
        String plantUML = "";
        Field[] fields = cls.getDeclaredFields();
        for(Field field: fields) {
            if(cls.isInterface()) {
                plantUML = plantUML + "~";
            } else {
                if (Modifier.isPublic(field.getModifiers())) {
                    plantUML = plantUML + "+";
                } else if (Modifier.isPrivate(field.getModifiers())) {
                    plantUML = plantUML + "-";
                } else {
                    plantUML = plantUML + "#";
                }
            }
            if (Modifier.isStatic(field.getModifiers())) {
                plantUML = plantUML + " {static}";
            }
            plantUML = plantUML + " " + field.getName() + " : " + field.getType().getSimpleName();
            if(field.getType().getSimpleName().equals("List")) {
                plantUML = plantUML + typeSpecial((ParameterizedType) field.getGenericType());
            }
            plantUML = plantUML + "\n";
        }
        return plantUML;
    }

    private static String getMethods(Class<?> cls) {
        String plantUML = "";
        Method[] methods = cls.getDeclaredMethods();
        for(Method method: methods) {
            if(cls.isEnum() &&
                (method.getName().equals("$VALUES")
                || method.getName().equals("values")
                || method.getName().equals("alueOf")
                || method.getName().equals("$values"))) {
                plantUML = plantUML + "";
            }
            else  {
                if(cls.isInterface()) {
                    plantUML = plantUML + "~";
                } else {
                    if (Modifier.isPublic(method.getModifiers())) {
                        plantUML = plantUML + "+";
                    } else if (Modifier.isPrivate(method.getModifiers())) {
                        plantUML = plantUML + "-";
                    } else {
                        plantUML = plantUML + "#";
                    }
                }
                if (Modifier.isStatic(method.getModifiers())) {
                    plantUML = plantUML + " {static}";
                }
                plantUML = plantUML + " " + method.getName() + "(";
                Parameter[] parameters = method.getParameters();
                for(Parameter parameter : parameters) {
                    plantUML = plantUML + parameter.getName() + " : " + parameter.getType().getSimpleName();
                    if(parameter.getType().getSimpleName().equals("List")
                            || parameter.getType().getSimpleName().equals("ResponseEntity")) {
                        plantUML = plantUML + typeSpecial((ParameterizedType) parameter.getParameterizedType());
                    }
                    plantUML = plantUML + ", ";
                }
                plantUML = plantUML + ")";
                plantUML = plantUML.replace(", )", ")");
                plantUML = plantUML + " : " + method.getReturnType().getSimpleName();
                if(method.getReturnType().getSimpleName().equals("List")
                        || method.getReturnType().getSimpleName().equals("ResponseEntity")) {
                    plantUML = plantUML + typeSpecial((ParameterizedType) method.getGenericReturnType());
                }
                plantUML = plantUML + "\n";
            }
        }
        return plantUML;
    }

    private static String typeSpecial(ParameterizedType listType) {
        String plantUML = "";
        Type[] typeArguments = listType.getActualTypeArguments();

/*        for (Type typeArgument : typeArguments) {
            System.out.println("Type argument - " + typeArgument);
            System.out.println("Type name - " + typeArgument.getTypeName());
            System.out.println("Class - " + typeArgument.getClass());
            System.out.println("Component type - " + typeArgument.getClass().componentType());
            System.out.println("Descriptor - " + typeArgument.getClass().descriptorString());
            System.out.println("Canonical name - " + typeArgument.getClass().getCanonicalName());
            System.out.println("Array type - " + typeArgument.getClass().arrayType());
            System.out.println("Super class - " + typeArgument.getClass().getGenericSuperclass());
        }
*/
        if ((listType.getRawType() == List.class //|| listType.getRawType() == ResponseEntity.class
                ) && typeArguments.length > 0) {
            Class<?> genericType = (Class<?>) typeArguments[0];
            plantUML = plantUML + "<" + genericType.getSimpleName() + ">";
        }
        return plantUML;
    }
}