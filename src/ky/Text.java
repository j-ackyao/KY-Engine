package ky;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Text extends Asset {
	
	private String text;
	private Font font;
	private Color color;
	
	private String updateText;
	private Font updateFont;
	private Color updateColor;
	
	public Text(String text, Font font, Color color, Vector2D position, int width, int height, int layer) {
		super(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), position, layer);
		this.text = text;
		this.font = font;
		this.color = color;
		this.updateText = text;
		this.updateFont = font;
		this.updateColor = color;
		
		Graphics g = this.images[0].getGraphics();
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, 0, font.getSize());
		g.dispose();
	}
	
	public Text(String text, Font font, Color color, Vector2D position, int width, int height, int layer, String name) {
		super(new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB), position, layer, name);
		this.text = text;
		this.font = font;
		this.color = color;
		this.updateText = text;
		this.updateFont = font;
		this.updateColor = color;
		
		Graphics2D g = this.images[0].createGraphics();
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
		this.updateText = text;
	}
	
	public void setFont(Font font) {
		this.updateFont = font;
	}
	
	public void setColor(Color color) {
		this.updateColor = color;
	}
	

	public Text clone() {
		Text clone = new Text(getText(), getFont(), getColor(), getPos(), getWidth(), getHeight(), getLayer(), getName());
		clone.setVisible(isVisible());
		clone.setImageIndex(getImageIndex());
		return clone; 
	}
	
	
	
	public void updateText() {
		if(!this.text.equals(this.updateText) || !this.font.equals(this.updateFont) || !this.color.equals(this.updateColor)) {
			this.text = this.updateText;
			this.font = this.updateFont;
			this.color = this.updateColor;
			
			Graphics2D g = this.images[0].createGraphics();
			g.setBackground(new Color(255, 255, 255, 0));
			g.clearRect(0, 0, getWidth(), getHeight());
			
			g.setFont(this.font);
			g.setColor(this.color);
			g.drawString(this.text, 0, this.font.getSize());
			g.dispose();
		}
	}
}
