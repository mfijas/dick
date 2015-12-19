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
package com.dickthedeployer.dick.web.gitlab;

/**
 *
 * @author mariusz
 */
public class GitlabUtils {

    static String getCommitUrl(GitlabTrigger trigger) {
        return trigger.getGitlab_url() + "/commit/" + trigger.getSha();
    }

    static String getBuildUrl(GitlabTrigger trigger) {
        return trigger.getGitlab_url() + "/builds/" + trigger.getBuild_id();
    }

    static String getProjectName(GitlabTrigger trigger) {
        return trigger.getProject_name().replaceAll(" ", "");
    }

}