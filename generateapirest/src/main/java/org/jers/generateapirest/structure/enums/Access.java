package org.jers.generateapirest.structure.enums;

import java.lang.reflect.Modifier;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Access {
    PRIVATE("private", "- "),
    PROTECTED("protected", "# "),
    PUBLIC("public", "+ ");
    
    private final String text;
    private final String plantUML;
    
    public static Access copare(int accessModifier) {
        if(Modifier.isPrivate(accessModifier)) {
            return PRIVATE;
        } else if(Modifier.isProtected(accessModifier)) {
            return PROTECTED;
        } else {
            return PUBLIC;
        }
    }
}