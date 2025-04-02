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
package com.hivemq.extensions.instantx;

import com.codahale.metrics.MetricRegistry;
import com.hivemq.extension.sdk.api.events.EventRegistry;
import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import com.hivemq.extensions.instantx.events.AppConnectionListener;
import com.hivemq.extensions.instantx.events.MetricPubAckInboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPubAckOutboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPublishInboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPublishReceiveInterceptor;
import com.hivemq.extensions.instantx.events.MetricSubAckInterceptor;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import javax.inject.Provider;

@DaggerGenerated
public final class ExtensionInitializer_Factory implements Factory<ExtensionInitializer> {
    private final Provider<EventRegistry> eventRegistryProvider;

    private final Provider<InitializerRegistry> initializerRegistryProvider;

    private final Provider<MetricRegistry> metricRegistryProvider;

    private final Provider<MetricApplicationConfiguration> multiTenancyConfigurationProvider;

    private final Provider<MetricPubAckInboundInterceptor> pubAckInboundInterceptorProvider;

    private final Provider<MetricPublishInboundInterceptor> inboundInterceptorProvider;

    private final Provider<MetricPublishReceiveInterceptor> receiveInterceptorProvider;

    private final Provider<MetricPubAckOutboundInterceptor> pubAckReceiveInterceptorProvider;

    private final Provider<MetricSubAckInterceptor> subscribeInterceptorProvider;

    private final Provider<AppConnectionListener> appConnectionListenerProvider;

    public ExtensionInitializer_Factory(Provider<EventRegistry> eventRegistryProvider, Provider<InitializerRegistry> initializerRegistryProvider, Provider<MetricRegistry> metricRegistryProvider, Provider<MetricApplicationConfiguration> multiTenancyConfigurationProvider, Provider<MetricPubAckInboundInterceptor> pubAckInboundInterceptorProvider, Provider<MetricPublishInboundInterceptor> inboundInterceptorProvider, Provider<MetricPublishReceiveInterceptor> receiveInterceptorProvider, Provider<MetricPubAckOutboundInterceptor> pubAckReceiveInterceptorProvider, Provider<MetricSubAckInterceptor> subscribeInterceptorProvider, Provider<AppConnectionListener> appConnectionListenerProvider) {
        this.eventRegistryProvider = eventRegistryProvider;
        this.initializerRegistryProvider = initializerRegistryProvider;
        this.metricRegistryProvider = metricRegistryProvider;
        this.multiTenancyConfigurationProvider = multiTenancyConfigurationProvider;
        this.pubAckInboundInterceptorProvider = pubAckInboundInterceptorProvider;
        this.inboundInterceptorProvider = inboundInterceptorProvider;
        this.receiveInterceptorProvider = receiveInterceptorProvider;
        this.pubAckReceiveInterceptorProvider = pubAckReceiveInterceptorProvider;
        this.subscribeInterceptorProvider = subscribeInterceptorProvider;
        this.appConnectionListenerProvider = appConnectionListenerProvider;
    }

    public ExtensionInitializer get() {
        return newInstance((EventRegistry)this.eventRegistryProvider.get(), (InitializerRegistry)this.initializerRegistryProvider.get(), (MetricRegistry)this.metricRegistryProvider.get(), (MetricApplicationConfiguration)this.multiTenancyConfigurationProvider.get(), (MetricPubAckInboundInterceptor)this.pubAckInboundInterceptorProvider.get(), (MetricPublishInboundInterceptor)this.inboundInterceptorProvider.get(), (MetricPublishReceiveInterceptor)this.receiveInterceptorProvider.get(), (MetricPubAckOutboundInterceptor)this.pubAckReceiveInterceptorProvider.get(), (MetricSubAckInterceptor)this.subscribeInterceptorProvider.get(), (AppConnectionListener)this.appConnectionListenerProvider.get());
    }

    public static ExtensionInitializer_Factory create(Provider<EventRegistry> eventRegistryProvider, Provider<InitializerRegistry> initializerRegistryProvider, Provider<MetricRegistry> metricRegistryProvider, Provider<MetricApplicationConfiguration> multiTenancyConfigurationProvider, Provider<MetricPubAckInboundInterceptor> pubAckInboundInterceptorProvider, Provider<MetricPublishInboundInterceptor> inboundInterceptorProvider, Provider<MetricPublishReceiveInterceptor> receiveInterceptorProvider, Provider<MetricPubAckOutboundInterceptor> pubAckReceiveInterceptorProvider, Provider<MetricSubAckInterceptor> subscribeInterceptorProvider, Provider<AppConnectionListener> appConnectionListenerProvider) {
        return new ExtensionInitializer_Factory(eventRegistryProvider, initializerRegistryProvider, metricRegistryProvider, multiTenancyConfigurationProvider, pubAckInboundInterceptorProvider, inboundInterceptorProvider, receiveInterceptorProvider, pubAckReceiveInterceptorProvider, subscribeInterceptorProvider, appConnectionListenerProvider);
    }

    public static ExtensionInitializer newInstance(EventRegistry eventRegistry, InitializerRegistry initializerRegistry, MetricRegistry metricRegistry, MetricApplicationConfiguration multiTenancyConfiguration, MetricPubAckInboundInterceptor pubAckInboundInterceptor, MetricPublishInboundInterceptor inboundInterceptor, MetricPublishReceiveInterceptor receiveInterceptor, MetricPubAckOutboundInterceptor pubAckReceiveInterceptor, MetricSubAckInterceptor subscribeInterceptor, AppConnectionListener appConnectionListener) {
        return new ExtensionInitializer(eventRegistry, initializerRegistry, metricRegistry, multiTenancyConfiguration, pubAckInboundInterceptor, inboundInterceptor, receiveInterceptor, pubAckReceiveInterceptor, subscribeInterceptor, appConnectionListener);
    }
}
