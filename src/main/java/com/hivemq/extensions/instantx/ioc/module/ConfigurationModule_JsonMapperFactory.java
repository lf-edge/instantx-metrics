/*-
 * ========================LICENSE_START=================================
 * hivemq-extension-poc
 * %%
 * Copyright (C) 2024 - 2025 Vodafone
 * %%
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
 * =========================LICENSE_END==================================
 */
package com.hivemq.extensions.instantx.ioc.module;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
public final class ConfigurationModule_JsonMapperFactory implements Factory<ObjectMapper> {
    private final ConfigurationModule module;

    public ConfigurationModule_JsonMapperFactory(ConfigurationModule module) {
        this.module = module;
    }

    public ObjectMapper get() {
        return jsonMapper(this.module);
    }

    public static ConfigurationModule_JsonMapperFactory create(ConfigurationModule module) {
        return new ConfigurationModule_JsonMapperFactory(module);
    }

    public static ObjectMapper jsonMapper(ConfigurationModule instance) {
        return (ObjectMapper)Preconditions.checkNotNullFromProvides(instance.jsonMapper());
    }
}
