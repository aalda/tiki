/*
 * Copyright (c) 2017
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package object tiki {
  import shapeless.Poly1
  import tiki.Predef._
  import tiki.implicits._

  /**
    * Provides 'reverse' function for different 'Edge' case classes.
    */
  object reverse extends Poly1 {
    implicit def edge[A] : Case.Aux[Edge[A],Edge[A]]= at({x=> x.to --> x.from})
    implicit def labelledEdge[A,B] : Case.Aux[LabelledEdge[A,B],LabelledEdge[A,B]]
      = at({ x=> x.edge.to --> x.edge.from :+ x.label})
    implicit def weightedEdge[A] : Case.Aux[WeightedEdge[A],WeightedEdge[A]]
      = at({ x=> x.edge.to --> x.edge.from :# x.weight})
  }

  /**
    * Remove all edges into the stream of vertices.
    *
    * @param g    the digraph.
    * @param xs   the list of vertices.
    * @tparam A   the vertex type.
    * @return     the digraph with edges into vertices of xs removed.
    */
  def removeEdgeTo[A](g: Digraph[A], xs: Stream[A]): Digraph[A] = new Digraph[A] {
    override def vertices: Stream[A] = g.vertices
    override def predecessors(v: A): Set[A] = if (xs.contains(v)) Set.empty[A] else g.predecessors(v)
    override def successors(v: A): Set[A] = g.successors(v).filter(xs.contains)
    override def contains(v: A): Boolean = g.contains(v)
    override def edges: Stream[EdgeLike[A]] = g.edges.filterNot(e => xs.contains(e.to))
  }

}

