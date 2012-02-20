/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.plugins.binaries.model.internal;

import groovy.lang.Closure;
import org.gradle.api.DomainObjectSet;
import org.gradle.api.internal.DefaultDomainObjectSet;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.plugins.binaries.model.Binary;
import org.gradle.plugins.binaries.model.CompileSpec;
import org.gradle.plugins.binaries.model.CompilerRegistry;
import org.gradle.plugins.binaries.model.SourceSet;
import org.gradle.util.ConfigureUtil;
import org.gradle.util.DeprecationLogger;

public class DefaultBinary implements Binary {

    private final String name;
    private final ProjectInternal project;
    private final CompileSpec spec;
    private final DomainObjectSet<SourceSet> sourceSets;

    public DefaultBinary(String name, ProjectInternal project) {
        this.name = name;
        this.project = project;
        this.sourceSets = new DefaultDomainObjectSet<SourceSet>(SourceSet.class);
        CompilerRegistry compilers = project.getExtensions().getByType(CompilerRegistry.class);
        this.spec = compilers.getDefaultCompiler().getSpecFactory().create(this);
    }

    public String getName() {
        return name;
    }

    public ProjectInternal getProject() {
        DeprecationLogger.nagUserOfDiscontinuedMethod("Binary.getProject()");
        return project;
    }

    public CompileSpec getSpec() {
        return spec;
    }

    public DomainObjectSet<SourceSet> getSourceSets() {
        return sourceSets;
    }
    
    public CompileSpec spec(Closure closure) {
        return ConfigureUtil.configure(closure, spec);
    }
}