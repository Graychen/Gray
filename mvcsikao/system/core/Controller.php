<?php
class Controller{
	public function __construct(){
		//header('Content-type:text/html;charset=utf-8');
	}
	
	final protected function model($model){
		if(empty($model)){
			trigger_error('����ʵ��ʵ������ģ��');
		}
		$model_name = $model.'Model';
		return new $model_name;
	}
	
	final protected function load($lib,$auto=TRUE){
		if(empty($lib)){
			trigger_error('������������Ϊ��');
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
         * ����ģ���ļ� 
         * @access      final   protect 
         * @param       string  $path   ģ��·�� 
         * @param       array   $data   ģ����� 
         * @return      string  ģ���ַ��� 
         */ 
        final protected function showTemplate($path,$data = array()){ 
                $template =  $this->load('template'); 
                $template->init($path,$data); 
                $template->outPut(); 
        } 
}