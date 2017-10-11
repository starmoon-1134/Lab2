/*按作者查询图书*/
function QueryResponse(){
      	$(".form-submit").unbind();
          $.ajax({
          	
              type:"post",
              url:"queryBook",//需要用来处理ajax请求的action,excuteAjax为处理的方法名，JsonAction为action名
              data:{//设置数据源
                  'AuthorName':$("input[name=AuthorName]").val()//这里不要加","  不然会报错，而且根本不会提示错误地方
              },
              dataType:"json",//设置需要返回的数据类型
              success:function(resultString){
              	var retJudge=new RegExp("{");
              	if(retJudge.exec(resultString)==null){
              		alert(resultString);
              		location.replace("../jsp/showBook.jsp");
              		return false;
              	}
	          var authors = eval("("+resultString+")");//将数据转换成json类型，可以把data用alert()输出出来看看到底是什么样的结构
	          //得到的d是一个形如{"key":"value","key1":"value1"}的数据类型，然后取值出来
	          var tableText = "";
	          $(".query-table").empty();
	          var line = document.createElement("tr");  // 以 DOM 创建新元素
	          
	         	line.innerHTML="<th>作者ID</th>";
	         	line.innerHTML+="<th>ISBN</th";
	         	line.innerHTML+="<th>书名</th>";
	         	line.innerHTML+="<th>作者</th>";
	         	line.innerHTML+="<th>出版社</th>";
	         	line.innerHTML+="<th>出版日期</th>";
	         	$(line).css("color","red");
	         	$(".query-table").append(line);
	          for(i in authors){
	          	 var book = authors[i].bookInfos;
	          	 for(j in book){
	          		 var tmpAuthor = JSON.stringify(authors[i]);//传参用       /*String传值，object传引用*/
	         		 var tmpBook = JSON.stringify(book[j]);//传参用
	          		 
	          		 //添加表格数据
	          		 var line = document.createElement("tr"); 
	          		 line.innerHTML="<td>"+authors[i].authorID+"</td>";
	          		 line.innerHTML+="<td>"+book[j].ISBN+"</td>";
	          		 line.innerHTML+="<td class=title " +
	          		 		"onclick="+"\'showDetailPre("+tmpAuthor+","+tmpBook+")\'>"+book[j].title+"</td>";
	          		 line.innerHTML+="<td>"+authors[i].authorName+"</td>";
	          		 line.innerHTML+="<td>"+book[j].publisher+"</td>";
	          		 line.innerHTML+="<td>"+book[j].publishDate+"</td>";
	          		 //添加操作按钮
	          		 
	          		 var alterBtn = document.createElement("a");
	          		 alterBtn.innerHTML="修改";
	          		 $(alterBtn).attr("href","javascript:void(0);");
	          		 $(alterBtn).attr("class","alterBtn");
	         		 $(alterBtn).attr("onclick","alterBookPre("+tmpAuthor+","+tmpBook+")");
	         		 
	         		 var delBtn = document.createElement("a");
	          		 delBtn.innerHTML="删除";
	          		 $(delBtn).attr("href","javascript:void(0);");
	          		 $(delBtn).attr("class","delBtn");
	          		 $(delBtn).attr("onclick",
	          				 		"delBook(\""+book[j].ISBN
				 							+"\",\""+authors[i].authorID
				 							+"\",\""+authors[i].authorName+"\")");
	         		 
//	         		 var detailBtn = document.createElement("a");
//	         		 detailBtn.innerHTML="详情";
//	         		 $(detailBtn).attr("href","javascript:void(0);");
//	         		 $(detailBtn).attr("onclick","showDetail("+tmpAuthor+","+tmpBook+")");
	        		
	         		$(line).append(delBtn);
	         		$(line).append(alterBtn);
	         		 
	          		 //当前行添加到table标签中
	          		 $(line).css("color","red");
	          		 $(".query-table").append(line);
	          		 
	          		 //查询成功后，暂存当前查询输入
	          		$("#query-Stroge").text($("input[name=AuthorName]").val());
	          	 }
	          }
	         //$(".table-container").text(""+d[0].bookInfos[0].price+"");
	      },
	      error:function(resultString){
	          alert("系统错误，稍后再试");
	      }//这里不要加","
	  });//$.ajax{} end
	  $(".form-submit").bind("click",QueryResponse);
	};

 /* 删除图书 */
 function delBook(ISBN,authorID,authorName){
	 $.ajax({
		 type:"post",
		 url:"delBook",
		 data:{//设置数据源
             'ISBN':ISBN,
             'AuthorID':authorID,
             'AuthorName':authorName
         },
         dataType:"json",//设置需要返回的数据类型
         success:function(resultString){
        	if (resultString=="SQL error") {
				alert("删除过程出现了一些错误...");
				return false;
			}
        	 else if(resultString!="delete success")
    		 {//该作者再无其他作品
    		 	alert(resultString);
    		 	delAuthor(""+authorID+"");
    		 }
        	 $("input[name=AuthorName]").text($("#query-Stroge").val());//将查询输入从暂存区中取出
        	 QueryResponse();//删除后刷新结果
         },
		 error:function(resultString){
			 alert(resultString);
		 }
	 });//$.ajax({})  end
	
 };
 
 function alterBookPre(author,book) {
	 $("#back-layer-2", parent.document).addClass("blur");
	 $("#alterForm", parent.document).animate({ height:'100%',},{easing:'swing',speed:'slow'});
	 $("input[name=AuthorName]", parent.document).val(author.authorName);
	 $("input[name=Age]", parent.document).val(author.authorAge);
	 $("input[name=Country]", parent.document).val(author.authorCountry);
	 $("input[name=AuthorID]", parent.document).val(author.authorID);
	 $("input[name=Price]", parent.document).val(book.price);
	 $("input[name=Publisher]", parent.document).val(book.publisher);
	 $("input[name=PublishDate]", parent.document).val(book.publishDate);
	 $("input[name=ISBN]", parent.document).val(book.ISBN);
	 $("input[name=Title]", parent.document).val(book.title);
}
 

 function showDetailPre(author,book){//传过来的 数据已经是 Object对象
	
	$("#back-layer-2", parent.document).addClass("blur");
	$(".showDetailLayer", parent.document).animate({ height:'100%',},{easing:'swing',speed:'slow'});
	$("span[name=AuthorName]", parent.document).text(author.authorName);
	$("span[name=Age]", parent.document).text(author.authorAge);
	$("span[name=Country]", parent.document).text(author.authorCountry);
	$("span[name=AuthorID]", parent.document).text(author.authorID);
	$("span[name=Price]", parent.document).text(book.price);
	$("span[name=Publisher]", parent.document).text(book.publisher);
	$("span[name=PublishDate]", parent.document).text(book.publishDate);
	$("span[name=ISBN]", parent.document).text(book.ISBN);
	$("span[name=Title]", parent.document).text(book.title);
 };
 
 /*删除作者*/
 function delAuthor(authorID) {
	 $.ajax({
		 type:"post",
		 url:"delAuthor",
		 data:{//设置数据源
             'AuthorID':authorID,
             'AuthorName':$("#query-Stroge").val()
         },
         dataType:"json",//设置需要返回的数据类型
         success:function(resultString){
        	 alert(resultString);
         },
		 error:function(resultString){
			 alert("抱歉,好像出错了...");
		 }
	 });//$.ajax({})  end
}

 /*template*/
// function alterBook(ISBN){
//	 $.ajax({
//		 type:"post",
//		 url:"alterBook",
//		 data:{//设置数据源
//             'ISBN':ISBN
//         },
//         dataType:"json",//设置需要返回的数据类型
//         success:function(resultString){
//        	 alert(resultString);
//         },
//		 error:function(resultString){
//			 
//		 }
//	 });//$.ajax({})  end
//	
// };
 	function reflashQuery() {
 		 $("input[name=AuthorName]").text($("#query-Stroge").val());//将查询输入从暂存区中取出
 		 QueryResponse();//删除后刷新结果
	}
 
   /* 页面加载完成，绑定事件 */
   $(document).ready(function(){
	   /*按作者查询图书*/    
		 var $btn = $(".searchPNG");//获取按钮元素
		 $btn.bind("click",QueryResponse); //给按钮绑定点击事件
		 $("input[name=AuthorName]").keydown(function(e){
			 if(e.keyCode==13){
				 QueryResponse();
			 }
		 });
		 
		 $("input[name=AuthorName]").focus(function() {
			$(this).removeClass("defaultTip");
			if($(this).val()=="输入作者名字"){
				$(this).val("");
			}
		});
		 
		 $("input[name=AuthorName]").blur(function() {
			$(this).addClass("defaultTip");
			if($(this).val()==""){
				$(this).val("输入作者名字");
			}
		});
		 $("input[name=AuthorName]").addClass("defaultTip");
   });	