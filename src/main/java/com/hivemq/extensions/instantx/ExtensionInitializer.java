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
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.events.EventRegistry;
import com.hivemq.extension.sdk.api.interceptor.puback.PubackInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.puback.PubackOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.suback.SubackOutboundInterceptor;
import com.hivemq.extension.sdk.api.services.intializer.ClientInitializer;
import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import com.hivemq.extensions.instantx.events.AppConnectionListener;
import com.hivemq.extensions.instantx.events.MetricPubAckInboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPubAckOutboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPublishInboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPublishReceiveInterceptor;
import com.hivemq.extensions.instantx.events.MetricSubAckInterceptor;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class ExtensionInitializer {
    @NotNull
    private static final Logger log = LoggerFactory.getLogger(ExtensionInitializer.class);

    @NotNull
    final InitializerRegistry initializerRegistry;

    @NotNull
    final MetricApplicationConfiguration configuration;

    @NotNull
    final MetricRegistry metricRegistry;

    @NotNull
    final EventRegistry eventRegistry;

    @NotNull
    final MetricPubAckInboundInterceptor pubAckInboundInterceptor;

    @NotNull
    final MetricPublishInboundInterceptor inboundInterceptor;

    @NotNull
    final MetricPublishReceiveInterceptor receiveInterceptor;

    @NotNull
    final MetricPubAckOutboundInterceptor pubAckReceiveInterceptor;

    @NotNull
    final MetricSubAckInterceptor subscribeInterceptor;

    @NotNull
    final AppConnectionListener appConnectionListener;

    @Inject
    public ExtensionInitializer(@NotNull EventRegistry eventRegistry, @NotNull InitializerRegistry initializerRegistry, @NotNull MetricRegistry metricRegistry, @NotNull MetricApplicationConfiguration multiTenancyConfiguration, @NotNull MetricPubAckInboundInterceptor pubAckInboundInterceptor, @NotNull MetricPublishInboundInterceptor inboundInterceptor, @NotNull MetricPublishReceiveInterceptor receiveInterceptor, @NotNull MetricPubAckOutboundInterceptor pubAckReceiveInterceptor, @NotNull MetricSubAckInterceptor subscribeInterceptor, @NotNull AppConnectionListener appConnectionListener) {
        this.eventRegistry = eventRegistry;
        this.initializerRegistry = initializerRegistry;
        this.configuration = multiTenancyConfiguration;
        this.metricRegistry = metricRegistry;
        this.pubAckInboundInterceptor = pubAckInboundInterceptor;
        this.pubAckReceiveInterceptor = pubAckReceiveInterceptor;
        this.inboundInterceptor = inboundInterceptor;
        this.receiveInterceptor = receiveInterceptor;
        this.subscribeInterceptor = subscribeInterceptor;
        this.appConnectionListener = appConnectionListener;
    }

    public void start() {
        ClientInitializer clientInitializer = (initializerInput, clientContext) -> {
            clientContext.addPubackInboundInterceptor((PubackInboundInterceptor)this.pubAckInboundInterceptor);
            clientContext.addPublishInboundInterceptor((PublishInboundInterceptor)this.inboundInterceptor);
            clientContext.addPublishOutboundInterceptor((PublishOutboundInterceptor)this.receiveInterceptor);
            clientContext.addPubackOutboundInterceptor((PubackOutboundInterceptor)this.pubAckReceiveInterceptor);
            clientContext.addSubackOutboundInterceptor((SubackOutboundInterceptor)this.subscribeInterceptor);
        };
        this.initializerRegistry.setClientInitializer(clientInitializer);
        this.eventRegistry.setClientLifecycleEventListener(clientLifecycleEventListenerProviderInput -> this.appConnectionListener);
        log.info("Registered application instantx metrics enforcing interceptors");
    }

    public void stop() {
        log.info("Registered application instantx metrics removed interceptors");
    }
}
