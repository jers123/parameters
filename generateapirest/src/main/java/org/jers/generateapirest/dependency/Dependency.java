package org.jers.generateapirest.dependency;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class Dependency {
	private final DependencyData data;

	public abstract String getParentDependency();

	public abstract String getFullDependency();

	public abstract String getDependencyWithoutScope();

	public abstract String getDependencyWithoutVersion();

	public abstract String getSpringDependency();
}