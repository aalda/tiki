---
layout: docs 
title:  "Point"
section: "datastructures"
source: "core/src/main/scala/tiki/cluster/Point.scala"
scaladoc: "#tiki.cluster.Point"
---
# Point

The Point class is used in the clustering algorithms. At present just representing 2D points,
for basic graph clustering algorithms.

Together will a number of utility functions within the companion object, such as orderings`YXOrdering`
and `CCWOrdering`, these allow sorting by _y_ then _x_ value and counter clockwise about an origin point.

These orderings are used for Delaunay triangulation, required to calculate Euclidean minimum spanning trees.

If we have edges between vertices (_e.g._ waypoints with names) it may make sense to introduce a `Cartesian` typeclass,
that a vertex type could support.

## Usage

### Example

```tut
import tiki._
import tiki.geometry._
import tiki.implicits._
import cats.implicits._

val a = Point(2,3)
a.show
val b = Point(3,4)
b.show
val z = a ⨯ b
```

### Functions

- `rightTurn(p1,p2,p3)` returns true, if _p3_ is a right turn on the _p1_,_p2_ line.
- `distance(p1,p2)` returns the distance between points _p1_ and _p2_.
- `collinear(p1,p2,p3)` returns true, if _p1_,_p2_ and _p3_ are collinear.
- `side(p1,p2,p3)` returns cross product of (_p2_-_p1_) and (_p3_-_p1_) (i.e. is counter-clockwise).
- `ccw(p1,p2,p3)` uses `side` to return 0, 1 , -1 counter-clockwise comparison.
- `angle(p1,p2)` calculates the angle between the _p1_ and _p2_ points.
- `minAngle(p1,p2,p3)` calculates the minimum between the points.
- `YXOrdering` order `Point` by _y_ value, in case of equal _y_, then order by _x_.
- `CCWOrdering(origin)` order `Point` by counter clockwise distance from given _origin_ point.
