/*
 * Copyright dick the deployer.
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

import com.dickthedeployer.dick.web.dao.GroupDao;
import com.dickthedeployer.dick.web.domain.Group;
import com.dickthedeployer.dick.web.domain.Namespace;
import com.dickthedeployer.dick.web.model.GroupModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author mariusz
 */
@Service
public class GroupService {

    @Autowired
    GroupDao groupDao;

    public void createGroup(GroupModel groupModel) {
        Group group = new Group.Builder()
                .withNamespace(
                        new Namespace.Builder()
                        .withName(groupModel.getName())
                        .build()
                ).build();

        groupDao.save(group);
    }
}
