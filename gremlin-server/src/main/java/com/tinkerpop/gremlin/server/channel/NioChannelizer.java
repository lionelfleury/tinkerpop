/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.tinkerpop.gremlin.server.channel;

import com.tinkerpop.gremlin.groovy.engine.GremlinExecutor;
import com.tinkerpop.gremlin.server.AbstractChannelizer;
import com.tinkerpop.gremlin.server.Graphs;
import com.tinkerpop.gremlin.server.Settings;
import com.tinkerpop.gremlin.server.handler.NioGremlinBinaryRequestDecoder;
import com.tinkerpop.gremlin.server.handler.NioGremlinResponseEncoder;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A {@link com.tinkerpop.gremlin.server.Channelizer} that exposes an NIO-based Gremlin endpoint with a custom
 * protocol.
 *
 * @author Stephen Mallette (http://stephen.genoprime.com)
 */
public class NioChannelizer extends AbstractChannelizer {
    private static final Logger logger = LoggerFactory.getLogger(NioChannelizer.class);

    private NioGremlinBinaryRequestDecoder nioGremlinBinaryRequestDecoder;

    @Override
    public void init(final Settings settings, final GremlinExecutor gremlinExecutor,
                     final ExecutorService gremlinExecutorService, final Graphs graphs,
                     final ScheduledExecutorService scheduledExecutorService) {
        super.init(settings, gremlinExecutor, gremlinExecutorService, graphs, scheduledExecutorService);
        nioGremlinBinaryRequestDecoder = new NioGremlinBinaryRequestDecoder(serializers);
    }

    @Override
    public void configure(final ChannelPipeline pipeline) {
        if (logger.isDebugEnabled())
            pipeline.addLast(new LoggingHandler("log-io", LogLevel.DEBUG));

        pipeline.addLast("response-encoder", new NioGremlinResponseEncoder());
        pipeline.addLast("request-binary-decoder", nioGremlinBinaryRequestDecoder);

        if (logger.isDebugEnabled())
            pipeline.addLast(new LoggingHandler("log-codec", LogLevel.DEBUG));
    }
}