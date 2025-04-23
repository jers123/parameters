package org.jers.generateapirest.dependency.gradle;

import org.jers.generateapirest.dependency.Dependency;
import org.jers.generateapirest.dependency.DependencyData;

public class DependencyGradle extends Dependency {

	public DependencyGradle(DependencyData data) {
		super(data);
	}

	@Override
	public String getParentDependency() {
		return "\t" + getData().getScopeGradle().getScope() + " '" + getData().getGroupId() + " '"
				+ getData().getVersion() + "'\n";
	}

	@Override
	public String getFullDependency() {
		return "\t" + getData().getScopeGradle().getScope() + " group: '" + getData().getGroupId() + "', name: '"
				+ getData().getArtefactId() + "', version: '" + getData().getVersion() + "'";
	}

	@Override
	public String getDependencyWithoutScope() {
		return "\t" + getData().getScopeGradle().getScope() + " '" + getData().getGroupId() + ":"
				+ getData().getArtefactId() + ":" + getData().getVersion() + "'";
	}

	@Override
	public String getDependencyWithoutVersion() {
		return "\t" + getData().getScopeGradle().getScope() + "(\"" + getData().getGroupId() + ":"
				+ getData().getArtefactId() + "\")";
	}

	@Override
	public String getSpringDependency() {
		return "\t" + getData().getScopeGradle().getScope() + " '" + getData().getGroupId() + ":"
				+ getData().getArtefactId() + "'";
	}

}