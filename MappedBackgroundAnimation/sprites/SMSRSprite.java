import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class SMSRSprite implements DisplayableSprite, MovableSprite {
	
	private static final double VELOCITY = 200;
	private static final int WIDTH = 50;
	private static final int HEIGHT = 50;
	private static final int PERIOD_LENGTH = 200;
	private static final int IMAGES_IN_CYCLE = 1;
	
	private static Image[] images;	
	private double centerX = 400;
	private double centerY = 300;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	
	private double velocityX = 0;
	private double velocityY = 0;
	private int elapsedTime = 0;
	
	private Direction direction = Direction.NEUTRAL;
	
	private enum Direction { NEUTRAL(0), DOWN(1), LEFT(2), UP(3), RIGHT(4);
		private int value = 0;
		private Direction(int value) {
			this.value = value; 
		} 
	};

	
	public SMSRSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		
		if (images == null) {
			try {
				images = new Image[5];
				for (int i = 0; i < 5; i++) {
					String path = String.format("res/SMSR/SMSRsprite-%d.png", i);
					images[i] = ImageIO.read(new File(path));
				}
			}
			catch (IOException e) {
				System.err.println(e.toString());

			}		
		}		
	}



	public Image getImage() {
		
		
		//calculate index into array of all images. this is an arbitrary value, depending on how the image files are ordered
		int index = direction.value;
						
		return SMSRSprite.images[index];
				
	}
	
	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
		
	}

	public void setVelocityX(double pixelsPerSecond) {
		this.velocityX = pixelsPerSecond;
		
	}

	public void setVelocityY(double pixelsPerSecond) {
		this.velocityY = pixelsPerSecond;
		
	}


	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return true;
	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getCenterX() {
		return centerX;
	}

	public double getCenterY() {
		return centerY;
	}
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void setDispose(boolean dispose) {
		this.dispose = dispose;
	}


	public void update(Universe universe, KeyboardInput keyboard, long actual_delta_time) {
		System.out.println(velocityX);
		
		elapsedTime += actual_delta_time;
		
		velocityX = 0;
		velocityY = 0;
		
		
		this.centerX += actual_delta_time * 0.001 * velocityX;
		this.centerY += actual_delta_time * 0.001 * velocityY;
		
		//LEFT ARROW
		if (keyboard.keyDown(37)) {
			velocityX = -VELOCITY;
		}
		//UP ARROW
		if (keyboard.keyDown(38)) {
			velocityY = -VELOCITY;			
		}
		//RIGHT ARROW
		if (keyboard.keyDown(39)) {
			velocityX += VELOCITY;
		}
		// DOWN ARROW
		if (keyboard.keyDown(40)) {
			velocityY += VELOCITY;			
		}
		
		//animation
		if (velocityY < 0) {
			direction = Direction.UP;
		}
		
		else if (velocityY > 0) {
			direction = Direction.DOWN;
		}
		
		else if (velocityX < 0) {
			direction = Direction.LEFT;
		}
		
		else if (velocityX > 0) {
			direction = Direction.RIGHT;
		}
		
		else if (velocityX == 0 && velocityY == 0) {
			direction = Direction.NEUTRAL;
		}
		
	}
}


