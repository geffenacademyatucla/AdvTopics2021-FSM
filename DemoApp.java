import processing.core.PApplet;
import processing.core.PVector;
import java.util.HashMap;

/**
 * Application to demonstrate controlling an application with a StateMachine
 * Notice that the application has no logic to control the visuals, just setup procedures.
 * All of the logic resides in the States
 * @author rspell
 *
 */
public class DemoApp extends PApplet {
	
	// Static Attributes
	public static final Integer UPPERLEFT = 0;
	public static final Integer UPPERRIGHT = 1;
	public static final Integer LOWERLEFT = 2;
	public static final Integer LOWERRIGHT = 3;
	
	// This is a place holder reference so that classes that need to access
	// processing methods can do so
	public static PApplet ctx;
	
	// Attributes
	// An Array of MouseAreas that cover the canvas of the application
	MouseArea[] mArea;
	
	// Object to manage the state of the application
	RegionStateMachine sm;
	
	// The current MouseArea where the mouse is located
	MouseArea hoverMouseArea;
	
	public static void main(String[] args) {
		PApplet.main(DemoApp.class);	
	}
	
	public void settings() { size(800,400); }
	
	public void setup() {
		ctx = this;
		
		//initialize areas
		mArea = new MouseArea[4];
		mArea[UPPERLEFT] = new MouseArea(0,height*0.5,0,width*0.5,color(0),color(255),UPPERLEFT);
		mArea[UPPERRIGHT] = new MouseArea(0,height*0.5,width*0.5,width,color(255),color(0),UPPERRIGHT);
		mArea[LOWERLEFT] = new MouseArea(height*0.5,height,0,width*0.5,color(200,0,0), color(0,200,0),LOWERLEFT);
		mArea[LOWERRIGHT] = new MouseArea(height*0.5, height, width*0.5, width, color(0,200,0), color(200,0,0),LOWERRIGHT);
		
		//initial hoverMouseArea is the upper left
		hoverMouseArea = mArea[UPPERLEFT];
		
		// create State Machine
		sm = new RegionStateMachine(this);
		// set the global state for the state machine
		sm.setGlobalState(GlobalRegionState.getInstance());
		// initialize the current state
		sm.setCurrentState(UpperLeftAnimateState.getInstance());

	}
	
	public void draw() {
		background(255);
		
		// everything happens in here...
		sm.update();

	}

	
	/**
	 * Convenience method to save typing when calling System.out.printf
	 * 
	 * @param formatString
	 * @param args
	 */
	public static void printf(String formatString, Object ... args ) {
		System.out.printf(formatString, args);
	}
}
