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

package local.example.draft.adt

class ArchAdt {
    val previous: VertexAdt?
    val following: VertexAdt?
    var capacity: Double = 0.0
    var flow: Double = 0.0

    val residualCapacity: Double
        get() = this.capacity - this.flow

    constructor(previous: VertexAdt, following: VertexAdt, capacity: Double) : super() {
        this.previous = previous
        this.following = following
        this.capacity = capacity
        this.flow = 0.0
    }

    constructor(previous: VertexAdt, following: VertexAdt, capacity: Double, flow: Double) : super() {
        this.previous = previous
        this.following = following
        this.capacity = capacity
        this.flow = flow
    }

    override fun equals(`object`: Any?): Boolean {
        if (`object` == null) {
            return false
        }
        if (`object` !is ArchAdt) {
            return false
        }
        val arch = `object` as ArchAdt?
        return arch!!.previous == this.previous &&
                arch.following == this.following &&
                arch.capacity == this.capacity &&
                arch.flow == this.flow
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun toString(): String {
        var jsonString = StringBuilder()
        jsonString.append("{\n")
        jsonString.append("\t\"vertex previous\" : ${this.previous!!.index},\n")
        jsonString.append("\t\"vertex following\" : ${this.following!!.index},\n")
        jsonString.append("\t\"flow\" : ${this.flow},\n")
        jsonString.append("\t\"capacity\" : ${this.capacity}\n")
        jsonString.append("}\n")
        return jsonString.toString()
    }
}
