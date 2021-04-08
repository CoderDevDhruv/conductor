/*
 *  Copyright 2021 Netflix, Inc.
 *  <p>
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 *  the License. You may obtain a copy of the License at
 *  <p>
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  <p>
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 *  an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 *  specific language governing permissions and limitations under the License.
 */

package com.netflix.conductor.core.execution.tasks;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * A container class that holds a mapping of system task types {@link com.netflix.conductor.common.metadata.tasks.TaskType} to
 * {@link WorkflowSystemTask} instances.
 */
@Component
public class SystemTaskRegistry {

    private final Map<String, WorkflowSystemTask> registry;

    /**
     * Spring creates a map of bean names to {@link WorkflowSystemTask} instances and injects it.
     * <p>
     * NOTE: It is important the {@link WorkflowSystemTask} instances are "qualified" with their respective
     * {@link com.netflix.conductor.common.metadata.tasks.TaskType}.
     */
    public SystemTaskRegistry(Map<String, WorkflowSystemTask> registry) {
        this.registry = registry;
    }

    public WorkflowSystemTask get(String taskType) {
        return Optional.ofNullable(registry.get(taskType))
                .orElseThrow(() -> new IllegalStateException(taskType + "not found in " + getClass().getSimpleName()));
    }

    public boolean isSystemTask(String taskType) {
        return registry.containsKey(taskType);
    }

    public Collection<WorkflowSystemTask> all() {
        return registry.values();
    }
}
