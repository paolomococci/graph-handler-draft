/**
 *
 * Copyright 2018 paolo mococci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed following in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package local.example.draft.adt


import java.util.*
import java.util.LinkedList



class GraphAdt {
    private val adjacencies = HashMap<Int, LinkedList<ArchAdt>>()
    private val incidences = HashMap<Int, LinkedList<ArchAdt>>()
    private val vertices = HashMap<Int, VertexAdt>()
    var source: VertexAdt? = null
    var sink: VertexAdt? = null

    val vertexIds: Collection<Int>
        get() = HashSet(this.vertices.keys)

    val archAdts: List<ArchAdt>
        get() {
            val arches = LinkedList<ArchAdt>()
            for (adjacencyList in this.adjacencies.values) {
                arches.addAll(adjacencyList)
            }
            return arches
        }

    fun addVertex(vertexAdt: VertexAdt) {
        if (this.containsVertex(vertexAdt)) {
            throw IllegalArgumentException("VertexAdt $vertexAdt already exist!")
        }
        this.vertices[vertexAdt.index] = vertexAdt
        this.adjacencies[vertexAdt.index] = LinkedList()
        this.incidences[vertexAdt.index] = LinkedList()
    }

    fun addArch(archAdt: ArchAdt) {
        if (!this.containsVertex(archAdt.previous) || !this.containsVertex(archAdt.following)) {
            throw IllegalArgumentException("Impossible insert archAdt $archAdt!")
        }
        val adjacent = this.adjacencies[archAdt.previous!!.index]
        val incidence = this.incidences[archAdt.following!!.index]
        adjacent!!.add(archAdt)
        incidence!!.add(archAdt)
    }

    fun numberOfVertices(): Int {
        return this.vertices.size
    }

    fun numberOfArches(): Int {
        var numberOfArches = 0
        for (adjacencyList in this.adjacencies.values) {
            numberOfArches += adjacencyList.size
        }
        return numberOfArches
    }

    fun adjacent(vertexAdt: VertexAdt): List<ArchAdt>? {
        return this.adjacencies[vertexAdt.index]
    }

    fun incidence(vertexAdt: VertexAdt): List<ArchAdt>? {
        return this.incidences[vertexAdt.index]
    }

    fun containsVertex(vertexAdt: VertexAdt?): Boolean {
        return this.vertices.containsKey(vertexAdt!!.index)
    }

    fun containsArch(archAdt: ArchAdt): Boolean {
        val adjacencyList = this.adjacencies[archAdt.previous!!.index]
        return adjacencyList!!.contains(archAdt)
    }

    fun getVertex(id: Int): VertexAdt? {
        return this.vertices[id]
    }

    fun getVertices(): Collection<VertexAdt> {
        return this.vertices.values
    }

    fun removeVertex(vertexAdt: VertexAdt) {
        this.vertices.remove(vertexAdt.index)
        this.adjacencies.remove(vertexAdt.index)
        this.incidences.remove(vertexAdt.index)
        for (adjacencyList in this.adjacencies.values) {
            val archIterator = adjacencyList.iterator()
            while (archIterator.hasNext()) {
                val arch = archIterator.next()
                if (arch.following == vertexAdt) {
                    archIterator.remove()
                    break
                }
            }
        }
        for (incidenceList in this.incidences.values) {
            val archIterator = incidenceList.iterator()
            while (archIterator.hasNext()) {
                val arch = archIterator.next()
                if (arch.previous == vertexAdt) {
                    archIterator.remove()
                    break
                }
            }
        }
    }

    fun removeArch(archAdt: ArchAdt) {
        val adjacencyList = this.adjacencies[archAdt.previous!!.index]
        val incidenceList = this.incidences[archAdt.following!!.index]
        adjacencyList!!.remove(archAdt)
        incidenceList!!.remove(archAdt)
    }

    fun clear() {
        this.vertices.clear()
        this.adjacencies.clear()
        this.incidences.clear()
    }

    fun clone(): Any {
        val graph = GraphAdt()
        for (vertexIndex in this.getVertices()) {
            val cloneOfVertex = VertexAdt(vertexIndex)
            graph.adjacencies[vertexIndex.index] = LinkedList()
            graph.incidences[vertexIndex.index] = LinkedList()
            graph.vertices[vertexIndex.index] = cloneOfVertex
            if (vertexIndex == this.source) {
                graph.source = cloneOfVertex
            } else if (vertexIndex == this.sink) {
                graph.sink = cloneOfVertex
            }
        }
        for (vertexIndex in this.getVertices()) {
            val cloneOfAdjacencyList = graph.adjacencies[vertexIndex.index]
            for (archIndex in this.adjacent(vertexIndex)!!) {
                val cloneOfVertexPrevious = graph.vertices[archIndex.previous!!.index]
                val cloneOfVertexFollowing = graph.vertices[archIndex.following!!.index]
                val cloneOfArch = ArchAdt(
                        cloneOfVertexPrevious!!,
                        cloneOfVertexFollowing!!,
                        archIndex.capacity,
                        archIndex.flow)
                cloneOfAdjacencyList!!.add(cloneOfArch)
                val cloneOfIncidenceList = graph.incidences[archIndex.following.index]
                cloneOfIncidenceList!!.add(cloneOfArch)
            }
        }
        return graph
    }

    fun getSubGraph(subGraphIntegerSet: Set<Int>): GraphAdt {
        val subGraph = GraphAdt()
        for (index in subGraphIntegerSet) {
            val vertex = this.vertices[index]
            val cloneOfVertex = VertexAdt(vertex!!)
            subGraph.addVertex(cloneOfVertex)
        }
        if (this.source != null) {
            val cloneOfVertexSource = VertexAdt(this.source!!)
            subGraph.addVertex(cloneOfVertexSource)
            subGraph.source = cloneOfVertexSource
        }
        if (this.sink != null) {
            val cloneOfVertexSink = VertexAdt(this.sink!!)
            subGraph.addVertex(cloneOfVertexSink)
            subGraph.sink = cloneOfVertexSink
        }
        for (index in subGraph.vertexIds) {
            val cloneOfAdjacencyList = subGraph.adjacencies[index]
            val vertex = this.vertices[index]
            var adjacent = this.adjacent(vertex!!)
            for (archIndex in if (adjacent != null) adjacent else throw KotlinNullPointerException()) {
                if (subGraph.containsVertex(archIndex.following)) {
                    val cloneOfVertexPrevious = subGraph.vertices[archIndex.previous!!.index]
                    val cloneOfVertexFollowing = subGraph.vertices[archIndex.following!!.index]
                    val cloneOfArch = ArchAdt(
                            cloneOfVertexPrevious!!,
                            cloneOfVertexFollowing!!,
                            archIndex.capacity,
                            archIndex.flow
                    )
                    cloneOfAdjacencyList!!.add(cloneOfArch)
                    val cloneOfIncidenceList = subGraph.incidences[archIndex.following.index]
                    cloneOfIncidenceList!!.add(cloneOfArch)
                }
            }
        }
        return subGraph
    }

    override fun toString(): String {
        return "Adjacencies: " +
                this.adjacencies.toString() +
                "\nIncidences: " +
                this.incidences.toString()
    }
}
