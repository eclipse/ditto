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
package org.eclipse.ditto.signals.events.things;

import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.json.JsonField;
import org.eclipse.ditto.json.JsonFieldDefinition;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.json.JsonObjectBuilder;
import org.eclipse.ditto.json.JsonPointer;
import org.eclipse.ditto.json.JsonValue;
import org.eclipse.ditto.model.base.entity.metadata.Metadata;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.base.json.FieldType;
import org.eclipse.ditto.model.base.json.JsonParsableEvent;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;
import org.eclipse.ditto.model.things.AclEntry;
import org.eclipse.ditto.model.things.ThingId;
import org.eclipse.ditto.model.things.ThingsModelFactory;
import org.eclipse.ditto.signals.events.base.EventJsonDeserializer;

/**
 * This event is emitted after a Thing ACL entry was created.
 *
 * @deprecated AccessControlLists belong to deprecated API version 1. Use API version 2 with policies instead.
 */
@Deprecated
@Immutable
@JsonParsableEvent(name = AclEntryCreated.NAME, typePrefix = AclEntryCreated.TYPE_PREFIX)
public final class AclEntryCreated extends AbstractThingEvent<AclEntryCreated>
        implements ThingModifiedEvent<AclEntryCreated> {

    /**
     * Name of this event.
     */
    public static final String NAME = "aclEntryCreated";

    /**
     * Type of this event.
     */
    public static final String TYPE = TYPE_PREFIX + NAME;

    static final JsonFieldDefinition<JsonObject> JSON_ACL_ENTRY =
            JsonFactory.newJsonObjectFieldDefinition("aclEntry", FieldType.REGULAR, JsonSchemaVersion.V_1);

    private final AclEntry aclEntry;

    private AclEntryCreated(final ThingId thingId,
            final AclEntry aclEntry,
            final long revision,
            @Nullable final Instant timestamp,
            final DittoHeaders dittoHeaders,
            @Nullable final Metadata metadata) {

        super(TYPE, thingId, revision, timestamp, dittoHeaders, metadata);
        this.aclEntry = Objects.requireNonNull(aclEntry, "The created ACL Entry must not be null!");
    }

    /**
     * Constructs a new {@code AclEntryCreated} object.
     *
     * @param thingId the ID of the Thing with which this event is associated.
     * @param aclEntry the created ACL Entry.
     * @param revision the revision of the Thing.
     * @param dittoHeaders the headers of the command which was the cause of this event.
     * @return the created AclEntryCreated.
     * @throws NullPointerException if any argument is {@code null}.
     * @deprecated Thing ID is now typed. Use
     * {@link #of(org.eclipse.ditto.model.things.ThingId, org.eclipse.ditto.model.things.AclEntry, long, org.eclipse.ditto.model.base.headers.DittoHeaders)}
     * instead.
     */
    @Deprecated
    public static AclEntryCreated of(final String thingId,
            final AclEntry aclEntry,
            final long revision,
            final DittoHeaders dittoHeaders) {

        return of(ThingId.of(thingId), aclEntry, revision, null, dittoHeaders, null);
    }

    /**
     * Constructs a new {@code AclEntryCreated} object.
     *
     * @param thingId the ID of the Thing with which this event is associated.
     * @param aclEntry the created ACL Entry.
     * @param revision the revision of the Thing.
     * @param dittoHeaders the headers of the command which was the cause of this event.
     * @return the created AclEntryCreated.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static AclEntryCreated of(final ThingId thingId,
            final AclEntry aclEntry,
            final long revision,
            final DittoHeaders dittoHeaders) {

        return of(thingId, aclEntry, revision, null, dittoHeaders, null);
    }

    /**
     * Constructs a new {@code AclEntryCreated} object.
     *
     * @param thingId the ID of the Thing with which this event is associated.
     * @param aclEntry the created ACL Entry.
     * @param revision the revision of the Thing.
     * @param timestamp the timestamp of this event.
     * @param dittoHeaders the headers of the command which was the cause of this event.
     * @return the created AclEntryCreated.
     * @throws NullPointerException if any argument but {@code timestamp} is {@code null}.
     * @deprecated Thing ID is now typed. Use
     * {@link #of(org.eclipse.ditto.model.things.ThingId, org.eclipse.ditto.model.things.AclEntry, long, java.time.Instant, org.eclipse.ditto.model.base.headers.DittoHeaders, org.eclipse.ditto.model.base.entity.metadata.Metadata)}
     * instead.
     */
    @Deprecated
    public static AclEntryCreated of(final String thingId,
            final AclEntry aclEntry,
            final long revision,
            @Nullable final Instant timestamp,
            final DittoHeaders dittoHeaders) {

        return of(ThingId.of(thingId), aclEntry, revision, timestamp, dittoHeaders, null);
    }

    /**
     * Constructs a new {@code AclEntryCreated} object.
     *
     * @param thingId the ID of the Thing with which this event is associated.
     * @param aclEntry the created ACL Entry.
     * @param revision the revision of the Thing.
     * @param timestamp the timestamp of this event.
     * @param dittoHeaders the headers of the command which was the cause of this event.
     * @return the created AclEntryCreated.
     * @throws NullPointerException if any argument but {@code timestamp} is {@code null}.
     * @deprecated Use {@link #of(org.eclipse.ditto.model.things.ThingId, org.eclipse.ditto.model.things.AclEntry, long, java.time.Instant, org.eclipse.ditto.model.base.headers.DittoHeaders, org.eclipse.ditto.model.base.entity.metadata.Metadata)}
     * instead.
     */
    @Deprecated
    public static AclEntryCreated of(final ThingId thingId,
            final AclEntry aclEntry,
            final long revision,
            @Nullable final Instant timestamp,
            final DittoHeaders dittoHeaders) {

        return of(thingId, aclEntry, revision, timestamp, dittoHeaders, null);
    }

    /**
     * Constructs a new {@code AclEntryCreated} object.
     *
     * @param thingId the ID of the Thing with which this event is associated.
     * @param aclEntry the created ACL Entry.
     * @param revision the revision of the Thing.
     * @param timestamp the timestamp of this event.
     * @param dittoHeaders the headers of the command which was the cause of this event.
     * @param metadata the metadata to apply for the event.
     * @return the created AclEntryCreated.
     * @throws NullPointerException if any argument but {@code timestamp} and {@code metadata} is {@code null}.
     * @since 1.3.0
     */
    public static AclEntryCreated of(final ThingId thingId,
            final AclEntry aclEntry,
            final long revision,
            @Nullable final Instant timestamp,
            final DittoHeaders dittoHeaders,
            @Nullable final Metadata metadata) {

        return new AclEntryCreated(thingId, aclEntry, revision, timestamp, dittoHeaders, metadata);
    }

    /**
     * Creates a new {@code AclEntryCreated} from a JSON string.
     *
     * @param jsonString the JSON string of which a new AclEntryCreated instance is to be deleted.
     * @param dittoHeaders the headers of the command which was the cause of this event.
     * @return the {@code AclEntryCreated} which was created from the given JSON string.
     * @throws NullPointerException if {@code jsonString} is {@code null}.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonString} was not in the expected
     * 'AclEntryCreated' format.
     */
    public static AclEntryCreated fromJson(final String jsonString, final DittoHeaders dittoHeaders) {
        return fromJson(JsonFactory.newObject(jsonString), dittoHeaders);
    }

    /**
     * Creates a new {@code AclEntryCreated} from a JSON object.
     *
     * @param jsonObject the JSON object from which a new AclEntryCreated instance is to be created.
     * @param dittoHeaders the headers of the command which was the cause of this event.
     * @return the {@code AclEntryCreated} which was created from the given JSON object.
     * @throws NullPointerException if {@code jsonObject} is {@code null}.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonObject} was not in the expected
     * 'AclEntryCreated' format.
     */
    public static AclEntryCreated fromJson(final JsonObject jsonObject, final DittoHeaders dittoHeaders) {
        return new EventJsonDeserializer<AclEntryCreated>(TYPE, jsonObject).deserialize(
                (revision, timestamp, metadata) -> {
                    final String extractedThingId = jsonObject.getValueOrThrow(JsonFields.THING_ID);
                    final ThingId thingId = ThingId.of(extractedThingId);
                    final JsonObject aclEntryJsonObject = jsonObject.getValueOrThrow(JSON_ACL_ENTRY);
                    final AclEntry extractedAclEntry = ThingsModelFactory.newAclEntry(aclEntryJsonObject);

                    return of(thingId, extractedAclEntry, revision, timestamp, dittoHeaders, metadata);
                });
    }

    /**
     * Returns the created ACL Entry.
     *
     * @return the ACL Entry.
     */
    public AclEntry getAclEntry() {
        return aclEntry;
    }

    @Override
    public AclEntryCreated setDittoHeaders(final DittoHeaders dittoHeaders) {
        return of(getThingEntityId(), aclEntry, getRevision(), getTimestamp().orElse(null), dittoHeaders,
                getMetadata().orElse(null));
    }

    @Override
    public Optional<JsonValue> getEntity(final JsonSchemaVersion schemaVersion) {
        return Optional.of(aclEntry.toJson(FieldType.notHidden()));
    }

    @Override
    public JsonPointer getResourcePath() {
        final String path = "/acl/" + aclEntry.getAuthorizationSubject().getId();
        return JsonPointer.of(path);
    }

    @Override
    public AclEntryCreated setRevision(final long revision) {
        return of(getThingEntityId(), aclEntry, revision, getTimestamp().orElse(null), getDittoHeaders(),
                getMetadata().orElse(null));
    }

    @Override
    protected void appendPayloadAndBuild(final JsonObjectBuilder jsonObjectBuilder,
            final JsonSchemaVersion schemaVersion, final Predicate<JsonField> thePredicate) {

        final Predicate<JsonField> predicate = schemaVersion.and(thePredicate);
        jsonObjectBuilder.set(JSON_ACL_ENTRY, aclEntry.toJson(schemaVersion, thePredicate), predicate);
    }

    @SuppressWarnings("squid:S109")
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + Objects.hashCode(aclEntry);
        return result;
    }

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    @Override
    public boolean equals(@Nullable final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AclEntryCreated that = (AclEntryCreated) o;
        return that.canEqual(this) && Objects.equals(aclEntry, that.aclEntry) && super.equals(that);
    }

    @Override
    protected boolean canEqual(@Nullable final Object other) {
        return other instanceof AclEntryCreated;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + super.toString() + ", aclEntry=" + aclEntry + "]";
    }

}
