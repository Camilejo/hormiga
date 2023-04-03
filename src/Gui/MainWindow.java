package Gui;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.shortestpath.DijkstraShortestPath;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.*;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame {

    public MainWindow() {
        setTitle("Grafo de hormigas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void begin() {
        Graph<String, String> grafo = new UndirectedSparseGraph<String, String>();

        String HORMIGA = "Salida";

        grafo.addVertex(HORMIGA);
        grafo.addVertex("Comida");
        grafo.addVertex("Playa");
        grafo.addVertex("Arboleda");
        grafo.addVertex("Cueva");
        grafo.addVertex("Granja");
        grafo.addVertex("Colonia");
        grafo.addVertex("Madriguera");
        grafo.addVertex("Colonia enemiga");
        grafo.addVertex("Duna");

        grafo.addEdge("10.10", HORMIGA, "Comida");
        grafo.addEdge("12.00", HORMIGA, "Playa");
        grafo.addEdge("5.00", "Arboleda", "Cueva");
        grafo.addEdge("1.10", "Granja", "Colonia");
        grafo.addEdge("5.20", "Duna", "Colonia enemiga");
        grafo.addEdge("2.00", "Granja", HORMIGA);
        grafo.addEdge("4.15", "Colonia", "Colonia enemiga");
        grafo.addEdge("7.78", "Granja", "Duna");
        grafo.addEdge("6.12", "Cueva", "Comida");
        grafo.addEdge("3.54", "Cueva", "Arboleda");
        grafo.addEdge("2.21", "Madriguera", "Arboleda");
        grafo.addEdge("4.94", "Duna", "Madriguera");

        VisualizationViewer<String, String> zone = new VisualizationViewer<String, String>(
                new CircleLayout<String, String>(grafo), new Dimension(650, 600));

        zone.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<String>());
        zone.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.N);

        zone.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<String>());
        zone.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.S);

        String finalHORMIGA = HORMIGA;
        zone.getRenderContext().setVertexFillPaintTransformer(s -> {
            if (s.equals(finalHORMIGA)) {
                return Color.ORANGE;
            } else {
                return Color.green;
            }
        });

        getContentPane().add(zone);
        pack();
        setVisible(true);

        DijkstraShortestPath<String, String> dijkstra = new DijkstraShortestPath<String, String>(grafo);

        boolean option = true;
        while (option) {
        double distanciaRecorrida = 0.0;

            String lugarFinal = JOptionPane.showInputDialog("Digite el lugar a donde la hormiga quiere llegar: ");
            String normalizedLugarFinal = capitalize(lugarFinal);

            try {
                for (String v : dijkstra.getPath(HORMIGA, normalizedLugarFinal)) {
                    distanciaRecorrida += Double.parseDouble(v);
                    HORMIGA = normalizedLugarFinal;
                }

                zone.getRenderContext().setVertexFillPaintTransformer(vertex -> vertex.equals(normalizedLugarFinal) ? Color.ORANGE : Color.green);

                zone.repaint();

                JOptionPane.showMessageDialog(null, "El camino mas corto de la hormiga para llegar a la " + normalizedLugarFinal + " tiene un recorrido de " + distanciaRecorrida + " Km ");

            } catch (Exception e) {
                // TODO: handle exception
                JOptionPane.showMessageDialog(null, "El lugar ingresado no es valido");
                option = false;
            }
        }
    }

    public static String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }

        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

}
