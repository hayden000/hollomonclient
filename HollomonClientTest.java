import java.net.*;
import java.io.IOException;
import java.util.List;

public class HollomonClientTest {
    public void HollomonClientTest() {
        try { //If the login fails
            HollomonClient test = new HollomonClient("netsrv.cim.rhul.ac.uk", 1812);
            List testList = test.login("certainly", "chanceexperiencepeace"); //Args values
            test.close(); //Call the close method to close the socket
        } catch (IOException e) {
        }
    }

    public void main(String[] args) {
        HollomonClientTest(); //Call the method
    }
}