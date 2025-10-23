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
import com.hivemq.extension.sdk.api.interceptor.publish.PublishOutboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishOutboundOutput;
import com.hivemq.extension.sdk.api.parameter.ClientBasedInput;
import com.hivemq.extensions.instantx.metrics.MetricHolder;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MetricPublishReceiveInterceptor implements PublishOutboundInterceptor {
    private static final Logger log = LoggerFactory.getLogger(MetricPublishReceiveInterceptor.class);

    private final MetricHolder metricHolder;

    @Inject
    public MetricPublishReceiveInterceptor(MetricHolder metricHolder) {
        this.metricHolder = metricHolder;
    }

    public void onOutboundPublish(@NotNull PublishOutboundInput input, @NotNull PublishOutboundOutput output) {
        String topic = input.getPublishPacket().getTopic();
        String appAlias = this.metricHolder.getAppName((ClientBasedInput) input);
        String clientId = input.getClientInformation().getClientId();
        if (this.metricHolder.isPublicMessage(topic)) {
            this.metricHolder.getAppPublicReceiveCount(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias)
                    .inc();
            this.metricHolder
                    .getAppPublicReceiveByTypeCount(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias, topic)
                    .inc();
            log.debug("App {} public msg receive, topic {}, clientId {}.", new Object[] { appAlias, topic, clientId });
        } else {
            this.metricHolder.getAppPrivateReceiveCount(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias)
                    .inc();
            this.metricHolder
                    .getAppPrivateReceiveByTypeCount(appAlias.equalsIgnoreCase("unknown") ? clientId : appAlias, topic)
                    .inc();
            log.debug("App {} private msg receive, topic {}, clientId {}.", new Object[] { appAlias, topic, clientId });
        }
    }
}
