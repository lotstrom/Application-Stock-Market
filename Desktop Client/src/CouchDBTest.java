import java.io.IOException;
import java.util.ArrayList;
import org.lightcouch.CouchDbClient;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class CouchDBTest {
	/**
	 * Authors: Daniel Hosseini, Lídia Nyman, Odzaya Batsaikhan.
	 */

	public static ArrayList<JsonObject> infolist = new ArrayList<JsonObject>();
	public static String Time;
	public static String Open;
	public static String High;
	public static String Low;
	public static String Close;
	public static String Volume;
	private String jsonline;

	public static String[] line;

	public void ConnectDB(String DBName) throws IOException {

		/* Creating a session with couch db running in 5984 port */

		/**
		 * Creating a session with our online db running in port 5984
		 */

		CouchDbClient dbClient2 = new CouchDbClient("stockin", true, "http",
				"stockin.cloudant.com", 5984, "stockin", "jupiter05");

		/**
		 * Takes the search input (DBName) and tries to find a document that
		 * matches that name. Returns the found data and sets it to String
		 * jsonline
		 */

		jsonline = dbClient2.find(JsonObject.class, DBName.toLowerCase())
				.toString();
		/**
		 * The print is for testing and prints out the whole object found in the
		 * database. The method parse will convert our data to the right
		 * structure
		 */
	//	System.out.println(jsonline);
		parse(jsonline);
	}

	/**
	 * It goes into the nested arrays "Stock -> Data" and then puts all the data
	 * to the infolist arraylist.
	 */
	public void parse(String jsonLine) {
		JsonElement element = new JsonParser().parse(jsonLine);
		JsonObject object = element.getAsJsonObject();
		object = object.getAsJsonObject("Stock");
		JsonArray jarray = object.getAsJsonArray("Data");

		// System.out.println("Jarray: " + jarray);
		// System.out.println("Object:" + object);

		for (int i = 0; i <= jarray.size() - 1; i++) {
			object = jarray.get(i).getAsJsonObject();
		//	System.out.println(jarray.get(i));

			infolist.add(object);

		}

	}

}
