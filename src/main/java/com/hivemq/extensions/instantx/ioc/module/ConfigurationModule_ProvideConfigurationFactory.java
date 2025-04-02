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

import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfigurationYamlReader;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

@DaggerGenerated
public final class ConfigurationModule_ProvideConfigurationFactory implements Factory<MetricApplicationConfiguration> {
    private final ConfigurationModule module;

    private final Provider<MetricApplicationConfigurationYamlReader> configurationReaderProvider;

    public ConfigurationModule_ProvideConfigurationFactory(ConfigurationModule module, Provider<MetricApplicationConfigurationYamlReader> configurationReaderProvider) {
        this.module = module;
        this.configurationReaderProvider = configurationReaderProvider;
    }

    public MetricApplicationConfiguration get() {
        return provideConfiguration(this.module, this.configurationReaderProvider);
    }

    public static ConfigurationModule_ProvideConfigurationFactory create(ConfigurationModule module, Provider<MetricApplicationConfigurationYamlReader> configurationReaderProvider) {
        return new ConfigurationModule_ProvideConfigurationFactory(module, configurationReaderProvider);
    }

    public static MetricApplicationConfiguration provideConfiguration(ConfigurationModule instance, Provider<MetricApplicationConfigurationYamlReader> configurationReader) {
        return (MetricApplicationConfiguration)Preconditions.checkNotNullFromProvides(instance.provideConfiguration(configurationReader));
    }
}
