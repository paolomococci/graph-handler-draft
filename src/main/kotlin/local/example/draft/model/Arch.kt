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

package local.example.draft.model

import org.hibernate.annotations.NaturalId
import java.security.SecureRandom
import javax.persistence.*

@Entity
class Arch {
    @Id @GeneratedValue val id: Int = 0
    @NaturalId val label: String = """A${SecureRandom.getInstanceStrong().nextLong()}"""
    var previous: Int = 0
    var following: Int = 0
    var flow: Double = 0.0
    var capacity: Double = 0.0
    @ManyToOne
    @JoinColumn(name = "graph_id")
    var graph: Graph? = null
    constructor()
}
