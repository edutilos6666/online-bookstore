<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register - OnlineBookStore</title>
<link rel="stylesheet" type="text/css"
	href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" />
</head>
<body>
	<div class="container col-lg-3">
		<form class="needs-validation border border-dark-1" method="POST" action="${register-user}"
			style="margin-top: 15%;" novalidate>
			<!-- <img src="./onlinebookstore-logo.png" alt="" width="20%;"
				class="mt-4"
				style="display: block; margin-left: auto; margin-right: auto;"> -->
			<h3 class="mt-4 ml-4">Create an account</h3>
			<div class="form-row ml-4 mr-4">
				<div class="col-md-12 mb-0">
					<label for="validationCustom01"></label> <input type="text"
						class="form-control" name="firstName" id="validationCustom01"
						placeholder="First name" required>
					<div class="valid-feedback">Looks good!</div>
					<div class="invalid-feedback">Please enter a valid first
						name!</div>
				</div>
			</div>
			<div class="form-row ml-4 mr-4">
				<div class="col-md-12 mb-0">
					<label for="validationCustom01"></label> <input type="text"
						class="form-control" name="lastName" id="validationCustom01"
						placeholder="Last name" required>
					<div class="valid-feedback">Looks good!</div>
					<div class="invalid-feedback">Please enter a valid last name!
					</div>
				</div>
			</div>
			<div class="form-row ml-4 mr-4">
				<div class="col-md-12 mb-0">
					<label for="validationCustom01"></label> <input type="email"
						class="form-control" name="email" id="validationCustom01"
						placeholder="Email" required>
					<div class="valid-feedback">Looks good!</div>
					<div class="invalid-feedback">Please enter a valid email!</div>
				</div>
			</div>
			<div class="form-row ml-4 mr-4">
				<div class="col-md-12 mb-0">
					<label for="validationCustom01"></label> <input type="password"
						class="form-control" name="password" id="validationCustom01"
						placeholder="Password" required>
					<div class="valid-feedback">Looks good!</div>
					<div class="invalid-feedback">Please enter a valid password!
					</div>
				</div>
			</div>
			<div class="form-row ml-4 mr-4">
				<div class="col-md-12 mb-3">
					<label for="validationCustom01"></label> <input type="password"
						class="form-control" name="verifyPassword"
						placeholder="Verify password" required>
					<div class="valid-feedback">
						<!-- Looks good! -->
					</div>
					<!-- <div class="valid-feedback">
                            Password does not match!
                        </div> -->
				</div>
			</div>
			<div class="form-group ml-4">
				<div class="form-check">
					<input class="form-check-input" type="checkbox" value=""
						name="checkBox" id="invalidCheck" required> <label
						class="form-check-label" for="invalidCheck"> Agree to
						terms and conditions </label>
					<div class="invalid-feedback">You must agree before
						submitting.</div>

				</div>

			</div>
			<div class="form-row ml-4 mr-4">
				<button class="btn btn-primary btn-lg btn-block mt-1 mb-4"
					type="submit">Sign up</button>
			</div>
		</form>

		<script>
			// Example starter JavaScript for disabling form submissions if there are invalid fields
			(function() {
				'use strict';
				window
						.addEventListener(
								'load',
								function() {
									// Fetch all the forms we want to apply custom Bootstrap validation styles to
									var forms = document
											.getElementsByClassName('needs-validation');
									// Loop over them and prevent submission
									var validation = Array.prototype.filter
											.call(
													forms,
													function(form) {
														form
																.addEventListener(
																		'submit',
																		function(
																				event) {
																			if (form
																					.checkValidity() === false) {
																				event
																						.preventDefault();
																				event
																						.stopPropagation();
																			}
																			form.classList
																					.add('was-validated');
																		},
																		false);
													});
								}, false);
			})();
		</script>
	</div>
</body>
</html>