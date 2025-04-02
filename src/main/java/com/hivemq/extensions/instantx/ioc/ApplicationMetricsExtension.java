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

import com.hivemq.extensions.instantx.ExtensionInitializer;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import com.hivemq.extensions.instantx.ioc.module.ConfigurationModule;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule;
import dagger.Component;
import javax.inject.Singleton;

@Component(modules = {HiveMQBindingsModule.class, ConfigurationModule.class})
@Singleton
public interface ApplicationMetricsExtension {
    MetricApplicationConfiguration configuration();

    ExtensionInitializer initializer();
}
