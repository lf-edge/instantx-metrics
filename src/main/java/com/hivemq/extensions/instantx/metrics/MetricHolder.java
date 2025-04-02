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
package com.hivemq.extensions.instantx.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.MetricRegistry;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.client.parameter.ConnectionAttributeStore;
import com.hivemq.extension.sdk.api.parameter.ClientBasedInput;
import com.hivemq.extensions.instantx.configuration.MetricApplicationConfiguration;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MetricHolder {
    public static final String PUBLISH_ACK_COUNT = ".messages.publish.ack.count";

    public static final String PUBLIC_PUBLISH_COUNT = ".messages.publish.public.count";

    public static final String PRIVATE_PUBLISH_COUNT = ".messages.publish.private.count";

    public static final String RECEIVE_ACK_COUNT = ".messages.receive.ack.count";

    public static final String PUBLIC_RECEIVE_COUNT = ".messages.receive.public.count";

    public static final String PRIVATE_RECEIVE_COUNT = ".messages.receive.private.count";

    public static final String PUBLISH_UNAUTHORIZED_COUNT = ".publishes.unauthorized.count";

    public static final String SUBSCRIBE_UNAUTHORIZED_COUNT = ".subscriptions.unauthorized.count";

    public static final String DISCONNECT_UNAUTHORIZED_COUNT = ".disconnect.unauthorized.count";

    public static final String CONNECTION_COUNT = ".connection.count";

    public static final String CONNECTION_TIME = ".connection.time";

    public static final String APP = "app";

    public static final String UNKNOWN = "unknown";

    @NotNull
    public static final String METRIC_ROOT = "com.hivemq.instantx.app.";

    @NotNull
    private final MetricRegistry metricRegistry;

    @NotNull
    private final MetricApplicationConfiguration config;

    @Inject
    public MetricHolder(@NotNull MetricRegistry registry, @NotNull MetricApplicationConfiguration config) {
        this.metricRegistry = registry;
        this.config = config;
    }

    @NotNull
    public Counter getAppPubAckCount(String app) {
        return getCounter(app, PUBLISH_ACK_COUNT);
    }

    @NotNull
    public Counter getAppPublicPublishCount(String app) {
        return getCounter(app, PUBLIC_PUBLISH_COUNT);
    }

    @NotNull
    public Counter getAppPrivatePublishCount(String app) {
        return getCounter(app, PRIVATE_PUBLISH_COUNT);
    }

    @NotNull
    public Counter getAppReceiveAckCount(String app) {
        return getCounter(app, RECEIVE_ACK_COUNT);
    }

    @NotNull
    public Counter getAppPublicReceiveCount(String app) {
        return getCounter(app, PUBLIC_RECEIVE_COUNT);
    }

    @NotNull
    public Counter getAppPrivateReceiveCount(String app) {
        return getCounter(app, PRIVATE_RECEIVE_COUNT);
    }

    @NotNull
    public Counter getAppConnectionCount(String app) {
        return getCounter(app, CONNECTION_COUNT);
    }

    @NotNull
    public Counter getAppPublishUnauthorizedCount(String app) {
        return getCounter(app, PUBLISH_UNAUTHORIZED_COUNT);
    }

    @NotNull
    public Counter getAppSubscribeUnauthorizedCount(String app) {
        return getCounter(app, SUBSCRIBE_UNAUTHORIZED_COUNT);
    }

    @NotNull
    public Counter getAppDisconnectUnauthorizedCount(String app) {
        return getCounter(app, DISCONNECT_UNAUTHORIZED_COUNT);
    }

    @NotNull
    public Counter getAppConnectionTime(String app) {
        return getCounter(app, CONNECTION_TIME);
    }

    @NotNull
    public Counter getAppPublicReceiveByTypeCount(@NotNull String app, @NotNull String topic) {
        return getCounter(app, topic, PUBLIC_RECEIVE_COUNT);
    }

    @NotNull
    public Counter getAppPrivateReceiveByTypeCount(@NotNull String app, @NotNull String topic) {
        return getCounter(app, topic, PRIVATE_RECEIVE_COUNT);
    }

    @NotNull
    public Counter getAppPublicPublishByTypeCount(@NotNull String app, @NotNull String topic) {
        return getCounter(app, topic, PUBLIC_PUBLISH_COUNT);
    }

    @NotNull
    public Counter getAppPrivatePublishByTypeCount(@NotNull String app, @NotNull String topic) {
        return getCounter(app, topic, PRIVATE_PUBLISH_COUNT);
    }

    @NotNull
    private Counter getCounter(@NotNull String app, @NotNull String topic, @NotNull String metricType) {
        List<String> levels = getTopicLevels(topic);
        String service = (levels.isEmpty() ? "": levels.get(0));
        String subService = (levels.isEmpty() || levels.size() < 2 ? "": levels.get(1));
        String subServiceGroup = (levels.isEmpty() || levels.size() < 3 ? "": levels.get(2));

        return this.metricRegistry.counter(METRIC_ROOT + app + "." + service + "." + subService + metricType);
    }

    @NotNull
    private Counter getCounter(String app, String metricType) {
        return this.metricRegistry.counter(METRIC_ROOT + app + metricType);
    }

    @NotNull
    public List<String> getTopicLevels(@NotNull String topic) {
        List<String> levels = MqttTopic.of(topic).getLevels();
        if (levels.size() < 2)
            return new ArrayList<>();
        return levels;
    }

    @NotNull
    public String getAppName(@NotNull ClientBasedInput input) {
        ConnectionAttributeStore store = input.getConnectionInformation().getConnectionAttributeStore();
        return getValueAsStringFrom(store.get("app"));
    }

    @NotNull
    public String getAppAliasByUserName(@NotNull String uuidString) {
        String alias = (String)this.config.applicationMap().get(uuidString);
        return (alias != null) ? alias : uuidString;
    }

    public boolean isPublicMessage(@NotNull String topic) {
        boolean isMatching = false;
        for (String filter : this.config.publicTopics()) {
            if (MqttTopicFilter.of(filter).matches(MqttTopic.of(topic))) {
                isMatching = true;
                break;
            }
        }
        return isMatching;
    }

    @NotNull
    public String getValueAsStringFrom(@NotNull Optional<ByteBuffer> attributeBuffer) {
        byte[] attributeValue;
        if (attributeBuffer.isEmpty()) {
            attributeValue = new byte[0];
        } else {
            attributeValue = new byte[((ByteBuffer)attributeBuffer.get()).remaining()];
            ((ByteBuffer)attributeBuffer.get()).get(attributeValue);
        }
        return new String(attributeValue, StandardCharsets.UTF_8);
    }
}
