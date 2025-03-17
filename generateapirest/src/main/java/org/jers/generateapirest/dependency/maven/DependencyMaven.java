package org.jers.generateapirest.dependency.maven;

import org.jers.generateapirest.dependency.Dependency;
import org.jers.generateapirest.dependency.DependencyData;

public class DependencyMaven extends Dependency {

    public DependencyMaven(DependencyData data) {
        super(data);
    }

    @Override
    public String getParentDependency() {
        return "\t<parent>\n" +
                "\t\t<groupId>" + getData().getGroupId() +"</groupId>\n" +
                "\t\t<artifactId>" + getData().getArtefactId() + "</artifactId>\n" +
                "\t\t<version>" + getData().getVersion() + "</version>\n" +
                "\t\t<relativePath/> <!-- lookup parent from repository -->\n" +
                "\t</parent>\n";
    }
    
    @Override
    public String getFullDependency() {
        return "\t\t<dependency>\n" +
                "\t\t\t<groupId>" + getData().getGroupId() +"</groupId>\n" +
                "\t\t\t<artifactId>" + getData().getArtefactId() + "</artifactId>\n" +
                "\t\t\t<version>" + getData().getVersion() + "</version>\n" +
                "\t\t\t<scope>" + getData().getScopeMaven().getScope() + "</scope>\n" +
                "\t\t</dependency>\n";
    }

    @Override
    public String getDependencyWithoutScope() {
        return "\t\t<dependency>\n" +
                "\t\t\t<groupId>" + getData().getGroupId() +"</groupId>\n" +
                "\t\t\t<artifactId>" + getData().getArtefactId() + "</artifactId>\n" +
                "\t\t\t<version>" + getData().getVersion() + "</version>\n" +
                "\t\t</dependency>\n";
    }

    @Override
    public String getDependencyWithoutVersion() {
        return "\t\t<dependency>\n" +
                "\t\t\t<groupId>" + getData().getGroupId() +"</groupId>\n" +
                "\t\t\t<artifactId>" + getData().getArtefactId() + "</artifactId>\n" +
                "\t\t\t<scope>" + getData().getScopeMaven().getScope() + "</scope>\n" +
                "\t\t</dependency>\n";
    }

    @Override
    public String getSpringDependency() {
        return "\t\t<dependency>\n" +
                "\t\t\t<groupId>" + getData().getGroupId() +"</groupId>\n" +
                "\t\t\t<artifactId>" + getData().getArtefactId() + "</artifactId>\n" +
                "\t\t</dependency>\n";
    }
}