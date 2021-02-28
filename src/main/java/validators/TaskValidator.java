package validators;

public class TaskValidator {
    public boolean isValid;

    public String task;
    public String entity;
    public String arguments;

    public TaskValidator (String inputString){
        int taskIndex = inputString.indexOf(" ");
        if (taskIndex == -1){
            isValid = false;
        }
        else {
            task = inputString.substring(0,taskIndex);
            if (task.equals("Insert") || task.equals("Delete")){
                isValid = true;
                int entityIndex = inputString.indexOf(":");
                entity = inputString.substring(taskIndex + 1, entityIndex);
                arguments = inputString.substring(entityIndex + 2);
            }
            if (task.equals("Report")){
                isValid = true;
                entity = inputString.substring(taskIndex + 1);
            }
            if (task.equals("Order:")){
                isValid = true;
                entity = "orderInsert";
                arguments = inputString.substring(7);
            }
        }
    }
}
