<?php
/*Êý¾Ý¿âÅäÖÃ*/
$CONFIG['system']['db']=array(
	'db_host'	=> 'localhost',
	'db_user'	=> 'root',
	'db_password'	=> '',
	'db_database'	=> 'app',
	'db_table_prefix'	=> 'app_',
	'db_charset'	=> 'utf8',
	'db_conn'	=> ''
				
);

$CONFIG['system']['lib']=array(
	'prefix'	=> 'my'
);

$CONFIG['system']['route'] = array(
	'default_controller'	=> 'home',
	'default_action'		=> 'index',
	'url_type'				=> 1
);

$CONFIG['system']['cache'] = array(
	'cache_dir'				=>'cache',
	'cache_prefix'			=>'cache_',
	'cache_time'			=>'1800',
	'cache_mode'			=>2,


);