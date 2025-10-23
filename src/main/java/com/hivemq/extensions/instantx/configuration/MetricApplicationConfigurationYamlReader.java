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

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.google.common.annotations.VisibleForTesting;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extensions.schema.Alias;
import com.hivemq.extensions.schema.ApplicationMetricConfig;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MetricApplicationConfigurationYamlReader implements MetricApplicationConfiguration {
    @NotNull
    public static final String CONFIG_FILE_NAME = "application-metric-configuration.yaml";

    @NotNull
    private static final Logger log = LoggerFactory.getLogger(MetricApplicationConfigurationYamlReader.class);

    @NotNull
    private final File extensionFolder;

    @NotNull
    private final AtomicReference<ApplicationMetricConfig> currentConfiguration;

    @NotNull
    private final List<MetricApplicationConfiguration.AppUpdatedCallback> appUpdatedCallbacks;

    @NotNull
    private final Map<String, String> aliasMap;

    @NotNull
    private final SchemaValidatedYamlReaderFactory.SchemaValidatedConfigReader<ApplicationMetricConfig> configReader;

    @NotNull
    private final LoadingCache<String, String> aliasCache;

    @NotNull
    private final List<MetricApplicationConfiguration.ConfigChangeCallback<?>> configChangeCallbacks;

    @Inject
    public MetricApplicationConfigurationYamlReader(@Named("extension-home") @NotNull File extensionFolder,
            @NotNull ScheduledExecutorService scheduledExecutor,
            @NotNull SchemaValidatedYamlReaderFactory configFactory) {
        try {
            this.configReader = configFactory.createReader("application-metric-configuration.yaml",
                    "application-metric-config.json", ApplicationMetricConfig.class);
        } catch (IOException | ProcessingException e) {
            log.error("Could not read tenant configuration", e);
            throw new InvalidConfigurationException("Could not load tenant configuration");
        }
        this.aliasMap = new HashMap<>();
        Objects.requireNonNull(this.aliasMap);
        this.aliasCache = Caffeine.newBuilder().maximumSize(100L).expireAfterWrite(Duration.ofSeconds(60L))
                .build(this.aliasMap::get);
        this.extensionFolder = extensionFolder;
        this.appUpdatedCallbacks = new CopyOnWriteArrayList<>();
        this.configChangeCallbacks = new CopyOnWriteArrayList<>();
        this.currentConfiguration = new AtomicReference<>(null);
        updateConfiguration();
    }

    @VisibleForTesting
    public void updateConfiguration() {
        File configFile = new File(this.extensionFolder, "application-metric-configuration.yaml");
        try {
            ApplicationMetricConfig newConfiguration = this.configReader.read();
            this.currentConfiguration.set(newConfiguration);
            newConfiguration.getConfig().getApplicationAliasMap()
                    .forEach(alias -> this.aliasMap.put(alias.getUuid(), alias.getName()));
            return;
        } catch (FileNotFoundException e) {
            log.error("Could not find configuration file at '{}'.", configFile.getPath());
            log.debug("Original exception: ", e);
        } catch (IOException e) {
            log.error("Failed to read configuration file '{}'", configFile.getPath(), e);
        } catch (ProcessingException e) {
            log.error("Configuration file failed validation:", (Throwable) e);
        }
        throw new InvalidConfigurationException("Failed to read configuration");
    }

    @NotNull
    public Iterator<Alias> applications() {
        return ((ApplicationMetricConfig) this.currentConfiguration.get()).getConfig().getApplicationAliasMap()
                .iterator();
    }

    @NotNull
    public LoadingCache<String, String> applicationMap() {
        return this.aliasCache;
    }

    @NotNull
    public List<String> publicTopics() {
        return ((ApplicationMetricConfig) this.currentConfiguration.get()).getConfig().getPublicMessageTopics();
    }

    @NotNull
    public Set<String> typeSet() {
        return new HashSet<>(
                ((ApplicationMetricConfig) this.currentConfiguration.get()).getConfig().getApplicationTypes());
    }

    public <T> void addConfigChangedCallback(@NotNull MetricApplicationConfiguration.ConfigChangeCallback<T> callback) {
    }

    public void addApplicationUpdatedCallback(@NotNull MetricApplicationConfiguration.AppUpdatedCallback callback) {
    }
}
