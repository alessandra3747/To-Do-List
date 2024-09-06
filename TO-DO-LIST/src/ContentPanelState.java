public enum ContentPanelState {

    ALL_TASKS, TODAY;

    private static ContentPanelState currentState = ContentPanelState.ALL_TASKS;

    public static ContentPanelState getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(ContentPanelState newState) {
        currentState = newState;
    }

}
