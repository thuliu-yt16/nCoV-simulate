package mavendemo;
import javax.swing.*;
/**
 * Hello world!
 *
 */
public class App {
    private static void createAndShowGUI() {
        // 确保一个漂亮的外观风格

        // 创建及设置窗口
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 添加 "Hello World" 标签
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);

        // 显示窗口
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // 显示应用 GUI
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
    // public static void main( String[] args ) {
    //     System.out.println( "Hello World!" );
    // }
}
