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

package local.example.draft.repository

import local.example.draft.GraphHandlerDraftApplication
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [GraphHandlerDraftApplication::class])
@AutoConfigureMockMvc
class GraphRepositoryMockMvcTests {

    val vertexItems: List<String> = listOf(
            """{"label":"VS","index":"0","source":"true"}""",
            """{"label":"V1","index":"1"}""",
            """{"label":"V2","index":"2"}""",
            """{"label":"V3","index":"3"}""",
            """{"label":"V4","index":"4"}""",
            """{"label":"V5","index":"5"}""",
            """{"label":"VT","index":"6","sink":"true"}"""
    )

    val archItems: List<String> = listOf(
            """{"label":"A1","previous":"0","following":"1","capacity":"3.0"}""",
            """{"label":"A2","previous":"0","following":"2","capacity":"3.0"}""",
            """{"label":"A3","previous":"0","following":"3","capacity":"5.0"}""",
            """{"label":"A4","previous":"1","following":"4","capacity":"3.0"}""",
            """{"label":"A5","previous":"1","following":"3","capacity":"4.0"}""",
            """{"label":"A6","previous":"2","following":"5","capacity":"2.0"}""",
            """{"label":"A7","previous":"2","following":"3","capacity":"2.0"}""",
            """{"label":"A8","previous":"3","following":"4","capacity":"2.0"}""",
            """{"label":"A9","previous":"3","following":"5","capacity":"4.0"}""",
            """{"label":"A10","previous":"3","following":"6","capacity":"1.0"}""",
            """{"label":"A11","previous":"4","following":"6","capacity":"4.0"}""",
            """{"label":"A12","previous":"5","following":"6","capacity":"4.0"}"""
    )

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val graphRepository: GraphRepository? = null

    @Autowired
    val archRepository: ArchRepository? = null

    @Autowired
    val vertexRepository: VertexRepository? = null

    @Before
    @Throws(Exception::class)
    fun initialize() {
        graphRepository?.deleteAll()
        archRepository?.deleteAll()
        vertexRepository?.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun `create and retrieve a graph entity with the only label element assigned`() {
        val mvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/graphs").content("{\"label\":\"G1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.get(result!!))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("G1"))
    }

    @Test
    @Throws(Exception::class)
    fun `create and retrieve all the proper references of a graph including the label element`() {
        val graphMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/graphs").content("{\"label\":\"G1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val graphHeader = graphMvcResult.response.getHeader("Location")
        val vertexesIterator = vertexItems.iterator()
        val archesIterator = archItems.iterator()
        for (vertexJson in vertexesIterator) {
            val vertexMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/vertexes").content(vertexJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
            val vertexHeader = vertexMvcResult.response.getHeader("Location")
            mockMvc!!.perform(MockMvcRequestBuilders.put("""$vertexHeader/graph""")
                    .content(graphHeader!!).contentType("text/uri-list"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent)
        }
        for (archJson in archesIterator) {
            val archMvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/arches").content(archJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated).andReturn()
            val archHeader = archMvcResult.response.getHeader("Location")
            mockMvc!!.perform(MockMvcRequestBuilders.put("""$archHeader/graph""")
                    .content(graphHeader!!).contentType("text/uri-list"))
                    .andExpect(MockMvcResultMatchers.status().isNoContent)
        }
        mockMvc!!.perform(MockMvcRequestBuilders.get("""$graphHeader/vertexes"""))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes[0].label").value("VS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes[6].label").value("VT"))
        mockMvc!!.perform(MockMvcRequestBuilders.get("""$graphHeader/arches"""))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches[0].label").value("A1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches[11].label").value("A12"))
    }
}
