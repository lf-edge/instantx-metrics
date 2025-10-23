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
package com.hivemq.extensions.instantx.metrics;

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
public final class MetricHolder_Factory implements Factory<MetricHolder> {
    private final Provider<MetricRegistry> registryProvider;

    private final Provider<MetricApplicationConfiguration> configProvider;

    public MetricHolder_Factory(Provider<MetricRegistry> registryProvider,
            Provider<MetricApplicationConfiguration> configProvider) {
        this.registryProvider = registryProvider;
        this.configProvider = configProvider;
    }

    public MetricHolder get() {
        return newInstance((MetricRegistry) this.registryProvider.get(),
                (MetricApplicationConfiguration) this.configProvider.get());
    }

    public static MetricHolder_Factory create(Provider<MetricRegistry> registryProvider,
            Provider<MetricApplicationConfiguration> configProvider) {
        return new MetricHolder_Factory(registryProvider, configProvider);
    }

    public static MetricHolder newInstance(MetricRegistry registry, MetricApplicationConfiguration config) {
        return new MetricHolder(registry, config);
    }
}
