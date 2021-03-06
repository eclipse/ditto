/*
 * Copyright (c) 2017 Contributors to the Eclipse Foundation
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
package org.eclipse.ditto.signals.commands.live.modify;

import java.time.Instant;
import java.util.function.Function;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import org.eclipse.ditto.signals.commands.base.CommandResponse;
import org.eclipse.ditto.signals.commands.live.base.LiveCommandAnswer;
import org.eclipse.ditto.signals.commands.things.ThingErrorResponse;
import org.eclipse.ditto.signals.commands.things.exceptions.FeatureDefinitionNotAccessibleException;
import org.eclipse.ditto.signals.commands.things.exceptions.FeatureDefinitionNotModifiableException;
import org.eclipse.ditto.signals.commands.things.modify.ModifyFeatureDefinitionResponse;
import org.eclipse.ditto.signals.events.base.Event;
import org.eclipse.ditto.signals.events.things.FeatureDefinitionCreated;
import org.eclipse.ditto.signals.events.things.FeatureDefinitionModified;

/**
 * A mutable builder with a fluent API for creating a {@link LiveCommandAnswer} for a
 * {@link ModifyFeatureDefinitionLiveCommand}.
 */
@ParametersAreNonnullByDefault
@NotThreadSafe
final class ModifyFeatureDefinitionLiveCommandAnswerBuilderImpl
        extends AbstractLiveCommandAnswerBuilder<ModifyFeatureDefinitionLiveCommand, ModifyFeatureDefinitionLiveCommandAnswerBuilder.ResponseFactory, ModifyFeatureDefinitionLiveCommandAnswerBuilder.EventFactory>
        implements ModifyFeatureDefinitionLiveCommandAnswerBuilder {

    private ModifyFeatureDefinitionLiveCommandAnswerBuilderImpl(final ModifyFeatureDefinitionLiveCommand command) {
        super(command);
    }

    /**
     * Returns a new instance of {@code ModifyFeatureDefinitionLiveCommandAnswerBuilderImpl}.
     *
     * @param command the command to build an answer for.
     * @return the instance.
     * @throws NullPointerException if {@code command} is {@code null}.
     */
    public static ModifyFeatureDefinitionLiveCommandAnswerBuilderImpl newInstance(
            final ModifyFeatureDefinitionLiveCommand command) {
        return new ModifyFeatureDefinitionLiveCommandAnswerBuilderImpl(command);
    }

    @Override
    protected CommandResponse doCreateResponse(
            final Function<ResponseFactory, CommandResponse<?>> createResponseFunction) {

        return createResponseFunction.apply(new ResponseFactoryImpl());
    }

    @Override
    protected Event doCreateEvent(final Function<EventFactory, Event<?>> createEventFunction) {
        return createEventFunction.apply(new EventFactoryImpl());
    }

    @Immutable
    private final class ResponseFactoryImpl implements ResponseFactory {

        @Nonnull
        @Override
        public ModifyFeatureDefinitionResponse created() {
            return ModifyFeatureDefinitionResponse.created(command.getThingEntityId(), command.getFeatureId(),
                    command.getDefinition(),
                    command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ModifyFeatureDefinitionResponse modified() {
            return ModifyFeatureDefinitionResponse.modified(command.getThingEntityId(), command.getFeatureId(),
                    command.getDittoHeaders());
        }

        @Nonnull
        @Override
        public ThingErrorResponse featureDefinitionNotAccessibleError() {
            return errorResponse(command.getThingEntityId(),
                    FeatureDefinitionNotAccessibleException.newBuilder(command.getThingEntityId(),
                            command.getFeatureId())
                            .dittoHeaders(command.getDittoHeaders())
                            .build());
        }

        @Nonnull
        @Override
        public ThingErrorResponse featureDefinitionNotModifiableError() {
            return errorResponse(command.getThingEntityId(),
                    FeatureDefinitionNotModifiableException.newBuilder(command.getThingEntityId(),
                            command.getFeatureId())
                            .dittoHeaders(command.getDittoHeaders())
                            .build());
        }

    }

    @Immutable
    private final class EventFactoryImpl implements EventFactory {

        @Nonnull
        @Override
        public FeatureDefinitionCreated created() {
            return FeatureDefinitionCreated.of(command.getThingEntityId(), command.getFeatureId(),
                    command.getDefinition(), -1, Instant.now(), command.getDittoHeaders(), null);
        }

        @Nonnull
        @Override
        public FeatureDefinitionModified modified() {
            return FeatureDefinitionModified.of(command.getThingEntityId(), command.getFeatureId(),
                    command.getDefinition(), -1, Instant.now(), command.getDittoHeaders(), null);
        }

    }

}
