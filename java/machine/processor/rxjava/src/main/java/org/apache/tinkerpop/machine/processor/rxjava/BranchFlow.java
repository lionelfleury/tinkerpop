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
package org.apache.tinkerpop.machine.processor.rxjava;

import io.reactivex.functions.Function;
import org.apache.tinkerpop.machine.bytecode.compiler.Compilation;
import org.apache.tinkerpop.machine.function.BranchFunction;
import org.apache.tinkerpop.machine.traverser.Traverser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
final class BranchFlow<C, S> implements Function<Traverser<C, S>, List> {

    private final ThreadLocal<List<Compilation<C, S, ?>>> selectors;

    BranchFlow(final BranchFunction<C, S, ?> function) {
        this.selectors = ThreadLocal.withInitial(() -> {
            final List<Compilation<C, S, ?>> branches = new ArrayList<>();
            for (final Compilation<C, S, ?> branch : function.getBranches().keySet()) {
                branches.add(null == branch ? null : branch.clone());
            }
            return branches;
        });
    }

    @Override
    public List apply(final Traverser<C, S> traverser) {
        for (int i = 0; i < this.selectors.get().size(); i++) {
            final Compilation<C, S, ?> selector = this.selectors.get().get(i);
            if (null != selector) {
                if (selector.filterTraverser(traverser))
                    return List.of(i, traverser);
            }
        }
        return List.of(-1, traverser);
    }
}
