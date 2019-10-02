import java.util.Scanner;

/**
 * @author Mahedi Hassan
 * 2019-09-30
 */

public class Main {
    public Scanner scanner =  new Scanner(System.in);
    public static void main(String []a){
        Main main = new Main();
        main.start();
    }

    public void start(){
        System.out.println("Set Request :");
        String request = scanner.nextLine();
        ParseRequest parseRequest = new ParseRequest();
        RequestModel requestModel = parseRequest.parse(request);

        PrepareRequest prepareRequest = new PrepareRequest();

        switch (requestModel.requestType){
            case Constant.HELP :
                prepareRequest.showHelpData(requestModel);
                break;
            case Constant.GET :
                prepareRequest.makeGETRequest(requestModel);
                break;
            case Constant.POST :
                prepareRequest.makePOSTRequest(requestModel);
                break;
        }
    }
}
