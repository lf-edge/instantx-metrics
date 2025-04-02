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

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfigurationYamlReader;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

@Module
public class ConfigurationModule {
    @Provides
    @Singleton
    @Named("yamlMapper")
    @NotNull
    ObjectMapper yamlMapper() {
        return new ObjectMapper((JsonFactory)new YAMLFactory());
    }

    @Provides
    @Singleton
    @Named("jsonMapper")
    @NotNull
    ObjectMapper jsonMapper() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    @NotNull
    MetricApplicationConfiguration provideConfiguration(@NotNull Provider<MetricApplicationConfigurationYamlReader> configurationReader) {
        return (MetricApplicationConfiguration)configurationReader.get();
    }
}
