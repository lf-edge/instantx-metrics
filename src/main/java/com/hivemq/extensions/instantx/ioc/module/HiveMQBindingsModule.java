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

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.events.EventRegistry;
import com.hivemq.extension.sdk.api.parameter.ExtensionStartInput;
import com.hivemq.extension.sdk.api.services.Services;
import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class HiveMQBindingsModule {
    @NotNull
    private final File extensionHomeFolder;

    public HiveMQBindingsModule(@NotNull ExtensionStartInput extensionStartInput) {
        this.extensionHomeFolder = extensionStartInput.getExtensionInformation().getExtensionHomeFolder();
    }

    @Provides
    @Singleton
    @NotNull
    MetricRegistry provideMetricRegistry() {
        return Services.metricRegistry();
    }

    @Provides
    @Singleton
    @NotNull
    ExecutorService provideExecutorService() {
        return (ExecutorService) Services.extensionExecutorService();
    }

    @Provides
    @Singleton
    @NotNull
    InitializerRegistry provideInitializerRegistry() {
        return Services.initializerRegistry();
    }

    @Provides
    @Singleton
    @NotNull
    EventRegistry provideEventRegistry() {
        return Services.eventRegistry();
    }

    @Provides
    @Singleton
    @NotNull
    ScheduledExecutorService provideScheduledExecutorService() {
        return (ScheduledExecutorService) Services.extensionExecutorService();
    }

    @Provides
    @Singleton
    @Named("extension-home")
    @NotNull
    File provideExtensionHomeFolder() {
        return this.extensionHomeFolder;
    }
}
