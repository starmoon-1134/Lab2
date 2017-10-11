package BookQuery;

import java.util.List;

public class AuthorInfo {
	private List<BookInfo> bookInfos;
	private String AuthorID;
	private String AuthorCountry;
	private String AuthorAge;
	private String AuthorName;

	public List<BookInfo> getBookInfos() {
		return bookInfos;
	}

	public void setBookInfos(List<BookInfo> bookInfos) {
		this.bookInfos = bookInfos;
	}

	public String getAuthorID() {
		return AuthorID;
	}

	public void setAuthorID(String authorID) {
		AuthorID = authorID;
	}

	public String getAuthorCountry() {
		return AuthorCountry;
	}

	public void setAuthorCountry(String authorCountry) {
		AuthorCountry = authorCountry;
	}

	public String getAuthorAge() {
		return AuthorAge;
	}

	public void setAuthorAge(String authorAge) {
		AuthorAge = authorAge;
	}

	public String getAuthorName() {
		return AuthorName;
	}

	public void setAuthorName(String authorName) {
		AuthorName = authorName;
	}
}