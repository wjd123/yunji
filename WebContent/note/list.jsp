<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="col-md-9">



	<div class="data_list">
		<div class="data_list_title">
			<span class="glyphicon glyphicon glyphicon-th-list"></span>&nbsp;
			云记列表
		</div>

		<!-- 判断云记列表是否存在 -->
		<c:if test="${empty page }">
			<h2>暂未查询到云记记录！</h2>
		</c:if>
		
		<c:if test="${!empty page }">
			<div class="note_datas">
			<ul>
			
				<!--遍历-->
				<c:forEach items="${page.datas }" var="item">
					<li>『 <fmt:formatDate value="${item.pubTime }" pattern="yyyy-MM-dd"/>』&nbsp;&nbsp;<a
					href="noteServlet?actionName=detail&noteId=${item.noteId }">${item.title }</a>
					</li>
				</c:forEach>
				
			</ul>
		</div>
		<nav style="text-align: center">
			<!-- 			角标 -->
			<ul class="pagination  center">
					<c:if test="${page.pageNum >1 }">
						<li>
							<a href="indexServlet?pageNum=${page.prePage }">
								<span>«</span>
							</a>
						</li>
					</c:if>
					<c:forEach begin="${page.startNavPage }" end="${page.endNavPage }" var="p">
						<li <c:if test="${page.pageNum==p }">class="active"</c:if> >
							<a href="indexServlet?pageNum=${p }">
								<span>${p }</span>
							</a>
						</li>
					</c:forEach>
					<c:if test="${page.pageNum < page.totalPages }">
						<li>
							<a href="indexServlet?pageNum=${page.nextPage }">
								<span>»</span>
							</a>
						</li>
					</c:if>
				</ul>
		</nav>
		</c:if>


	</div>

</div>