import java.util.Objects;

public class Card implements Comparable<Card> { //Extend class by using the input stream
    private long id;
    private String name;
    private Rank rank;
    private long price;

    public long getId() { //Create getters and setters for fields
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public Card(long id, String name, Rank rank) {
        this.id = id;
        this.name = name;
        this.rank = rank;
        this.price = 0L;
    }

    @java.lang.Override
    public java.lang.String toString() { //Override the toString method from java.lang package
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", rank=" + rank +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object object) { //Override equals method
        if (this == object) { //If objects are the same
            return true;
        }
        if (!(object instanceof Card)) { //If it is an object but not a card it can not be equal
            return false;
        }
        Card card = (Card) object; //Else cast the object
        return getId() == card.getId() && java.util.Objects.equals(getName(), card.getName()) && java.util.Objects.equals(getRank(), card.getRank());
    }

    public int hashCode() {
        super.hashCode(); //We can make our own hash code based on the id, name and rank
        return Objects.hash(new Object[]{this.getId(), this.getName(), this.getRank()});
    }

    @Override
    public int compareTo(Card comparisonCard) {
        if (this.equals(comparisonCard)) { //They are equal
            return 0; //Return 0 if equal
        } else if (this.getRank().equals(comparisonCard.getRank())) { //If not compare the name
            if (this.getName().equals(comparisonCard.getName())) { //If not compare by Id number
                return this.getId() > comparisonCard.getId() ? 1 : -1;
            }
            return this.getName().compareTo(comparisonCard.getName());
        } else if (this.getRank().equals("UNIQUE")) { //Compare by each rank as they are not equal
            return 1;
        } else if (this.getRank().equals("RARE")) {
            return comparisonCard.getRank().equals("UNIQUE") ? -1 : 1;
        } else if (this.getRank().equals("UNCOMMON")) {
            if (comparisonCard.getRank().equals("UNIQUE")) {
                return -1;
            }
            return comparisonCard.getRank().equals("RARE") ? -1 : 1;
        }
        return -1;
    }
}