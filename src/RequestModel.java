/**
 * @author Mahedi Hassan
 * 2019-09-30
 */

public class RequestModel {

    public String requestType;
    public boolean isHelpArg;
    public String helpArg;
    public boolean verbose;
    public boolean isHeader;
    public String headerData = "";
    public boolean isInlineData;
    public String inlineData; //-d
    public boolean isFile;
    public String readFileName;
    public String readFileDir;
    public String url;
    public boolean isWriteToFile;
    public String writeFileName;
}
