# tiki
<p align="left">
<img src="https://travis-ci.org/lewismj/tiki.svg?branch=master"/>
<img src="https://codecov.io/gh/lewismj/tiki/branch/master/graph/badge.svg" alt="Codecov" />
</p>


## Summary

A library for functional graph algorithms (_soon_)

Currently work in progress.

## Notes

A simple library for constructing graphs. The main edge classes
are defined as follows:

First there is a directed edge from one vertex to another.
```scala 
case class Edge[A](from: A, to: A)
```
The labelled edge is an edge with a label of some type.
A simple weighted edge may be defined as `LEdge[A,Double]`.
```scala
case class LEdge[A,B](edge: Edge[A], label: B) {
  def map[C](f: B => C): LEdge[A,C] = LEdge(edge,f(label))
}
```

In the example below the edge is a particular type of relationship given
by the class name `CounterpartyEdge`. The label is a pair of `DateTime`
that shows when that relationship existed.
```scala
case class Business(name: String)
case class Temporal(vt: DateTime, tt: DateTime)
type CounterpartyEdge[Business,Temporal] = LEdge[Business,Temporal]
```
Here, the type of Edge (relationship) is strongly typed i.e. `CounterparyEdge`.
A graph with these edges would show when two businesses were counterparties.

When representing graphs like this there are a couple of alternatives
for modelling the relationships:

1. Separate graphs for different relationships.

2. A single multigraph (this will allow edges of different types).

3. A single `Label` type with a fixed set of attributes. Here the actual
   relationships are no longer strongly typed, as our label is usually
   a collection of properties.
   
Many of the graph algorithms are simple if all edges are of 
the same type. `Multigraph` will use shapeless for its implementation.
Typically, I use 1. or 2. when building graphs, the core algorithms,
for directed acyclic graphs (e.g. maybe valuing a portfolio of trades)
 become easier to implement and cover most use-cases.

 
## Graph Types

- _Digraph_ 
    A set of directed edges, the graph may have more that 
    one sub graph, and may contain cycles. Though we can
    enforce and check properties to ensure _Digraph_ is
    acyclic and has one component etc. (i.e. forming trees).
    
 - Multigraph a graph that can have multiple edges.