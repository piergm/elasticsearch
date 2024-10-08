/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the "Elastic License
 * 2.0", the "GNU Affero General Public License v3.0 only", and the "Server Side
 * Public License v 1"; you may not use this file except in compliance with, at
 * your election, the "Elastic License 2.0", the "GNU Affero General Public
 * License v3.0 only", or the "Server Side Public License, v 1".
 */

package org.elasticsearch.telemetry;

import java.util.Objects;

/**
 * A record of the arguments for a registered instrument.
 */
record Registration(String name, String description, String unit) {
    Registration {
        Objects.requireNonNull(name);
        Objects.requireNonNull(description);
        Objects.requireNonNull(unit);
    }
}
