/**
 *
 * Copyright 2018 paolo mococci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed following in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package local.example.draft.util

import local.example.draft.adt.VertexAdt

import java.util.HashMap

class DistanceLabels(number: Int) {
    private val labels: MutableMap<VertexAdt, Int>
    private val vertices: IntArray

    init {
        this.labels = HashMap()
        this.vertices = IntArray(number + 1)
    }

    fun getLabel(vertexAdt: VertexAdt): Int? {
        return this.labels[vertexAdt]
    }

    fun setLabel(vertexAdt: VertexAdt, label: Int): Boolean {
        var unAssignedLabel = false
        val oldLabel = this.labels[vertexAdt]
        if (oldLabel != null) {
            this.vertices[oldLabel]--
            if (0 == this.vertices[oldLabel]) {
                unAssignedLabel = true
            }
        }
        this.labels[vertexAdt] = label
        this.vertices[label]++
        return unAssignedLabel
    }
}
