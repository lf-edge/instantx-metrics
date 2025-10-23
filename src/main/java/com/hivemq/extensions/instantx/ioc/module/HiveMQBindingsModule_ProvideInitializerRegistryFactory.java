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

import com.hivemq.extension.sdk.api.services.intializer.InitializerRegistry;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;

@DaggerGenerated
public final class HiveMQBindingsModule_ProvideInitializerRegistryFactory implements Factory<InitializerRegistry> {
    private final HiveMQBindingsModule module;

    public HiveMQBindingsModule_ProvideInitializerRegistryFactory(HiveMQBindingsModule module) {
        this.module = module;
    }

    public InitializerRegistry get() {
        return provideInitializerRegistry(this.module);
    }

    public static HiveMQBindingsModule_ProvideInitializerRegistryFactory create(HiveMQBindingsModule module) {
        return new HiveMQBindingsModule_ProvideInitializerRegistryFactory(module);
    }

    public static InitializerRegistry provideInitializerRegistry(HiveMQBindingsModule instance) {
        return (InitializerRegistry) Preconditions.checkNotNullFromProvides(instance.provideInitializerRegistry());
    }
}
