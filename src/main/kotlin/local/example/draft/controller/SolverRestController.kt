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

package local.example.draft.controller

import local.example.draft.adt.ArchAdt
import local.example.draft.adt.GraphAdt
import local.example.draft.adt.VertexAdt
import local.example.draft.algorithm.AhujaOrlin
import local.example.draft.model.Arch
import local.example.draft.model.Vertex
import local.example.draft.repository.GraphRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URISyntaxException

@RestController
@RequestMapping("/api/solver/graph")
class SolverRestController internal constructor(
        val graphRepository: GraphRepository
) {
    @GetMapping("/{id}")
    @Throws(URISyntaxException::class)
    internal fun trySolve(@PathVariable id: Int?): String {
        val graph = graphRepository.findById(id!!)
        val graphAdt = GraphAdt()
        val arches: MutableList<Arch>? = graph.get().arches
        val vertexes: MutableList<Vertex>? = graph.get().vertexes
        val archesIterator = arches?.iterator()
        val vertexesIterator = vertexes?.iterator()
        if (vertexesIterator != null) {
            for (v in vertexesIterator) {
                val temp = VertexAdt(index = v.index, label = v.label)
                graphAdt.addVertex(temp)
                if (v.source) graphAdt.source = temp
                if (v.sink) graphAdt.sink = temp
            }
        }
        if (archesIterator != null) {
            for (a in archesIterator) {
                val temp = ArchAdt(
                        previous = VertexAdt(index = a.previous),
                        following = VertexAdt(index = a.following),
                        capacity = a.capacity,
                        flow = a.flow)
                graphAdt.addArch(temp)
            }
        }
        val solver = AhujaOrlin()
        return solver.maxFlowToJson(graphAdt) + graphAdt.archAdts.toString()
    }
}
