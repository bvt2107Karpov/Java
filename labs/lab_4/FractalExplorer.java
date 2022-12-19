import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.geom.Rectangle2D;
import javax.imageio.ImageIO;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.*;
import javax.swing.*;

public class FractalExplorer {

    /*
     * 1) Целое число «размер экрана», которое является шириной и высотой
     * отображения в пикселях. (Отображение фрактала будет квадратным.)
     */
    private int _displaySize;

    /*
     * 2) Ссылка JImageDisplay, для обновления отображения в разных
     * методах в процессе вычисления фрактала.
     */
    private JImageDisplay _image;

    /*
     * 3) Объект FractalGenerator. Будет использоваться ссылка на базовый
     * класс для отображения других видов фракталов в будущем.
     */
    private FractalGenerator _gen;

    /*
     * 4) Объект Rectangle2D.Double, указывающий диапазона комплексной
     * плоскости, которая выводится на экран.
     * 
     */
    private Rectangle2D.Double _range;

    /*
     * Комбобокс
     */
    private JComboBox<String> _fractalChooser;

    /*
     * Кнопка сохранения в файл
     */
    private JButton _saveButton;

    /*
     * Кнопка сброса отображения
     */
    private JButton _resetButton;

    /*
     * Хранит информацию об оставшихся рядах
     */
    private int _rowsRemaining;

    /*
     * Не дает нажать на кнопку пока перерисовывается фрактал
     */
    private void enableUI(boolean val) {
        _fractalChooser.setEnabled(val);

        _saveButton.setEnabled(val);
        _resetButton.setEnabled(val);
    }

    // Воркер для рисования, работает в многопотоке
    private class FractalWorker extends SwingWorker<Object, Object> {

        private int _y;

        private int[] _RGBVals; // Определяем массив ргб

        public FractalWorker(int y) {
            _y = y;
        }

        public Object doInBackground() {
            _RGBVals = new int[_displaySize]; // Сразу задаем массиву размер, чтобы не было утечки

            double yCoord = FractalGenerator.getCoord(_range.y, _range.y + _range.height,
                    _displaySize, _y);

            for (int x = 0; x < _displaySize; x++) {

                double xCoord = FractalGenerator.getCoord(_range.x, _range.x + _range.width,
                        _displaySize, x);
                int numIters;
                int rgbColor = 0;
                float hue;

                numIters = _gen.numIterations(xCoord, yCoord);
                if (numIters >= 0) {
                    hue = 0.7f + numIters / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }

                _RGBVals[x] = rgbColor;
            }

            return null;
        }

        public void done() {
            for (int x = 0; x < _displaySize; x++) {
                _image.drawPixel(x, _y, _RGBVals[x]);
            }

            _image.repaint(0, 0, _y, _displaySize, 1);

            if (_rowsRemaining-- < 1) {
                enableUI(true);
            }
        }
    }

    // Вложенный класс обработчик кнопок в интерфейсе
    private class FractalHandler implements ActionListener {

        // Реагирует на любые ивенты связанные с интерфейсом
        public void actionPerformed(ActionEvent e) {
            String cmd = e.getActionCommand();

            if (e.getSource() == _fractalChooser) {
                String selectedItem = _fractalChooser.getSelectedItem().toString();

                if (selectedItem.equals(Mandelbrot.getString())) {
                    _gen = new Mandelbrot();
                } else if (selectedItem.equals(Tricorn.getString())) {
                    _gen = new Tricorn();
                } else if (selectedItem.equals(BurningShip.getString())) {
                    _gen = new BurningShip();
                } else {
                    JOptionPane.showMessageDialog(null, "Ошибка ввода");
                    return;
                }

                _range = new Rectangle2D.Double();
                _gen.getInitialRange(_range);

                drawFractal();
            } else if (cmd.equals("reset")) {
                _range = new Rectangle2D.Double();
                _gen.getInitialRange(_range);

                drawFractal();
            } else if (cmd.equals("save")) {
                JFileChooser chooser = new JFileChooser();

                FileNameExtensionFilter filter = new FileNameExtensionFilter("PNG", "png");
                chooser.setFileFilter(filter);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                    try {
                        File f = chooser.getSelectedFile();
                        String filePath = f.getPath();
                        if (!filePath.toLowerCase().endsWith(".png")) {
                            f = new File(filePath + ".png");
                        }

                        ImageIO.write(_image.getImage(), "png", f);
                    } catch (IOException exc) {
                        JOptionPane.showMessageDialog(null, "Ошибка сохранения ( " + exc.getMessage() + " )");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "Неизвестная ошибка");
            }
        }
    }

    // Вложенный класс обработчик события кликов мышкой по координатам на экране
    private class MouseHandler extends MouseAdapter {

        // Реакция на ивент клика, регенерирует фрактал для нового положения
        public void mouseClicked(MouseEvent e) {

            if (_rowsRemaining > 0) {
                return;
            }

            double xCoord = FractalGenerator.getCoord(_range.x, _range.x + _range.width,
                    _displaySize, e.getX());

            double yCoord = FractalGenerator.getCoord(_range.y, _range.y + _range.height,
                    _displaySize, e.getY());

            _gen.recenterAndZoomRange(_range, xCoord, yCoord, 0.5);

            drawFractal();
        }
    }

    /*
     * Конструктор, принимает размер отображения,
     * по умолчанию дефолтный фрактал - мандельброт
     */
    public FractalExplorer(int size) {
        this._displaySize = size;

        this._gen = new Mandelbrot();
        this._range = new Rectangle2D.Double();
        this._gen.getInitialRange(this._range);
    }

    /*
     * Инициализирует графический интерфейс Swing: JFrame, содержащий объект
     * JimageDisplay, и кнопку для сброса отображения.
     */
    public void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.getContentPane().setLayout(new BorderLayout());

        FractalHandler handler = new FractalHandler();

        // Панель выбора фрактала
        JPanel fractalPanel = new JPanel();

        // Лэйбл с названием
        JLabel label = new JLabel("Fractal: ");
        fractalPanel.add(label);

        _fractalChooser = new JComboBox<String>();
        _fractalChooser.addItem(Mandelbrot.getString());
        _fractalChooser.addItem(Tricorn.getString());
        _fractalChooser.addItem(BurningShip.getString());

        
        _fractalChooser.addActionListener(handler);
        fractalPanel.add(_fractalChooser);

        frame.getContentPane().add(fractalPanel, BorderLayout.NORTH);

        // Вставляет изображение
        _image = new JImageDisplay(_displaySize, _displaySize);
        frame.getContentPane().add(_image, BorderLayout.CENTER);

        // Панель кнопок
        JPanel buttonsPanel = new JPanel();

        // Кнопка сохранения по событию клика
        _saveButton = new JButton("Сохранить");
        _saveButton.setActionCommand("save");
        _saveButton.addActionListener(handler);
        buttonsPanel.add(_saveButton);

        // Сброс отображения
        _resetButton = new JButton("Reset Display");
        _resetButton.setActionCommand("reset");
        _resetButton.addActionListener(handler);
        buttonsPanel.add(_resetButton);

        // Компановка
        frame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        frame.getContentPane().addMouseListener(new MouseHandler());

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    // Вывод фрактала на экран
    public void drawFractal() {
        enableUI(false);

        for (int y = 0; y < _displaySize; y++) {
            FractalWorker worker = new FractalWorker(y);
            worker.execute();
        }

        _image.repaint();
    }

    // Точка входа в программу
    public static void main(String[] args) {
        FractalExplorer explorer = new FractalExplorer(800);
        explorer.createAndShowGUI();
        explorer.drawFractal();
    }
}