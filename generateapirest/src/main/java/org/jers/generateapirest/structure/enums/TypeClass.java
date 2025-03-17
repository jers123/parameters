package org.jers.generateapirest.structure.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum TypeClass {
    ANNOTATION("@interface"),
    ABSTRACT("abstract class"),
    CLASS("class"),
    ENUM("enum"),
    INTERFACE("interface");
    
    private final String text;
}