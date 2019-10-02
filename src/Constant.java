/**
 * @author Mahedi Hassan
 * 2019-10-01
 */

public class Constant {

    public static final String HELP = "help";
    public static final String GET = "get";
    public static final String POST = "post";
    public static final String HELP_GENERAL = "httpc help \nhttpc is a curl-like application but supports HTTP protocol only. \nUsage: \n\thttpc command [arguments] \nThe commands are: \n\tget\t executes a HTTP GET request and prints the response. \n\tpost\t executes a HTTP POST request and prints the response. \n\thelp\t prints this screen. \nUse \"httpc help [command]\" for more information about a command.";
    public static final String HELP_GET = "httpc help get\nusage: httpc get [-v] [-h key:value] URL\nGet executes a HTTP GET request for a given URL.\n -v \t\t\t Prints the detail of the response such as protocol, status, and headers.\n -h key:value\t Associates headers to HTTP Request with the format \'key:value\'.\n";
    public static final String HELP_POST = "httpc help post\nusage: httpc post [-v] [-h key:value] [-d inline-data] [-f file] URL\nPost executes a HTTP POST request for a given URL with inline data or from file.\n\t-v\t\t\t Prints the detail of the response such as protocol, status, and headers.\n\t-h key:value Associates headers to HTTP Request with the format\'key:value\'.\n\t-d string\t Associates an inline data to the body HTTP POST request.\n\t -f file\t Associates the content of a file to the body HTTP POST request.\nEither [-d] or [-f] can be used but not both.";
}
