package ADRAF.com.nk.panel;

import javax.swing.*;
import java.awt.*;

/**
 * 可以添加背景图片的面板
 */
public class BackgroundPanel extends JPanel {
	private static final long serialVersionUID = 1L;// 序列化编号
	private Image image; // 定义图像对象

	/**
	 * 面板构造方法
	 * 
	 * @param image
	 *            -背景图片对象
	 */
	public BackgroundPanel(Image image) {
		super(); // 调用超类的构造方法
		this.image = image; // 为图像对象赋值
		initialize();
	}

	/**
	 * 重写绘制组件方法
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // 调用父类的方法
		Graphics2D g2 = (Graphics2D) g; // 创建Graphics2D对象
		if (image != null) {
			int width = getWidth(); // 获得面板的宽度
			int height = getHeight(); // 获得面板的高度
			// 绘制图像
			g2.drawImage(image, 0, 0, width, height, this);
		}
	}

	/**
	 * 初始化面板大小
	 */
	private void initialize() {
		this.setSize(300, 200);
	}
}
