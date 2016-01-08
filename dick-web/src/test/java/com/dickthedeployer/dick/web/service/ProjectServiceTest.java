/*
 * Copyright 2015 dick the deployer.
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
package com.dickthedeployer.dick.web.service;

import com.dickthedeployer.dick.web.ContextTestBase;
import com.dickthedeployer.dick.web.domain.Namespace;
import com.dickthedeployer.dick.web.domain.Project;
import com.dickthedeployer.dick.web.exception.NameTakenException;
import com.dickthedeployer.dick.web.exception.RepositoryUnavailableException;
import com.dickthedeployer.dick.web.model.ProjectModel;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 *
 * @author mariusz
 */
public class ProjectServiceTest extends ContextTestBase {

    @Autowired
    ProjectService projectService;

    @Test
    public void shouldCreateProject() throws NameTakenException, RepositoryUnavailableException {
        ProjectModel model = getProjectModel("test-namespace", "some-semi-random-name");

        projectService.createProject(model);
        Project entity = projectDao.findByNamespaceNameAndName("test-namespace", "some-semi-random-name");
        assertThat(entity.getId()).isNotNull();
        assertThat(entity.getRef()).isEqualTo("master");
        assertThat(entity.getCreationDate()).isNotNull();

    }

    @Test(expected = NameTakenException.class)
    public void shouldThrowNameTakenException() throws NameTakenException, RepositoryUnavailableException {
        ProjectModel model = getProjectModel("test-namespace", "some-semi-random-name");

        projectService.createProject(model);
        projectService.createProject(model);
    }

    @Test
    public void shouldAllowCreatingTheSameProjectWithDifferentNamespace() throws NameTakenException, RepositoryUnavailableException {
        ProjectModel model = getProjectModel("test-namespace", "some-semi-random-name");
        projectService.createProject(model);

        model = getProjectModel("other-namespace", "some-semi-random-name");
        projectService.createProject(model);


        Project entity = projectDao.findByNamespaceNameAndName("test-namespace", "some-semi-random-name");
        assertThat(entity.getId()).isNotNull();
        entity = projectDao.findByNamespaceNameAndName("other-namespace", "some-semi-random-name");
        assertThat(entity.getId()).isNotNull();
    }

    @Test(expected = RepositoryUnavailableException.class)
    public void shouldThrowRepoUnavailableException() throws NameTakenException, RepositoryUnavailableException {
        ProjectModel model = getProjectModel("test-namespace", "some-semi-random-name");
        model.setRepository("asdasd");
        projectService.createProject(model);
    }

    private ProjectModel getProjectModel(String namespace, String name) {
        namespaceDao.save(new Namespace.Builder()
                .withName(namespace)
                .build()
        );
        ProjectModel model = new ProjectModel();
        model.setName(name);
        model.setRepository(UUID.randomUUID().toString());
        model.setRef("master");
        model.setRepository("https://github.com/dick-the-deployer/examples.git");
        model.setNamespace(namespace);
        return model;
    }
}
