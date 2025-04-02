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
package com.hivemq.extensions.schema;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"config"})
public class ApplicationMetricConfig {
    @JsonProperty("config")
    private Config config;

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("config")
    public Config getConfig() {
        return this.config;
    }

    @JsonProperty("config")
    public void setConfig(Config config) {
        this.config = config;
    }

    public ApplicationMetricConfig withConfig(Config config) {
        this.config = config;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    public ApplicationMetricConfig withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ApplicationMetricConfig.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("config");
        sb.append('=');
        sb.append((this.config == null) ? "<null>" : this.config);
        sb.append(',');
        sb.append("additionalProperties");
        sb.append('=');
        sb.append((this.additionalProperties == null) ? "<null>" : this.additionalProperties);
        sb.append(',');
        if (sb.charAt(sb.length() - 1) == ',') {
            sb.setCharAt(sb.length() - 1, ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    public int hashCode() {
        int result = 1;
        result = result * 31 + ((this.config == null) ? 0 : this.config.hashCode());
        result = result * 31 + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof ApplicationMetricConfig))
            return false;
        ApplicationMetricConfig rhs = (ApplicationMetricConfig)other;
        return ((this.config == rhs.config || (this.config != null && this.config.equals(rhs.config))) && (this.additionalProperties == rhs.additionalProperties || (this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties))));
    }
}
