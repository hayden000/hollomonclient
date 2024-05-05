import java.io.IOException;
import java.net.UnknownHostException;

public class CardTest {
    Card card;
    Card card2;
    String string;
    String s;

    public void cardTest() throws AssertionError { //Create some objects
        card = new Card(10, "card", Rank.UNIQUE);
        card2 = new Card(9, "newCard", Rank.COMMON);
        assert card.compareTo(card2) == (1); //Test method by thowing an assetion error
        string = new String("card");
        s = string.toString();
    }

    public void main(String[] args) throws IOException, UnknownHostException {
        cardTest(); //Call the method
    }
}