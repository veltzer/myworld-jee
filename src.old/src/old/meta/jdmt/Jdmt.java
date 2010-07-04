package meta.jdmt;

import org.jgrapht.graph.ListenableDirectedGraph;
import meta.jdmt.vertexplug.File;

public class Jdmt {
	private ListenableDirectedGraph<Vertex, Edge> g;
	private Shell s;

	public Jdmt() {
		System.out.println("building graph");
		g = new ListenableDirectedGraph<Vertex, Edge>(Edge.class);
		File v = new File();
		v.setName("foo.txt");
		g.addVertex(v);
		System.out.println(g.toString());
		s = new Shell();
	}

	public void runShell() {
		s.run();
	}

	public static void main(String[] args) {

		Jdmt j = new Jdmt();
		j.runShell();
	}
}
