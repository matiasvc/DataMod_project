import javafx.scene.layout.Pane;

public class InputHandler {

    public InputHandler() {
        this.SetState(InputHandlerState.MAIN);
    }

    public InputHandlerState state;

    public Boolean HandleInput(String[] input) {

        String cmd = input[0].toLowerCase();

        switch (this.state) {
            case MAIN:
                switch (cmd) {
                    case "activity":
                        SetState(InputHandlerState.ACTIVITY);
                        break;
                    case "goal":
                        SetState(InputHandlerState.GOAL);
                        break;
                    case "workout":
                        SetState(InputHandlerState.WORKOUT);
                        break;
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
                break;
            case ACTIVITY:
                switch (cmd) {
                    case "create":
                        if (NumArgs(input, 3)) {
                            CreateActivity(input[1], input[2]);
                        } break;
                    case "list":
                        if (NumArgs(input, 1)) {
                            //CreateActivity(input[1], input[2]);
                        } break;
                    case "delete":
                        if (NumArgs(input, 2)) {
                            DeleteActivity(input[1]);
                        } break;
                    case "back":
                        if (NumArgs(input, 1)) {
                            SetState(InputHandlerState.MAIN);
                        } break;
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
                break;

            case WORKOUT:
                switch (cmd) {
                    case "createtemplate":
                        if (NumArgs(input, 2)) {

                        } break;
                    case "listtemplate":
                        if (NumArgs(input, 1)) {

                        } break;
                    case "deletetemplate":
                        if (NumArgs(input, 2)) {

                        } break;
                    case "startworkout":
                        if (input.length == 1) {
                            StartWorkout();
                        } else if (input.length == 2) {
                            StartWorkout(input[1]);
                        } else {
                            System.out.println("Wrong number of arguments");
                            SetState(this.state);
                        }
                    default:
                        System.out.println("Invalid command: " + cmd);
                        break;
                }
                break;
            case GOAL:

                break;
        }

        return false;
    }

    public boolean NumArgs(String[] input, int n) {
        if (input.length == n) {
            return true;
        } else {
            System.out.println("Wrong number of arguments");
            SetState(this.state);
            return false;
        }
    }


    // ----- ACTIVITY ----
    public void CreateActivity(String name, String description) {

    }

    public void ListActivity() {

    }

    public void DeleteActivity(String name) {

    }

    // ---- WORKOUT ----
    public void CreateTemplate(String name) {

    }

    public void ListTemplate() {}

    public void StartWorkout() {}

    public void StartWorkout(String template) {}


    public void SetState(InputHandlerState newState) {

        switch (newState){
            case MAIN:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Main Commands: \n" +
                    "Activity  - Enter Activity menu\n" +
                    "Goal - Enter Goal menu\n" +
                    "Workout - Enter Workout menu" +
                    "-----------------------------------------------\n"
                );
                break;
            case ACTIVITY:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Activity Commands: \n" +
                    "Create [Name] [Description] - Create a new activity\n" +
                    "List - List all goals\n" +
                    "Delete [Name] - Delete a existing activity\n" +
                    "Back - Return to main menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case WORKOUT:
                System.out.print(
                    "-----------------------------------------------\n" +
                    "Workout Commands: \n" +
                    "CreateTemplate [Name]- Create a new workout template\n" +
                    "ListTemplate - List all workouts\n" +
                    "DeleteTemplate [Name] - Delete a existing workout template\n" +
                    "StartWorkout [Template(Optional)] - Start new workout\n" +
                    "Back - Return to main menu\n" +
                    "-----------------------------------------------\n"
                );
                break;
            case GOAL:
                break;
        }

        this.state = newState;
    }
}