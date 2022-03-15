
// DOM 加载完再执行
$(function() {
	
	var _pageSize; // 存储用于搜索
	var a = 1;
	var flag = 1;
	
	// 根据用户名、页面索引、页面大小获取用户列表
	function getClassesByName(pageIndex, pageSize) {
		 $.ajax({
			 url: "/classes",
			 contentType : 'application/json',
			 data:{
				 "async":true,
				 "pageIndex":pageIndex,
				 "pageSize":pageSize,
				 "name":$("#searchName").val()
			 },
			 success: function(data){
				 $("#mainContainer").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	}
	
	// 分页
	$.tbpage("#mainContainer", function (pageIndex, pageSize) {
		getClassesByName(pageIndex, pageSize);
		_pageSize = pageSize;
	});
   
	// 搜索
	$("#searchNameBtn").click(function() {
		getClassesByName(0, _pageSize);
	});
	
	// 获取添加班级的界面
	$("#addClass").click(function() {
		$.ajax({ 
			 url: "/classes/add",
			 success: function(data){
				 $("#userFormContainer").html(data);
		     },
		     error : function(data) {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 获取编辑班级的界面
	$("#rightContainer").on("click",".edit-class", function () {
		a = a + 1;
		console.log(a)
		$.ajax({
			 url: "/classes/edit/" + $(this).attr("classId"),
			 success: function(data){
				 $("#userFormContainer").html(data);
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	

	$("#submitEdit").click(function() {
		$.ajax({ 
			 url: "/classes",
			 type: 'POST',
			 data:$('#classForm').serialize(),
			 success: function(data){
				 $('#classForm')[0].reset(); // 提交变更后，清空表单
				 if (data.success) {
					 // 从新刷新主界面
					 getClassesByName(0, _pageSize);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
	
	// 删除班级
	$("#rightContainer").on("click",".delete-class", function () {
		// 获取 CSRF Token 
		var csrfToken = $("meta[name='_csrf']").attr("content");
		var csrfHeader = $("meta[name='_csrf_header']").attr("content");
		console.log("删除班级")
		
		$.ajax({ 
			 url: "/classes/" + $(this).attr("classId") ,
			 type: 'DELETE', 
			 beforeSend: function(request) {
                 request.setRequestHeader(csrfHeader, csrfToken); // 添加  CSRF Token 
             },
			 success: function(data){
				 if (data.success) {
					 // 从新刷新主界面
					 getClassesByName(0, _pageSize);
				 } else {
					 toastr.error(data.message);
				 }
		     },
		     error : function() {
		    	 toastr.error("error!");
		     }
		 });
	});
});