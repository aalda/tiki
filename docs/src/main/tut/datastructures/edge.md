---
layout: docs 
title:  "Edge"
section: "datastructures"
source: "core/src/main/scala/tiki/Edge.scala"
scaladoc: "#tiki.Edge"
---
# Edges

The following case classes may used to define edges:

- `Edge` an unweighted edge from one vertex to another.
- `LabelledEdge` an unweighted, labelled edge from one vertex to another.
- `WeightedEdge` is a weighted edge.

Each `EdgeLike` trait specifies the interface of the edge. Implicit instances implement behaviours.

```scala
trait EdgeLike[T] {
  def from: T
  def to: T
}

object EdgeLike {
  def apply[T: EdgeLike]: EdgeLike[T] = implicitly
  def from[T: EdgeLike](t: T): T = EdgeLike[T].from
  def to[T: EdgeLike](t: T): T = EdgeLike[T].to
}

case class Edge[A](from: A, to: A)
case class LabelledEdge[A,B](edge: Edge[A], label: B)
case class WeightedEdge[A](edge: Edge[A], weight: Double)

```

Undirected edges? _Unless explicitly stated_ most algorithms would assume an undirected
 edge be represented by two directed edges. 
 
Why? Many practical use cases tend to be concerned with directed graphs, and usually directed
acyclic graphs (DAG) that are connected, i.e. _Trees_. 

_Often vertex type of an Edge may be a proxy for some underlying vertex type.
Where an instance of the underlying type may be costly to hold within a graph._
 
Alternative as functions are first class obejcts in Scala, we can build a '_calculation_'
 graph by defining vertices as a function type.

## Usage

### Constructing Edges

Edges can be constructed directly. However, importing `implicits` 
will allow you to use the `-->` and `--> :+` operators.


#### Edge

```tut
import tiki._
import tiki.implicits._
import cats.implicits._

val e = 1 --> 2
e.show
```

#### LabelledEdge

```tut
import tiki._
import tiki.implicits._
import cats.implicits._

val le = 1 --> 2 :+ "a label"
le.show
```

#### WeightedEdge

The weighted edge is a case where we have only one label of type double. There is the `:#` operator
to create these edges:

```tut
import tiki._
import tiki.implicits._
import cats.implicits._

val we = 1 --> 2 :# 2.345
we.show
```