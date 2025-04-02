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
import com.hivemq.extension.sdk.api.interceptor.puback.PubackOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.puback.parameter.PubackOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.puback.parameter.PubackOutboundOutput;
import com.hivemq.extension.sdk.api.packets.publish.AckReasonCode;
import com.hivemq.extension.sdk.api.parameter.ClientBasedInput;
import com.hivemq.extensions.instantx.metrics.MetricHolder;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MetricPubAckOutboundInterceptor implements PubackOutboundInterceptor {
    private static final Logger log = LoggerFactory.getLogger(MetricPubAckOutboundInterceptor.class);

    private final MetricHolder metricHolder;

    @Inject
    public MetricPubAckOutboundInterceptor(MetricHolder metricHolder) {
        this.metricHolder = metricHolder;
    }

    public void onOutboundPuback(@NotNull PubackOutboundInput input, @NotNull PubackOutboundOutput output) {
        String appAlias = this.metricHolder.getAppName((ClientBasedInput)input);
        AckReasonCode code = input.getPubackPacket().getReasonCode();
        String clientId = input.getClientInformation().getClientId();
        if (AckReasonCode.NOT_AUTHORIZED == code) {
            this.metricHolder.getAppPublishUnauthorizedCount(appAlias.equalsIgnoreCase("unknown")?clientId:appAlias).inc();
            log.debug("App {} ack unauthorized publish, clientId {}.", appAlias, clientId);
        } else if (AckReasonCode.SUCCESS == code) {
            this.metricHolder.getAppPubAckCount(appAlias.equalsIgnoreCase("unknown")?clientId:appAlias).inc();
            log.debug("App {} ack msg publish success, clientId {}.", appAlias, clientId);
        } else {
            log.debug("App {} ack msg publish failed, reason {}, clientId {}.", new Object[] { appAlias, code, clientId });
        }
    }
}
