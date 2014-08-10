
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.Viewer;

public class GraphTest2 extends JFrame implements Runnable
{
    private static final long serialVersionUID = - 804177406404724792L;
    private JPanel contentPane;
    Graph graph;
    Viewer viewer;
    View view;

    public static void main(String[] args)
    {
        Graph graph  = new MultiGraph("mg");
        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_SWING_THREAD);
        SwingUtilities.invokeLater(new GraphTest2(graph, viewer));
    }

    public GraphTest2(Graph graph, Viewer viewer)
    {
        this.graph = graph;
        this.viewer = viewer;

    }

    public void run()
    {
        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);
     //   JPanel panelSettings = new JPanel();
     //   panelSettings.setBorder(new EmptyBorder(0, 8, 0, 8));
      //  panelSettings.setBackground(SystemColor.activeCaption);
      //  panelSettings.setLayout(new BorderLayout(0, 0));
      //  contentPane.add(panelSettings, BorderLayout.WEST);

        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("AB", "A", "B");
        graph.addEdge("BC", "B", "C");
        graph.addEdge("CA", "C", "A");

        viewer.enableAutoLayout();
        view = viewer.addDefaultView(false);

        contentPane.add(view, BorderLayout.CENTER);
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}