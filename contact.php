<?php


$text = "<span style='color:red; font-size: 35px; line-height: 40px; magin: 10px;'>Error! Please try again.</span>";

if(isset($_POST['submitcontact']))
{
	$name=$_POST['name'];
	$email=$_POST['email'];
	$message=$_POST['message'];

	$to = "youremail@gmail.com";
	$subject = "Zerotheme - Testing Contact Form";
	$message = " Name: " . $name ."\r\n Email: " . $email . "\r\n Message:\r\n" . $message;
	 
	$from = "Zerotheme";
	$headers = "From:" . $from . "\r\n";
	$headers .= "Content-type: text/plain; charset=UTF-8" . "\r\n"; 
	 
	if(@mail($to,$subject,$message,$headers))
	{
	  $text = "<span style='color:blue; font-size: 35px; line-height: 40px; margin: 10px;'>Your Message was sent successfully !</span>";
	}
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="description" content=" Free Responsive Html5 Templates" />
    
	<title>Foody</title>
	
	<!-- Bootstrap core CSS -->
	<link rel="stylesheet" href="css/bootstrap.min.css">
	
    <!-- Custom Fonts -->
	<link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        
    <!-- Custom styles for this template -->
    <link rel="stylesheet" type="text/css" href="css/style.css">
	<link rel="stylesheet" type="text/css" href="css/style-responsive">
    
	
	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="template/js/html5shiv.js"></script>
        <script src="template/js/respond.min.js"></script>
    <![endif]-->
	
</head>

<body>
   <!--header start-->
	<header class="header">
		<div class="sidebar-toggle-box">
			<div class="fa fa-bars tooltips" data-placement="right"></div>
		</div>
		<!--logo start-->
		<a href="#" class="logo"><b>FOODY</b></a>
		<!--logo end-->
		
		<div class="top-menu">
			<ul class="nav pull-right top-menu">
				<li><a class="social" href="#">Facebook</a></li>
			</ul>
		</div>
	</header>
	<!--header end-->
	<!--sidebar start-->
	<aside>
		<div id="sidebar"  class="nav-collapse ">
			<!-- sidebar menu start-->
			<ul class="sidebar-menu">
				<li>
					<a href="index.html">
						<i class="fa fa-tags"></i>
						<span>Home</span>
					</a>
				</li>
				<li>
					<a href="single.html">
						<i class="fa fa-tags"></i>
						<span>About</span>
					</a>
				</li>
				<li>
					<a href="archive.html">
						<i class="fa fa-tags"></i>
						<span>Blogs</span>
					</a>
				</li>
				<li>
					<a href="contact.html">
						<i class="fa fa-tags"></i>
						<span>Contact</span>
					</a>
				</li>
			</ul>
		  <!-- sidebar menu end-->
		</div>
	</aside>
    <!--sidebar end-->
	
	
	
	<!--main content start-->
	<section id="main-content">
		<section class="wrapper">
			<div class="post-thumbnail" style="">
				<img width="100%" height="auto"src="images/10.jpg">
			</div>
			<div class="site-inner">
				<div class="site-content">
					<article>
						<div class="art-content">
							<div class="heading text-center">
								<h2>Contact Us</h2>
								<hr class="line">
							</div>
							<div class="col-md-12">
							<h3>Contact Info</h3>
							<span>SED UT PERSPICIATIS UNDE OMNIS ISTE NATUS ERROR SIT VOLUPTATEM ACCUSANTIUM DOLOREMQUE LAUDANTIUM, TOTAM REM APERIAM.</span><br> <br>
							<p>JL.Kemacetan timur no.23. block.Q3<br>
								Jakarta-Indonesia</p>
							   <p>+6221 888 888 90 <br>
								+6221 888 88891</p>
							<p>info@yourdomain.com</p>
						</div>
						<div class="col-md-12">
							<h3>Contact Form</h3>
							<!--Warning-->
							<center><?php echo $text;?></center>
							<!---->
							<form id="ff" name="form1" method="post" action="contact.php">
								<div class="row">
									<div class="col-md-6">
										<div class="form-group">
										<input type="text" class="form-control input-lg" name="name" id="name" placeholder="Enter name" required="required" />
										</div>
									</div>
									<div class="col-md-6">
										<div class="form-group">
											<input type="email" class="form-control input-lg" name="email" id="email" placeholder="Enter email" required="required" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12">
										<div class="form-group">
											<input type="text" class="form-control input-lg" name="subject" id="subject" placeholder="Subject" required="required" />
										</div>
									</div>
								</div>
								<div class="row">
									<div class="col-md-12 text-center">
										<div class="form-group">
											<textarea name="message" id="message" class="form-control" rows="4" cols="25" required="required"
											placeholder="Message"></textarea>
										</div>						
										<button type="submit" class="btn btn-send btn-lg page-scroll" name="submitcontact" id="submitcontact">Submit</button>
									</div>
								</div>
							</form>
						</div>
						</div>
					</article>
				</div>
			</div>
		</section><!-- /wrapper -->
	</section><!-- /MAIN CONTENT -->
	
	<!--main content end-->
	<!--footer start-->
	<footer class="site-footer">
	  <div class="text-center">
		  20xx @ Foody - Designed by Zerotheme - More Templates <a href="http://www.cssmoban.com/" target="_blank" title="模板之家">模板之家</a> - Collect from <a href="http://www.cssmoban.com/" title="网页模板" target="_blank">网页模板</a>
	  </div>
	</footer>
	<!--footer end-->
	
	
	<!-- js placed at the end of the document so the pages load faster -->
    <script src="js/jquery.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/jquery.nicescroll.js"></script>
	
    <!--common script for all pages-->
    <script src="js/common-scripts.js"></script>

</body>
</html>