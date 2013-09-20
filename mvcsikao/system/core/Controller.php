<?php
class Controller{
	public function __construct(){
		//header('Content-type:text/html;charset=utf-8');
	}
	
	final protected function model($model){
		if(empty($model)){
			trigger_error('不能实现实例化空模型');
		}
		$model_name = $model.'Model';
		return new $model_name;
	}
	
	final protected function load($lib,$auto=TRUE){
		if(empty($lib)){
			trigger_error('加载类名不能为空');
		}elseif($auto===TRUE){
			return Application::$_lib[$lib];
		}elseif ($auto===FALSE){
			return Application::newLib($lib);
		}
	}
	
	final protected function config($config){
		return Application::$_config[$config];
	}
	 /** 
         * 加载模板文件 
         * @access      final   protect 
         * @param       string  $path   模板路径 
         * @param       array   $data   模板变量 
         * @return      string  模板字符串 
         */ 
        final protected function showTemplate($path,$data = array()){ 
                $template =  $this->load('template'); 
                $template->init($path,$data); 
                $template->outPut(); 
        } 
}