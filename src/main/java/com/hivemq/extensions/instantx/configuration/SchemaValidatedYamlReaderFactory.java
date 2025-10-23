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

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonschema.core.exceptions.ProcessingException;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchema;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SchemaValidatedYamlReaderFactory {
    @NotNull
    public static final String VALIDATION_FAILED_MESSAGE = "Config file %s has failed validation, will not be loaded";

    @NotNull
    private static final Logger log = LoggerFactory.getLogger(SchemaValidatedYamlReaderFactory.class);

    @NotNull
    private final File extensionFolder;

    @NotNull
    private final ObjectMapper jsonMapper;

    @NotNull
    private final ObjectMapper yamlMapper;

    @Inject
    public SchemaValidatedYamlReaderFactory(@Named("extension-home") @NotNull File extensionFolder,
            @Named("jsonMapper") @NotNull ObjectMapper jsonMapper,
            @Named("yamlMapper") @NotNull ObjectMapper yamlMapper) {
        this.extensionFolder = extensionFolder;
        this.jsonMapper = jsonMapper;
        this.yamlMapper = yamlMapper;
    }

    @NotNull
    public <T> SchemaValidatedConfigReader<T> createReader(@NotNull String configFile, @NotNull String schemaName,
            @NotNull Class<T> clazz) throws IOException, ProcessingException {
        JsonSchema jsonSchema = JsonSchemaFactory.byDefault().getJsonSchema(this.jsonMapper
                .readTree(MetricApplicationConfigurationYamlReader.class.getResource("/schema/" + schemaName)));
        return new SchemaValidatedConfigReader<>(this.extensionFolder, this.yamlMapper, configFile, jsonSchema, clazz);
    }

    public static class SchemaValidatedConfigReader<T> {
        @NotNull
        private final File extensionFolder;

        @NotNull
        private final ObjectMapper yamlMapper;

        @NotNull
        private final String configFile;

        @NotNull
        private final JsonSchema jsonSchema;

        @NotNull
        private final Class<T> clazz;

        public SchemaValidatedConfigReader(@Named("extension-home") @NotNull File extensionFolder,
                @Named("yamlMapper") @NotNull ObjectMapper yamlMapper, @NotNull String configFile,
                @NotNull JsonSchema jsonSchema, @NotNull Class<T> clazz) {
            this.extensionFolder = extensionFolder;
            this.yamlMapper = yamlMapper;
            this.configFile = configFile;
            this.jsonSchema = jsonSchema;
            this.clazz = clazz;
        }

        @NotNull
        public T read() throws IOException, ProcessingException {
            FileReader fileReader = new FileReader(new File(this.extensionFolder, this.configFile));
            try {
                JsonNode configurationJsonNode = this.yamlMapper.readTree(fileReader);
                ProcessingReport validation = this.jsonSchema.validate(configurationJsonNode);
                if (!validation.isSuccess()) {
                    validation.forEach(msg -> SchemaValidatedYamlReaderFactory.log.error(msg.asJson().toString()));
                    throw new InvalidConfigurationException(
                            String.format("Config file %s has failed validation, will not be loaded",
                                    new Object[] { this.configFile }));
                }
                T newConfiguration = (T) this.yamlMapper.treeToValue((TreeNode) configurationJsonNode, this.clazz);
                T t1 = newConfiguration;
                fileReader.close();
                return t1;
            } catch (Throwable throwable) {
                try {
                    fileReader.close();
                } catch (Throwable throwable1) {
                    throwable.addSuppressed(throwable1);
                }
                throw throwable;
            }
        }
    }
}
