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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [GraphHandlerDraftApplication::class])
@AutoConfigureMockMvc
class VertexRepositoryMockMvcTests {

    val vertexItems: List<String> = listOf(
            """{"label":"VS","index":"0","source":"true"}""",
            """{"label":"V1","index":"1"}""",
            """{"label":"V2","index":"2"}""",
            """{"label":"V3","index":"3"}""",
            """{"label":"V4","index":"4"}""",
            """{"label":"V5","index":"5"}""",
            """{"label":"VT","index":"6","sink":"true"}"""
    )

    @Autowired
    val mockMvc: MockMvc? = null

    @Autowired
    val vertexRepository: VertexRepository? = null

    @Before
    @Throws(Exception::class)
    fun initialize() {
        vertexRepository?.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/vertexes"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes").exists())
    }

    @Test
    @Throws(Exception::class)
    fun `create and retrieve the vertex without any assigned value except for the label`() {
        val mvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/vertexes").content("{\"label\":\"V1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.get(result!!))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("V1"))
    }

    @Test
    @Throws(Exception::class)
    fun `creates and retrieves all the values assigned to the vertex`() {
        val mvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/vertexes").content(vertexItems[0]))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.get(result!!))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("VS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.index").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.source").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.sink").value(false))
    }

    @Test
    @Throws(Exception::class)
    fun `create and retrieve vertices with all their values`() {
        val iterator = vertexItems.iterator()
        for (vertexJson in iterator) {
            mockMvc!!.perform(MockMvcRequestBuilders.post("/api/vertexes").content(vertexJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated)
        }
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/vertexes"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes[6].label").value("VT"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes[6].index").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes[6].source").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.vertexes[6].sink").value(true))
    }
}
