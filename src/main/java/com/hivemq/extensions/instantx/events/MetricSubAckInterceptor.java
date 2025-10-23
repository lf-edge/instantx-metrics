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
import com.hivemq.extension.sdk.api.interceptor.suback.SubackOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.suback.parameter.SubackOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.suback.parameter.SubackOutboundOutput;
import com.hivemq.extension.sdk.api.packets.subscribe.SubackReasonCode;
import com.hivemq.extension.sdk.api.parameter.ClientBasedInput;
import com.hivemq.extensions.instantx.metrics.MetricHolder;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MetricSubAckInterceptor implements SubackOutboundInterceptor {
    private static final Logger log = LoggerFactory.getLogger(MetricSubAckInterceptor.class);

    private final MetricHolder metricHolder;

    @Inject
    public MetricSubAckInterceptor(MetricHolder metricHolder) {
        this.metricHolder = metricHolder;
    }

    public void onOutboundSuback(@NotNull SubackOutboundInput input, @NotNull SubackOutboundOutput output) {
        String appAlias = this.metricHolder.getAppName((ClientBasedInput) input);
        List<SubackReasonCode> codes = output.getSubackPacket().getReasonCodes();
        String clientId = input.getClientInformation().getClientId();
        for (SubackReasonCode code : codes) {
            if (SubackReasonCode.NOT_AUTHORIZED == code) {
                this.metricHolder
                        .getAppSubscribeUnauthorizedCount(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias)
                        .inc();
                log.debug("App {} unauthorized subscription, clientId {}.", appAlias, clientId);
            }
        }
    }
}
