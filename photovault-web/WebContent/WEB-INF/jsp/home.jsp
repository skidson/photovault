<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<%@ include file="/WEB-INF/jsp/include.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".post:first").css("display","block");
			$(".expander:first").removeClass("min").addClass("max");
			$(".post-header").click(function() {
				var expander = $(this).find($(".expander"));
				$(this).next().slideToggle("normal", function() {
					if (expander.hasClass("max")) {
						expander.removeClass("max").addClass("min");
					} else if (expander.hasClass("min")){
						expander.removeClass("min").addClass("max");
					} else {
						expander.addClass("max");
					}
				});
			});
		});
	</script>
</head>
<body>
	<div id="wrap">
		<c:set var="directory" value="Home"/>
		<%@ include file="/WEB-INF/jsp/header.jsp" %>
		
		<div id="content-wrap">
			<%@ include file="/WEB-INF/jsp/sidebar.jsp" %>
			<div id="main">
				<%@ include file="/WEB-INF/jsp/title.jsp" %>
				<br/>
			</div>
		</div>
		
		<%@ include file="/WEB-INF/jsp/footer.jsp" %>
	</div>

</body>
</html>
