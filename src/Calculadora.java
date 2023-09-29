import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculadora extends JFrame implements ActionListener {

    private JTextField pantalla;
    private JButton[] botones;
    private String[] etiquetas = {
        "7", "8", "9", "/",
        "4", "5", "6", "*",
        "1", "2", "3", "-",
        "0", "C", "=", "+"
    };
    private String operador = "";
    private double numero1 = 0;
    private boolean nuevoNumero = true;

    public Calculadora() {
        pantalla = new JTextField(10);
        pantalla.setEditable(false);
        pantalla.setHorizontalAlignment(JTextField.RIGHT);

        botones = new JButton[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            botones[i] = new JButton(etiquetas[i]);
            botones[i].addActionListener(this);
        }

        JPanel panelNumeros = new JPanel();
        panelNumeros.setLayout(new GridLayout(4, 4, 10, 10));

        for (int i = 0; i < etiquetas.length; i++) {
            panelNumeros.add(botones[i]);
        }

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(pantalla, BorderLayout.NORTH);
        getContentPane().add(panelNumeros, BorderLayout.CENTER);

        setTitle("Calculadora Simple");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String etiqueta = ((JButton) e.getSource()).getText();
        if (etiqueta.matches("[0-9]")) {
            if (nuevoNumero) {
                pantalla.setText(etiqueta);
                nuevoNumero = false;
            } else {
                pantalla.setText(pantalla.getText() + etiqueta);
            }
        } else if (etiqueta.matches("[+\\-*/=]")) {
            if (!operador.isEmpty() && !nuevoNumero) {
                calcular();
            }
            operador = etiqueta;
            numero1 = Double.parseDouble(pantalla.getText());
            nuevoNumero = true;
        } else if (etiqueta.equals("C")) {
            pantalla.setText("");
            operador = "";
            numero1 = 0;
            nuevoNumero = true;
        } else if (etiqueta.equals("=")) {
            if (!operador.isEmpty() && !nuevoNumero) {
                calcular();
                operador = "";
                nuevoNumero = true;
            }
        }
    }

    private void calcular() {
        double numero2 = Double.parseDouble(pantalla.getText());
        double resultado = 0;

        switch (operador) {
            case "+":
                resultado = numero1 + numero2;
                break;
            case "-":
                resultado = numero1 - numero2;
                break;
            case "*":
                resultado = numero1 * numero2;
                break;
            case "/":
                if (numero2 != 0) {
                    resultado = numero1 / numero2;
                } else {
                    pantalla.setText("Error");
                    return;
                }
                break;
        }

        pantalla.setText(String.valueOf(resultado));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Calculadora calc = new Calculadora();
            calc.setVisible(true);
        });
    }
}
