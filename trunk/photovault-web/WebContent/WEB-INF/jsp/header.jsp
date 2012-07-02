<!--header -->
<div id="header">
	<h1 id="logo-text"><a href="<c:url value="/home/index"/>">Stephen Kidson</a></h1>		
	<p id="slogan">EIT BASc. Computer Engineering</p>		
	
	<div id="header-links">
		<p><a href="mailto:stephenkidson@gmail.com">Contact</a></p>		
	</div>	
	
</div>
<!-- menu -->	
<div  id="menu">
	<ul>
		<li <c:if test="${directory == 'Home'}" >id="current"</c:if>><a href="<c:url value="/home/index"/>">Home</a></li>
		<%-- <li <c:if test="${directory == 'Projects'}" >id="current"</c:if>><a href="<c:url value="/projects/index"/>">Projects</a></li>
		<li <c:if test="${directory == 'Courses'}" >id="current"</c:if>><a href="<c:url value="/courses/index"/>">Courses</a></li>
		<li <c:if test="${directory == 'Resume'}" >id="current"</c:if>> <a href="<c:url value="/resume/index"/>">Resume</a></li> --%>
	</ul>
</div>			