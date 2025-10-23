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
package com.hivemq.extensions.instantx.ioc;

import com.codahale.metrics.MetricRegistry;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.extension.sdk.api.events.EventRegistry;
import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry;
import com.hivemq.extensions.instantx.ExtensionInitializer;
import com.hivemq.extensions.instantx.ExtensionInitializer_Factory;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfigurationYamlReader;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfigurationYamlReader_Factory;
import com.hivemq.extensions.instantx.configuration.SchemaValidatedYamlReaderFactory;
import com.hivemq.extensions.instantx.configuration.SchemaValidatedYamlReaderFactory_Factory;
import com.hivemq.extensions.instantx.events.AppConnectionListener;
import com.hivemq.extensions.instantx.events.AppConnectionListener_Factory;
import com.hivemq.extensions.instantx.events.MetricPubAckInboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPubAckInboundInterceptor_Factory;
import com.hivemq.extensions.instantx.events.MetricPubAckOutboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPubAckOutboundInterceptor_Factory;
import com.hivemq.extensions.instantx.events.MetricPublishInboundInterceptor;
import com.hivemq.extensions.instantx.events.MetricPublishInboundInterceptor_Factory;
import com.hivemq.extensions.instantx.events.MetricPublishReceiveInterceptor;
import com.hivemq.extensions.instantx.events.MetricPublishReceiveInterceptor_Factory;
import com.hivemq.extensions.instantx.events.MetricSubAckInterceptor;
import com.hivemq.extensions.instantx.events.MetricSubAckInterceptor_Factory;
import com.hivemq.extensions.instantx.ioc.module.ConfigurationModule;
import com.hivemq.extensions.instantx.ioc.module.ConfigurationModule_JsonMapperFactory;
import com.hivemq.extensions.instantx.ioc.module.ConfigurationModule_ProvideConfigurationFactory;
import com.hivemq.extensions.instantx.ioc.module.ConfigurationModule_YamlMapperFactory;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule_ProvideEventRegistryFactory;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule_ProvideExtensionHomeFolderFactory;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule_ProvideInitializerRegistryFactory;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule_ProvideMetricRegistryFactory;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule_ProvideScheduledExecutorServiceFactory;
import com.hivemq.extensions.instantx.metrics.MetricHolder;
import com.hivemq.extensions.instantx.metrics.MetricHolder_Factory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Provider;

@DaggerGenerated
public final class DaggerApplicationMetricsExtension implements ApplicationMetricsExtension {
    private Provider<File> provideExtensionHomeFolderProvider;

    private Provider<ScheduledExecutorService> provideScheduledExecutorServiceProvider;

    private Provider<ObjectMapper> jsonMapperProvider;

    private Provider<ObjectMapper> yamlMapperProvider;

    private Provider<SchemaValidatedYamlReaderFactory> schemaValidatedYamlReaderFactoryProvider;

    private Provider<MetricApplicationConfigurationYamlReader> metricApplicationConfigurationYamlReaderProvider;

    private Provider<MetricApplicationConfiguration> provideConfigurationProvider;

    private Provider<EventRegistry> provideEventRegistryProvider;

    private Provider<InitializerRegistry> provideInitializerRegistryProvider;

    private Provider<MetricRegistry> provideMetricRegistryProvider;

    private Provider<MetricHolder> metricHolderProvider;

    private Provider<MetricPubAckInboundInterceptor> metricPubAckInboundInterceptorProvider;

    private Provider<MetricPublishInboundInterceptor> metricPublishInboundInterceptorProvider;

    private Provider<MetricPublishReceiveInterceptor> metricPublishReceiveInterceptorProvider;

    private Provider<MetricPubAckOutboundInterceptor> metricPubAckOutboundInterceptorProvider;

    private Provider<MetricSubAckInterceptor> metricSubAckInterceptorProvider;

    private Provider<AppConnectionListener> appConnectionListenerProvider;

    private Provider<ExtensionInitializer> extensionInitializerProvider;

    private DaggerApplicationMetricsExtension(HiveMQBindingsModule hiveMQBindingsModuleParam,
            ConfigurationModule configurationModuleParam) {
        initialize(hiveMQBindingsModuleParam, configurationModuleParam);
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(HiveMQBindingsModule hiveMQBindingsModuleParam,
            ConfigurationModule configurationModuleParam) {
        this.provideExtensionHomeFolderProvider = DoubleCheck.provider(
                (Provider) HiveMQBindingsModule_ProvideExtensionHomeFolderFactory.create(hiveMQBindingsModuleParam));
        this.provideScheduledExecutorServiceProvider = DoubleCheck
                .provider((Provider) HiveMQBindingsModule_ProvideScheduledExecutorServiceFactory
                        .create(hiveMQBindingsModuleParam));
        this.jsonMapperProvider = DoubleCheck
                .provider((Provider) ConfigurationModule_JsonMapperFactory.create(configurationModuleParam));
        this.yamlMapperProvider = DoubleCheck
                .provider((Provider) ConfigurationModule_YamlMapperFactory.create(configurationModuleParam));
        this.schemaValidatedYamlReaderFactoryProvider = DoubleCheck.provider(
                (Provider) SchemaValidatedYamlReaderFactory_Factory.create(this.provideExtensionHomeFolderProvider,
                        this.jsonMapperProvider, this.yamlMapperProvider));
        this.metricApplicationConfigurationYamlReaderProvider = DoubleCheck
                .provider((Provider) MetricApplicationConfigurationYamlReader_Factory.create(
                        this.provideExtensionHomeFolderProvider, this.provideScheduledExecutorServiceProvider,
                        this.schemaValidatedYamlReaderFactoryProvider));
        this.provideConfigurationProvider = DoubleCheck
                .provider((Provider) ConfigurationModule_ProvideConfigurationFactory.create(configurationModuleParam,
                        this.metricApplicationConfigurationYamlReaderProvider));
        this.provideEventRegistryProvider = DoubleCheck.provider(
                (Provider) HiveMQBindingsModule_ProvideEventRegistryFactory.create(hiveMQBindingsModuleParam));
        this.provideInitializerRegistryProvider = DoubleCheck.provider(
                (Provider) HiveMQBindingsModule_ProvideInitializerRegistryFactory.create(hiveMQBindingsModuleParam));
        this.provideMetricRegistryProvider = DoubleCheck.provider(
                (Provider) HiveMQBindingsModule_ProvideMetricRegistryFactory.create(hiveMQBindingsModuleParam));
        this.metricHolderProvider = DoubleCheck.provider((Provider) MetricHolder_Factory
                .create(this.provideMetricRegistryProvider, this.provideConfigurationProvider));
        this.metricPubAckInboundInterceptorProvider = DoubleCheck
                .provider((Provider) MetricPubAckInboundInterceptor_Factory.create(this.metricHolderProvider));
        this.metricPublishInboundInterceptorProvider = DoubleCheck
                .provider((Provider) MetricPublishInboundInterceptor_Factory.create(this.metricHolderProvider));
        this.metricPublishReceiveInterceptorProvider = DoubleCheck
                .provider((Provider) MetricPublishReceiveInterceptor_Factory.create(this.metricHolderProvider));
        this.metricPubAckOutboundInterceptorProvider = DoubleCheck
                .provider((Provider) MetricPubAckOutboundInterceptor_Factory.create(this.metricHolderProvider));
        this.metricSubAckInterceptorProvider = DoubleCheck
                .provider((Provider) MetricSubAckInterceptor_Factory.create(this.metricHolderProvider));
        this.appConnectionListenerProvider = DoubleCheck
                .provider((Provider) AppConnectionListener_Factory.create(this.metricHolderProvider));
        this.extensionInitializerProvider = DoubleCheck
                .provider((Provider) ExtensionInitializer_Factory.create(this.provideEventRegistryProvider,
                        this.provideInitializerRegistryProvider, this.provideMetricRegistryProvider,
                        this.provideConfigurationProvider, this.metricPubAckInboundInterceptorProvider,
                        this.metricPublishInboundInterceptorProvider, this.metricPublishReceiveInterceptorProvider,
                        this.metricPubAckOutboundInterceptorProvider, this.metricSubAckInterceptorProvider,
                        this.appConnectionListenerProvider));
    }

    public MetricApplicationConfiguration configuration() {
        return (MetricApplicationConfiguration) this.provideConfigurationProvider.get();
    }

    public ExtensionInitializer initializer() {
        return (ExtensionInitializer) this.extensionInitializerProvider.get();
    }

    public static final class Builder {
        private HiveMQBindingsModule hiveMQBindingsModule;

        private ConfigurationModule configurationModule;

        public Builder hiveMQBindingsModule(HiveMQBindingsModule hiveMQBindingsModule) {
            this.hiveMQBindingsModule = (HiveMQBindingsModule) Preconditions.checkNotNull(hiveMQBindingsModule);
            return this;
        }

        public Builder configurationModule(ConfigurationModule configurationModule) {
            this.configurationModule = (ConfigurationModule) Preconditions.checkNotNull(configurationModule);
            return this;
        }

        public ApplicationMetricsExtension build() {
            Preconditions.checkBuilderRequirement(this.hiveMQBindingsModule, HiveMQBindingsModule.class);
            if (this.configurationModule == null)
                this.configurationModule = new ConfigurationModule();
            return new DaggerApplicationMetricsExtension(this.hiveMQBindingsModule, this.configurationModule);
        }
    }
}
