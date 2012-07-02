<c:if test="${not empty photos}">
	<div id="photoflow" class="imageflow">
		<c:forEach var="photo" items="${photos}">
			<img src="<c:url value="${photo.url}"/>" longdesc="<c:url value="${photo.url}"/>"/>
		</c:forEach>
	</div>
	<br/><br/>
</c:if>