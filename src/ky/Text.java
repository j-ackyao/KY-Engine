package ky;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Text extends Asset {
	
	private String text;
	private Font font;
	private Color color;
	
	public Text(String text, Font font, Color color, Vector2D position, int width, int height, int layer) {
		super(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), position, layer, "text");
		this.text = text;
		this.font = font;
		this.color = color;
		Graphics g = this.images[0].getGraphics();
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, 0, font.getSize());
		g.dispose();
	}
	
	public String getText() {
		return this.text;
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public void setText(String text) {
		this.text = text;
		this.images[0] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);;
		Graphics g = this.images[0].getGraphics();
		g.setFont(this.font);
		g.setColor(this.color);
		g.drawString(text, 0, this.font.getSize());
		g.dispose();
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
}
