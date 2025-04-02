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

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import java.io.File;
import javax.inject.Provider;

@DaggerGenerated
public final class SchemaValidatedYamlReaderFactory_Factory implements Factory<SchemaValidatedYamlReaderFactory> {
    private final Provider<File> extensionFolderProvider;

    private final Provider<ObjectMapper> jsonMapperProvider;

    private final Provider<ObjectMapper> yamlMapperProvider;

    public SchemaValidatedYamlReaderFactory_Factory(Provider<File> extensionFolderProvider, Provider<ObjectMapper> jsonMapperProvider, Provider<ObjectMapper> yamlMapperProvider) {
        this.extensionFolderProvider = extensionFolderProvider;
        this.jsonMapperProvider = jsonMapperProvider;
        this.yamlMapperProvider = yamlMapperProvider;
    }

    public SchemaValidatedYamlReaderFactory get() {
        return newInstance((File)this.extensionFolderProvider.get(), (ObjectMapper)this.jsonMapperProvider.get(), (ObjectMapper)this.yamlMapperProvider.get());
    }

    public static SchemaValidatedYamlReaderFactory_Factory create(Provider<File> extensionFolderProvider, Provider<ObjectMapper> jsonMapperProvider, Provider<ObjectMapper> yamlMapperProvider) {
        return new SchemaValidatedYamlReaderFactory_Factory(extensionFolderProvider, jsonMapperProvider, yamlMapperProvider);
    }

    public static SchemaValidatedYamlReaderFactory newInstance(File extensionFolder, ObjectMapper jsonMapper, ObjectMapper yamlMapper) {
        return new SchemaValidatedYamlReaderFactory(extensionFolder, jsonMapper, yamlMapper);
    }
}
