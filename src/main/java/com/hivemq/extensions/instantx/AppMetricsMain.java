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

import com.hivemq.extension.sdk.api.ExtensionMain;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.annotations.Nullable;
import com.hivemq.extension.sdk.api.parameter.ExtensionInformation;
import com.hivemq.extension.sdk.api.parameter.ExtensionStartInput;
import com.hivemq.extension.sdk.api.parameter.ExtensionStartOutput;
import com.hivemq.extension.sdk.api.parameter.ExtensionStopInput;
import com.hivemq.extension.sdk.api.parameter.ExtensionStopOutput;
import com.hivemq.extensions.instantx.ioc.ApplicationMetricsExtension;
import com.hivemq.extensions.instantx.ioc.DaggerApplicationMetricsExtension;
import com.hivemq.extensions.instantx.ioc.module.HiveMQBindingsModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppMetricsMain implements ExtensionMain {
    @NotNull
    private static final Logger log = LoggerFactory.getLogger(AppMetricsMain.class);

    @Nullable
    private ExtensionInitializer initializer;

    public void extensionStart(@NotNull ExtensionStartInput input, @NotNull ExtensionStartOutput output) {
        try {
            ExtensionInformation extensionInformation = input.getExtensionInformation();
            ApplicationMetricsExtension extension = DaggerApplicationMetricsExtension.builder()
                    .hiveMQBindingsModule(new HiveMQBindingsModule(input)).build();
            this.initializer = extension.initializer();
            this.initializer.start();
            log.info("Started PoC " + extensionInformation.getName() + ":" + extensionInformation.getVersion());
        } catch (Exception e) {
            log.error("Exception thrown at extension start: ", e);
        }
    }

    public void extensionStop(@NotNull ExtensionStopInput input, @NotNull ExtensionStopOutput output) {
        ExtensionInformation extensionInformation = input.getExtensionInformation();
        if (this.initializer != null)
            this.initializer.stop();
        log.info("Stopped PoC " + extensionInformation.getName() + ":" + extensionInformation.getVersion());
    }
}
