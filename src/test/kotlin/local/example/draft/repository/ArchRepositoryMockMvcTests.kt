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
class ArchRepositoryMockMvcTests {

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
    val archRepository: ArchRepository? = null

    @Before
    @Throws(Exception::class)
    fun initialize() {
        archRepository?.deleteAll()
    }

    @Test
    @Throws(Exception::class)
    fun `verify existence`() {
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/arches"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches").exists())
    }

    @Test
    @Throws(Exception::class)
    fun `create and retrieve the arch without any assigned value except for the label`() {
        val mvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/arches").content("{\"label\":\"A1\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.get(result!!))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("A1"))
    }

    @Test
    @Throws(Exception::class)
    fun `creates and retrieves all the values assigned to the arch`() {
        val mvcResult = mockMvc!!.perform(MockMvcRequestBuilders.post("/api/arches").content(archItems[0]))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
        val result = mvcResult.response.getHeader("Location")
        mockMvc!!.perform(MockMvcRequestBuilders.get(result!!))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$.label").value("A1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.previous").value(0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.following").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.flow").value(0.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$.capacity").value(3.0))
    }

    @Test
    @Throws(Exception::class)
    fun `create and retrieve arches with all their values`() {
        val iterator = archItems.iterator()
        for (archJson in iterator) {
            mockMvc!!.perform(MockMvcRequestBuilders.post("/api/arches").content(archJson))
                    .andExpect(MockMvcResultMatchers.status().isCreated)
        }
        mockMvc!!.perform(MockMvcRequestBuilders.get("/api/arches"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches[11].label").value("A12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches[11].previous").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches[11].following").value(6))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches[11].flow").value(0.0))
                .andExpect(MockMvcResultMatchers.jsonPath("$._embedded.arches[11].capacity").value(4.0))
    }
}
