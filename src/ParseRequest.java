/**
 * @author Mahedi Hassan
 * 2019-09-30
 */


public class ParseRequest {

    private static RequestModel requestModel = new RequestModel();

    public RequestModel parse(String request){
        String [] requestArray = request.trim().split(" ");

        switch (requestArray[1].toLowerCase()){
            case "help" :
                requestModel.requestType = "help";

                if (requestArray.length > 2){
                    requestModel.isHelpArg = true;
                    requestModel.helpArg = requestArray[2];
                }
                break;
            case "get":
                requestModel.requestType = "get";

//                String tmpUrl = requestArray[requestArray.length - 1].trim();
//                requestModel.url = tmpUrl.substring(1, tmpUrl.length() - 1);

                String allCommand = request.substring(request.indexOf(" ", request.indexOf(" ") + 1)).trim();
                prepareRequestForGet(allCommand);
                break;
            case "post":
                requestModel.requestType = "post";
//                requestModel.url = requestArray[requestArray.length - 1];
                String allCommandPost = request.substring(request.indexOf(" ", request.indexOf(" ") + 1)).trim();

                prepareRequestForPost(allCommandPost);
                break;
//            default:
//                requestModel.requestType = "get";
//                String allCommandDefault = request.substring(request.indexOf(" ", request.indexOf(" "))).trim();
//                prepareRequestForGet(allCommandDefault);
//                break;
        }
        return requestModel;
    }

    private void prepareRequestForGet(String commands){
        if (commands != null && !commands.equals("")) {
            String [] commandArray = commands.split(" ");
            if (commandArray.length > 0) {

                for (int i = 0; i < commandArray.length; i++) {
                    if (commandArray[i].trim().contains("http://")){
                        String tmpUrl = commandArray[i].trim();
                        requestModel.url = tmpUrl.substring(1, tmpUrl.length() - 1);
                    }
                    else {
                        switch (commandArray[i]) {
                            case "-v":
                                requestModel.verbose = true;
                                break;
                            case "-h":
                                if (requestModel.isHeader){
                                    requestModel.headerData+=",";
                                }
                                requestModel.headerData += commandArray[i + 1];
                                requestModel.isHeader = true;
                                i++;
                                break;
                            case "-o":
                                requestModel.writeFileName = commandArray[i + 1];
                                requestModel.isWriteToFile = true;
                                i++;
                                break;
                        }
                    }
                }

            }
        }
    }
    private void prepareRequestForPost(String commands){
        if (commands != null && !commands.equals("")) {
            String[] commandArray = commands.split(" ");
            if (commandArray.length > 0) {
                for (int i = 0; i < commandArray.length; i++) {
                    if (commandArray[i].trim().contains("http://")){
                        requestModel.url = commandArray[i];
                    }
                    else {
                        switch (commandArray[i]) {
                            case "-v":
                                requestModel.verbose = true;
                                break;
                            case "-d":
                                if (!requestModel.isFile) {
                                    requestModel.isInlineData = true;
                                    requestModel.inlineData = commandArray[i + 1] + commandArray[i + 2];
                                    i += 2;
                                } else {
                                    i++; //passing this command
                                }
                                break;
                            case "-f":
                                if (!requestModel.isInlineData) {
                                    requestModel.isFile = true;
                                    requestModel.readFileName = commandArray[i + 1];
                                    requestModel.readFileDir = Main.userStringInput("Provide file path : \n");
                                    i++;
                                } else {
                                    i++;//passing this command
                                }
                                break;
                            case "-h":
                                if (requestModel.isHeader){
                                    requestModel.headerData+=",";
                                }
                                requestModel.headerData += commandArray[i + 1];
                                requestModel.isHeader = true;
                                i++;
                                break;
                            case "-o":
                                requestModel.writeFileName = commandArray[i + 1];
                                requestModel.isWriteToFile = true;
                                i++;
                                break;
                        }
                    }
                }
            }
        }
    }
}
