import org.json.JSONObject;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * @author Mahedi Hassan
 * 2019-10-01
 */

public class PrepareRequest {

    private int redirectCounter = 0;

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

            String redirectURl  = extractOutput(in, requestModel);
            if (!redirectURl.equals("") && redirectCounter < 5){
                redirectCounter++;
                requestModel.url = redirectURl;
                makeGETRequest(requestModel);
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
        BufferedReader read;
        try {

            String hostName = requestModel.url.substring(requestModel.url.indexOf("/") + 2);
            hostName = hostName.substring(0, hostName.indexOf("/"));
            String path = requestModel.url.substring(requestModel.url.lastIndexOf("/"));

            Socket httpSocket = new Socket(hostName,80);

            BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(httpSocket.getOutputStream(), "UTF-8"));
            wr.write("POST " + path + " HTTP/1.0" + "\r\n");




            String fileData = "";
            int fileLength = 0;
            if (requestModel.isFile){
                FileReader fileReader = new FileReader(requestModel.fileDir + "/" + requestModel.fileName);
                read =new BufferedReader(fileReader);
                String line;
                while((line = read.readLine())!= null){
                    fileData += line + "\r\n";
                    fileLength += line.length();
                }
            }


            if(requestModel.isInlineData){
                wr.write("Content-Length: " +requestModel.inlineData.length() + "\r\n");
            }
            else if (requestModel.isFile){

                wr.write("Content-Length: " + fileLength + "\r\n");
            }
            if (requestModel.isHeader){
                String [] headers = parseHeader(requestModel.headerData);
                for (String tmpHeader : headers){
                    String [] tmp = tmpHeader.split(":");
                    wr.write(tmp[0] + ": " + tmp[1] + "\r\n");
                }
            }
            wr.write("\r\n");
            if (requestModel.isInlineData){
                wr.write(requestModel.inlineData + "\r\n");
            }
            else {
                wr.write(fileData);
            }
            wr.flush();



            BufferedReader in = new BufferedReader(new InputStreamReader(httpSocket.getInputStream()));

            String redirectURl  = extractOutput(in, requestModel);
            if (!redirectURl.equals("") && redirectCounter < 5){
                redirectCounter++;
                requestModel.url = redirectURl;
                makePOSTRequest(requestModel);
            }
            in.close();
            wr.close();
        }
        catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    private String extractOutput(BufferedReader in, RequestModel model) throws IOException {
        String response;
        String responseStatus = "";
        String header = "";
        String body = "";
        boolean firstLine = true;
        boolean isHeader = true;

        while ((response = in.readLine()) != null){
            if (isHeader){
                if (firstLine) {
                    responseStatus = response;
                    firstLine = false;
                }
                if (response.equals("")){
                    isHeader = false;
                    continue;
                }
                header += response + "\r\n";
            }
            else {

                body += response;
            }
        }
        if (model.verbose){
            System.out.println(header + "\n" + body);
        }
        else {
            System.out.println(body);
        }

        String redirectURL = "";
        if (responseStatus.contains("302")){
            redirectURL = extractURL(body);
        }
        return redirectURL;
    }

    private String extractURL(String jsonBody) {
        JSONObject obj = new JSONObject(jsonBody);
        return obj.getString("url");
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
