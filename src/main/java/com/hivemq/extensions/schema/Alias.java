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

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"uuid", "name"})
public class Alias {
    @JsonProperty("uuid")
    @JsonPropertyDescription("the user name from the client")
    private String uuid;

    @JsonProperty("name")
    @JsonPropertyDescription("the alias to be used fro metric")
    private String name;

    @JsonProperty("uuid")
    public String getUuid() {
        return this.uuid;
    }

    @JsonProperty("uuid")
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Alias withUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    @JsonProperty("name")
    public String getName() {
        return this.name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    public Alias withName(String name) {
        this.name = name;
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Alias.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("uuid");
        sb.append('=');
        sb.append((this.uuid == null) ? "<null>" : this.uuid);
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append((this.name == null) ? "<null>" : this.name);
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
        result = result * 31 + ((this.name == null) ? 0 : this.name.hashCode());
        result = result * 31 + ((this.uuid == null) ? 0 : this.uuid.hashCode());
        return result;
    }

    public boolean equals(Object other) {
        if (other == this)
            return true;
        if (!(other instanceof Alias))
            return false;
        Alias rhs = (Alias)other;
        return ((this.name == rhs.name || (this.name != null && this.name.equals(rhs.name))) && (this.uuid == rhs.uuid || (this.uuid != null && this.uuid.equals(rhs.uuid))));
    }
}
