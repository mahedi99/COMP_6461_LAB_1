import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author Mahedi Hassan
 * 2019-10-01
 */

public class PrepareRequest {

    public void makeGETRequest(RequestModel requestModel){
        String hostName = requestModel.url.substring(requestModel.url.indexOf("/") + 2,requestModel.url.lastIndexOf("/"));
        String message = requestModel.url.substring(requestModel.url.lastIndexOf("/"));
        try {
            InetAddress address = InetAddress.getByName(hostName);
            Socket socket = new Socket(address, 80);

            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("GET "+ message +" HTTP/1.1");
            pw.println("Host: " + hostName);
            if (requestModel.isHeader){
                pw.println(requestModel.headerData);
            }

            pw.println("Connection: Close");
            pw.println();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String response;

            if (!requestModel.verbose){
                boolean print = false;
                while ((response = in.readLine()) != null)
                {
                    if (print){
                        System.out.println( response );
                    }
                    if (!print && response.equals("")){
                        print = true;
                    }
                }
            }
            else { //verbose
                while ((response = in.readLine()) != null)
                {
                    System.out.println( response );
                }
            }

            in.close();
            pw.close();
            socket.close();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makePOSTRequest(RequestModel requestModel) {
        try {

            String hostName = requestModel.url.substring(requestModel.url.indexOf("/") + 2,requestModel.url.lastIndexOf("/"));
            String path = requestModel.url.substring(requestModel.url.lastIndexOf("/"));



            Socket httpSocket = new Socket(hostName,80);

            PrintWriter pw =
                    new PrintWriter(httpSocket.getOutputStream(), true);

            pw.println("POST "  + path + " HTTP/1.1");
            pw.println("Host: " + hostName);
            if(requestModel.isInlineData){
                pw.println("Content-Length:" +requestModel.inlineData.length());
            }
            if (requestModel.isHeader){
                String [] headers = parseHeader(requestModel.headerData);
                for (String tmpHeader : headers){
                    pw.println(tmpHeader);
                }
            }
            pw.println("");
            if (requestModel.isInlineData){
                String param1 = requestModel.inlineData.substring(0, requestModel.inlineData.indexOf(":") + 1);
                String param2 = requestModel.inlineData.substring(requestModel.inlineData.indexOf(":") + 1);
                pw.print(param1 + "=" + param2);
            }
            pw.flush();



            BufferedReader in =
                    new BufferedReader(
                            new InputStreamReader(httpSocket.getInputStream()));





            System.out.println("Showing response from the server now"); // Only reads from the Server


            String response;
            if (!requestModel.verbose){
                boolean print = false;
                while ((response = in.readLine()) != null)
                {
                    if (print){
                        System.out.println( response );
                    }
                    if (!print && response.equals("")){
                        print = true;
                    }
                }
            }
            else { //verbose
                while ((response = in.readLine()) != null)
                {
                    System.out.println( response );
                }
            }




        }
        catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String[] parseHeader(String headerData) {
        return headerData.trim().split(",");
    }

    public void showHelpData(RequestModel requestModel) {
        if (requestModel.isHelpArg){
            if (requestModel.helpArg.equalsIgnoreCase(Constant.GET)){
                System.out.println(Constant.HELP_GET);
            }
            else {
                System.out.println(Constant.HELP_POST);
            }
        }
        else {
            System.out.println(Constant.HELP_GENERAL);
        }
    }
}
