package BookQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

public class Book {
	private String ISBN;
	private String Title;
	private String AuthorID;
	private String Publisher;
	private String PublishDate;
	private String AuthorCountry;
	private String AuthorAge;
	private String AuthorName;
	private String resultString;
	private float Price;

	public String addBook() throws SQLException {
		SqlCon mysCon = new SqlCon();
		boolean isAuthorIDExist = false;
		boolean isRequirAddAuthor = true;

		// �ж����������ߵ�ID�Ƿ��Ǻ�
		String queryAuthor = "select * from Author where AuthorID='" + getAuthorID() + "';";
		System.out.println(queryAuthor);
		ResultSet queryAuthorResult = mysCon.executeQuery(queryAuthor);
		if (queryAuthorResult.next()) {
			isAuthorIDExist = true;
			if (queryAuthorResult.getString("Name").equals(getAuthorName())) {
				isRequirAddAuthor = false;
			}
		}

		// �����Ҫ���������
		if (isRequirAddAuthor) {
			if (isAuthorIDExist) {
				resultString = "��ǰID������Ϊ \"" + queryAuthorResult.getString("Name") + "\" ,�����\n" + "���߻�һ��ID������������������Ϣ";
				return "success";
			}
			// ��������ʱ��Ҫ��֤�����������Ϣ����
			if (getAuthorAge().equals("") || getAuthorCountry().equals("")) {
				resultString = "ID������\n������������������Ϣ�����½�һ������";
				return "success";
			}
			String addNewAuthor = "insert into Author values('" + getAuthorID() + "','" + getAuthorName() + "','"
					+ getAuthorAge() + "','" + getAuthorCountry() + "');";
			System.out.println(addNewAuthor);

			if (mysCon.executeUpdate(addNewAuthor) == -1) {
				resultString = "�������ʧ�ܣ��Ժ�����";
				return "success";
			}
		}

		// �����������ݿ�
		mysCon.closeCon();
		mysCon = null;
		mysCon = new SqlCon();

		String queryISBN = "select * from Book where ISBN='" + getISBN() + "'";
		ResultSet ISBNResultSet = mysCon.executeQuery(queryISBN);

		if (!ISBNResultSet.next()) {// �ж�ISBN�Ƿ��ظ�
			String insert = "insert into Book values('" + getISBN() + "','" + getTitle() + "','" + getAuthorID() + "','"
					+ getPublisher() + "','" + getPublishDate() + "','" + getPrice() + "');";
			System.out.println(insert);
			mysCon.executeUpdate(insert);
			mysCon.closeCon();
			resultString = "��ӳɹ�";
			return "success";
		} else {
			// String alter = "update Book set AuthorID='" + getAuthorID() + "', Title='" +
			// getTitle() + "', Publisher='"
			// + getPublisher() + "', PublishDate='" + getPublishDate() + "', Price='" +
			// getPrice()
			// + "' where ISBN='" + getISBN() + "';";
			// System.out.println(alter);
			// mysCon.executeUpdate(alter);
			// mysCon.closeCon();
			// resultString = "ISBN�Ѿ����ڣ��ɹ�����ͼ����Ϣ!";
			SqlCon sqlCon_Au = new SqlCon();
			String queryBOOOK = "select * from Book where ISBN='" + getISBN() + "'";
			ResultSet bookinfotmp = sqlCon_Au.executeQuery(queryBOOOK);
			bookinfotmp.next();
			String cur_AuthorID = bookinfotmp.getString("AuthorID");
			String cur_title = bookinfotmp.getString("Title");
			String queryAuthorr = "select * from Author where AuthorID='" + cur_AuthorID + "'";
			ResultSet authorinfotmp = sqlCon_Au.executeQuery(queryAuthorr);
			authorinfotmp.next();
			String cur_AuthorName = authorinfotmp.getString("Name");

			sqlCon_Au.closeCon();
			mysCon.closeCon();
			resultString = "ISBN�Ѿ�����,��Ӧ������Ϊ��" + cur_title + "   ���ߣ�" + cur_AuthorName + "\n���飡";
			return "success";
		}
	}

	public String queryBook() throws SQLException {
		SqlCon sqlCon_Au = new SqlCon();
		SqlCon sqlCon_Bo = new SqlCon();
		String queryAuthorName = "select * from Author where Name='" + getAuthorName() + "'";
		String queryBook = null;
		System.out.println(queryAuthorName);
		ResultSet AuthorRet = sqlCon_Au.executeQuery(queryAuthorName);
		ResultSet BookRet = null;
		List<AuthorInfo> authorInfos = new ArrayList<AuthorInfo>();
		List<BookInfo> bookItems = null;
		AuthorInfo tmpAuthorInfo = null;
		BookInfo tmpBookInfo = null;
		int flag = 0;

		// �жϲ�ѯ����Ƿ�Ϊ��
		AuthorRet.last();
		int queryRows = AuthorRet.getRow();
		AuthorRet.beforeFirst();
		if (queryRows <= 0) {
			sqlCon_Au.closeCon();
			sqlCon_Bo.closeCon();
			resultString = "û�и�������Ϣ";
			return "error";
		}
		try {
			// ��������ͬ������
			while (AuthorRet.next()) {
				tmpAuthorInfo = new AuthorInfo();
				tmpAuthorInfo.setAuthorAge(AuthorRet.getString("Age"));
				tmpAuthorInfo.setAuthorCountry(AuthorRet.getString("Country"));
				tmpAuthorInfo.setAuthorID(AuthorRet.getString("AuthorID"));
				tmpAuthorInfo.setAuthorName(AuthorRet.getString("Name"));
				System.out.println("1");
				// ����һ�����ߵ�������
				bookItems = new ArrayList<BookInfo>();
				queryBook = "select * from Book where AuthorID='" + tmpAuthorInfo.getAuthorID() + "'";
				System.out.println(queryBook);
				BookRet = sqlCon_Bo.executeQuery(queryBook);

				while (BookRet.next()) {
					System.out.println("2");
					flag = 1;
					tmpBookInfo = new BookInfo();
					tmpBookInfo.setISBN(BookRet.getString("ISBN"));
					tmpBookInfo.setPrice(BookRet.getString("Price"));
					tmpBookInfo.setPublishDate(BookRet.getString("PublishDate"));
					tmpBookInfo.setPublisher(BookRet.getString("Publisher"));
					tmpBookInfo.setTitle(BookRet.getString("Title"));
					bookItems.add(tmpBookInfo);
				}
				tmpAuthorInfo.setBookInfos(bookItems);
				authorInfos.add(tmpAuthorInfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			sqlCon_Bo.closeCon();
			sqlCon_Au.closeCon();
			resultString = "SQL wrong";
			return "error";
		}
		sqlCon_Au.closeCon();
		sqlCon_Bo.closeCon();
		// �����ݴ洢��map���ת����json�������ݣ�Ҳ�����Լ��ֶ�����json��������

		JSONArray json = JSONArray.fromObject(authorInfos);// �Զ����������Ҫsetter��getter
		setResultString(json.toString());// ��ResultString(�Զ����)��ֵ�����ݸ�ҳ��
		System.out.println(resultString);
		if (flag != 1) {
			resultString = "No Book";
			return "error";
		}
		return "success";
	}

	public String delBook() throws SQLException {
		// ɾ��ĳ������
		SqlCon sqlCon = new SqlCon();
		String deleteBookString = "delete from Book where ISBN='" + getISBN() + "'";
		int delResult = sqlCon.executeUpdate(deleteBookString);// ִ�гɹ�����0
		// System.out.println("delResult:" + delResult);
		sqlCon.closeCon();
		sqlCon = null;
		if (delResult != 0) {
			resultString = "SQL error";
			return "error";
		}
		// ����������������Ʒ����ʾɾ��
		sqlCon = new SqlCon();
		String queryAuthor_book = "select * from Book where AuthorID='" + getAuthorID() + "'";
		ResultSet AuthorRet = sqlCon.executeQuery(queryAuthor_book);
		AuthorRet.last();
		int queryRows = AuthorRet.getRow();
		sqlCon.closeCon();
		if (queryRows <= 0) {
			resultString = "ID:" + getAuthorID() + "\n����:" + getAuthorName() + "\nû��������Ʒ������ɾ��";
			return "success";
		}
		resultString = "delete success";
		return "success";
	}

	public String delAuthor() {
		// ɾ��ĳ������
		SqlCon sqlCon = new SqlCon();
		String deleteBookString = "delete from Author where AuthorID='" + getAuthorID() + "';";
		int delResult = sqlCon.executeUpdate(deleteBookString);// ִ�гɹ�����0
		// System.out.println("delResult:" + delResult);
		System.out.println(deleteBookString);
		sqlCon.closeCon();
		sqlCon = null;
		if (delResult != 0) {
			resultString = "ɾ������" + getAuthorName() + "ʧ��...";
			return "error";
		}
		resultString = "ɾ������" + getAuthorName() + "�����";
		return "success";
	}

	public String alterBook() throws SQLException {
		// System.out.println("ISBN:" + getISBN());
		// System.out.println("AuthorName:" + getAuthorName());
		// System.out.println("AuhtorID:" + getAuthorID());
		// System.out.println("AuthorAge:" + getAuthorAge());
		// System.out.println("AuthorCountry:" + getAuthorCountry());
		// System.out.println("Pulisher:" + getPublisher());
		// System.out.println("PulishDate:" + getPublishDate());
		// System.out.println("Price:" + getPrice());

		boolean isRequirAddAuthor = true;
		boolean isAuthorIDExist = false;
		SqlCon mysCon = new SqlCon();

		// ��¼ԭ���ߣ��޸�֮������������û����Ҳ����ɾ��
		String queryAuthor_book = "select * from Book where ISBN='" + getISBN() + "';";
		System.out.println(queryAuthor_book);
		ResultSet AuthorRet = mysCon.executeQuery(queryAuthor_book);
		AuthorRet.next();
		String oldAuthorID = AuthorRet.getString("AuthorID");

		// �ж����������ߵ�ID�Ƿ��Ǻ�
		String queryAuthor = "select * from Author where AuthorID='" + getAuthorID() + "';";
		System.out.println(queryAuthor);
		ResultSet queryAuthorResult = mysCon.executeQuery(queryAuthor);
		if (queryAuthorResult.next()) {
			isAuthorIDExist = true;
			if (queryAuthorResult.getString("Name").equals(getAuthorName())) {
				isRequirAddAuthor = false;
			}
		}

		// �����Ҫ���������
		if (isRequirAddAuthor) {
			if (isAuthorIDExist) {
				resultString = "��ǰID�Ѿ���ռ�ã�";
				return "success";
			}
			String addNewAuthor = "insert into Author values('" + getAuthorID() + "','" + getAuthorName() + "','"
					+ getAuthorAge() + "','" + getAuthorCountry() + "');";
			System.out.println(addNewAuthor);

			if (mysCon.executeUpdate(addNewAuthor) == -1) {
				resultString = "�������ʧ�ܣ��Ժ�����";
				return "success";
			}
			// ���ܻ���Ҫ�޸�������Ϣ��ֻ�����䡢���ң�
		} else {
			String alterAuthor = "update Author set Age='" + getAuthorAge() + "', Country='" + getAuthorCountry()
					+ "' where AuthorID='" + getAuthorID() + "';";
			System.out.println(alterAuthor);
			if (mysCon.executeUpdate(alterAuthor) == -1) {
				resultString = "�޸�����ʧ�ܣ��Ժ�����";
				return "success";
			}
		}

		// �����������ݿ�
		mysCon.closeCon();
		mysCon = null;
		mysCon = new SqlCon();

		// �޸�ͼ����Ϣ
		String alterBook = "update Book set AuthorID='" + getAuthorID() + "', Title='" + getTitle() + "', Publisher='"
				+ getPublisher() + "', PublishDate='" + getPublishDate() + "', Price='" + getPrice() + "' where ISBN='"
				+ getISBN() + "';";
		System.out.println(alterBook);
		if (mysCon.executeUpdate(alterBook) == -1) {
			resultString = "�޸�ͼ����Ϣʧ��";
			return "success";
		}
		resultString = "�޸ĳɹ�";

		// �����������ݿ�
		mysCon.closeCon();
		mysCon = null;
		mysCon = new SqlCon();

		queryAuthor_book = "select * from Book where AuthorID='" + oldAuthorID + "';";
		ResultSet oldAuthor = mysCon.executeQuery(queryAuthor_book);
		oldAuthor.last();
		int oldAuthorBookCnt = oldAuthor.getRow();
		if (oldAuthorBookCnt <= 0) {
			String delAuthor = "delete from Author where AuthorID='" + oldAuthorID + "';";
			mysCon.executeUpdate(delAuthor);
			resultString += ",�޸�֮ǰ������û��ͼ�飬��Ϣ��ɾ��";
		}
		mysCon.closeCon();
		return "success";
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getAuthorID() {
		return AuthorID;
	}

	public void setAuthorID(String authorID) {
		AuthorID = authorID;
	}

	public String getPublisher() {
		return Publisher;
	}

	public void setPublisher(String publisher) {
		Publisher = publisher;
	}

	public String getPublishDate() {
		return PublishDate;
	}

	public void setPublishDate(String publishDate) {
		PublishDate = publishDate;
	}

	public float getPrice() {
		return Price;
	}

	public void setPrice(float price) {
		Price = price;
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

	public String getResultString() {
		return resultString;
	}

	public void setResultString(String resultString) {
		this.resultString = resultString;
	}

}
