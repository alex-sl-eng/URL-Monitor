<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="includes/header.jsp" />

<main>
<div class="filter">
	<ul class="horizontal">
		<li style="width: 50%"><input type="text" /></li>
		<li><select>
				<option value="name"><spring:message code="sort.name"/></option>
				<option value="status"><spring:message code="sort.status"/></option>
		</select></li>
		<li>
			<button class="icon-list" id="list" onclick="setView('list')"></button>
		</li>

		<li>
			<button class="icon-grid" id="grid" onclick="setView('grid')"></button>
		</li>
	</ul>
</div>
<div class="content">
	<div class="container success">
		<span class="status icon-checkmark"></span> <span class="name"><a
			href="http://google.com">Google
				testingggggggggggggggggggggggggggggggg</a></span> <span class="time right">12/07/1900
			08:00:00 <a href=# class="more-info icon-chevron-down"
			onclick="toggleDetails(this, 'row1')"></a>
		</span>
		<div id="row1" class="details">
			<table>
				<tr>
					<td class="header">Description</td>
					<td class="value"><span>google search engine</span></td>
				</tr>
				<tr>
					<td class="header">Url</td>
					<td class="value"><a href="http://google.com">http://google.com</a></td>
				</tr>
				<tr>
					<td class="header">Search text</td>
					<td class="value">searched string testing long string that.
						sssssssssssssssssssss.</td>
				</tr>
				<tr>
					<td class="header">Cron expression</td>
					<td class="value">0/5 * * * * ?</td>
				</tr>
				<tr>
					<td class="header">Tag</td>
					<td class="value">#search;#public</td>
				</tr>
			</table>
		</div>
	</div>

	<div class="container failed">
		<span class="status icon-close"></span> <span class="name"><a
			href="http://yahoo.com">Yahoo</a></span> <span class="time right">12/07/1900
			08:00:00 <a href=# class="more-info icon-chevron-down"></a>
		</span>
	</div>

	<div class="container unknown">
		<span class="status icon-question"></span> <span class="name"><a
			href="http://amazon.com">Amazon</a></span> <span class="time right">12/07/1900
			08:00:00 <a href=# class="more-info icon-chevron-down"></a>
		</span>
	</div>

	<div class="container unknown">
		<span class="status icon-question"></span> <span class="name"><a
			href="http://amazon.com">Amazon</a></span> <span class="time right">12/07/1900
			08:00:00 <a href=# class="more-info icon-chevron-down"></a>
		</span>
	</div>
</div>
</main>