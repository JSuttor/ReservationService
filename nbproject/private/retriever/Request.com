
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ReQuest Serious Play: Premium Multi-room Music and Movie Solutions</title>


<meta name="description" content="ReQuest: premium whole-house music and video entertainment systems for the home, office or yacht. Built-in integration with iTunes, multi-location sync, and anywhere streaming.">

<meta name="keywords" content="ReQuest, ReQuest Multimedia, Serious Play, iQ, Intelligent Music Server, Intelligent Multiroom Amplifier, Intelligent Media Client, IMS, IMA, IMC, NetSync for iTunes, NetSync, multi-location sync, touch screen, ARQLink, Freedom, AudioReQuest, VideoReQuest, ARQ, VRQ, music server, video server, digital video controller, digital video client, F.Series, N.Series, high-end audio, digital audio, multi-room audio, distributed audio, audio server, video server, whole house audio, whole house entertainment, online video, custom integration, CEDIA, custom installation, home theater, home automation, Crestron, AMX, mp3, m4a, WAV, Flac, Ogg Vorbis, dvd, CD, iPod, iPhone, iTunes, media center, media server, windows media center, entertainment, audiophile">

<link rel="stylesheet" type="text/css" href="/css/request_2013.css">
<link rel="stylesheet" type="text/css" href="/css/tooltipster.css">
<script src="/scripts/jquery-1.7.min.js"></script>
<!--<script src="/scripts/jquery-1.12.1.min.js"></script>-->
<script src="/scripts/jquery.tooltipster.min.js"></script>
<script src="/scripts/jquery.language.js"></script> 
<script src="/scripts/jquery.custom.js"></script> 
<script src="/includes/request.js"></script> 

<script type="text/javascript">
$(document).ready(function() {
/*		
	if( $(".tooltip").length > 0 ){
	$('.tooltip').tooltipster({
	   animation: 'fade',
	   delay: 200,
	   maxWidth: 200,
	   position: 'right',
	   trigger: 'hover'
	});
	}
	
	$('#polyglotLanguageSwitcher').polyglotLanguageSwitcher({
		effect: 'fade',
		testMode: true,
		onChange: function(evt){
			alert("The selected language is: "+evt.selectedItem);
		}
*/
//                ,afterLoad: function(evt){
//                    alert("The selected language has been loaded");
//                },
//                beforeOpen: function(evt){
//                    alert("before open");
//                },
//                afterOpen: function(evt){
//                    alert("after open");
//                },
//                beforeClose: function(evt){
//                    alert("before close");
//                },
//                afterClose: function(evt){
//                    alert("after close");
//                }
//			});
});
</script>


</head>

<body>
<div id="polyglotLanguageSwitcher" style="display:none;">
	<form action="#">
		<select id="polyglot-language-options">
			<option id="en" value="en" selected>English</option>
			<option id="fr" value="fr">French</option>
			<option id="de" value="de">German</option>
			<option id="it" value="it">Italian</option>
			<option id="es" value="es">Spanish</option>
		</select>
	</form>
</div>

<div class="TopBlackContainer">
  <div class="TopLogoContainer"><a href="/default.asp"><span>ReQuest Serious Play</span></a></div>
    <div class="TopNavContainer">
        <ul>
            <li><a href="/default.asp">HOME</a></li>
    	    <li><a href="/products/residential.asp">RESIDENTIAL</a></li>
            <li><a href="/products/yacht.asp">YACHT</a></li>
            <li><a href="/products/airplane.asp">AIRPLANE</a></li>
            <li><a href="/products/commercial.asp">COMMERCIAL A/V</a></li>
            <li><a href="/products/oem.asp">OEM</a></li>
            <li><a href="/support/default.asp">SUPPORT</a></li>
            <li><a href="/company.asp">ABOUT US</a></li>
            <li><a href="/sales/default.asp">SALES</a></li>
            <li><a href="/contact.asp">CONTACT</a></li>
			
            <li><a href="/login.asp">LOGIN</a>
			
    </ul>


    </div>

    
</div>
    
<style>
#promofloater {
	position: absolute;
	z-index: 200;
	top: 110px;
	width: 300px;
	text-align:left;
}

</style>    



<!-- *** Promo (eg specials/halloween.asp) *** -->
<!--<div id="promofloater"></div>-->
<!--#iinclude virtual="/specials/2016_stpatricks.asp"-->
<!-- **** -->

<!-- Large Image Cover  -->
<div id="rq_hero">
    <div class="frontblurb_container">
        <div class="frontblurb">
            <h2>WHY CHOOSE REQUEST?</h2>
            <p>At home, at sea, or in the air, ReQuest creates a seamless entertainment universe that follows you no matter where you go.</p>

			<p>ReQuest brings maximum quality music and video to your deluxe home system while intelligently streaming to your portable devices. Your media is never more than a click away.</p>
            <a href="upgrade.asp" class="button buttonbasic"><span>Do You Own A ReQuest?</span> <br><span style="font-size:18px; font-weight:bold;">Click here!</span></a>

		</div>

		
		
		
</div>
</div>

<!-- Positioned Cover Text -->    


<!-- Feature Blocks -->
<div class="frontblocksHead">
    <h2>ONLY REQUEST...</h2>
</div>
    
<!-- Feature Blocks 1 -->
<div class="frontblocks1 clearfix">
    <div class="frontblocks_container">
        <div class="frontblock">
            <img src="images/front/rq_home_icons_maestro.png">
            <p>...allows you to take your media everywhere with the ability to stream your music and movies directly to your Maestro equipped portable device.
</p>
        </div>
        <div class="frontblock">
            <img src="images/front/rq_home_icons_ade.png">
            <p>...automatically dual encodes your music to give you a full sized high quality music file for filling your home audio system with rich sound while also giving you a smaller file for portable devices and streaming.


</p>
        </div>
        <div class="frontblock">
            <img src="images/front/rq_home_icons_nsfit.png">
            <p>...synchronizes your music to multiple iTunes libraries with our one-of-a-kind NetSync for iTunes library sync application.

</p>
        </div>
        <div class="frontblock">
            <img src="images/front/rq_home_icons_sync.png">
            <p>...synchronizes your music to multiple homes so your vacation home and yacht can have the same great selection.
</p>
        </div>
    
    </div>
    
</div>
    
<!-- Feature Blocks 2 -->
<div class="frontblocks2 clearfix">
    <div class="frontblocks_container">
        <div class="frontblock">
            <img src="images/front/rq_home_icons_stream.png">
            <p>...delivers a whole universe of streaming entertainment blended seamlessly with your own meticulously managed media library.
</p>
        </div>
        <div class="frontblock">
            <img src="images/front/rq_home_icons_yacht.png">
            <p>...provides the same luxury entertainment experience on land, sea, and air with our Yacht and Airplane A/V solutions.
</p>
        </div>
        <div class="frontblock">
            <img src="images/front/rq_home_icons_lang.png">
            <p>...can be configured to communicate with you in an ever-expanding array of international languages.
</p>
        </div>
        <div class="frontblock">
            <img src="images/front/rq_home_icons_oem.png">
            <p>...brings the experience to the open marketplace with our new OEM options.
</p>
        </div>
    
    </div>
    
</div>    
    
    
   
<div id="footer">
<div id="footer_nav">
    <a href="/default.asp">HOME</a> 
	| <a href="/products/residential.asp">RESIDENTIAL</a> 
	| <a href="/products/yacht.asp">YACHT</a>
	| <a href="/products/airplane.asp">AIRPLANE</a>
	| <a href="/products/commercial.asp">COMMERCIAL A/V</a>
	| <a href="/products/oem.asp">OEM</a>
	| <a href="/support/default.asp">SUPPORT</a> 
	| <a href="/company.asp">COMPANY</a> 
	| <a href="/sales/default.asp">SALES</a> 
	| <a href="/contact.asp">CONTACT</a> 
	| <a href="/login.asp">LOGIN</a>
</div>
	
	<div id="footer_social">
		<a href="http://twitter.com/ReQuestSP" target="_blank"><img src="/images/navigation/footer_twitter.png" border="0"></a>&nbsp;
		<a href="http://facebook.com/ReQuestSP" target="_blank"><img src="/images/navigation/footer_facebook.png" border="0"></a>
	</div>
	
</div>




<script type="text/javascript">
var gaJsHost = (("https:" == document.location.protocol) ? "https://ssl." : "http://www.");
document.write(unescape("%3Cscript src='" + gaJsHost + "google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E"));
</script>
<script type="text/javascript">
try {
var pageTracker = _gat._getTracker("UA-534683-1");
pageTracker._trackPageview();
} catch(err) {}</script>
</body>
</html>
