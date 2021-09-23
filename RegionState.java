/**
 * Abstract class that all states inherit from.
 * 
 * @author rspell
 *
 */
public abstract class RegionState {
	protected String name;
	
	/**
	 * Subclasses must implement this method. This method provides
	 * the behavior that should be performed when the app is in this state.
	 * 
	 * @param r the application that this state controls
	 */
	abstract public void execute(DemoApp r);
	
	/**
	 * Objects are created with a name that will serve as an identifier
	 * @param name
	 */
	protected RegionState(String name) {
		this.name = name;
	}
	
	/**
	 * Actions to perform when entering a state
	 * 
	 * @param r the application that this state controls 
	 */
	public void enter(DemoApp r) { 
		System.out.printf("entering state %s\n",name,r);
	}
	
	/**
	 * Actions to perform when exiting a state
	 * 
	 * @param r the application that this state controls 
	 */
	public void exit(DemoApp r) { 
		System.out.printf("exiting state %s\n",name,r);
	}	
		
	public String name() { return name;}
	
	
	public String toString() {
		return name();
	}
}

/**
 * State logic that is universal to all states. 
 * @author rspell
 *
 */
class GlobalRegionState extends RegionState {
	private static String myName = "GlobalRegionState";
	private static GlobalRegionState instance = null;
	
	private GlobalRegionState() {
		super(myName);
	}
	
	public static GlobalRegionState getInstance() {
		if(instance == null)
			instance = new GlobalRegionState();
		return instance;	
	}
	
	/**
	 * This is where the magic happens. This global state checks each
	 * region to see if it contains the current mouse position.
	 * Depending on the mouse's position the StateMachine for the DemoApp
	 * is used to change the state of the application
	 */
	public void execute(DemoApp r) {
		int mX = r.mouseX;
		int mY = r.mouseY;
		MouseArea currentArea = r.hoverMouseArea;
		
		// we shouldn't need to do anything because we are 
		// in the same area
		if(currentArea.contains(mX, mY)) {
			return;
		} else {			
			for(MouseArea ma : r.mArea) {
				if(ma.contains(mX, mY)) {
					r.hoverMouseArea = ma;
					
					if(ma.id() == DemoApp.UPPERLEFT) {
						r.sm.changeState(UpperLeftAnimateState.getInstance());
					} else if(ma.id() == DemoApp.UPPERRIGHT) {
						r.sm.changeState(UpperRightAnimateState.getInstance());
					} else if(ma.id() == DemoApp.LOWERLEFT) {
						r.sm.changeState(LowerLeftAnimateState.getInstance());
					} else if(ma.id() == DemoApp.LOWERRIGHT) {
						r.sm.changeState(LowerRightAnimateState.getInstance());
					}
					
					break;
				}
			}
		}
	}
	
	// Override enter and exit so we don't print the default messages
	@Override
	public void enter(DemoApp r) {}
	@Override
	public void exit(DemoApp r) {}	
}


/**
 * Logic for when the mouse is in the UpperLeft region of the canvas
 * @author rspell
 *
 */
class UpperLeftAnimateState extends RegionState {
	private static String myName = "UpperLeftAnimateState";
	private static UpperLeftAnimateState instance = null;
	
	
	private UpperLeftAnimateState() {
		super(myName);
	}
	
	public static UpperLeftAnimateState getInstance() {
		if(instance == null)
			instance = new UpperLeftAnimateState();
		return instance;	
	}
	
	/**
	 * Update and draw the region associated with this state
	 */
	public void execute(DemoApp r) {
		for(MouseArea ma : r.mArea) {
			
			// only draw regions if they are not the one associated with this state
			if(ma.id() != DemoApp.UPPERLEFT) {
				ma.draw();
			}
			// if the MouseArea is the one associated with this state
			// then we need to update it before we draw it. The update
			// will handle the animation
			else {
				ma.update();
				ma.draw();
			}
		}
	}
	
	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.UPPERLEFT].resetColor();
	}
}


/**
 * @see class UpperLeftAnimateState
 * 
 * @author rspell
 *
 */
class UpperRightAnimateState extends RegionState {
	private static String myName = "UpperRightAnimateState";
	private static UpperRightAnimateState instance = null;
	
	
	private UpperRightAnimateState() {
		super(myName);
	}
	
	public static UpperRightAnimateState getInstance() {
		if(instance == null)
			instance = new UpperRightAnimateState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		for(MouseArea ma : r.mArea) {
			if(ma.id() != DemoApp.UPPERRIGHT) {
				ma.draw();
			} else {
				ma.update();
				ma.draw();
			}
		}		
	}

	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.UPPERRIGHT].resetColor();
	}
}


/**
 * @see class UpperLeftAnimateState
 * 
 * @author rspell
 *
 */
class LowerLeftAnimateState extends RegionState {
	private static String myName = "LowerLeftAnimateState";
	private static LowerLeftAnimateState instance = null;
	
	
	private LowerLeftAnimateState() {
		super(myName);
	}
	
	public static LowerLeftAnimateState getInstance() {
		if(instance == null)
			instance = new LowerLeftAnimateState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		for(MouseArea ma : r.mArea) {
			if(ma.id() != DemoApp.LOWERLEFT) {
				ma.draw();
			} else {
				ma.update();
				ma.draw();
			}
		}		
	}

	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.LOWERLEFT].resetColor();
	}	
}

/**
 * @see class UpperLeftAnimateState
 * 
 * @author rspell
 *
 */
class LowerRightAnimateState extends RegionState {
	private static String myName = "LowerRightAnimateState";
	private static LowerRightAnimateState instance = null;
	
	
	private LowerRightAnimateState() {
		super(myName);
	}
	
	public static LowerRightAnimateState getInstance() {
		if(instance == null)
			instance = new LowerRightAnimateState();
		return instance;	
	}
	
	@Override
	public void execute(DemoApp r) {
		for(MouseArea ma : r.mArea) {
			if(ma.id() != DemoApp.LOWERRIGHT) {
				ma.draw();
			} else {
				ma.update();
				ma.draw();
			}
		}		
	}
	
	public void exit(DemoApp r) {
		super.exit(r);
		r.mArea[DemoApp.LOWERRIGHT].resetColor();
	}	
}
