
CS5413 - Assignment: DFS and etc.
The input graph is to be given in the form of adjacency matrix as follows. Note that the
input should in a file. Note that each edge is to be assigned with a weight (”1” is just to
show a format).
a b c d e f g h i j
a 0 1 0 0 0 1 0 0 0 0
b 0 0 1 0 0 0 1 0 0 0
c 0 0 0 1 0 0 0 1 0 0
d 0 0 0 0 1 0 0 1 1 1
e 0 0 0 0 0 0 0 0 0 1
f 0 0 0 0 0 0 1 0 0 0
g 1 0 0 0 0 0 0 1 0 0
h 0 0 1 0 0 0 0 0 0 0
i 0 0 0 0 0 0 0 0 0 1
j 0 0 0 0 1 0 0 0 0 0

(a) Implement the the following algorithm (70%).

DFS(G)
1 for each vertex u 2 V[G]
2 do color [u]   WHITE
3 [u]   NIL
4 time   0
5 for each vertex u 2 V[G]
6 do if color [u] = WHITE
7 then DFS-VISIT(u)
DFS-VISIT(u)
1 color [u]   GRAY
2 time   time+1
3 d[u]   time
4 for each v 2 Adj[u]
5 do if color [v] = WHITE
6 then [v]   u
7 DFS-VISIT(v)
8 color [u]   BLACK
9 f[u]   time   time + 1
Output: an adjacency matrix with discovery time and finishing time on each vertex.


(b) Then implement the following algorithm. (20%)

TOPOLOGICAL-SORT(G)

1 call DFS(G) to compute finishing times f[v] for each vertex v
2 as each vertex is finished, insert it onto the front of a linked list
3 return the linked list of vertices
Output: a sorted list of vertices with the finishing time in decreasing order.


(c) Given directed graph G = (V,E) (10%)

a strongly connected component (SCC)
of G is a maximal set of vertices C  V such that for all u, v 2 C, both u ?
b v and v ?
b v.
Implement an algorithm in the book to find the SCCs in the graph.
Output: a set of sub-adjacency graphs as resulting SCC’s. No birding edges need be
indicated.
2



# DS -2 Program 3 

# To complie the java file

javac Program3.java

# run the java project 

#case 1 (Without file)
java Program3

Output : Error Mention the filename as an argument

#case 2 (with wrong file)
java Program3 input2.txt

Output : The file you supplied doesn't seem to exist!

#case 3 (with correct file)
java Program3 input.txt

Output : 

Print vetex
a:1/20
b:2/17
c:3/14
d:4/13
e:5/8
f:18/19
g:15/16
h:9/10
i:11/12
j:6/7
Topological sorted
|a||f||b||g||c||d||i||h||e||j|
Songly Connected Components
1,2,6,7
3,4,8
9
5,10
