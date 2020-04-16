<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="col-md-9">
	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon-cloud-upload"></span>&nbsp;发布云记
		 </div>
		<div class="container-fluid">
			<div class="container-fluid">
			  <div class="row" style="padding-top: 20px;">
			  	<div class="col-md-12">
			  		
			  		<c:if test="${empty typeList }">
			  			<h2>暂未查询到云记类型！</h2>
			  			<h4><a href="typeServlet?actionName=list">添加类型</a></h4>
			  		</c:if>
			  		
			  		<c:if test="${!empty typeList }">
			  			<form class="form-horizontal" method="post" action="noteServlet">
			  				<input type="hidden" name="actionName" value="addOrUpdate" />
			  				<input type="hidden" name="noteId" value="${noteInfo.noteId }" />
				  		   <div class="form-group">
						    <label for="typeId" class="col-sm-2 control-label">类别:</label>
						    <div class="col-sm-8">
						    	<select id="typeId" class="form-control" name="typeId">
									<option value="">请选择云记类别...</option>
									<c:forEach items="${typeList }" var="item">
										<c:choose>
						      				<c:when test="${!empty resultInfo }">
						      					<option <c:if test="${item.typeId == resultInfo.result.typeId }">selected</c:if>  value="${item.typeId }">${item.typeName }</option>
									      	</c:when>
									      	<c:otherwise>
									      		<option <c:if test="${item.typeId == noteInfo.typeId }">selected</c:if>  value="${item.typeId }">${item.typeName }</option>
									      	</c:otherwise>
								        </c:choose>
									</c:forEach>
								</select>
								
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="title" class="col-sm-2 control-label">标题:</label>
						    <div class="col-sm-8">
						      <c:choose>
						      	<c:when test="${!empty resultInfo }">
						      		<input class="form-control" name="title" id="title" placeholder="云记标题" value="${resultInfo.result.title }">
						      	</c:when>
						      	<c:otherwise>
						      		<input class="form-control" name="title" id="title" placeholder="云记标题" value="${noteInfo.title }">
						      	</c:otherwise>
						      </c:choose>
						    </div>
						   </div>
						  
						  <div class="form-group">
						  	<label for="content" class="col-sm-2 control-label">内容:</label>
						    <div class="col-sm-8">
						    	<c:choose>
						      	<c:when test="${!empty resultInfo }">
						      		<!-- 富文本编辑器 -->
						    		<textarea id="content" name="content">${resultInfo.result.content }</textarea>
						      	</c:when>
						      	<c:otherwise>
						      		<!-- 富文本编辑器 -->
						    		<textarea id="content" name="content">${noteInfo.content }</textarea>
						      	</c:otherwise>
						      </c:choose>
						    </div>
						  </div>			 
						  <div class="form-group">
						    <div class="col-sm-offset-4 col-sm-4">
						      <input type="submit" class="btn btn-primary" onclick="return saveNote();" value="保存">
							  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							  <font id="error" color="red">${resultInfo.msg }</font>  
						    </div>
						  </div>
						</form>
			  		</c:if>
			  		
			  	</div>
			  </div>
		</div>	
	</div>		
	</div>
	<script type="text/javascript">
		/* $(function(){
			//UE.getEditor('noteEditor');
			 var editor = new UE.ui.Editor({initialFrameHeight:'100%',initialFrameWidth:'100%'});  
		     editor.render("noteEditor");  
		 }); */
		 
		 
		 var ue; // 富文本编辑器对象
		 /* 预加载方法：当页面的dom结构加载完毕后才执行 */
		 $(function(){
			 // 加载富文本编辑器	UE.getEditor('容器的ID属性值')	
			 ue = UE.getEditor('content');
		 });
		
		 
		 
		 
		 function saveNote() {
			 // 获取表单元素
			 var typeId = $('#typeId').val(); // 获取下拉框被选中项
			 var title = $("#title").val(); // 获取文本框的内容
			 // 获取富文本编辑器的内容（包括html标签）	ue.getContent()
			 var content = ue.getContent();
			 // 获取富文本编辑器的纯文本	ue.getContentTxt();
			 // var content1 = ue.getContentTxt();
			 
			 /* console.log(typeId);
			 console.log(title);
			 console.log(content);
			 console.log(content1); */
			 
			 // 非空判断
			 if (isEmpty(typeId)) {
				 $("#error").html("请选择类型名称！");
				 return false;
			 }
			 if (isEmpty(title)) {
				 $("#error").html("云记标题不可为空！");
				 return false;
			 }
			 if (isEmpty(content)) {
				 $("#error").html("云记内容不可为空！");
				 return false;
			 }
			 
			 return true;
			 
		 }
	
	</script>

</div>	