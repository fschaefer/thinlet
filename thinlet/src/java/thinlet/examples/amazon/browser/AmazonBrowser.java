package thinlet.examples.amazon.browser;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

import thinlet.*;

/**
 *
 */
public class AmazonBrowser extends Thinlet {

	private final static String[] MODES = { "baby", "books", "classical", "dvd",
		"electronics", "garden", "kitchen", "magazines", "music", "pc-hardware",
		"photo", "software", "toys", "universal", "vhs", "videogames" };

	private final static String[] SEARCHBY = { "Keyword", "Asin", "Upc",
		"Author", "Artist", "Actor", "Director", "Manufacturer" };

	int level = -1;
	String name;
	boolean listmode;
	Vector details = new Vector();

	/**
	 *
	 */
	public class Details {
		String asin;
		String productName;
		String catalog;
		String authors;
		String artists;
		String releaseDate;
		String manufacturer;
		String imageUrl;
		String listPrice;
		String ourPrice;
		String usedPrice;

		Image image;

		public Image getIcon() {
			if ((image == null) && (imageUrl != null)) {
				try {
					image = getToolkit().getImage(new URL(imageUrl));
				} catch (Exception exc) { showException(exc); }
				imageUrl = null;
			}
			return image;
		}
	}

	/**
	 *
	 */
	public AmazonBrowser() {
		try {
			add(parse("amazonbrowser.xml"));
			productSelected();
		} catch (Exception exc) { showException(exc); }
	}

	/**
	 *
	 */
	public void productSelected() {
		int productid = getInteger(find("product"), "selected");
		boolean music = (productid == 8) || (productid == 2);
		boolean books = (productid == 1);
		boolean films = (productid == 3) || (productid == 14); //video?
		boolean homes = (productid == 4) || (productid == 6) || (productid == 15) ||
			(productid == 11) || (productid == 10) || (productid == 9);

		Object searchType = find("searchType");
		setInteger(searchType, "selected", 0);
		setString(find("searchText"), "text", "");
		
		setBoolean(getItem(searchType, 2), "enabled", music);
		setBoolean(getItem(searchType, 3), "enabled", books);
		setBoolean(getItem(searchType, 4), "enabled", music);
		setBoolean(getItem(searchType, 5), "enabled", films);
		setBoolean(getItem(searchType, 6), "enabled", films);
		setBoolean(getItem(searchType, 7), "enabled", homes);
	}

	/**
	 *
	 */
	public void search() {
		String mode = MODES[getInteger(find("product"), "selected")];
		String searchby = SEARCHBY[getInteger(find("searchType"), "selected")];
		String text = getString(find("searchText"), "text");
		StringBuffer converted = new StringBuffer(text.length());
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (((c >= 'a') && (c <= 'z')) || ((c >= 'A') && (c <= 'Z')) ||
					((c >= '0') && (c <= '9'))) {
				converted.append(c);
			}
			else {
				converted.append('%');
				converted.append(Integer.toHexString((int) c));
			}
		}
		text = converted.toString();

		setBoolean(find("errorMessage"), "visible", false);
		removeAll(find("resultList"));
		details.removeAllElements();
		showProduct(null);

		try {
			parseXML(new URL("http://xml.amazon.com/onca/xml?v=1.0&" +
				"t=webservices-20&dev-t=D3MAIAYX2Q6JLY&" + searchby + "Search=" +
				text + "&mode=" + mode + "&type=lite&page=1&f=xml").openStream());
			//parseXML(getClass().getResourceAsStream("xml.xml"));
			//parseXML(getClass().getResourceAsStream("err.xml"));
		} catch (Exception exc) { showException(exc); }
	}

	/**
	 *
	 */
	public void resultSelected() {
		int selected = getSelectedIndex(find("resultList"));
		if (selected != -1) {
			Details detail = (Details) details.elementAt(selected);
			showProduct(detail);
		}
	}

	/**
	 *
	 */
	private void showProduct(Details detail) {
		Object detailsPanel = find("detailsPanel");
		setIcon(find(detailsPanel, "image"), "icon", (detail != null) ? detail.getIcon() : null);
		setString(find(detailsPanel, "authors"), "text", (detail != null) ? detail.authors : "");
		boolean hasartists = (detail != null) && (detail.artists != null);
		setBoolean(find(detailsPanel, "artistsLabel"), "visible", hasartists);
		setBoolean(find(detailsPanel, "artists"), "visible", hasartists);
		if (hasartists) {
			setString(find(detailsPanel, "artists"), "text", (detail != null) ? detail.artists : "");
		}
		setString(find(detailsPanel, "productName"), "text", (detail != null) ? detail.productName : "");
		setString(find(detailsPanel, "manufactured"), "text", (detail != null) ? detail.manufacturer : "");
		setString(find(detailsPanel, "released"), "text", (detail != null) ? detail.releaseDate : "");
		setString(find(detailsPanel, "listPrice"), "text", (detail != null) ? detail.listPrice : "");
		setString(find(detailsPanel, "ourPrice"), "text", (detail != null) ? detail.ourPrice : "");
		setString(find(detailsPanel, "usedPrice"), "text", (detail != null) ? detail.usedPrice : "");
	}

	/**
	 *
	 */
	protected void startElement(String name, Hashtable attributelist) {
		level++;
		this.name = name;

		if (level == 1) {
			if ("Details".equals(name)) {
				details.addElement(new Details());
				listmode = true;
			}
			else if ("ErrorMsg".equals(name)) {
				listmode = false;
			}
		}
	}

	/**
	 *
	 */
	protected void characters(String text) {
		if ((level == 1) && !listmode) {
			Object errorMessage = find("errorMessage");
			setBoolean(errorMessage, "visible", true);
			setString(errorMessage, "text", text);
		}
		else if (level == 2) {
			if ("Asin".equals(name)) {
				getLastDetails().asin = text;
			}
			else if ("ProductName".equals(name)) {
				getLastDetails().productName = text;
			}
			else if ("Catalog".equals(name)) {
				getLastDetails().catalog = text;
			}
			else if ("ReleaseDate".equals(name)) {
				getLastDetails().releaseDate = text;
			}
			else if ("Manufacturer".equals(name)) {
				getLastDetails().manufacturer = text;
			}
			else if ("ImageUrlMedium".equals(name)) {
				getLastDetails().imageUrl = text;
			}
			else if ("ListPrice".equals(name)) {
				getLastDetails().listPrice = text;
			}
			else if ("OurPrice".equals(name)) {
				getLastDetails().ourPrice = text;
			}
			else if ("UsedPrice".equals(name)) {
				getLastDetails().usedPrice = text;
			}
		}
		else if (level == 3) {
			if ("Author".equals(name)) {
				Details last = getLastDetails();
				last.authors = (last.authors == null) ? text :
					(last.authors + ", " + text);
			}
			else if ("Artist".equals(name)) {
				Details last = getLastDetails();
				last.artists = (last.artists == null) ? text :
					(last.artists + ", " + text);
			}
		}
	}

	/**
	 *
	 */
	protected void endElement() {
		if (listmode && (level == 1)) {
			Details last = getLastDetails();
			Object row = create("row");
			Object productcell = create("cell");
			setString(productcell, "text", last.productName);
			add(row, productcell);
			Object catalogcell = create("cell");
			setString(catalogcell, "text", last.catalog);
			add(row, catalogcell);
			Object pricecell = create("cell");
			setString(pricecell, "text", last.ourPrice);
			add(row, pricecell);
			add(find("resultList"), row);
		}
		level--;
	}

	/**
	 *
	 */
	private Details getLastDetails() {
		return (Details) details.elementAt(details.size() - 1);
	}

	/**
	 *
	 */
	public static void main(String[] args) throws Exception {
		new FrameLauncher("Amazon Browser", new AmazonBrowser(), 320, 320);
	}
	
	/**
	 *
	 */
	private void showException(Throwable exc) {
		StringWriter writer = new StringWriter();
		exc.printStackTrace(new PrintWriter(writer));
		String trace = writer.toString().replace('\r', ' ').replace('\t', ' ');
		
		Object dialog = create("dialog");
		setInteger(dialog, "columns", 1); setInteger(dialog, "gap", 4);
		setInteger(dialog, "top", 4); setInteger(dialog, "left", 4); setInteger(dialog, "bottom", 4); setInteger(dialog, "right", 4);
		setString(dialog, "text", exc.getMessage());
		setBoolean(dialog, "modal", true);
		Object textarea = create("textarea");
		setString(textarea, "text", trace);
		setInteger(textarea, "width", 240); setInteger(textarea, "height", 180);
		add(dialog, textarea);
		Object button = create("button");
		setString(button, "text", "OK");
		setChoice(button, "halign", "center");
		add(dialog, button);
		try {
			setMethod(button, "action", "closeException", dialog, this);
		} catch (Exception e) {}
		add(dialog);		
	}
	
	/**
	 *
	 */
	public void closeException() {
		remove(getItem(getDesktop(), 0));
	}
}
/*ProductInfo url
	Details*
		Asin?
		ProductName
		Catalog
		Authors?
			Author+
		Artists?
			Artist+
		ReleaseDate?
		Manufacturer?
		ImageUrlSmall
		ImageUrlMedium
		ImageUrlLarge
		ListPrice?
		OurPrice?
		UsedPrice?
	ShoppingCart?
		CartId?
		Items?
			Item+
				ItemId
				ProductName?
				Description?
				Asin
				Quantity?
				ListPrice?
				OurPrice?
	ErrorMsg?

ProductInfo
	Details* url
		Asin
		ProductName
		Catalog
		KeyPhrases?
			KeyPhrase+
		Artists?
			Artist+
		Authors?
			Author+
		Mpn?
		Starring?
			Actor+
		Directors?
			Director+
		TheatricalReleaseDate?
		ReleaseDate?
		Manufacturer?
		Distributor?
		ImageUrlSmall?
		ImageUrlMedium?
		ImageUrlLarge?
		ListPrice?
		OurPrice?
		UsedPrice?
		RefurbishedPrice?
		CollectiblePrice?
		ThirdPartyNewPrice?
		SalesRank?
		BrowseList?
			BrowseNode+
				BrowseId?
				BrowseName?
		Media?
		ReadingLevel?
		Publisher?
		NumMedia?
		Isbn?
		Features?
			Feature+
		Platform?
		MpaaRating?
		EsrbRating?
		AgeGroup?
		Availability?
		Upc?
		Tracks?
			Track*
			ByArtist*
		Accessories?
			Accessory+
		Platforms?
			Platform*
		Encoding?
		Reviews?
			AvgCustomerRating?
			CustomerReview*
				Rating
				Summary
				Comment
		SimilarProducts?
			Product*
		Lists?
			ListId*
	ShoppingCart?
		...
	ErrorMsg?
		...*/
