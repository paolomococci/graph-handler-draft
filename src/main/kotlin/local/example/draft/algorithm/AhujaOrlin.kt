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

package local.example.draft.algorithm

import local.example.draft.adt.ArchAdt
import local.example.draft.adt.GraphAdt
import local.example.draft.adt.VertexAdt
import local.example.draft.util.DistanceLabels
import java.util.*

class AhujaOrlin {

    private fun getMaxFlow(graphAdt: GraphAdt): Double {
        if (0 == graphAdt.numberOfVertices()) {
            return 0.0
        }
        val labels = computeDistanceLabels(graphAdt)
        var maxFlow = 0.0
        val numberOfVertexADTs = graphAdt.numberOfVertices()
        val backArchADTs = addBackArches(graphAdt)
        val currentAugmentingPath = LinkedList<ArchAdt>()
        var currentVertexADT = graphAdt.source
        while (currentVertexADT != null && labels!!.getLabel(graphAdt.source!!)!! < numberOfVertexADTs) {
            val archADT = getAdmissibleArch(graphAdt, currentVertexADT, labels)
            if (archADT != null) {
                currentVertexADT = advance(archADT, currentAugmentingPath)
                if (currentVertexADT.equals(graphAdt.sink)) {
                    val delta = augment(graphAdt, currentAugmentingPath)
                    maxFlow += delta
                    currentVertexADT = graphAdt.source
                    currentAugmentingPath.clear()
                }
            } else {
                currentVertexADT = retrograde(graphAdt, labels, currentVertexADT, currentAugmentingPath)
            }
        }
        removeBackArches(graphAdt, backArchADTs)
        return maxFlow
    }

    private fun computeDistanceLabels(graphAdt: GraphAdt): DistanceLabels? {
        val numberOfVertices = graphAdt.numberOfVertices()
        val labels = DistanceLabels(numberOfVertices)
        val visited = HashSet<VertexAdt>()
        for (iVertex in graphAdt.getVertices()) {
            labels.setLabel(iVertex, numberOfVertices)
        }
        labels.setLabel(graphAdt.sink!!, 0)
        val verticesQueue = LinkedList<VertexAdt>()
        verticesQueue.addLast(graphAdt.sink)
        while (!verticesQueue.isEmpty()) {
            val jVertex = verticesQueue.removeFirst()
            for (archIndex in graphAdt.incidence(jVertex)!!) {
                val iVertex = archIndex.previous
                if (!visited.contains(iVertex)) {
                    labels.setLabel(iVertex!!, labels.getLabel(jVertex)!! + 1)
                    visited.add(iVertex)
                    verticesQueue.addLast(iVertex)
                }
            }
            visited.add(jVertex)
        }
        return labels
    }

    private fun addBackArches(graphAdt: GraphAdt): List<ArchAdt> {
        val backArches = LinkedList<ArchAdt>()
        for (archIndex in graphAdt.archAdts) { // getArchADTs()
            val backArch = ArchAdt(archIndex.following!!, archIndex.previous!!, 0.0, 0.0)
            graphAdt.addArch(backArch)
            backArches.add(backArch)
        }
        return backArches
    }

    private fun getAdmissibleArch(graphAdt: GraphAdt,
                                  iVertexAdt: VertexAdt,
                                  distanceLabels: DistanceLabels): ArchAdt? {
        for (archIndex in graphAdt.adjacent(iVertexAdt)!!) {
            if (archIndex.residualCapacity > 0 &&
                    distanceLabels.getLabel(iVertexAdt) === 1 + distanceLabels.getLabel(archIndex.following!!)!!) {
                return archIndex
            }
        }
        return null
    }

    private fun advance(archAdt: ArchAdt, path: LinkedList<ArchAdt>): VertexAdt {
        path.addLast(archAdt)
        return archAdt.following!!
    }

    private fun augment(graphAdt: GraphAdt, path: LinkedList<ArchAdt>): Double {
        var delta: Double
        delta = Double.MAX_VALUE
        for (archIndex in path) {
            val residualCapacity = archIndex.residualCapacity
            if (residualCapacity < delta) {
                delta = residualCapacity
            }
        }
        for (archIndex in path) {
            var flow = archIndex.flow
            flow += delta
            archIndex.flow = flow
            var reverseArchAdt: ArchAdt? = null
            for (incidenceArch in graphAdt.incidence(archIndex.previous!!)!!) {
                if (incidenceArch.previous!!.equals(archIndex.following)) {
                    reverseArchAdt = incidenceArch
                    break
                }
            }
            var capacityTemp = reverseArchAdt!!.capacity
            capacityTemp += delta
            reverseArchAdt.capacity = capacityTemp
            flow = reverseArchAdt.flow
            if (flow > 0.0) {
                flow -= delta
                reverseArchAdt.flow = flow
            }
        }
        return delta
    }

    private fun retrograde(graphAdt: GraphAdt,
                           distanceLabels: DistanceLabels,
                           iVertexAdt: VertexAdt,
                           path: LinkedList<ArchAdt>): VertexAdt? {
        var distaceLabelMin = graphAdt.numberOfVertices() - 1
        val predecessorVertexAdt: VertexAdt?
        for (archIndex in graphAdt.adjacent(iVertexAdt)!!) {
            if (archIndex.residualCapacity > 0.0) {
                val jVertex = archIndex.following
                val distanceLabelJVertex = distanceLabels.getLabel(jVertex!!)
                if (distanceLabelJVertex != null) {
                    if (distanceLabelJVertex < distaceLabelMin) {
                        distaceLabelMin = distanceLabelJVertex
                    }
                }
            }
        }
        val flag = distanceLabels.setLabel(iVertexAdt, 1 + distaceLabelMin)
        if (!flag) {
            if (!iVertexAdt.equals(graphAdt.source)) {
                val arch = path.removeLast()
                predecessorVertexAdt = arch.previous
            } else {
                predecessorVertexAdt = graphAdt.source
            }
        } else {
            predecessorVertexAdt = null
        }
        return predecessorVertexAdt
    }

    private fun removeBackArches(graphAdt: GraphAdt, backArchAdts: List<ArchAdt>) {
        for (archADTIndex in backArchAdts) {
            graphAdt.removeArch(archADTIndex)
        }
    }

    fun maxFlowToJson(graphAdt: GraphAdt) : String {
        var jsonString = StringBuilder()
        jsonString.append("[{\n")
        jsonString.append("\t\"max flow\" : ${getMaxFlow(graphAdt)}\n")
        jsonString.append("}],\n")
        return jsonString.toString()
    }
}
