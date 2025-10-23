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
package com.hivemq.extensions.instantx.events;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.client.parameter.ConnectionAttributeStore;
import com.hivemq.extension.sdk.api.events.client.ClientLifecycleEventListener;
import com.hivemq.extension.sdk.api.events.client.parameters.AuthenticationSuccessfulInput;
import com.hivemq.extension.sdk.api.events.client.parameters.ConnectionStartInput;
import com.hivemq.extension.sdk.api.events.client.parameters.DisconnectEventInput;
import com.hivemq.extension.sdk.api.packets.general.DisconnectedReasonCode;
import com.hivemq.extensions.instantx.metrics.MetricHolder;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class AppConnectionListener implements ClientLifecycleEventListener {
    @NotNull
    private static final Logger log = LoggerFactory.getLogger(AppConnectionListener.class);

    @NotNull
    private final MetricHolder metricHolder;

    protected static final String START = "con_start";

    @Inject
    public AppConnectionListener(MetricHolder metricHolder) {
        this.metricHolder = metricHolder;
    }

    public void onMqttConnectionStart(@NotNull ConnectionStartInput input) {
        ConnectionAttributeStore store = input.getConnectionInformation().getConnectionAttributeStore();
        String uname = input.getConnectPacket().getUserName().orElse("unknown");
        String appAlias = this.metricHolder.getAppAliasByUserName(uname);
        store.put("app", ByteBuffer.wrap(appAlias.getBytes(StandardCharsets.UTF_8)));
        log.debug("App {} connect, clientId: {} ", appAlias, input.getClientInformation().getClientId());
    }

    public void onAuthenticationSuccessful(@NotNull AuthenticationSuccessfulInput input) {
        ConnectionAttributeStore store = input.getConnectionInformation().getConnectionAttributeStore();
        String appAlias = this.metricHolder.getValueAsStringFrom(store.get("app"));
        String conStartTime = "" + System.currentTimeMillis();
        String clientId = input.getClientInformation().getClientId();
        store.put("con_start", ByteBuffer.wrap(conStartTime.getBytes(StandardCharsets.UTF_8)));
        this.metricHolder.getAppConnectionCount(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias).inc();
        log.debug("App {} authn success, startTime: {}, clientId: {} ", new Object[] { appAlias, conStartTime, input
                .getClientInformation().getClientId() });
    }

    public void onDisconnect(@NotNull DisconnectEventInput input) {
        ConnectionAttributeStore store = input.getConnectionInformation().getConnectionAttributeStore();
        String appAlias = this.metricHolder.getValueAsStringFrom(store.get("app"));
        Optional<ByteBuffer> startTime = store.get("con_start");
        String clientId = input.getClientInformation().getClientId();
        Optional<DisconnectedReasonCode> reasonCode = input.getReasonCode();
        if (reasonCode.isPresent() && ((DisconnectedReasonCode) reasonCode
                .get()).equals(DisconnectedReasonCode.NOT_AUTHORIZED)) {
            this.metricHolder
                    .getAppDisconnectUnauthorizedCount(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias)
                    .inc();
            log.debug("App {} authz disconnect, clientId: {}", appAlias, clientId);
        }
        if (startTime.isPresent()) {
            long start = Long.parseLong(this.metricHolder.getValueAsStringFrom(startTime));
            long durationInSeconds = (System.currentTimeMillis() - start) / 1000L;
            this.metricHolder.getAppConnectionTime(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias)
                    .inc(durationInSeconds);
            log.debug("App {} disconnects, conn duration {} secs, clientId: {}",
                    new Object[] { appAlias, Long.valueOf(durationInSeconds), clientId });
        } else {
            log.debug("App {} no start time, clientId {}", appAlias, clientId);
        }
    }
}
