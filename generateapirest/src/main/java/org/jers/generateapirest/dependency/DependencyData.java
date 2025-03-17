package org.jers.generateapirest.dependency;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@AllArgsConstructor 
@RequiredArgsConstructor
@Getter
public class DependencyData {
    private final String groupId;
    private final String artefactId;
    
    @Setter
    private String version;
    
    @Setter
    private ScopeMaven scopeMaven;
    
    @Setter
    private ScopeGradle scopeGradle;
    
    public String getRepositoryUrl() {
        return this.groupId.replace(".", "/") + "/" + this.artefactId + "/";
    }
}