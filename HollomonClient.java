import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class HollomonClient {
    private String server; //Create constructor fields for objects
    private int port;
    Socket Socket = new Socket(); //Constructs the socket object
    public String username; //We can provide acessors for login details to be used anywhere
    public String password;

    public HollomonClient(String server, int port) throws IOException, UnknownHostException {
        this.server = server;
        this.port = port;
        Socket socket = new Socket(server, port);
    }

    public List<Card> login(String username, String password) throws IOException, UnknownHostException { //If server not found
        this.username = username;
        this.password = password;
        try {
            Socket socket = new Socket(server, port);
            PrintWriter output = new PrintWriter(socket.getOutputStream(), true);//Flush the buffers
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
            output.println(username); //Send username to server
            output.println(password); //Send password to server
            String value = input.readLine(); //Read line and save in string variable
            List<Card> lst;
            lst = new ArrayList<Card>(); //Create list from util collections package library
            if (value.contains("successfully")) { //If a logon was sucessful
                InputStream CardInputStreams = new DataInputStream(new BufferedInputStream(socket.getInputStream())); //Create a data input stream
                boolean statusRegister = true; //HasNext()
                while (statusRegister) {
                    CardInputStream cardItem = new CardInputStream(CardInputStreams); //Create an input stream
                    lst.add(cardItem.readCard()); //Create a new card object and add it to a list
                    lst = CardInputStream.tempLst; //Add it to the final list
                    if (cardItem.readResponse().equals("OK")) { //The last item
                        statusRegister = false; //hasInput()
                    } else {
                        lst.add(cardItem.readCard()); //Othwerwise dd item to output list
                    }
                }
            }
            return value.contains("successfully") ? lst : null; //If a connection was sucessful
        } catch (UnknownHostException e) {
            return null;
        }
    }

    public void close() { //Overide close method
        try {
            this.Socket.close();
        } catch (IOException e) {
        }
    }

    public long getCredits() throws IOException, UnknownHostException {
        Socket socket = new Socket("netsrv.cim.rhul.ac.uk", 1812); //Logon script
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        InputStream CardInputStreams = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output.println(getUsername());
        output.println(getPasword());
        String value = input.readLine(); //Reconnect
        value = input.readLine();
        output.println("CREDITS");
        while (!value.equals("OK")) { //Ignore everything server sends back
            value = input.readLine();
        }
        return Long.parseLong(input.readLine()); //Take the last line sent from server
    }

    public List<Card> getCards() throws IOException, UnknownHostException {
        Socket socket = new Socket("netsrv.cim.rhul.ac.uk", 1812); //Open connection
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        InputStream CardInputStreams = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output.println(getUsername()); //Relogin
        output.println(getPasword());
        String value = input.readLine();
        input.readLine();
        output.println("CARDS"); //Send CARDS to server
        List<Card> lst;
        lst = new ArrayList<Card>(); //Create a blank arrayList from collections library
        long id = 0L; //Fields that need to be stored again using the new socket
        String name = null;
        Rank rank = null;
        long price = 0L;
        while (!value.equals("OK")) { //End of transmission
            if (value != null) {
                value = input.readLine(); //Give object attributes
            }
            if (value != null) {
                id = Long.parseLong(value);
            }
            value = input.readLine();
            if (value != null) {
                name = value;
            }
            value = input.readLine();
            if (value != null) {
                rank = Rank.valueOf(value);
            }
            value = input.readLine();
            if (value != null) {
                price = Long.parseLong(value);
            }
            value = input.readLine();
            lst.add(new Card(id, name, rank));
        }
        return lst; //Output the final list made
    }

    public List<Card> getOffers() throws IOException, UnknownHostException {
        Socket socket = new Socket("netsrv.cim.rhul.ac.uk", 1812); //Create a new socket for this scope
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true); //Flush the buffers automatically
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        InputStream CardInputStreams = new DataInputStream(new BufferedInputStream(socket.getInputStream())); //Create the stream
        output.println(getUsername()); //Login
        output.println(getPasword());
        String value = input.readLine();
        value = input.readLine();
        List<Card> lst; //To store offers avalible
        lst = new ArrayList<Card>(); //Create a blank arrayList from the collections
        while (!value.equals("OK")) { //Skip to end of transmission
            value = input.readLine();
        }
        output.println("OFFERS"); //Send out OFFERS command to sever
        long id = 0L; //Create fields to store data to be added
        String name = null;
        Rank rank = null;
        long price = 0L;
        value = input.readLine(); //Read reply
        while (!value.equals("OK")) { //Assin the data to objects
            if (value != null) { //Carry on until the end of transmission
                value = input.readLine();
            }
            if (value != null) {
                id = Long.parseLong(value); //Cast the values to long type
            }
            value = input.readLine();
            if (value != null) {
                name = value;
            }
            value = input.readLine();
            if (value != null) {
                rank = Rank.valueOf(value);
            }
            value = input.readLine();
            if (value != null) {
                price = Long.parseLong(value);
            }
            value = input.readLine();
            lst.add(new Card(id, name, rank)); //Create new card object and directly add it to list
        }
        return lst;
    }

    public boolean buyCard(Card card) throws IOException, UnknownHostException { //Return it is possible to by card
        Socket socket = new Socket("netsrv.cim.rhul.ac.uk", 1812); //Create socket
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        InputStream CardInputStreams = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output.println(getUsername()); //Create stream and login
        output.println(getPasword());
        String value = input.readLine();


        for (String i = input.readLine(); !value.equals("OK"); value = input.readLine()) {
        } //Quickly skip to end of transmission
        output.println("BUY " + card.getId()); //Tell the server details of card to buy
        return !input.readLine().equals("ERROR"); //Return boolean
    }

    public boolean sellCard(Card card, long price) throws IOException, UnknownHostException {
        Socket socket = new Socket("netsrv.cim.rhul.ac.uk", 1812); //Create new socket
        PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        InputStream CardInputStreams = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        output.println(getUsername()); //Login and make data input stream
        output.println(getPasword());
        String value = input.readLine();
        for (String i = input.readLine(); !value.equals("OK"); value = input.readLine()) {
        } //Jump to end of the list
        output.println("SELL " + card.getId() + " " + card.getPrice());
        return !input.readLine().equals("ERROR"); //Just return the boolean directly
    }

    public int getPort() { //Acessors and modifiers
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getServer() {
        return this.server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPasword() {
        return this.password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}