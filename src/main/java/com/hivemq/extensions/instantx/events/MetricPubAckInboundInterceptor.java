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
import com.hivemq.extension.sdk.api.interceptor.puback.PubackInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.puback.parameter.PubackInboundInput;
import com.hivemq.extension.sdk.api.interceptor.puback.parameter.PubackInboundOutput;
import com.hivemq.extension.sdk.api.packets.publish.AckReasonCode;
import com.hivemq.extension.sdk.api.parameter.ClientBasedInput;
import com.hivemq.extensions.instantx.metrics.MetricHolder;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MetricPubAckInboundInterceptor implements PubackInboundInterceptor {
    private static final Logger log = LoggerFactory.getLogger(MetricPubAckInboundInterceptor.class);

    private final MetricHolder metricHolder;

    @Inject
    public MetricPubAckInboundInterceptor(MetricHolder metricHolder) {
        this.metricHolder = metricHolder;
    }

    public void onInboundPuback(@NotNull PubackInboundInput input, @NotNull PubackInboundOutput output) {
        String appAlias = this.metricHolder.getAppName((ClientBasedInput)input);
        AckReasonCode code = output.getPubackPacket().getReasonCode();
        String clientId = input.getClientInformation().getClientId();
        if (AckReasonCode.SUCCESS == code) {
            this.metricHolder.getAppReceiveAckCount(appAlias.equalsIgnoreCase("unknown")?clientId:appAlias).inc();
            log.debug("App {} ack msg receive success, clientId {}.", appAlias, clientId);
        } else {
            log.debug("App {} ack msg receive failed, reason {}, clientId {}.", new Object[] { appAlias, code, clientId });
        }
    }
}
