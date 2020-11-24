<%@ page session="true"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="layoutTag" tagdir="/WEB-INF/tags"%>

<layoutTag:layout>

	<!DOCTYPE html>
	<html>
<head>
<meta charset="UTF-8">
<title>게시글 목록 보기</title>
<style>
.navbar-inverse .navbar-nav>.active>a, .navbar-inverse .navbar-nav>.active>a:focus,
	.navbar-inverse .navbar-nav>.active>a:hover {
	color: rgb(255, 255, 255);
	background-color: red
}

div#paginationBox {
	width: 800px;
	margin-left: auto;
	margin-right: auto;
	text-align: center;
}

.search {
	margin-left: 60%
}
</style>
</head>
<body>

	<div class="container">

		<h2>게시글 목록</h2>
		
		<button class="btn btn-primary" id="inserting"
			onclick="location.href='/product/productinsert'">글쓰기</button>

		<table class="table table-hover table-bordered">
			<thead>
				<tr>
					<th>No</th>
					<th>이미지</th>
					<th>제품명</th>
					<th>가격</th>
					<th>판매수량</th>
				</tr>
			</thead>
			<c:forEach var="product" items="${list}">
				<tr>
					<td class="info"
						onclick="location.href='/board/detail/${product.productno}'">${product.productno}</td>
					<td>${product.productimagefile}</td>
					<td>${product.productname}</td>
					<td>${product.productprice}</td>
					<td>${product.productsalescnt}</td>
					<td class="warning" onclick="location.href='/board/detailComment/${product.productno}'">댓글</td>
				</tr>
			</c:forEach>
		</table>



		<!-- pagination{s} -->
		<div id="paginationBox">
			<ul class="pagination">
				<c:if test="${productpagination.prev}">

					<li class="page-item"><a class="page-link" href="#"
						onClick="fn_prev('${productpagination.page}', '${productpagination.range}', '${productpagination.rangeSize}')">Previous</a></li>

				</c:if>
				<c:forEach begin="${productpagination.startPage}"
					end="${pagination.endPage}" var="idx">

					<li
						class="page-item <c:out value="${productpagination.page == idx ? 'active' : ''}"/> "><a
						class="page-link" href="#"
						onClick="fn_pagination('${idx}', '${productpagination.range}', '${productpagination.rangeSize}')">
							${idx} </a></li>

				</c:forEach>

				<c:if test="${pagination.next}">

					<li class="page-item"><a class="page-link" href="#"
						onClick="fn_next('${productpagination.range}','${productpagination.range}', '${productpagination.rangeSize}')">Next</a></li>

				</c:if>

			</ul>

		</div>
	</div>
	<script>
		//이전 버튼 이벤트

		function fn_prev(page, range, rangeSize) {

			var page = ((range - 2) * rangeSize) + 1;

			var range = range - 1;

			var url = "${pageContext.request.contextPath}/product/productlist";

			url = url + "?page=" + page;

			url = url + "&range=" + range;

			location.href = url;

		}

		//페이지 번호 클릭

		function fn_pagination(page, range, rangeSize, searchType, keyword) {

			var url = "${pageContext.request.contextPath}/product/productlist";

			url = url + "?page=" + page;

			url = url + "&range=" + range;

			location.href = url;

		}

		//다음 버튼 이벤트

		function fn_next(page, range, rangeSize) {

			var page = parseInt((range * rangeSize)) + 1;

			var range = parseInt(range) + 1;

			var url = "${pageContext.request.contextPath}/product/productlist";

			url = url + "?page=" + page;

			url = url + "&range=" + range;

			location.href = url;

		}
	</script>

</body>
	</html>
</layoutTag:layout>

<!-- layoutTag를 적용하므로 bootstrap.jsp 파일이 필요 없어졌다. -->
<%--== : eq
!= : ne
< : lt
> : gt
<= : le
>= : ge --%>

















