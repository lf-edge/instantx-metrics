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
import com.hivemq.extension.sdk.api.interceptor.publish.PublishInboundInterceptor;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundInput;
import com.hivemq.extension.sdk.api.interceptor.publish.parameter.PublishInboundOutput;
import com.hivemq.extension.sdk.api.parameter.ClientBasedInput;
import com.hivemq.extensions.instantx.metrics.MetricHolder;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class MetricPublishInboundInterceptor implements PublishInboundInterceptor {
    private static final Logger log = LoggerFactory.getLogger(MetricPublishInboundInterceptor.class);

    private final MetricHolder metricHolder;

    @Inject
    public MetricPublishInboundInterceptor(MetricHolder metricHolder) {
        this.metricHolder = metricHolder;
    }

    public void onInboundPublish(@NotNull PublishInboundInput input, @NotNull PublishInboundOutput output) {
        String topic = input.getPublishPacket().getTopic();
        String appAlias = this.metricHolder.getAppName((ClientBasedInput)input);
        String clientId = input.getClientInformation().getClientId();
        if (this.metricHolder.isPublicMessage(topic)) {
            this.metricHolder.getAppPublicPublishCount(appAlias.equalsIgnoreCase("unknown")?clientId:appAlias).inc();
            this.metricHolder.getAppPublicPublishByTypeCount(appAlias.equalsIgnoreCase("unknown")?clientId:appAlias, topic).inc();
            log.debug("App {} public msg publish, topic {}, clientId {}.", new Object[] { appAlias, topic, clientId });
        } else {
            this.metricHolder.getAppPrivatePublishCount(appAlias.equalsIgnoreCase("unknown")?clientId:appAlias).inc();
            this.metricHolder.getAppPrivatePublishByTypeCount(appAlias.equalsIgnoreCase("unknown")?clientId:appAlias, topic).inc();
            log.debug("App {} private msg publish, topic {}, clientId {}.", new Object[] { appAlias, topic, clientId });
        }
    }
}
