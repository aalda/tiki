---
layout: docs 
title:  "GraphRep"
section: "datastructures"
source: "core/src/main/scala/tiki/GraphRep.scala"
scaladoc: "#tiki.GraphRep"
---
# GraphRep

## Traits

`GraphRep` defines a trait that different graph representations should adhere to.

- `adjacent(v)` should return the set of adjacent edges.

```scala
trait GraphRep[A] {
  def contains(v: A): Boolean
  def adjacent(v: A): Option[Set[A]]
}
```

`DirectedGraphRep` extends the `GraphRep` and defined the functions that directed graphs
should implement. The `adjacent` vertices are assumed to be the successors (or children) of
the vertex.

```scala
trait DirectedGraphRep[A] extends GraphRep[A] {
  def adjacent(v: A): Set[A] = successors(v)
  def contains(v: A): Boolean
  def successors(v: A): Option[Set[A]]
  def predecessors(v: A): Option[Set[A]]
}
```