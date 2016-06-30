package restrouting.extended_gh.util;

/*
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.util.EdgeExplorer;
import com.graphhopper.util.EdgeIterator;
import com.graphhopper.util.PointList;


/**
 * This interface represents an edge and is one possible state of an EdgeIterator.
 * <p>
 * @author Peter Karich
 * @see EdgeIterator
 * @see EdgeExplorer
 */
public interface EdgeIteratorState extends com.graphhopper.util.EdgeIteratorState {


    @Override
    boolean getBoolean(int i, boolean b, boolean b1);

    /**
     * Get additional boolean information of the edge.
     * <p>
     * @param key direction or vehicle dependent integer key
     * @param _default default value if key is not found
     */
    boolean getBool(int key, boolean _default);

   }

