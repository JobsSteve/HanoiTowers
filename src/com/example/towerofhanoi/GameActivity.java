package com.example.towerofhanoi;

import java.io.IOException;
import java.io.InputStream;
import java.util.Stack;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.input.touch.TouchEvent;

public class GameActivity extends SimpleBaseGameActivity {

	private static int CAMERA_HEIGHT = 480;
	private static int CAMERA_WIDTH = 800;
	private ITextureRegion mBackgroundTextureRegion, mTowerTextureRegion, mRing1, mRing2, mRing3;
	private Sprite mTower1,mTower2,mTower3;
	private Stack mStack1, mStack2,mStack3;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		//Create camera and its position on screen
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), camera);
	}

	@Override
	protected void onCreateResources() {
		//Load images
		try {
			ITexture backgroundTexture = new BitmapTexture(this.getTextureManager(),
					new IInputStreamOpener() {
						
						@Override
						public InputStream open() throws IOException {
							// TODO Auto-generated method stub
							return getAssets().open("gfx/background.png");
						}
					});
			
			ITexture towerTexture = new BitmapTexture(this.getTextureManager(),
					new IInputStreamOpener() {
						
						@Override
						public InputStream open() throws IOException {
							// TODO Auto-generated method stub
							return getAssets().open("gfx/tower.png");
						}
					});
			
			ITexture ring1Texture = new BitmapTexture(this.getTextureManager(),
					new IInputStreamOpener() {
						
						@Override
						public InputStream open() throws IOException {
							// TODO Auto-generated method stub
							return getAssets().open("gfx/ring1.png");
						}
					});
			
			ITexture ring2Texture = new BitmapTexture(this.getTextureManager(),
					new IInputStreamOpener() {
						
						@Override
						public InputStream open() throws IOException {
							// TODO Auto-generated method stub
							return getAssets().open("gfx/ring2.png");
						}
					});
			
			ITexture ring3Texture = new BitmapTexture(this.getTextureManager(),
					new IInputStreamOpener() {
						
						@Override
						public InputStream open() throws IOException {
							// TODO Auto-generated method stub
							return getAssets().open("gfx/ring3.png");
						}
					});
			
			backgroundTexture.load();
			towerTexture.load();
			ring1Texture.load();
			ring2Texture.load();
			ring3Texture.load();
			

			//Setup texture regions
			mBackgroundTextureRegion = TextureRegionFactory.extractFromTexture(backgroundTexture);
			mTowerTextureRegion = TextureRegionFactory.extractFromTexture(towerTexture);

			mRing1 = TextureRegionFactory.extractFromTexture(ring1Texture);
			mRing2 = TextureRegionFactory.extractFromTexture(ring2Texture);
			mRing3 = TextureRegionFactory.extractFromTexture(ring3Texture);
			
			//Create stacks
			mStack1 = new Stack();
			mStack2 = new Stack();
			mStack3 = new Stack();

			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected Scene onCreateScene() {
		//Create new scene
		final Scene scene = new Scene();
		Sprite backgroundSprite = new Sprite(0, 0, mBackgroundTextureRegion, getVertexBufferObjectManager());
		scene.attachChild(backgroundSprite);
		
		//Create towers
		mTower1 = new Sprite(192, 63, this.mTowerTextureRegion, getVertexBufferObjectManager());
		mTower2 = new Sprite(400, 63, this.mTowerTextureRegion, getVertexBufferObjectManager());
		mTower3 = new Sprite(604, 63, this.mTowerTextureRegion, getVertexBufferObjectManager());
		//Add towers to screen
		scene.attachChild(mTower1);
		scene.attachChild(mTower2);
		scene.attachChild(mTower3);

		//Create rings
		Ring ring1 = new Ring(1, 139, 174, mRing1, getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if(((Ring)this.getmStack().peek()).getmWeight() != this.getmWeight()){
					return false;
				} 
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, 
						pSceneTouchEvent.getY() - this.getHeight()/2);
				
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
					checkForCollisionwithTowers(this);
				}
				
				return true;
			}
			
		};
		
		Ring ring2 = new Ring(2, 118, 212, mRing2, getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if(((Ring)this.getmStack().peek()).getmWeight() != this.getmWeight()){
					return false;
				} 
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, 
						pSceneTouchEvent.getY() - this.getHeight()/2);
				
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
					checkForCollisionwithTowers(this);
				}
				
				return true;
			}
			
		};
		
		Ring ring3 = new Ring(3, 97, 255, mRing3, getVertexBufferObjectManager()) {

			@Override
			public boolean onAreaTouched(TouchEvent pSceneTouchEvent,
					float pTouchAreaLocalX, float pTouchAreaLocalY) {

				if(((Ring)this.getmStack().peek()).getmWeight() != this.getmWeight()){
					return false;
				} 
				this.setPosition(pSceneTouchEvent.getX() - this.getWidth()/2, 
						pSceneTouchEvent.getY() - this.getHeight()/2);
				
				if(pSceneTouchEvent.getAction() == TouchEvent.ACTION_UP){
					checkForCollisionwithTowers(this);
				}
				
				return true;
			}
			
		};
		

		//Add rings to scene
		scene.attachChild(ring1);
		scene.attachChild(ring2);
		scene.attachChild(ring3);
		
		//Add rings to the first stack
		mStack1.add(ring3);
		mStack1.add(ring2);
		mStack1.add(ring1);

		//Setup rings initial position
		ring1.setmStack(mStack1);
		ring2.setmStack(mStack1);
		ring3.setmStack(mStack1);
		ring1.setmTower(mTower1);
		ring2.setmTower(mTower1);
		ring3.setmTower(mTower1);

		//Add touch event handlers
		scene.registerTouchArea(ring1);
		scene.registerTouchArea(ring2);
		scene.registerTouchArea(ring3);
		scene.setTouchAreaBindingOnActionDownEnabled(true);
		
		return scene;
	}

	public void checkForCollisionwithTowers(Ring ring){
		
		Stack stack = null;
		Sprite tower = null;
		
		if(ring.collidesWith(mTower1) && (mStack1.size() == 0 || ring.getmWeight() < ((Ring)mStack1.peek()).getmWeight())){
			stack = mStack1;
			tower = mTower1;
		} else if(ring.collidesWith(mTower2) && (mStack2.size() == 0 ||	ring.getmWeight() < ((Ring)mStack2.peek()).getmWeight())){
			stack = mStack2;
			tower = mTower2;
		} else if(ring.collidesWith(mTower3) && (mStack3.size() == 0 ||
				ring.getmWeight() < ((Ring)mStack3.peek()).getmWeight())){
			stack = mStack3;
			tower = mTower3;
		} else {
			stack = ring.getmStack();
			tower = ring.getmTower();
		}
		
		ring.getmStack().remove(ring);
		
		if(stack != null && tower != null && stack.size() == 0){
			ring.setPosition(tower.getX() + tower.getWidth()/2 - ring.getWidth()/2,
					tower.getY() + tower.getHeight() - ring.getHeight());
		} else if (stack != null && tower !=null && stack.size() > 0) {
	        ring.setPosition(tower.getX() + tower.getWidth()/2 - 
	                ring.getWidth()/2, ((Ring) stack.peek()).getY() - 
	                ring.getHeight());
	    }
		
		stack.add(ring);
		ring.setmStack(stack);
		ring.setmTower(tower);
	}
}
