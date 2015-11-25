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
package com.dickthedeployer.dick.web.model.dickfile;

import java.util.List;
import static java.util.stream.Collectors.toList;
import lombok.Data;

/**
 *
 * @author mariusz
 */
@Data
public class Dickfile {

    private Pipeline pipeline;

    private List<Job> jobs;

    public Stage getFirstStage() {
        return pipeline.getStages().stream()
                .findFirst()
                .get();
    }

    public Stage getStage(String name) {
        return pipeline.getStages().stream()
                .filter(stage -> stage.getName().equals(name))
                .findAny()
                .get();
    }

    public List<Job> getJobs(Stage firstStage) {
        return jobs.stream().filter(job -> job.getStage()
                .equals(firstStage.getName()))
                .collect(toList());
    }

    public Stage getNextStage(Stage stage) {
        int indexOfStage = pipeline.getStages().indexOf(stage);
        if (pipeline.getStages().size() < indexOfStage + 1) {
            return pipeline.getStages().get(indexOfStage + 1);
        }
        return null;
    }

}
