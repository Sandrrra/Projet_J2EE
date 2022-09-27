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
<title>Page Voiture</title>
</head>
<body>


	<div class="container text-white" id="creabeer">
		<h2 class="titre">Créer/Modifier un produit</h2>
		<c:if test="${not empty message}">
			<p class="invalid">${message}</p>
		</c:if>

		<form action="VoiturePage" method="post">
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Nom : </div>
				<div class="col-12 col-md-8">
					<input type="text" class="form-control" id="nom"
						placeholder="Saisir un nom" name="nom" value="${voiture.voitureNom}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Description : </div>
				<div class="col-12 col-md-8">
					<input type="text" class="form-control" id="description"
						placeholder="Saisir une description" name="description"
						value="${voiture.voitureDescription}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Prix : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="prix" name="prix" placeholder="Saisir un prix"
						class="form-control validate" value="${voiture.prix}" required />
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Mise en marché : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="miseAuMarche" name="miseAuMarche"
						placeholder="date en format : AAAA-MM-JJ" class="form-control validate"
						value="${voiture.miseAuMarche}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Pays : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="pays" name="pays" placeholder="Saisir un pays"
						class="form-control validate" value="${voiture.pays}" required />
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Carburant : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="carburant" name="carburant"
						placeholder="Saisir un carburant" class="form-control validate"
						value="${voiture.carburant}" required />
				</div>
			</div>
			<div class="row align-items-center">
				<div class="col-12 col-md-4">Portes : </div>
				<div class="col-12 col-md-8">
					<input type="text" id="portes" name="portes" placeholder="Saisir un nombre de portes"
						class="form-control validate" value="${voiture.portes}" required />
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Modèle : </div>
				<div class="col-12 col-md-8">
					<select name="modeles" id="modeles">
						<c:forEach var="modele" items="${modeles}">
							<option value="${modele.modeleId}"
								<c:if test="${modele.modeleId == voiture.modeleId}">
					selected
				</c:if>>${modele.modeleNom}</option>
						</c:forEach>
					</select>
				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4">Société : </div>
				<div class="col-12 col-md-8">
					<fieldset>
						<c:forEach var="marque" items="${marques}">
							<input type="checkbox" id="scales${marque.marqueId}"
								name="marques" value="${marque.marqueId}"
								<c:if test="${marque.checked}">
					checked
				</c:if>>
							<label for="scales${marque.marqueId}">${marque.marqueNom}</label>
						</c:forEach>
					</fieldset>

				</div>
			</div>

			<div class="row align-items-center">
				<div class="col-12 col-md-4 offset-md-3">
					<button type="submit" class="btn btn-primary mt-3">Modifier</button>
				</div>
				<div class="col-12 col-md-5">
					<a class="btn btn-primary mt-3" href="ListVoiture">Retour</a>
				</div>
			</div>


		</form>
	</div>


</body>
</html>