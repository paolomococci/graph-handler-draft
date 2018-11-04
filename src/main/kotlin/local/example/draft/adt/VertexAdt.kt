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

class VertexAdt {
    var index: Int = 0
    var label = ""

    constructor()

    constructor(index: Int) : super() {
        this.index = index
    }

    constructor(index: Int, label: String) : super() {
        this.index = index
        this.label = label
    }

    constructor(vertexAdt: VertexAdt) : super() {
        this.index = vertexAdt.index
        this.label = vertexAdt.label
    }

    override fun equals(`object`: Any?): Boolean {
        if (`object` == null) {
            return false
        }
        if (`object` !is VertexAdt) {
            return false
        }
        val vertex = `object` as VertexAdt?
        return vertex!!.index == this.index
    }

    override fun hashCode(): Int {
        return this.index
    }

    override fun toString(): String {
        return "VertexADT [index=" + this.index + ", label=" + this.label + "]"
    }
}
