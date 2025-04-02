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

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extensions.schema.Alias;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public interface MetricApplicationConfiguration {
    @NotNull
    Iterator<Alias> applications();

    @NotNull
    LoadingCache<String, String> applicationMap();

    @NotNull
    List<String> publicTopics();

    @NotNull
    Set<String> typeSet();

    <T> void addConfigChangedCallback(@NotNull ConfigChangeCallback<T> paramConfigChangeCallback);

    void addApplicationUpdatedCallback(@NotNull AppUpdatedCallback paramAppUpdatedCallback);

    public static interface AppUpdatedCallback {
        void appUpdated(@NotNull List<Alias> param1List);
    }

    public static interface ConfigChangeCallback<T> {
        void configChanged(@NotNull T param1T);

        @NotNull
        Class<T> type();
    }
}
