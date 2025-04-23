package org.jers.generateapirest.dependency;

import java.util.List;

import org.jers.generateapirest.dependency.maven.OnlineVersionDetector;

import lombok.Getter;
import lombok.Setter;

@Getter
public class DependencyData {
	private final String name;
	private final String groupId;
	private final String artefactId;

	@Setter
	private String version;

	@Setter
	private ScopeMaven scopeMaven;

	@Setter
	private ScopeGradle scopeGradle;

	@Setter
	private Boolean required;

	public DependencyData(String name, String groupId, String artefactId, String version, ScopeMaven scopeMaven,
			ScopeGradle scopeGradle, boolean required) {
		this.name = name;
		this.groupId = groupId;
		this.artefactId = artefactId;
		if (version != null) {
			this.version = version;
		} else {
			this.version = OnlineVersionDetector.getVersion(this);
		}
		this.scopeMaven = scopeMaven;
		this.scopeGradle = scopeGradle;
		this.required = required;
	}

	public String getRepositoryUrl() {
		return this.groupId.replace(".", "/") + "/" + this.artefactId + "/";
	}
	
	public static DependencyData findDependency(List<DependencyData> dependencies, String name) {
		DependencyData dependency = null;
		for (int i = 0; i < dependencies.size(); i++) {
			if (dependencies.get(i).getName().equals(name)) {
				dependency = dependencies.get(i);
				break;
			}
		}
		return dependency;
	}
}