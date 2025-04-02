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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"applicationAliasMap", "publicMessageTopics", "applicationTypes"})
public class Config {
    @JsonProperty("applicationAliasMap")
    private List<Alias> applicationAliasMap = new ArrayList<>();

    @JsonProperty("publicMessageTopics")
    private List<String> publicMessageTopics = new ArrayList<>();

    @JsonProperty("applicationTypes")
    private List<String> applicationTypes = new ArrayList<>();

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<>();

    @JsonProperty("applicationAliasMap")
    public List<Alias> getApplicationAliasMap() {
        return this.applicationAliasMap;
    }

    @JsonProperty("applicationAliasMap")
    public void setApplicationAliasMap(List<Alias> applicationAliasMap) {
        this.applicationAliasMap = applicationAliasMap;
    }

    public Config withApplicationAliasMap(List<Alias> applicationAliasMap) {
        this.applicationAliasMap = applicationAliasMap;
        return this;
    }

    @JsonProperty("publicMessageTopics")
    public List<String> getPublicMessageTopics() {
        return this.publicMessageTopics;
    }

    @JsonProperty("publicMessageTopics")
    public void setPublicMessageTopics(List<String> publicMessageTopics) {
        this.publicMessageTopics = publicMessageTopics;
    }

    public Config withPublicMessageTopics(List<String> publicMessageTopics) {
        this.publicMessageTopics = publicMessageTopics;
        return this;
    }

    @JsonProperty("applicationTypes")
    public List<String> getApplicationTypes() {
        return this.applicationTypes;
    }

    @JsonProperty("applicationTypes")
    public void setApplicationTypes(List<String> applicationTypes) {
        this.applicationTypes = applicationTypes;
    }

    public Config withApplicationTypes(List<String> applicationTypes) {
        this.applicationTypes = applicationTypes;
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

    public Config withAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Config.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("applicationAliasMap");
        sb.append('=');
        sb.append((this.applicationAliasMap == null) ? "<null>" : this.applicationAliasMap);
        sb.append(',');
        sb.append("publicMessageTopics");
        sb.append('=');
        sb.append((this.publicMessageTopics == null) ? "<null>" : this.publicMessageTopics);
        sb.append(',');
        sb.append("applicationTypes");
        sb.append('=');
        sb.append((this.applicationTypes == null) ? "<null>" : this.applicationTypes);
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
        result = result * 31 + ((this.publicMessageTopics == null) ? 0 : this.publicMessageTopics.hashCode());
        result = result * 31 + ((this.applicationTypes == null) ? 0 : this.applicationTypes.hashCode());
        result = result * 31 + ((this.additionalProperties == null) ? 0 : this.additionalProperties.hashCode());
        result = result * 31 + ((this.applicationAliasMap == null) ? 0 : this.applicationAliasMap.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Config))
            return false;
        Config rhs = (Config)other;
        return ((this.publicMessageTopics == rhs.publicMessageTopics || (this.publicMessageTopics != null && this.publicMessageTopics.equals(rhs.publicMessageTopics))) && (this.applicationTypes == rhs.applicationTypes || (this.applicationTypes != null && this.applicationTypes.equals(rhs.applicationTypes))) && (this.additionalProperties == rhs.additionalProperties || (this.additionalProperties != null && this.additionalProperties.equals(rhs.additionalProperties))) && (this.applicationAliasMap == rhs.applicationAliasMap || (this.applicationAliasMap != null && this.applicationAliasMap.equals(rhs.applicationAliasMap))));
    }
}
