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
package org.eclipse.ditto.services.utils.cache.entry;

/**
 * Cache entry for authorization.
 *
 * @param <T> the type of the cache entry's value
 */
public interface Entry<T> {

    static <T> Entry<T> permanent(final T value) {
        return new ExistentEntry<>(Long.MAX_VALUE, value);
    }

    static <T> Entry<T> of(final long revision, final T value) {
        return new ExistentEntry<>(revision, value);
    }

    static <T> Entry<T> nonexistent() {
        return NonexistentEntry.getInstance();
    }

    /**
     * Returns the revision of the cache entry.
     * An entry may only override those with smaller revisions.
     *
     * @return the revision number.
     */
    long getRevision();

    boolean exists();

    /**
     * Retrieve the value if present.
     *
     * @return the cached value if present.
     * @throws java.util.NoSuchElementException if this entry has no existing value.
     * @see #exists()
     */
    T getValueOrThrow();

}
