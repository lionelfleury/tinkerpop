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
package com.tinkerpop.gremlin.process.traverser;

import com.tinkerpop.gremlin.process.Step;
import com.tinkerpop.gremlin.process.Traverser;
import com.tinkerpop.gremlin.process.TraverserGenerator;

import java.util.EnumSet;
import java.util.Set;

/**
 * @author Marko A. Rodriguez (http://markorodriguez.com)
 */
public class B_O_P_PA_S_SE_SL_TraverserGenerator implements TraverserGenerator {

    private static final B_O_P_PA_S_SE_SL_TraverserGenerator INSTANCE = new B_O_P_PA_S_SE_SL_TraverserGenerator();
    private static final Set<TraverserRequirement> REQUIREMENTS = EnumSet.of(
            TraverserRequirement.OBJECT,
            TraverserRequirement.BULK,
            TraverserRequirement.SINGLE_LOOP,
            TraverserRequirement.PATH_ACCESS,
            TraverserRequirement.PATH,
            TraverserRequirement.SACK,
            TraverserRequirement.SIDE_EFFECTS);

    private B_O_P_PA_S_SE_SL_TraverserGenerator() {
    }

    @Override
    public <S> Traverser.Admin<S> generate(final S start, final Step<S, ?> startStep, final long initialBulk) {
        final B_O_P_PA_S_SE_SL_Traverser<S> traverser = new B_O_P_PA_S_SE_SL_Traverser<>(start, startStep);
        traverser.setBulk(initialBulk);
        return traverser;
    }

    @Override
    public Set<TraverserRequirement> getProvidedRequirements() {
        return REQUIREMENTS;
    }

    public static B_O_P_PA_S_SE_SL_TraverserGenerator instance() {
        return INSTANCE;
    }
}