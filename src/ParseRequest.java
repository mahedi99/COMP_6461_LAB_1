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
                                requestModel.isHeader = true;
                                requestModel.headerData = commandArray[i + 1];
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
                                    i++; //ignoring this command
                                }
                                break;
                            case "-f":
                                if (!requestModel.isInlineData) {
                                    requestModel.isFile = true;
                                    requestModel.fileData = commandArray[i + 1];
                                    i++;
                                } else {
                                    i++;//ignoring this command
                                }
                                break;
                            case "-h":
                                requestModel.isHeader = true;
                                requestModel.headerData = commandArray[i + 1];
                                i++;
                                break;
                        }
                    }
                }
            }
        }
    }
}
