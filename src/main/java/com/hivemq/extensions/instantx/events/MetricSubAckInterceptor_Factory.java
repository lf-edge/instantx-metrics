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
package com.hivemq.extensions.instantx.events;

import com.hivemq.extensions.instantx.metrics.MetricHolder;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
public final class MetricSubAckInterceptor_Factory implements Factory<MetricSubAckInterceptor> {
    private final Provider<MetricHolder> metricHolderProvider;

    public MetricSubAckInterceptor_Factory(Provider<MetricHolder> metricHolderProvider) {
        this.metricHolderProvider = metricHolderProvider;
    }

    public MetricSubAckInterceptor get() {
        return newInstance((MetricHolder)this.metricHolderProvider.get());
    }

    public static MetricSubAckInterceptor_Factory create(Provider<MetricHolder> metricHolderProvider) {
        return new MetricSubAckInterceptor_Factory(metricHolderProvider);
    }

    public static MetricSubAckInterceptor newInstance(MetricHolder metricHolder) {
        return new MetricSubAckInterceptor(metricHolder);
    }
}
