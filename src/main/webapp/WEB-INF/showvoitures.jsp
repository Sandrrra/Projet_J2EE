<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Bootstrap CSS -->
<link rel="stylesheet" href="style.css" type="text/css" media="screen" />
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">


<meta charset="UTF-8">
<title>Liste Voiture</title>
</head>
<body>

<div class="container text-dark" id="container">	
	<h1 class="titre">Liste des voitures</h1>
	<div class="row">			
				
		<form action="ListVoiture" method="get" class="box">
			<label for="modele">Choisir/créer un modèle : </label>
			<select name="modele" id="modele">
				<option value="all">Tout les modèles</option>
				<c:forEach var="modele" items="${modele}">
					<option value="${modele.modeleId}"
					<c:if test="${not empty selected and modele.modeleId == selected}">
						selected
					</c:if>
					>${modele.modeleNom}</option>			
				</c:forEach>
				<option value="new">Ajouter un modèle</option>
			</select> 
			<br>
			<label for="marque">Choisir/créer une marque : </label>
			<select name="marque" id="marque">
				<option value="all">Toutes les marques</option>
				<c:forEach var="marque" items="${marque}">
				<option value="${marque.marqueId}"
					<c:if test="${not empty selectedMarque and marque.marqueId == selectedMarque}">
					selected
					</c:if>
					>${marque.marqueNom}</option>			
				</c:forEach>
				<option value="new">Ajouter une société</option>
			</select> 			
		
			<input type="submit" value="Envoyer">
		</form>
					
	</div>
	<div class="row">
		<a href="VoiturePage" class="btn btn-primary mt-3">Créer une nouvelle voiture</a>
	</div>
	<div class="row">
		<table>
			<thead>
				<tr>
					<th>Voiture</th>
					<th>Description</th>
					<th>Modèle</th>
					<th>Société</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="voiture" items="${voiture}">
					<tr>
						<td><a class="colorlist" href="VoiturePage?voitureId=${voiture.voitureId}">${voiture.voitureNom}</a></td>
						<td class="colorlist">${voiture.voitureDescription}</td>
						<td><a class="colorlist" href="NewModele?modeleId=${voiture.modeleId}">${voiture.modeleNom}</a></td>
						<td><a class="colorlist" href="NewMarque?marqueId=${voiture.marqueId}">${voiture.marqueNom}</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</div>
</body>
</html>