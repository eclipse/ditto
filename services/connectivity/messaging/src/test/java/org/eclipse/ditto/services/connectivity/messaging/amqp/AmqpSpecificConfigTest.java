/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.services.connectivity.messaging.amqp;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.eclipse.ditto.services.connectivity.config.DefaultAmqp10Config;
import org.eclipse.ditto.services.connectivity.messaging.TestConstants;
import org.junit.Test;

import com.typesafe.config.ConfigFactory;

/**
 * Tests {@link AmqpSpecificConfig}.
 */
public final class AmqpSpecificConfigTest {

    @Test
    public void decodeDoublyEncodedUsernameAndPassword() {
        final var uri = "amqps://%2525u%2525s%2525e%2525r:%2525p%2525a%2525%252Bs%2525s@localhost:1234/";
        final var connection = TestConstants.createConnection()
                .toBuilder()
                .uri(uri)
                .build();

        final var underTest = AmqpSpecificConfig.withDefault("CID", connection, Map.of());

        assertThat(underTest.render("amqps://localhost:1234/"))
                .isEqualTo("failover:(amqps://localhost:1234/?amqp.saslMechanisms=PLAIN)" +
                        "?jms.clientID=CID&jms.username=%25u%25s%25e%25r&jms.password=%25p%25a%25%2Bs%25s" +
                        "&failover.startupMaxReconnectAttempts=5&failover.maxReconnectAttempts=-1" +
                        "&failover.initialReconnectDelay=128&failover.reconnectDelay=128" +
                        "&failover.maxReconnectDelay=900000&failover.reconnectBackOffMultiplier=2" +
                        "&failover.useReconnectBackOff=true");
    }

    @Test
    public void decodeSinglyEncodedUsernameAndPasswordContainingPercentageSign() {
        final var uri = "amqps://%25u%25s%25e%25r:%25p%25a%25%2Bs%25s@localhost:1234/";
        final var connection = TestConstants.createConnection()
                .toBuilder()
                .uri(uri)
                .build();

        final var underTest = AmqpSpecificConfig.withDefault("CID", connection, Map.of());

        assertThat(underTest.render("amqps://localhost:1234/"))
                .isEqualTo("failover:(amqps://localhost:1234/?amqp.saslMechanisms=PLAIN)" +
                        "?jms.clientID=CID&jms.username=%25u%25s%25e%25r&jms.password=%25p%25a%25%2Bs%25s" +
                        "&failover.startupMaxReconnectAttempts=5&failover.maxReconnectAttempts=-1" +
                        "&failover.initialReconnectDelay=128&failover.reconnectDelay=128" +
                        "&failover.maxReconnectDelay=900000&failover.reconnectBackOffMultiplier=2" +
                        "&failover.useReconnectBackOff=true");
    }

    @Test
    public void decodeSinglyEncodedUsernameAndPassword() {
        final var uri = "amqps://user:pa%2Bss@localhost:1234/";
        final var connection = TestConstants.createConnection()
                .toBuilder()
                .uri(uri)
                .build();

        final var underTest = AmqpSpecificConfig.withDefault("CID", connection, Map.of());

        assertThat(underTest.render("amqps://localhost:1234/"))
                .isEqualTo("failover:(amqps://localhost:1234/?amqp.saslMechanisms=PLAIN)" +
                        "?jms.clientID=CID&jms.username=user&jms.password=pa%2Bss" +
                        "&failover.startupMaxReconnectAttempts=5&failover.maxReconnectAttempts=-1" +
                        "&failover.initialReconnectDelay=128&failover.reconnectDelay=128" +
                        "&failover.maxReconnectDelay=900000&failover.reconnectBackOffMultiplier=2" +
                        "&failover.useReconnectBackOff=true");
    }

    @Test
    public void appendDefaultParameters() {
        final var connection = TestConstants.createConnection();
        final var amqp10Config = DefaultAmqp10Config.of(ConfigFactory.empty());
        final var defaultConfig = AmqpSpecificConfig.toDefaultConfig(amqp10Config);

        final var underTest = AmqpSpecificConfig.withDefault("CID", connection, defaultConfig);

        assertThat(underTest.render("amqps://localhost:1234/"))
                .isEqualTo("failover:(amqps://localhost:1234/?amqp.saslMechanisms=PLAIN)" +
                        "?jms.sendTimeout=60000&jms.prefetchPolicy.all=10&jms.connectTimeout=15000" +
                        "&jms.requestTimeout=5000&jms.clientID=CID" +
                        "&jms.username=username&jms.password=password" +
                        "&failover.startupMaxReconnectAttempts=5&failover.maxReconnectAttempts=-1" +
                        "&failover.initialReconnectDelay=128&failover.reconnectDelay=128" +
                        "&failover.maxReconnectDelay=900000&failover.reconnectBackOffMultiplier=2" +
                        "&failover.useReconnectBackOff=true");
    }

    @Test
    public void withoutFailover() {
        final var connection = TestConstants.createConnection().toBuilder().failoverEnabled(false).build();
        final var underTest = AmqpSpecificConfig.withDefault("CID", connection, Map.of());
        assertThat(underTest.render("amqps://localhost:1234/"))
                .isEqualTo("amqps://localhost:1234/?amqp.saslMechanisms=PLAIN&jms.clientID=CID" +
                        "&jms.username=username&jms.password=password");
    }
}
