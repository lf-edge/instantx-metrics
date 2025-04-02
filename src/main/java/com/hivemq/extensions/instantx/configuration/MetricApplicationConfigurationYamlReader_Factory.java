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
package com.hivemq.extensions.instantx.configuration;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Provider;

@DaggerGenerated
public final class MetricApplicationConfigurationYamlReader_Factory implements Factory<MetricApplicationConfigurationYamlReader> {
    private final Provider<File> extensionFolderProvider;

    private final Provider<ScheduledExecutorService> scheduledExecutorProvider;

    private final Provider<SchemaValidatedYamlReaderFactory> configFactoryProvider;

    public MetricApplicationConfigurationYamlReader_Factory(Provider<File> extensionFolderProvider, Provider<ScheduledExecutorService> scheduledExecutorProvider, Provider<SchemaValidatedYamlReaderFactory> configFactoryProvider) {
        this.extensionFolderProvider = extensionFolderProvider;
        this.scheduledExecutorProvider = scheduledExecutorProvider;
        this.configFactoryProvider = configFactoryProvider;
    }

    public MetricApplicationConfigurationYamlReader get() {
        return newInstance((File)this.extensionFolderProvider.get(), (ScheduledExecutorService)this.scheduledExecutorProvider.get(), (SchemaValidatedYamlReaderFactory)this.configFactoryProvider.get());
    }

    public static MetricApplicationConfigurationYamlReader_Factory create(Provider<File> extensionFolderProvider, Provider<ScheduledExecutorService> scheduledExecutorProvider, Provider<SchemaValidatedYamlReaderFactory> configFactoryProvider) {
        return new MetricApplicationConfigurationYamlReader_Factory(extensionFolderProvider, scheduledExecutorProvider, configFactoryProvider);
    }

    public static MetricApplicationConfigurationYamlReader newInstance(File extensionFolder, ScheduledExecutorService scheduledExecutor, SchemaValidatedYamlReaderFactory configFactory) {
        return new MetricApplicationConfigurationYamlReader(extensionFolder, scheduledExecutor, configFactory);
    }
}
