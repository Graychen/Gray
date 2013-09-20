
<?php
class DemoController
{
	function index()
	{
	$data['title']='First Title';
	$data['list']=array('A','B','C','D');
	require ('view/index.php');
	}
}