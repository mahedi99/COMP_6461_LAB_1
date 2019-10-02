import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.Scanner;

public class GetPost {

    static Scanner sc = new Scanner(System.in);

    public static void main(String a[]) {

        String[] input = sc.nextLine().split(" ");
        GetPost getPost = new GetPost();
        if (input[1].equalsIgnoreCase("get")) {
            getPost.httpGET(input);
        }
        else if (input[1].equalsIgnoreCase("post")){
            getPost.httpPOST(input[2], input[2]);//method, message
        }
    }

    private void httpGET(String [] input) {
        String request = "";
        String command = "";
        if (input.length == 3){
            request = input[2].trim();
        }
        else if (input.length == 4){
            command = input[2].trim();
            request = input[3].trim();
        }

        if (!request.equals("") && command.equals("-h")){

        }
        else if (!request.equals("")){

            String hostName = request.substring(0,request.lastIndexOf("/"));
            hostName = hostName.substring(hostName.lastIndexOf("/") + 1);
            String message = request.substring(request.lastIndexOf("/"));

//            try {
//                InetAddress address = InetAddress.getByName(hostName);
//                Socket socket = new Socket(address, 80);
//
//                PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
//                pw.println("GET "+ message +" HTTP/1.1");
//                pw.println("Host: " + hostName);
////                pw.write("Content-Type: application/json\r\n");
//                pw.println("Connection: Close");
//                pw.println();
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                String response;
//
//                if (command.equals("")){
//                    boolean print = false;
//                    while ((response = in.readLine()) != null)
//                    {
//                        if (print){
//                            System.out.println( response );
//                        }
//                        if (!print && response.equals("")){
//                            print = true;
//                        }
//                    }
//                }
//                else if (!command.equals("")){
//                    while ((response = in.readLine()) != null)
//                    {
//                        System.out.println( response );
//                    }
//                }
//
//                in.close();
//                pw.close();
//                socket.close();
//            } catch (UnknownHostException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
//        else if (!request.equals("") && !command.equals("")){
    }

    private void httpPOST(String hostname, String message){
        try {
            //String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");

            InetAddress address = InetAddress.getByName(hostname);
            Socket socket = new Socket(address, 8080);

            //String path = "/servlet";
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            pw.println("POST " + "/post" + " HTTP/1.1");
            pw.println("Host: " + hostname);
            pw.println("Content-Type: application/json");
            pw.println("{\"Assignment\": 1}");
            pw.println("Connection: Close");
            pw.flush();

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String response;
            while ((response = in.readLine()) != null)
            {
                System.out.println( response );
            }
            in.close();
            socket.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
