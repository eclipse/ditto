/*
 * Copyright (c) 2017-2018 Bosch Software Innovations GmbH.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/epl-2.0/index.php
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.ditto.signals.commands.policies.modify;

import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import org.eclipse.ditto.json.JsonFactory;
import org.eclipse.ditto.json.JsonField;
import org.eclipse.ditto.json.JsonFieldDefinition;
import org.eclipse.ditto.json.JsonObject;
import org.eclipse.ditto.json.JsonObjectBuilder;
import org.eclipse.ditto.json.JsonPointer;
import org.eclipse.ditto.model.base.headers.DittoHeaders;
import org.eclipse.ditto.model.base.json.FieldType;
import org.eclipse.ditto.model.base.json.JsonSchemaVersion;
import org.eclipse.ditto.model.policies.PolicyIdValidator;
import org.eclipse.ditto.signals.commands.base.AbstractCommand;
import org.eclipse.ditto.signals.commands.base.CommandJsonDeserializer;

/**
 * This command deletes a {@link org.eclipse.ditto.model.policies.PolicyImport}.
 */
@Immutable
public final class DeletePolicyImport extends AbstractCommand<DeletePolicyImport>
        implements PolicyModifyCommand<DeletePolicyImport> {

    /**
     * Name of this command.
     */
    public static final String NAME = "deletePolicyImport";

    /**
     * Type of this command.
     */
    public static final String TYPE = TYPE_PREFIX + NAME;

    static final JsonFieldDefinition<String> JSON_IMPORTED_POLICY_ID =
            JsonFactory.newStringFieldDefinition("importedPolicyId", FieldType.REGULAR, JsonSchemaVersion.V_2);

    private final String policyId;
    private final String importedPolicyId;

    private DeletePolicyImport(final String policyId, final String importedPolicyId, final DittoHeaders dittoHeaders) {
        super(TYPE, dittoHeaders);
        PolicyIdValidator.getInstance().accept(policyId, dittoHeaders);
        this.policyId = policyId;
        this.importedPolicyId = importedPolicyId;
    }

    /**
     * Creates a command for deleting a {@code PolicyImport}.
     *
     * @param policyId the identifier of the Policy.
     * @param importedPolicyId the Policy ID of the imported Policy to delete.
     * @param dittoHeaders the headers of the command.
     * @return the command.
     * @throws NullPointerException if any argument is {@code null}.
     */
    public static DeletePolicyImport of(final String policyId, final String importedPolicyId,
            final DittoHeaders dittoHeaders) {
        Objects.requireNonNull(policyId, "The Policy identifier must not be null!");
        Objects.requireNonNull(importedPolicyId, "The imported Policy ID must not be null!");
        return new DeletePolicyImport(policyId, importedPolicyId, dittoHeaders);
    }

    /**
     * Creates a command for deleting a {@code PolicyImport} from a JSON string.
     *
     * @param jsonString the JSON string of which the command is to be created.
     * @param dittoHeaders the headers of the command.
     * @return the command.
     * @throws NullPointerException if {@code jsonString} is {@code null}.
     * @throws IllegalArgumentException if {@code jsonString} is empty.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonString} was not in the expected
     * format.
     */
    public static DeletePolicyImport fromJson(final String jsonString, final DittoHeaders dittoHeaders) {
        return fromJson(JsonFactory.newObject(jsonString), dittoHeaders);
    }

    /**
     * Creates a command for deleting a {@code PolicyImport} from a JSON object.
     *
     * @param jsonObject the JSON object of which the command is to be created.
     * @param dittoHeaders the headers of the command.
     * @return the command.
     * @throws NullPointerException if {@code jsonObject} is {@code null}.
     * @throws org.eclipse.ditto.json.JsonParseException if the passed in {@code jsonObject} was not in the expected
     * format.
     */
    public static DeletePolicyImport fromJson(final JsonObject jsonObject, final DittoHeaders dittoHeaders) {
        return new CommandJsonDeserializer<DeletePolicyImport>(TYPE, jsonObject).deserialize(() -> {
            final String policyId = jsonObject.getValueOrThrow(PolicyModifyCommand.JsonFields.JSON_POLICY_ID);
            final String theImportedPolicyId = jsonObject.getValueOrThrow(JSON_IMPORTED_POLICY_ID);

            return of(policyId, theImportedPolicyId, dittoHeaders);
        });
    }

    /**
     * Returns the {@code ID} of the imported {@code Policy} to delete.
     *
     * @return the id of the Policy Import to delete.
     */
    public String getImportedPolicyId() {
        return importedPolicyId;
    }

    /**
     * Returns the identifier of the {@code Policy}.
     *
     * @return the identifier of the Policy.
     */
    @Override
    public String getId() {
        return policyId;
    }

    @Override
    public JsonPointer getResourcePath() {
        final String path = "/imports/" + importedPolicyId;
        return JsonPointer.of(path);
    }

    @Override
    protected void appendPayload(final JsonObjectBuilder jsonObjectBuilder, final JsonSchemaVersion schemaVersion,
            final Predicate<JsonField> thePredicate) {

        final Predicate<JsonField> predicate = schemaVersion.and(thePredicate);
        jsonObjectBuilder.set(PolicyModifyCommand.JsonFields.JSON_POLICY_ID, policyId, predicate);
        jsonObjectBuilder.set(JSON_IMPORTED_POLICY_ID, importedPolicyId, predicate);
    }

    @Override
    public Category getCategory() {
        return Category.DELETE;
    }

    @Override
    public DeletePolicyImport setDittoHeaders(final DittoHeaders dittoHeaders) {
        return of(policyId, importedPolicyId, dittoHeaders);
    }

    @Override
    protected boolean canEqual(@Nullable final Object other) {
        return other instanceof DeletePolicyImport;
    }

    @SuppressWarnings("squid:MethodCyclomaticComplexity")
    @Override
    public boolean equals(@Nullable final Object obj) {
        if (this == obj) {
            return true;
        }
        if (null == obj || getClass() != obj.getClass()) {
            return false;
        }
        final DeletePolicyImport that = (DeletePolicyImport) obj;
        return that.canEqual(this) && Objects.equals(policyId, that.policyId) && Objects.equals(
                importedPolicyId, that.importedPolicyId)
                && super.equals(obj);
    }

    @SuppressWarnings("squid:S109")
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), policyId, importedPolicyId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [" + super.toString() + ", policyId=" + policyId + ", importedPolicyId=" +
                importedPolicyId +
                "]";
    }

}
