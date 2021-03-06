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
package com.dickthedeployer.dick.web.controller;

import com.dickthedeployer.dick.web.exception.BuildAlreadyQueuedException;
import com.dickthedeployer.dick.web.exception.NotFoundException;
import com.dickthedeployer.dick.web.model.BuildDetailsModel;
import com.dickthedeployer.dick.web.model.TriggerModel;
import com.dickthedeployer.dick.web.service.BuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 *
 * @author mariusz
 */
@RestController
@RequestMapping("/api/builds")
public class BuildController {

    @Autowired
    BuildService buildService;

    @RequestMapping(method = POST)
    public void onTrigger(@RequestBody TriggerModel trigger) throws BuildAlreadyQueuedException {
        buildService.onTrigger(trigger);
    }

    @RequestMapping(method = POST, value = "/{buildId}/{stageName}")
    public void buildStage(@PathVariable("buildId") Long buildId, @PathVariable("stageName") String stageName) throws NotFoundException {
        buildService.buildStage(buildId, stageName);
    }

    @RequestMapping(method = POST, value = "/{buildId}/kill")
    public void kill(@PathVariable("buildId") Long buildId) throws NotFoundException {
        buildService.kill(buildId);
    }

    @RequestMapping(method = GET, value = "/{buildId}")
    public BuildDetailsModel getBuildDetails(@PathVariable("buildId") Long buildId) throws NotFoundException {
        return buildService.getBuild(buildId);
    }
}
