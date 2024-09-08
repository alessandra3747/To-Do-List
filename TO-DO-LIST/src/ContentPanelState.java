public enum ContentPanelState {

    ALL_TASKS, TODAY;

    private static ContentPanelState currentState = ContentPanelState.ALL_TASKS;

    public static ContentPanelState getCurrentState() {
        return currentState;
    }

    public static void setCurrentState(ContentPanelState newState) {

        switch (newState) {
            case ALL_TASKS:
                ContentPanel.getInstance().refreshTitle("YOUR TASKS");
                break;
            case TODAY:
                ContentPanel.getInstance().refreshTitle("YOUR TASKS TODAY");
                break;
        }

        currentState = newState;
    }

}
