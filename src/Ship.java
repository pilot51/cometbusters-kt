/*
 * Copyright 2013 Mark Injerd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.awt.Image;
import java.awt.geom.AffineTransform;


public class Ship {
	private static final int THRUST = 1, ROTATE_SPEED = 1; 
	private Image image;
	private Image[] thrustImages;
	private float posX, posY, velX, velY;
	private int shipRadius, thrustRadius, rotateSpeed, rotateDeg;
	private boolean isThrusting;
	private AffineTransform trans = new AffineTransform();
	
	/**
	 * Creates a new ship.
	 * @param img Image to use for the ship.
	 * @param thrustImgs Array of thrust images to be animated.
	 * @param x Initial x-position.
	 * @param y Initial y-position.
	 */
	Ship(Image img, Image[] thrustImgs, int x, int y) {
		image = img;
		shipRadius = image.getWidth(null) / 2;
		thrustImages = thrustImgs;
		thrustRadius = thrustImages[0].getWidth(null) / 2;
		posX = x;
		posY = y;
	}
	
	Image getImage() {
		return image;
	}
	
	private int thrustFrame;
	Image getThrustImage() {
		if (++thrustFrame == thrustImages.length) {
			thrustFrame = 0;
		}
		return thrustImages[thrustFrame];
	}
	
	void calculateMotion() {
		rotateDeg += rotateSpeed;
		if (rotateDeg < 0) {
			rotateDeg += 360;
		} else if (rotateDeg > 359) {
			rotateDeg -= 360;
		}
		final double radians = Math.toRadians(rotateDeg);
		final float dt = 0.1f;
		int thrust = isThrusting ? THRUST : 0;
		velY -= Math.cos(radians) * thrust * dt;
		velX += Math.sin(radians) * thrust * dt;
		posY += velY * dt;
		posX += velX * dt;
		if (posX < 0) {
			posX += GameView.VIEW_WIDTH;
		} else if (posX > GameView.VIEW_WIDTH) {
			posX -= GameView.VIEW_WIDTH;
		}
		if (posY < 0) {
			posY += GameView.VIEW_HEIGHT;
		} else if (posY > GameView.VIEW_HEIGHT) {
			posY -= GameView.VIEW_HEIGHT;
		}
	}
	
	AffineTransform getTransform() {
		trans.setToTranslation(posX - shipRadius, posY - shipRadius);
		trans.rotate(Math.toRadians(rotateDeg), shipRadius, shipRadius);
		return trans;
	}
	
	AffineTransform getThrustTransform() {
		trans.setToTranslation(posX - (thrustRadius - 1), posY + shipRadius / 2);
		trans.rotate(Math.toRadians(rotateDeg), thrustRadius - 1, -shipRadius / 2);
		return trans;
	}
	
	boolean isThrustActive() {
		return isThrusting;
	}
	
	void thrust(boolean activate) {
		if (isThrusting == activate) return;
		isThrusting = activate;
		if (activate) Sound.THRUST.loop();
		else Sound.THRUST.stop();
	}
	
	void rotateLeft() {
		rotateSpeed = -ROTATE_SPEED;
	}
	
	void rotateRight() {
		rotateSpeed = ROTATE_SPEED;
	}
	
	void rotateStop() {
		rotateSpeed = 0;
	}
}