/**
 * StateMachine for managing a DemoApp's states
 * @author rspell
 *
 */
public class RegionStateMachine {
	// the owner of the state machine
	protected DemoApp app = null;
	// the current state that the DemoApp is in
	protected RegionState currentState = null;
	// the state that the owner was in during the previous time step
	protected RegionState previousState = null;
	// a state to run every time step; this state typically has logic that is
	// common to any state in the state machine
	protected RegionState globalState = null;
	
	/**
	 * Create a StateMachine that is owned by a DemoApp, owner.
	 * Owner just means that the state machine will have access to the properties and methods
	 * of the owner. In other words it will be able to change the properties in the owner
	 * @param owner
	 */
	public RegionStateMachine (DemoApp owner) {
		this.app = owner;
	}
	
	// the next 3 methods are used to initialize the State Machine
	public void setCurrentState(RegionState state) {
		currentState = state;
	}
	
	public void setPreviousState(RegionState state) {
		previousState = state;	
	}
	
	public void setGlobalState(RegionState state) {
		globalState = state;
	}

	// update the State Machine
	public void update() {
		// only run this if we have a global state
		if(globalState != null) {
			globalState.execute( app );
		}
		
		// only run if we have a current state
		if(currentState != null) {
			// the logic for the application is in the states...
			// the application "behaves" differently depending on the state 
			// that the app is in
			currentState.execute(app);
		}
	}
	
	/**
	 * Will change the currentState to newState.
	 * This method will call the exit method of the currentState that is being left
	 * and the enter method on newState
	 * 
	 * @param newState
	 */
	public void changeState(RegionState newState) {
		assert newState != null : "<StateMachine::changeState> trying to assign null state to current";
		
		//keep a record of the previous state
		previousState = currentState;
		
		//call exit on the current state
		currentState.exit(app);
		
		// update the current state...
		currentState = newState;
		
		// ...run entry logic for current state
		currentState.enter(app);
	}
	
	/**
	 * go pack to the previousState
	 */
	public void revertToPreviousState() {
		changeState(previousState);
	}
	
	// note this does straight object comparison. So even though the 2 states might be of the 
	// class they will not be equal unless they are the same object. 
	// TODO: make this more fool proof
	public boolean isInState(RegionState state) {
		return currentState.getClass().getName().equals(state.getClass().getName());
	}

}
