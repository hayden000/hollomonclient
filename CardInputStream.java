import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CardInputStream extends HollomonClient {
    private long id; //Constructor fields
    private String name;
    private Rank rank;
    private long price;
    public String response;
    public static List<Card> tempLst;

    public CardInputStream(InputStream Input) throws IOException { //If the logon fails
        super("netsrv.cim.rhul.ac.uk", 1812); //Use class in HollomonClient
        tempLst = new ArrayList<Card>(); //Create new blank list
        BufferedReader item = new BufferedReader(new InputStreamReader(Input)); //Read the entire line with buffer
        String value = item.readLine(); //Read first line of input stream
        while (value.contains("CARD")) { //If a card has been sent from server
            if (value != null) {
                value = item.readLine();
            }
            if (value != null) {
                this.id = Long.parseLong(value); //Cast string to long
            }
            value = item.readLine();
            if (value != null) {
                this.name = value;
            }
            value = item.readLine();
            if (value != null) {
                this.rank = Rank.valueOf(value); //Cast to rank enum from string
            }
            value = item.readLine();
            if (value != null) {
                this.price = Long.parseLong(value); //Do not sort by price
            }
            value = item.readLine(); //Skip line
            tempLst.add(new Card(this.id, this.name, this.rank));
        }
        if (value.equals("OK")) {
            this.response = "OK"; //End of transmission
        }
    }

    @Override
    public void close() { //Close the socket
        this.close();
    }

    public List<Card> printList() { //Return entire list
        return tempLst;
    }

    public Card readCard() { //Create card object for one card
        Card card = new Card(this.id, this.name, this.rank);
        return card;
    }

    public String readResponse() { //Return null if the transmission has reached the end
        return this.response == null ? "OK" : this.response;
    }

    public long getId() { //Setters and getters for fields
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return this.rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public long getPrice() {
        return this.price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
}