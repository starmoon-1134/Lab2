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

		// 判断作者与作者的ID是否吻合
		String queryAuthor = "select * from Author where AuthorID='" + getAuthorID() + "';";
		System.out.println(queryAuthor);
		ResultSet queryAuthorResult = mysCon.executeQuery(queryAuthor);
		if (queryAuthorResult.next()) {
			isAuthorIDExist = true;
			if (queryAuthorResult.getString("Name").equals(getAuthorName())) {
				isRequirAddAuthor = false;
			}
		}

		// 如果需要添加新作者
		if (isRequirAddAuthor) {
			if (isAuthorIDExist) {
				resultString = "当前ID的作者为 \"" + queryAuthorResult.getString("Name") + "\" ,请更正\n" + "或者换一个ID并填入完整的作者信息";
				return "success";
			}
			// 新添作者时需要保证输入的作者信息完整
			if (getAuthorAge().equals("") || getAuthorCountry().equals("")) {
				resultString = "ID不存在\n请输入完整的作者信息用于新建一个作者";
				return "success";
			}
			String addNewAuthor = "insert into Author values('" + getAuthorID() + "','" + getAuthorName() + "','"
					+ getAuthorAge() + "','" + getAuthorCountry() + "');";
			System.out.println(addNewAuthor);

			if (mysCon.executeUpdate(addNewAuthor) == -1) {
				resultString = "添加作者失败，稍后再试";
				return "success";
			}
		}

		// 重新连接数据库
		mysCon.closeCon();
		mysCon = null;
		mysCon = new SqlCon();

		String queryISBN = "select * from Book where ISBN='" + getISBN() + "'";
		ResultSet ISBNResultSet = mysCon.executeQuery(queryISBN);

		if (!ISBNResultSet.next()) {// 判断ISBN是否重复
			String insert = "insert into Book values('" + getISBN() + "','" + getTitle() + "','" + getAuthorID() + "','"
					+ getPublisher() + "','" + getPublishDate() + "','" + getPrice() + "');";
			System.out.println(insert);
			mysCon.executeUpdate(insert);
			mysCon.closeCon();
			resultString = "添加成功";
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
			// resultString = "ISBN已经存在，成功更新图书信息!";
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
			resultString = "ISBN已经存在,对应的书名为：" + cur_title + "   作者：" + cur_AuthorName + "\n请检查！";
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

		// 判断查询结果是否为空
		AuthorRet.last();
		int queryRows = AuthorRet.getRow();
		AuthorRet.beforeFirst();
		if (queryRows <= 0) {
			sqlCon_Au.closeCon();
			sqlCon_Bo.closeCon();
			resultString = "没有该作者信息";
			return "error";
		}
		try {
			// 查找所有同名作者
			while (AuthorRet.next()) {
				tmpAuthorInfo = new AuthorInfo();
				tmpAuthorInfo.setAuthorAge(AuthorRet.getString("Age"));
				tmpAuthorInfo.setAuthorCountry(AuthorRet.getString("Country"));
				tmpAuthorInfo.setAuthorID(AuthorRet.getString("AuthorID"));
				tmpAuthorInfo.setAuthorName(AuthorRet.getString("Name"));
				System.out.println("1");
				// 查找一个作者的所有书
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
		// 将数据存储在map里，再转换成json类型数据，也可以自己手动构造json类型数据

		JSONArray json = JSONArray.fromObject(authorInfos);// 自定义的类型需要setter、getter
		setResultString(json.toString());// 给ResultString(自定义的)赋值，传递给页面
		System.out.println(resultString);
		if (flag != 1) {
			resultString = "No Book";
			return "error";
		}
		return "success";
	}

	public String delBook() throws SQLException {
		// 删除某条数据
		SqlCon sqlCon = new SqlCon();
		String deleteBookString = "delete from Book where ISBN='" + getISBN() + "'";
		int delResult = sqlCon.executeUpdate(deleteBookString);// 执行成功返回0
		// System.out.println("delResult:" + delResult);
		sqlCon.closeCon();
		sqlCon = null;
		if (delResult != 0) {
			resultString = "SQL error";
			return "error";
		}
		// 若该书作者再无作品，提示删除
		sqlCon = new SqlCon();
		String queryAuthor_book = "select * from Book where AuthorID='" + getAuthorID() + "'";
		ResultSet AuthorRet = sqlCon.executeQuery(queryAuthor_book);
		AuthorRet.last();
		int queryRows = AuthorRet.getRow();
		sqlCon.closeCon();
		if (queryRows <= 0) {
			resultString = "ID:" + getAuthorID() + "\n作者:" + getAuthorName() + "\n没有其他作品，将被删除";
			return "success";
		}
		resultString = "delete success";
		return "success";
	}

	public String delAuthor() {
		// 删除某条数据
		SqlCon sqlCon = new SqlCon();
		String deleteBookString = "delete from Author where AuthorID='" + getAuthorID() + "';";
		int delResult = sqlCon.executeUpdate(deleteBookString);// 执行成功返回0
		// System.out.println("delResult:" + delResult);
		System.out.println(deleteBookString);
		sqlCon.closeCon();
		sqlCon = null;
		if (delResult != 0) {
			resultString = "删除作者" + getAuthorName() + "失败...";
			return "error";
		}
		resultString = "删除作者" + getAuthorName() + "已完成";
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

		// 记录原作者，修改之后若该作者再没有书也将被删除
		String queryAuthor_book = "select * from Book where ISBN='" + getISBN() + "';";
		System.out.println(queryAuthor_book);
		ResultSet AuthorRet = mysCon.executeQuery(queryAuthor_book);
		AuthorRet.next();
		String oldAuthorID = AuthorRet.getString("AuthorID");

		// 判断作者与作者的ID是否吻合
		String queryAuthor = "select * from Author where AuthorID='" + getAuthorID() + "';";
		System.out.println(queryAuthor);
		ResultSet queryAuthorResult = mysCon.executeQuery(queryAuthor);
		if (queryAuthorResult.next()) {
			isAuthorIDExist = true;
			if (queryAuthorResult.getString("Name").equals(getAuthorName())) {
				isRequirAddAuthor = false;
			}
		}

		// 如果需要添加新作者
		if (isRequirAddAuthor) {
			if (isAuthorIDExist) {
				resultString = "当前ID已经被占用！";
				return "success";
			}
			String addNewAuthor = "insert into Author values('" + getAuthorID() + "','" + getAuthorName() + "','"
					+ getAuthorAge() + "','" + getAuthorCountry() + "');";
			System.out.println(addNewAuthor);

			if (mysCon.executeUpdate(addNewAuthor) == -1) {
				resultString = "添加作者失败，稍后再试";
				return "success";
			}
			// 可能会需要修改作者信息（只改年龄、国家）
		} else {
			String alterAuthor = "update Author set Age='" + getAuthorAge() + "', Country='" + getAuthorCountry()
					+ "' where AuthorID='" + getAuthorID() + "';";
			System.out.println(alterAuthor);
			if (mysCon.executeUpdate(alterAuthor) == -1) {
				resultString = "修改作者失败，稍后再试";
				return "success";
			}
		}

		// 重新连接数据库
		mysCon.closeCon();
		mysCon = null;
		mysCon = new SqlCon();

		// 修改图书信息
		String alterBook = "update Book set AuthorID='" + getAuthorID() + "', Title='" + getTitle() + "', Publisher='"
				+ getPublisher() + "', PublishDate='" + getPublishDate() + "', Price='" + getPrice() + "' where ISBN='"
				+ getISBN() + "';";
		System.out.println(alterBook);
		if (mysCon.executeUpdate(alterBook) == -1) {
			resultString = "修改图书信息失败";
			return "success";
		}
		resultString = "修改成功";

		// 重新连接数据库
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
			resultString += ",修改之前的作者没有图书，信息已删除";
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
