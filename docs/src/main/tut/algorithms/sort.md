---
layout: docs 
title:  "Topological Sort"
section: "algorithms"
source: "core/src/main/scala/tiki/Sort.scala"
scaladoc: "#tiki.Sort"
---
# Topological Sorting

From [wikipedia](https://en.wikipedia.org/wiki/Topological_sorting)

"_A topological sort of a directed graph is a linear
ordering of its vertices such that for every directed edge uv 
from vertex u to vertex v, u comes before v in the ordering_."

A topological sort (or ordering) is only possible if the digraph contains
no cycles. That is, we have a directed acyclic graph.

## Example

A topological sort of the graph ...

![graph](https://raw.github.com/lewismj/tiki/master/docs/src/main/resources/microsite/img/topologicalSort.png)

... should yield the set  _{A,B,C,D,E,F}_


## Kahn's Algorithm

See the wikipedia link for references. Implementation follows Kahn's algorithm.
The current function will return _None_ if a cycle is found.

```scala
  def tsort[A](g: Digraph[A]): Option[Stream[A]] = {
    @tailrec
    def kahn(s0: Stream[A], l: Stream[A], ys: Stream[EdgeLike[A]]): Option[Stream[A]] = s0 match {
      case _ if (s0 isEmpty) && (ys isEmpty) => Some(l)
      case _ if s0 isEmpty => None
      case n #:: tail =>
        val (edgesFrom, remainder) = ys.partition(_.from == n)
        val insert = edgesFrom.map(_.to).filterNot(remainder.map(_.to).contains(_))
        kahn(tail #::: insert, l :+ n, remainder)
    }
    kahn(g.vertices.filterNot(g.edges.map(_.to).contains(_)), Stream.empty, g.edges)
  }
```


```tut
import tiki._
import tiki.Sort._
import tiki.implicits._


val xs = Stream (
    'a' --> 'b',
    'a' --> 'c',
    'b' --> 'c',
    'b' --> 'd',
    'c' --> 'd',
    'd' --> 'e',
    'd' --> 'f',
    'e' --> 'f'
)

val digraph = new Digraph[Char] {
  lazy val ys = AdjacencyList(xs)
  def contains(v: Char) = ys.contains(v)
  def vertices: Stream[Char] = ys.vertices
  def successors(v: Char) = ys.successors(v)
  def predecessors(v: Char) = ys.predecessors(v)
  def edges: Stream[Edge[Char]] = xs
}

val sorted = tsort(digraph).getOrElse(Stream.empty)
sorted.mkString
```